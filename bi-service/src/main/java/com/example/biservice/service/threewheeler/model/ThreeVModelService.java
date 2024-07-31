package com.example.biservice.service.threewheeler.model;

import com.example.biservice.payload.threewheeler.ThreeVModelDto;

import java.util.List;

public interface ThreeVModelService {

    ThreeVModelDto createModel(ThreeVModelDto threeVModelDto, Integer brandId);

    List<ThreeVModelDto> findAllModelsByBrand(Integer modelId);

    List<ThreeVModelDto> findAllModels();

    ThreeVModelDto findModelById(Integer modelId);

    ThreeVModelDto updateModel(Integer modelId, ThreeVModelDto threeVModelDto);

    void deleteModel(Integer modelId);

    void addFuelTypeToModel(Integer modelId, Integer fuelTypeId);

    void addBatteryToModel(Integer modelId, Integer fuelTypeId, Integer batteryId);
}
