package com.example.biservice.service.twowheeler.model;

import com.example.biservice.payload.twowheeler.TwoVModelDto;

import java.util.List;

public interface TwoVModelService {

    TwoVModelDto createModel(TwoVModelDto twoVModelDto, Integer brandId);

    List<TwoVModelDto> findAllModelsByBrand(Integer brandId);


    List<TwoVModelDto> findAllModels();

    TwoVModelDto findModelById(Integer modelId);

    TwoVModelDto updateModel(Integer modelId, TwoVModelDto twoVModelDto);

    void deleteModel(Integer modelId);

    void addBatteryToModel(Integer modelId, Integer batteryId);
}
