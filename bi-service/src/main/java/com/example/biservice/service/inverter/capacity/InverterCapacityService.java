package com.example.biservice.service.inverter.capacity;

import com.example.biservice.payload.inverter.InverterCapacityDto;

import java.util.List;

public interface InverterCapacityService {

    InverterCapacityDto createCapacity(InverterCapacityDto inverterCapacityDto);

    List<InverterCapacityDto> findAllCapacities();

    InverterCapacityDto findCapacityById(Integer capacityId);

    InverterCapacityDto updateCapacity(Integer capacityId, InverterCapacityDto inverterCapacityDto);

    void deleteCapacity(Integer capacityId);
}
