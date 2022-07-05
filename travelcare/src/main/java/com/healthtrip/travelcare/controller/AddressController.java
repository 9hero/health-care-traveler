package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.AddressResponse;
import com.healthtrip.travelcare.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address")
@Tag(name = "주소 API")
public class AddressController {


    private final AddressService addressService;

    @Operation(summary = "주소ID로 주소찾기 (임시)")
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId) {
        return addressService.getAddressById(addressId);
    }
}
