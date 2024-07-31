package com.example.biservice.repository.fourwheeler;

import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.entity.fourwheeler.FourVModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FourVModelRepo extends JpaRepository<FourVModel, Integer> {

    List<FourVModel> findAllByFourVBrandOrderByModelName(FourVBrand fourVBrand);

//    @Modifying
//    @Transactional
//    @Query(value="insert into four_v_model_fuel(model_id, fuel_type_id) values (:modelId, :fuelTypeId)", nativeQuery = true)
//    void addFuelTypeToModel(@Param("modelId") Integer modelId, @Param("fuelTypeId") Integer fuelTypeId);

    @Modifying
    @Transactional
    @Query(value="insert into four_v_model_fuel_battery(model_id, fuel_type_id, product_id) values (:modelId, :fuelTypeId, :batteryId)", nativeQuery = true)
    void addBatteryToModel(@Param("modelId") Integer modelId, @Param("fuelTypeId") Integer fuelTypeId, @Param("batteryId") Integer batteryId);
}
