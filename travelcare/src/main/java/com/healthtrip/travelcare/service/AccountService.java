package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.Exception.CustomException;
import com.healthtrip.travelcare.common.Sender;
import com.healthtrip.travelcare.config.security.jwt.JwtProvider;
import com.healthtrip.travelcare.domain.entity.account.*;
import com.healthtrip.travelcare.domain.entity.location.Address;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.request.MailRequest;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import com.healthtrip.travelcare.repository.vo.AccountTimeTokenVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    @Value("${current.ipAddress}")
    private String ipAddress;

    private final Sender sender;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private final CountryRepository countryRepository;
    private final AccountsRepository accountsRepository;
    private final AccountAgentRepository accountAgentRepository;
    private final AccountCommonRepository accountCommonRepository;
    private final AccountTimeTokenRepository accountTimeTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findByEmail(username);
        if (account == null){
            log.info("account null");
            throw new UsernameNotFoundException("username can not found input: "+ username);
        }
        account.addAuthorities(account.getUserRole());
        return account;
    }

    @Transactional
    public ResponseEntity<AccountResponse> login(AccountRequest.SignInDto signInDto,AuthenticationManager authenticationManager) {

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInDto.getEmail(),signInDto.getPassword()
                    )
            );

            Account account = (Account) authentication.getPrincipal();
            AccountResponse accountResponse = this.getAccountInfoWithTokens(account);

            return ResponseEntity.ok().body(accountResponse);
        } catch (RuntimeException e) {
            log.info("msg: {} cause: {}",e.getMessage(),e.getCause());
            return ResponseEntity.status(401).body(null);
        }
    }

    @Transactional
    public ResponseEntity createCommon(AccountRequest.CommonSignUp commonSignUp) {
        String email = commonSignUp.getEmail();
        boolean emailPresent = accountsRepository.existsByEmail(email);
        if(emailPresent) {
            //아이디 존재
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 중복");
        }else {
            var addressRequest = commonSignUp.getAddressData();
            var personData = commonSignUp.getPersonDataRequest();

            Address address = Address.toEntityBasic(addressRequest);
            address.setCountry(countryRepository.getById(addressRequest.getCountryId()));

            Account account = Account.builder()
                    .email(email)
                    .password(passwordEncoder.encode(commonSignUp.getPassword()))
                    .userRole(Account.UserRole.ROLE_COMMON)
                    .status(Account.Status.Y)
                    .build();
            AccountCommon newAccount = AccountCommon.toEntityBasic(personData);
            newAccount.setRelation(address,account);
            accountCommonRepository.save(newAccount);
            // front read only : 가입시 토큰 재확인

            return ResponseEntity.ok("가입 완료");
        }
        // 회원가입시 먼저 이메일 유효성을 체크한다면, sendAccountConfirmMail X status -> Y
    }

    @Transactional
    public ResponseEntity createAgent(AccountRequest.AgentSignUp agentSignUp) {
        boolean emailPresent = accountsRepository.existsByEmail(agentSignUp.getEmail());
        if(emailPresent) {
            //아이디 존재
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 중복");
        }else {
            Account account = Account.builder()
                    .email(agentSignUp.getEmail())
                    .password(passwordEncoder.encode(agentSignUp.getPassword()))
                    .userRole(Account.UserRole.ROLE_AGENT)
                    .status(Account.Status.Y)
                    .build();
            AccountAgent accountAgent = AccountAgent.toEntityBasic(agentSignUp);
            accountAgent.setRelation(account);
            accountAgentRepository.save(accountAgent);
            return ResponseEntity.ok("가입 완료");
        }
    }


    public AccountResponse.EmailCheck emailCheck(String email) {
        // 이메일 중복체크 중복시 true
        boolean emailExist = accountsRepository.existsByEmail(email);

        if (emailExist){
            // 중복시 response true 보내기
            return AccountResponse.EmailCheck.builder()
                    .id(-1L)
                    .emailExist(true)
                    .build();
        }else {
            // 중복이 아니면 유효코드를 메일로 보내고
            // 유효코드를 찾을 수 있는 ID 값을 Response
            String subject = "Welcome to Travel-Heath-Care-Service (Sign Up Confirmation)";
            String content = "Please enter this code on sign-up page : ";
            return this.sendAccountConfirmMail(email,subject,content);
        }

    }
    public AccountResponse.EmailCheck sendAccountConfirmMail(String email,String subject,String content) {
        // issueToken and get value object
        AccountTimeTokenVO accountTimeTokenVO = issueTimeToken(email);
        String contentWithToken = content + accountTimeTokenVO.getAuthCode();

        // send mail
        try {
            var mail = MailRequest.builder()
                    .to(email)
                    .subject(subject)
                    .content(contentWithToken)
                    .build();
            sender.naverSender(mail);
        }catch(RuntimeException e) {
            log.info("발송 실패 msg: {}",e.getMessage());
        }

        // return id,expiration,emailExist
        return AccountResponse.EmailCheck.builder()
                .id(accountTimeTokenVO.getId())
                .expiration(accountTimeTokenVO.getExpirationDate())
                .emailExist(false)
                .build();
    }
    @Transactional// rollback for
    private AccountTimeTokenVO issueTimeToken(String email) {
        var token = AccountTimeToken.makeToken(email);
        var savedToken = accountTimeTokenRepository.save(token);
        return new AccountTimeTokenVO(savedToken.getId(), savedToken.getAuthToken(), savedToken.getExpirationDate());
    }
    @Transactional
    public boolean confirmAccount(Long tokenId, String authToken) {
        return timeTokenCheck(tokenId, authToken);
    }
    @Transactional
    public boolean timeTokenCheck(Long tokenId, String authToken) {
        var accountTimeToken = accountTimeTokenRepository.findById(tokenId)
                .orElseThrow(() -> new CustomException("토큰 못찾음",HttpStatus.BAD_REQUEST));
        // 만료일 비교 없으면 엑셉션
        boolean isTimePassed = LocalDateTime.now().isAfter(accountTimeToken.getExpirationDate());
        boolean authCodeEquals = accountTimeToken.getAuthToken().equals(authToken);
        if (!isTimePassed && authCodeEquals){
            // 현재 시각이 만료일을 지나지 않았고 인증 코드가 맞다면 true
            return true;
        }else {
            return false;
        }
    }

    // 패스워드 변경 요청 *중요 필수 변경 - 협의해서*
    public AccountResponse.EmailCheck sendPasswordResetMail(String email) {
        String subject = "<Travel-Heath-Care-Service> Reset password request";
        String content = "Please enter this code on reset-password-page : ";
        if (accountsRepository.existsByEmail(email)){
            var response = sendAccountConfirmMail(email,subject,content);
            response.setEmailExist(true);
            return response;
        }else {
            return AccountResponse.EmailCheck.builder()
                    .emailExist(false)
                    .build();
        }
        // 리셋 메일 생성 후 보내기
    }
    @Transactional
    public boolean passwordReset(AccountRequest.PasswordReset dto) {
        // 메일 받으면
        if(timeTokenCheck(dto.getTokenId(), dto.getAuthToken())){
            var account =accountsRepository.findByEmail(dto.getEmail());
            // 비밀번호 암호화로직
            account.resetPasswordAs(passwordEncoder.encode(dto.getPassword()));
            // 비밀번호 재설정
            accountsRepository.save(account);
            return true;
        }else {
            // 토큰 인증 실패
            return false;
        }

    }
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public AccountResponse getAccountInfoWithTokens(Account account) {
        String token = jwtProvider.issueAccessToken(account);
        RefreshToken refreshTokenEntity = jwtProvider.issueRefreshToken(account);
        // 객체 저장
        refreshTokenRepository.save(refreshTokenEntity);
        // refresh 토큰 만료 기간이 필요하다면 issueRefreshToken에서 refreshTokenExpirationAt 추가
        AccountResponse accountResponse = AccountResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .status(account.getStatus())
                .userRole(account.getUserRole())
                .jwt(token)
                .refreshTokenId(refreshTokenEntity.getId())
                .refreshToken(refreshTokenEntity.getRefreshToken())
                .build();
        return accountResponse;
    }

    @Transactional
    public ResponseEntity<?> newAccessToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        // 1. validate
        boolean valid = jwtProvider.validateJwtToken(token);
        if(valid){
            // 2. get claim
            Claims claims = jwtProvider.getClaims(token);
            String email = claims.getSubject();
            Integer userId = (Integer) claims.get("userId");
            // 3. find with claim
            RefreshToken refreshToken = refreshTokenRepository.findByIdAndAccountId(request.getTokenId(),userId.longValue());
            if(refreshToken == null){
                return ResponseEntity.status(401).body("PLS LOGIN AGAIN");
            }
            // 4. compare data
            boolean tokenEquals = request.getRefreshToken().equals(refreshToken.getRefreshToken());
        // 5. 성공-> 새토큰, 실패->401
            if (tokenEquals){
                String newToken = jwtProvider.issueAccessToken(Account.builder().id(userId.longValue()).email(email).build());
                return ResponseEntity
                        .status(200)
                        .header(HttpHeaders.AUTHORIZATION,newToken)
                        .body("Access confirm.");
            }else {
                return ResponseEntity.status(401).body("NOT YOUR TOKEN");
            }

        }

        return null;
    }
}
