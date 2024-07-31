package com.example.biservice.repository.threewheeler;

import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThreeVModelRepo extends JpaRepository<ThreeVModel, Integer> {

    List<ThreeVModel> findAllByThreeVBrandOrderByModelName(ThreeVBrand threeVBrand);

    @Modifying
    @Transactional
    @Query(value="insert into three_v_model_fuel_battery(model_id, fuel_type_id, product_id) values (:modelId, :fuelTypeId, :batteryId)", nativeQuery = true)
    void addBatteryToModel(@Param("modelId") Integer modelId, @Param("fuelTypeId") Integer fuelTypeId, @Param("batteryId") Integer batteryId);
}
