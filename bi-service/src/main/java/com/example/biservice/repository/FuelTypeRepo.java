package com.example.biservice.repository;

import com.example.biservice.entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelTypeRepo extends JpaRepository<FuelType, Integer> {

    @Query("SELECT f FROM FuelType f JOIN f.fourVModels m WHERE m.modelId = :modelId")
    List<FuelType> findFuelTypesByFourVModel(@Param("modelId") Integer modelId);

    @Query("SELECT f FROM FuelType f JOIN f.threeVModels m WHERE m.modelId = :modelId")
    List<FuelType> findFuelTypesByThreeVModel(@Param("modelId") Integer modelId);
}
