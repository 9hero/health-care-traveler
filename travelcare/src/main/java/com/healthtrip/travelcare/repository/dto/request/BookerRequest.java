package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.reservation.Booker;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookerRequest {

    private PersonData personData;
    private boolean tourReserved;
    private boolean hospitalReserved;
    private String simpleAddress;

    public Booker toEntity() {
        return Booker.builder()
                .birth(this.personData.getBirth())
                .firstName(this.personData.getFirstName())
                .lastName(this.personData.getLastName())
                .gender(this.personData.getGender())
                .phone(this.personData.getPhone())
                .emergencyContact(this.personData.getEmergencyContact())
                .tourReserved(this.tourReserved)
                .hospitalReserved(this.hospitalReserved)
                .simpleAddress(this.simpleAddress)
                .build();
    }
}
