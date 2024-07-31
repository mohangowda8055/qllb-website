package com.example.biservice.service.fueltype;

import com.example.biservice.payload.FuelTypeDto;

import java.util.List;

public interface FuelTypeService {

    List<FuelTypeDto> findAllFuelTypesByFourVModel(Integer modelId);

    List<FuelTypeDto> findAllFuelTypesByThreeVModel(Integer modelId);

    List<FuelTypeDto> findAllFuelTypes();

    FuelTypeDto findFuelById(Integer fuelTypeId);
}
