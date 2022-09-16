package com.healthtrip.travelcare.repository.tour;

import com.healthtrip.travelcare.entity.account.Address;
import com.healthtrip.travelcare.entity.tour.reservation.TourBookerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourBookerAddressRepository extends JpaRepository<TourBookerAddress,Long> {

}
