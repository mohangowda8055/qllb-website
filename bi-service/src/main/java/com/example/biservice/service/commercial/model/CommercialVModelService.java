package com.example.biservice.service.commercial.model;

import com.example.biservice.payload.commercial.CommercialVModelDto;

import java.util.List;

public interface CommercialVModelService {

    CommercialVModelDto createModel(CommercialVModelDto commercialVModelDto, Integer brandId, Integer segmentId);

    List<CommercialVModelDto> findAllModelsByBrandAndSegment(Integer brandId, Integer segmentId);

    List<CommercialVModelDto> findAllModels();

    CommercialVModelDto findModelById(Integer modelId);

    CommercialVModelDto updateModel(Integer modelId, CommercialVModelDto commercialVModelDto);

    void deleteModel(Integer modelId);

    void addBatteryToModel(Integer modelId, Integer batteryId);
}
