package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.repository.dto.response.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountsRepository accountsRepository;


    @Transactional
    public ResponseEntity login(AccountRequestDto accountRequestDto) {

        String email =  accountRequestDto.getEmail();
        // email 있는지 없는지 체크
        Boolean emailCheck = accountsRepository.existsByEmail(email);
        if (emailCheck){ //True
            Account account = accountsRepository.findByEmail(email);
            Boolean passCheck = accountRequestDto.getPassword().equals(account.getPassword());
            AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                    .password(account.getPassword())
                    .email(account.getEmail())
                    .status(account.getStatus())
                    .userRole(account.getUserRole()).build();


            ResponseEntity response;
            if (passCheck){
                return response = ResponseEntity.status(200).body(accountResponseDto);
            }else{
                return response = ResponseEntity.status(HttpStatus.valueOf(401)).body("비밀번호 오류");
            }

        }else{
            return ResponseEntity.status(401).body("아이디 오류");

        }
        //return
    }

    @Transactional
    public ResponseEntity create(AccountRequestDto accountRequestDto) {
        Boolean emailCheck = accountsRepository.existsByEmail(accountRequestDto.getEmail());
        if(emailCheck) {
            Account account = Account.builder()
                    .email(accountRequestDto.getEmail())
                    .password(accountRequestDto.getPassword())
                    .userRole(Account.UserRole.ROLE_COMMON)
                    .status(Account.Status.N)
                    .build();
            Account newAccount = accountsRepository.save(account);

            return ResponseEntity.created(URI.create("/")).build();
        }else {
            return ResponseEntity.status(401).body("이메일 중복");
        }
    }
}
