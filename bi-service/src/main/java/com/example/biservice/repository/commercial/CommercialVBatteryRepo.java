package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialVBatteryRepo extends ProductRepository<CommercialVBattery> {

    @Query("SELECT b FROM CommercialVBattery b JOIN b.commercialVModels m WHERE m.modelId = :modelId")
    Page<CommercialVBattery> findBatteriesByModel(@Param("modelId") Integer modelId, Pageable pageable);
}
