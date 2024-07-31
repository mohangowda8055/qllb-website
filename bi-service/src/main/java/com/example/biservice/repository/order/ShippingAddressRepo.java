package com.example.biservice.repository.order;

import com.example.biservice.entity.order.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingAddressRepo extends JpaRepository<ShippingAddress, Long> {
}
