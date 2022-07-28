package com.healthtrip.travelcare.repository.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AccountTimeTokenVO {

    private Long id;
    private String authCode;
    private LocalDateTime expirationDate;
}
