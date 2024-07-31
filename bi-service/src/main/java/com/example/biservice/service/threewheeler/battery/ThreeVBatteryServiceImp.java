package com.example.biservice.service.threewheeler.battery;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.threewheeler.ThreeVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.threewheeler.ThreeVBatteryDto;
import com.example.biservice.repository.threewheeler.ThreeVBatteryRepo;
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
public class ThreeVBatteryServiceImp implements ThreeVBatteryService{

    private final ModelMapper modelMapper;
    private final ThreeVBatteryRepo threeVBatteryRepo;


    @Override
    public ThreeVBatteryDto createBattery(ThreeVBatteryDto threeVBatteryDto) {
        ThreeVBattery threeVBattery = this.modelMapper.map(threeVBatteryDto, ThreeVBattery.class);
        threeVBattery.setProductType(ProductType.THREEVBATTERY);
        ThreeVBattery savedThreeVBattery;
        try {
            savedThreeVBattery = this.threeVBatteryRepo.save(threeVBattery);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the three wheeler battery - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedThreeVBattery, ThreeVBatteryDto.class);
    }

    @Override
    public PageResponse<ThreeVBatteryDto> findAllBatteriesByModelAndFuel(Integer modelId, Integer fuelTypeId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<ThreeVBattery> pageBatteries = this.threeVBatteryRepo.findBatteriesByModelAndFuel(modelId,fuelTypeId,pageable);
        List<ThreeVBattery> threeVBatteries = pageBatteries.getContent();
        List<ThreeVBatteryDto> threeVBatteryDtos = threeVBatteries.stream().map((battery)->this.modelMapper.map(battery, ThreeVBatteryDto.class)).collect(Collectors.toList());
        return PageResponse.<ThreeVBatteryDto>builder()
                .data(threeVBatteryDtos)
                .pageNumber(pageBatteries.getNumber())
                .pageSize(pageBatteries.getSize())
                .totalElements(pageBatteries.getTotalElements())
                .totalPages(pageBatteries.getTotalPages())
                .build();
    }

    @Override
    public List<ThreeVBatteryDto> findAllBatteries(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<ThreeVBattery> threeVBatteries = this.threeVBatteryRepo.findAll(sort);
        return threeVBatteries.stream().map((battery)->this.modelMapper.map(battery, ThreeVBatteryDto.class)).collect(Collectors.toList());
    }

    @Override
    public ThreeVBatteryDto findBatteryById(Integer batteryId) {
        ThreeVBattery threeVBattery = this.threeVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Three wheeler battery not found with id "+batteryId));
        return this.modelMapper.map(threeVBattery, ThreeVBatteryDto.class);
    }

    @Override
    public ThreeVBatteryDto updateBattery(Integer batteryId, ThreeVBatteryDto threeVBatteryDto) {
        Optional<ThreeVBattery> threeVBatteryOptional = this.threeVBatteryRepo.findById(batteryId);
        if(threeVBatteryOptional.isPresent()) {
            ThreeVBattery threeVBattery = this.modelMapper.map(threeVBatteryDto, ThreeVBattery.class);
            threeVBattery.setThreeVModels(threeVBatteryOptional.get().getThreeVModels());
            threeVBattery.setFuelTypes(threeVBatteryOptional.get().getFuelTypes());
            threeVBattery.setProductType(threeVBatteryOptional.get().getProductType());
            ThreeVBattery updatedBattery;
            try {
                updatedBattery = this.threeVBatteryRepo.save(threeVBattery);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the three wheeler battery - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBattery, ThreeVBatteryDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update three wheeler battery - no existing three wheeler battery found with id "+batteryId);
        }
    }

    @Override
    public void deleteBattery(Integer batteryId) {
        ThreeVBattery threeVBattery = this.threeVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot delete three wheeler battery - no existing three wheeler battery found with id "+batteryId));
        this.threeVBatteryRepo.delete(threeVBattery);
    }
}
