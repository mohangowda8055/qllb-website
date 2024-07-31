package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoVBatteryRepo extends ProductRepository<TwoVBattery> {

    @Query("SELECT b FROM TwoVBattery b JOIN b.twoVModels m WHERE m.modelId = :modelId")
    Page<TwoVBattery> findBatteriesByModel(@Param("modelId") Integer modelId, Pageable pageable);
}
