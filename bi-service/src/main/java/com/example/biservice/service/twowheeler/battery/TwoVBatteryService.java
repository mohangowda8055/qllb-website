package com.example.biservice.service.twowheeler.battery;

import com.example.biservice.payload.twowheeler.TwoVBatteryDto;
import com.example.biservice.payload.PageResponse;

import java.util.List;

public interface TwoVBatteryService {

    TwoVBatteryDto createBattery(TwoVBatteryDto twoVBatteryDto);

    PageResponse<TwoVBatteryDto> findAllBatteriesByModel(Integer modelId, Integer pageNumber, Integer pageSize, String sortBy);

    List<TwoVBatteryDto> findAllBatteries(String sortBy);

    TwoVBatteryDto findBatteryById(Integer batteryId);

    TwoVBatteryDto updateBattery(Integer batteryId, TwoVBatteryDto twoVBatteryDto);

    void deleteBattery(Integer batteryId);

}
