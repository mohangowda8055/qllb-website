package com.example.biservice.service.commercial.battery;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.commercial.CommercialVBatteryDto;

import java.util.List;

public interface CommercialVBatteryService {

    CommercialVBatteryDto createBattery(CommercialVBatteryDto commercialVBatteryDto);

    PageResponse<CommercialVBatteryDto> findAllBatteriesByModel(Integer modelId, Integer pageNumber, Integer pageSize, String sortBy);

    List<CommercialVBatteryDto> findAllBatteries(String sortBy);

    CommercialVBatteryDto findBatteryById(Integer batteryId);

    CommercialVBatteryDto updateBattery(Integer batteryId, CommercialVBatteryDto commercialVBatteryDto);

    void deleteBattery(Integer batteryId);
}
