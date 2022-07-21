package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.Sender;
import com.healthtrip.travelcare.config.security.jwt.JwtProvider;
import com.healthtrip.travelcare.domain.entity.*;
import com.healthtrip.travelcare.repository.*;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.request.MailRequest;
import com.healthtrip.travelcare.repository.dto.request.RefreshTokenRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
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
    public ResponseEntity createCommon(AccountRequest.commonSignUp commonSignUp) {
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
                    .status(Account.Status.N)
                    .build();
            AccountCommon newAccount = AccountCommon.toEntityBasic(personData);
            newAccount.setRelation(address,account);
            accountCommonRepository.save(newAccount);
            // 토큰 만들어서 메일 보내기
            sendAccountConfirmMail(email);
            return ResponseEntity.ok("가입 완료");
        }
        // 회원가입시 먼저 이메일 유효성을 체크한다면, sendAccountConfirmMail X status -> Y
    }

    @Transactional
    public ResponseEntity createAgent(AccountRequest.agentSignUp agentSignUp) {
        boolean emailPresent = accountsRepository.existsByEmail(agentSignUp.getEmail());
        if(emailPresent) {
            //아이디 존재
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 중복");
        }else {
            Account account = Account.builder()
                    .email(agentSignUp.getEmail())
                    .password(passwordEncoder.encode(agentSignUp.getPassword()))
                    .userRole(Account.UserRole.ROLE_AGENT)
                    .status(Account.Status.N)
                    .build();
            AccountAgent accountAgent = AccountAgent.toEntityBasic(agentSignUp);
            accountAgent.setRelation(account);
            accountAgentRepository.save(accountAgent);
            sendAccountConfirmMail(agentSignUp.getEmail());
            return ResponseEntity.ok("가입 완료");
        }
    }


    public boolean emailCheck(String email) {
        this.sendAccountConfirmMail(email);
        return accountsRepository.existsByEmail(email);
    }
    public void sendAccountConfirmMail(String email) {
        String url = "confirm";
        String subject = "Welcome to Travel-Heath-Care-Service (Sign Up Confirmation)";
//        String content = "Please confirm to sign up : "+ipAddress+"/api/account/"+url+"?email="+email;
        String content = "Please confirm to sign up : "+"http://localhost:3000/account/confirm"+"?email="+email;
        issueTimeToken(email,subject,content);
    }
    @Transactional
    public boolean confirmAccount(String email, String authToken) {
        boolean check = timeTokenCheck(email, authToken);
        if (check){
        accountsRepository.findByEmail(email).accountConfirm();
        // db data 절약
        accountTimeTokenRepository.deleteByEmail(email);
        return true;
        }else {
            return false;
        }
    }

    // 패스워드 변경 요청 *중요 필수 변경 - 협의해서*
    public void sendPasswordResetMail(String email) {
        String url = "reset-password";
        String subject = "Travel-Heath-Care-Service reset password";
        String content = "Please click the link to reset your password : "+ipAddress+"/api/account/"+url+"?email="+email;

        // 리셋 메일 생성 후 보내기
        issueTimeToken(email,subject,content); // 프론트에서 받아줘야함
    }
    @Transactional
    public boolean passwordReset(AccountRequest.PasswordReset dto) {
        // 메일 받으면
        if(timeTokenCheck(dto.getEmail(), dto.getAuthToken())){
            //
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
    @Transactional
    public boolean timeTokenCheck(String email, String authToken) {
        try {
            var accountTimeToken = accountTimeTokenRepository.findByEmailAndAuthToken(email, authToken)
                    .orElseThrow(() -> new Exception("토큰 못찾음"));
            // 만료일 비교 없으면 엑셉션
            if (LocalDateTime.now().isAfter(accountTimeToken.getExpirationDate())){
                // 현재 시각이 만료일을 지났음
                accountTimeTokenRepository.deleteByEmail(email);
                throw new Exception("만료일 지남");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // 예외 걸린경우 1. 프론트에서 처리 2. 여기서 재발급
            // return 값 어캐줄까
            return false;
        }
    }
    @Transactional// rollback for
    private void issueTimeToken(String email,String subject,String content) {
        var token = AccountTimeToken.makeToken(email);
        var savedToken = accountTimeTokenRepository.save(token);
        String contentWithToken = content+"&authToken="+savedToken.getAuthToken()+ "  \n Or if you have not sent this request, ignore this mail.";
        try {
            var mail = MailRequest.builder()
                    .to(email)
                    .subject(subject)
                    // 프론트 만들어지면 프론트 페이지로 바꾸고 거기서 useEffect로 보내기, 결과 받아서 예외처리
                    .content(contentWithToken)
                    .build();
            sender.naverSender(mail);
        }catch(RuntimeException e) {
            log.info("발송 실패 msg: {}",e.getMessage());
        }
    }

    @Transactional
    public boolean reConfirm(String email) {
        try {
            if(accountsRepository.findByEmail(email).getStatus() == Account.Status.N) {
                sendAccountConfirmMail(email);
            return true;
            }else {// 아이디 이미 인증된거임 or B인데 재인증 요청 보냄
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("이메일 찾을수 없음"+e.getMessage());
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
