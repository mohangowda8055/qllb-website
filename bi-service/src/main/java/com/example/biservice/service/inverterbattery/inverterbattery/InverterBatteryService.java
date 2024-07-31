package com.example.biservice.service.inverterbattery.inverterbattery;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryDto;

import java.util.List;

public interface InverterBatteryService {

    InverterBatteryDto createInverterBattery(InverterBatteryDto inverterBatteryDto);

    PageResponse<InverterBatteryDto> findAllInverterBatteriesByBackupDurationAndWarranty(Integer backupDurationId, Integer warrantyId, Integer pageNumber, Integer pageSize, String sortBy);

    List<InverterBatteryDto> findAllInverterBatteries(String sortBy);

    InverterBatteryDto findInverterBatteryById(Integer batteryId);

    InverterBatteryDto updateInverterBattery(Integer batteryId, InverterBatteryDto inverterBatteryDto);

    void deleteInverterBattery(Integer batteryId);

    void addBackupDurationWithWarrantyToBattery(Integer batteryId, Integer backupDurationId, Integer warrantyId);
}
