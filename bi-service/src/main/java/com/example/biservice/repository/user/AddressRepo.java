package com.example.biservice.repository.user;

import com.example.biservice.entity.user.Address;
import com.example.biservice.entity.user.AddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, AddressId> {
    List<Address> findByIdUserId(Long userId);

    Optional<Address> findByIdAddressTypeAndIdUserId(String addressType, Long userId);
}
