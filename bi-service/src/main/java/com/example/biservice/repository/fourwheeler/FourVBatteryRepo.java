package com.example.biservice.repository.fourwheeler;

import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FourVBatteryRepo extends ProductRepository<FourVBattery> {
    @Query("SELECT b FROM FourVBattery b JOIN b.fourVModels m JOIN b.fuelTypes f WHERE m.modelId = :modelId AND f.fuelTypeId = :fuelTypeId")
    Page<FourVBattery> findBatteriesByModelAndFuel(@Param("modelId") Integer modelId, @Param("fuelTypeId") Integer fuelTypeId, Pageable pageable);
}
