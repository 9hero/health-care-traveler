package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.AccountAgent;
import com.healthtrip.travelcare.domain.entity.AccountCommon;
import com.healthtrip.travelcare.domain.entity.Address;
import com.healthtrip.travelcare.repository.AccountAgentRepository;
import com.healthtrip.travelcare.repository.AccountCommonRepository;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.CountryRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CountryRepository countryRepository;
    private final AccountsRepository accountsRepository;
    private final AccountAgentRepository accountAgentRepository;
    private final AccountCommonRepository accountCommonRepository;

    @Transactional
    public ResponseEntity<AccountResponse> login(AccountRequest.SignInDto signInDto) {

        String email = signInDto.getEmail();

        // email 있는지 없는지 체크
        Boolean emailCheck = accountsRepository.existsByEmail(email);
        if (emailCheck){ //True
            Account account = accountsRepository.findByEmail(email);

            Boolean passCheck = signInDto.getPassword().equals(account.getPassword());

            if (passCheck){
                // jwt로 대체 예정
                AccountResponse accountResponse = AccountResponse.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .status(account.getStatus())
                        .userRole(account.getUserRole()).build();

                return ResponseEntity.status(200).body(accountResponse);
            }else{
                // 비밀번호오류
                return ResponseEntity.status(401).body(null);
            }
        }else{
            // 아이디 오류
            return ResponseEntity.status(401).body(null);
        }
        //return
    }

    @Transactional
    public ResponseEntity createCommon(AccountRequest.commonSignUp commonSignUp) {
        boolean emailPresent = accountsRepository.existsByEmail(commonSignUp.getEmail());
        if(emailPresent) {
            //아이디 존재
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 중복");
        }else {
            var addressRequest = commonSignUp.getAddressData();
            var personData = commonSignUp.getPersonDataRequest();

            Address address = Address.toEntityBasic(addressRequest);
            address.setCountry(countryRepository.getById(addressRequest.getCountryId()));

            Account account = Account.builder()
                    .email(commonSignUp.getEmail())
                    .password(commonSignUp.getPassword())
                    .userRole(Account.UserRole.ROLE_COMMON)
                    .status(Account.Status.Y)
                    .build();
            AccountCommon newAccount = AccountCommon.toEntityBasic(personData);
            newAccount.setRelation(address,account);
            accountCommonRepository.save(newAccount);

            return ResponseEntity.ok("가입 완료");
        }
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
                    .password(agentSignUp.getPassword())
                    .userRole(Account.UserRole.ROLE_AGENT)
                    .status(Account.Status.Y)
                    .build();
            AccountAgent accountAgent = AccountAgent.toEntityBasic(agentSignUp);
            accountAgent.setRelation(account);
            accountAgentRepository.save(accountAgent);

            return ResponseEntity.ok("가입 완료");
        }
    }

//    private final Sender sender;
//    @Transactional
//    public void mailTest(MailRequest mailRequest) {
//        sender.send(mailRequest);
//    }

    public boolean emailCheck(String email) {
        return accountsRepository.existsByEmail(email);
    }
}
