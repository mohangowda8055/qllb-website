package com.example.biservice.repository.threewheeler;

import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreeVBatteryRepo extends ProductRepository<ThreeVBattery> {
    @Query("SELECT b FROM ThreeVBattery b JOIN b.threeVModels m JOIN b.fuelTypes f WHERE m.modelId = :modelId AND f.fuelTypeId = :fuelTypeId")
    Page<ThreeVBattery> findBatteriesByModelAndFuel(@Param("modelId") Integer modeId, @Param("fuelTypeId") Integer fuelTypeId, Pageable pageable);
}
