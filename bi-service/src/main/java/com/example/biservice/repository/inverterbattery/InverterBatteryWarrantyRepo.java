package com.example.biservice.repository.inverterbattery;

import com.example.biservice.entity.inverterbattery.InverterBatteryWarranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InverterBatteryWarrantyRepo extends JpaRepository<InverterBatteryWarranty, Integer> {
}
