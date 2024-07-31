package com.example.biservice.service.fourwheeler.battery;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.fourwheeler.FourVBatteryDto;

import java.util.List;

public interface FourVBatteryService {
    
    FourVBatteryDto createBattery(FourVBatteryDto fourVBatteryDto);

    PageResponse<FourVBatteryDto> findAllBatteriesByModelAndFuelType(Integer modelId, Integer fuelTypeId, Integer pageNumber, Integer pageSize, String sortBy);

    List<FourVBatteryDto> findAllBatteries(String sortBy);

    FourVBatteryDto findBatteryById(Integer batteryId);

    FourVBatteryDto updateBattery(Integer batteryId, FourVBatteryDto fourVBatteryDto);

    void deleteBattery(Integer batteryId);
}
