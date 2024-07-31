package com.example.biservice.service.threewheeler.battery;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.threewheeler.ThreeVBatteryDto;

import java.util.List;

public interface ThreeVBatteryService {

    ThreeVBatteryDto createBattery(ThreeVBatteryDto threeVBatteryDto);

    PageResponse<ThreeVBatteryDto> findAllBatteriesByModelAndFuel(Integer modelId, Integer fuelTypeId, Integer pageNumber, Integer pageSize, String sortBy);

    List<ThreeVBatteryDto> findAllBatteries(String sortBy);

    ThreeVBatteryDto findBatteryById(Integer batteryId);

    ThreeVBatteryDto updateBattery(Integer batteryId, ThreeVBatteryDto threeVBatteryDto);

    void deleteBattery(Integer batteryId);
}
