package com.example.biservice.service.inverterbattery.inverterbatterywarranty;

import com.example.biservice.payload.inverterbattery.InverterBatteryWarrantyDto;

import java.util.List;

public interface InverterBatteryWarrantyService {
    InverterBatteryWarrantyDto createInverterBatteryWarranty(InverterBatteryWarrantyDto inverterBatteryWarrantyDto);

    List<InverterBatteryWarrantyDto> findAllInverterBatteryWarranties();

    InverterBatteryWarrantyDto findInverterBatteryWarrantyById(Integer warrantyId);

    InverterBatteryWarrantyDto updateInverterBatteryWarranty(Integer warrantyId, InverterBatteryWarrantyDto inverterBatteryWarrantyDto);

    void deleteInverterBatteryWarranty(Integer warrantyId);
}
