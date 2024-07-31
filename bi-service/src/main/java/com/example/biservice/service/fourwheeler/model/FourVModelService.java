package com.example.biservice.service.fourwheeler.model;

import com.example.biservice.payload.fourwheeler.FourVModelDto;

import java.util.List;

public interface FourVModelService {
    FourVModelDto createModel(FourVModelDto fourVModelDto, Integer brandId);

    List<FourVModelDto> findAllModelsByBrand(Integer brandId);

    List<FourVModelDto> findAllModels();

    FourVModelDto findModelById(Integer modelId);

    FourVModelDto updateModel(Integer modelId, FourVModelDto fourVModelDto);

    void deleteModel(Integer modelId);

    void addFuelTypeToModel(Integer modelId, Integer fuelTypeId);

    void addBatteryToModel(Integer modelId, Integer fuelTypeId, Integer batteryId);
}
