package com.example.biservice.repository.inverter;

import com.example.biservice.entity.inverter.InverterCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InverterCapacityRepo extends JpaRepository<InverterCapacity, Integer> {
}
