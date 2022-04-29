package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.dto.request.AccountRequestDto;
import com.healthtrip.travelcare.repository.dto.response.AccountResponseDto;
import lombok.RequiredArgsConstructor;
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

            if (passCheck){
                // jwt로 대체 예정
                AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .status(account.getStatus())
                        .userRole(account.getUserRole()).build();

                return ResponseEntity.status(200).body(accountResponseDto);
            }else{
                return ResponseEntity.status(401).body("비밀번호 오류");
            }

        }else{
            return ResponseEntity.status(401).body("아이디 오류");

        }
        //return
    }

    @Transactional
    public ResponseEntity create(AccountRequestDto accountRequestDto) {
        Boolean emailCheck = accountsRepository.existsByEmail(accountRequestDto.getEmail());
        if(!emailCheck) {
            Account newAccount = accountRequestDto.toEntity();
            accountsRepository.save(newAccount);
            return ResponseEntity.created(URI.create("/")).build();
        }else {
            return ResponseEntity.status(401).body("이메일 중복");
        }
    }
}
