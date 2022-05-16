package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequest;
import com.healthtrip.travelcare.repository.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountsRepository accountsRepository;

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
    public ResponseEntity create(AccountRequest.SignUpDto signUpDto) {
        boolean emailPresent = accountsRepository.existsByEmail(signUpDto.getEmail());
        if(emailPresent) {
            //아이디 존재
            return ResponseEntity.status(401).body("이메일 중복");
        }else {
            Account newAccount = Account.builder()
                    .email(signUpDto.getEmail())
                    .password(signUpDto.getPassword())
                    .userRole(Account.UserRole.ROLE_COMMON) // 임시로 일반
//                    .status(Account.Status.N) 임시로 모두 Y
                    .status(Account.Status.Y)
                    .build();
            accountsRepository.save(newAccount);

            return ResponseEntity.ok("가입 완료");
        }
    }
}
