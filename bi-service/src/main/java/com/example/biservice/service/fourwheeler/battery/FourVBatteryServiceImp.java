package com.example.biservice.service.fourwheeler.battery;

import com.example.biservice.entity.ProductType;
import com.example.biservice.entity.fourwheeler.FourVBattery;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.fourwheeler.FourVBatteryDto;
import com.example.biservice.repository.fourwheeler.FourVBatteryRepo;
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
public class FourVBatteryServiceImp implements FourVBatteryService{

    private final ModelMapper modelMapper;
    private final FourVBatteryRepo fourVBatteryRepo;


    @Override
    public FourVBatteryDto createBattery(FourVBatteryDto fourVBatteryDto) {
        FourVBattery fourVBattery = this.modelMapper.map(fourVBatteryDto, FourVBattery.class);
        fourVBattery.setProductType(ProductType.FOURVBATTERY);
        FourVBattery savedFourVBattery;
        try {
            savedFourVBattery = this.fourVBatteryRepo.save(fourVBattery);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the four wheeler battery - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedFourVBattery, FourVBatteryDto.class);
    }

    @Override
    public PageResponse<FourVBatteryDto> findAllBatteriesByModelAndFuelType(Integer modelId, Integer fuelTypeId, Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<FourVBattery> pageBatteries = this.fourVBatteryRepo.findBatteriesByModelAndFuel(modelId,fuelTypeId,pageable);
        List<FourVBattery> fourVBatteries = pageBatteries.getContent();
        List<FourVBatteryDto> fourVBatteryDtos = fourVBatteries.stream().map((battery)->this.modelMapper.map(battery, FourVBatteryDto.class)).collect(Collectors.toList());
        return PageResponse.<FourVBatteryDto>builder()
                .data(fourVBatteryDtos)
                .pageNumber(pageBatteries.getNumber())
                .pageSize(pageBatteries.getSize())
                .totalElements(pageBatteries.getTotalElements())
                .totalPages(pageBatteries.getTotalPages())
                .build();
    }

    @Override
    public List<FourVBatteryDto> findAllBatteries(String sortBy) {
        Sort sort = Sort.by(sortBy);
        List<FourVBattery> fourVBatteries = this.fourVBatteryRepo.findAll(sort);
        return fourVBatteries.stream().map((battery)->this.modelMapper.map(battery, FourVBatteryDto.class)).collect(Collectors.toList());
    }

    @Override
    public FourVBatteryDto findBatteryById(Integer batteryId) {
        FourVBattery fourVBattery = this.fourVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Four wheeler battery not found with id "+batteryId));
        return this.modelMapper.map(fourVBattery, FourVBatteryDto.class);
    }

    @Override
    public FourVBatteryDto updateBattery(Integer batteryId, FourVBatteryDto fourVBatteryDto) {
        Optional<FourVBattery> fourVBatteryOptional = this.fourVBatteryRepo.findById(batteryId);
        if(fourVBatteryOptional.isPresent()) {
            FourVBattery fourVBattery = this.modelMapper.map(fourVBatteryDto, FourVBattery.class);
            fourVBattery.setFourVModels(fourVBatteryOptional.get().getFourVModels());
            fourVBattery.setFuelTypes(fourVBatteryOptional.get().getFuelTypes());
            fourVBattery.setProductType(fourVBatteryOptional.get().getProductType());
            FourVBattery updatedBattery;
            try {
                updatedBattery = this.fourVBatteryRepo.save(fourVBattery);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the four wheeler battery - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBattery, FourVBatteryDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update four wheeler battery - no existing four wheeler battery found with id "+batteryId);
        }
    }

    @Override
    public void deleteBattery(Integer batteryId) {
        FourVBattery fourVBattery = this.fourVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot delete four wheeler battery - no existing four wheeler battery found with id "+batteryId));
        this.fourVBatteryRepo.delete(fourVBattery);
    }
}
