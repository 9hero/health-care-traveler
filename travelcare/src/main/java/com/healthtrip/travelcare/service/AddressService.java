package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.location.AddressRepository;
import com.healthtrip.travelcare.repository.dto.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<AddressResponse> getAddressById(Long addressId) {
        var a = addressRepository.findById(addressId).orElseThrow();
        AddressResponse responseBody = AddressResponse.builder()
                .address1(a.getAddress())
                .address2(a.getAddressDetail())
                .cityName(a.getCity())
                .district(a.getDistrict())
                .postalCode(a.getPostalCode())
                .countryName(a.getCountry().getName()) // 나중에 바꿔야함
                .build();
        return ResponseEntity.ok(responseBody);
    }
}
