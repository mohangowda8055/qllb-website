package com.example.biservice.service.inverter.inverter;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverter.InverterDto;

import java.util.List;

public interface InverterService {

    InverterDto createInverter(InverterDto inverterDto, Integer capacityId);

    PageResponse<InverterDto> findAllInvertersByInverterCapacity(Integer capacityId, Integer pageNumber, Integer pageSize, String sortBy);

    List<InverterDto> findAllInverters(String sortBy);

    InverterDto findInverterById(Integer inverterId);

    InverterDto updateInverter(Integer inverterId, InverterDto inverterDto);

    void deleteInverter(Integer inverterId);

}
