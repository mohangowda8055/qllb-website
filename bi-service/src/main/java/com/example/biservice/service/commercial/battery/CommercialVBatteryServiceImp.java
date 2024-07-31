package com.example.biservice.service.commercial.battery;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.commercial.CommercialVBatteryDto;
import com.example.biservice.repository.commercial.CommercialVBatteryRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommercialVBatteryServiceImp implements CommercialVBatteryService{

    private final ModelMapper modelMapper;
    private final CommercialVBatteryRepo commercialVBatteryRepo;


    @Override
    public CommercialVBatteryDto createBattery(CommercialVBatteryDto commercialVBatteryDto) {
        CommercialVBattery commercialVBattery = this.modelMapper.map(commercialVBatteryDto, CommercialVBattery.class);
        commercialVBattery.setProductType(ProductType.COMMERCIALVBATTERY);
        CommercialVBattery savedCommercialVBattery;
        try {
            savedCommercialVBattery = this.commercialVBatteryRepo.save(commercialVBattery);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the commercial vehicle battery - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedCommercialVBattery, CommercialVBatteryDto.class);
    }

    @Override
    public PageResponse<CommercialVBatteryDto> findAllBatteriesByModel(Integer modelId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<CommercialVBattery> pageBatteries = this.commercialVBatteryRepo.findBatteriesByModel(modelId,pageable);
        List<CommercialVBattery> commercialVBatteries = pageBatteries.getContent();
        List<CommercialVBatteryDto> commercialVBatteryDtos = commercialVBatteries.stream().map((battery)->this.modelMapper.map(battery, CommercialVBatteryDto.class)).collect(Collectors.toList());
        return PageResponse.<CommercialVBatteryDto>builder()
                .data(commercialVBatteryDtos)
                .pageNumber(pageBatteries.getNumber())
                .pageSize(pageBatteries.getSize())
                .totalElements(pageBatteries.getTotalElements())
                .totalPages(pageBatteries.getTotalPages())
                .lastPage(pageBatteries.isLast())
                .build();
    }

    @Override
    public List<CommercialVBatteryDto> findAllBatteries(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<CommercialVBattery> commercialVBatteries = this.commercialVBatteryRepo.findAll(sort);
        return commercialVBatteries.stream().map((battery)->this.modelMapper.map(battery, CommercialVBatteryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommercialVBatteryDto findBatteryById(Integer batteryId) {
        CommercialVBattery commercialVBattery = this.commercialVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Commercial vehicle battery not found with id "+batteryId));
        return this.modelMapper.map(commercialVBattery, CommercialVBatteryDto.class);
    }

    @Override
    public CommercialVBatteryDto updateBattery(Integer batteryId, CommercialVBatteryDto commercialVBatteryDto) {
        Optional<CommercialVBattery> commercialVBatteryOptional = this.commercialVBatteryRepo.findById(batteryId);
        if(commercialVBatteryOptional.isPresent()) {
            CommercialVBattery commercialVBattery = this.modelMapper.map(commercialVBatteryDto, CommercialVBattery.class);
            commercialVBattery.setCommercialVModels(commercialVBatteryOptional.get().getCommercialVModels());
            commercialVBattery.setProductType(commercialVBatteryOptional.get().getProductType());
            CommercialVBattery updatedBattery;
            try {
                updatedBattery = this.commercialVBatteryRepo.save(commercialVBattery);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the commercial vehicle battery - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBattery, CommercialVBatteryDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update commercial vehicle battery - no existing commercial vehicle battery found with id "+batteryId);
        }
    }

    @Override
    public void deleteBattery(Integer batteryId) {
        CommercialVBattery commercialVBattery = this.commercialVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot delete commercial vehicle battery - no existing commercial vehicle battery found with id "+batteryId));
        this.commercialVBatteryRepo.delete(commercialVBattery);
    }
}
