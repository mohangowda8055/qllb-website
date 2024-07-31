package com.example.biservice.service.fueltype;

import com.example.biservice.entity.FuelType;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.FuelTypeDto;
import com.example.biservice.repository.fourwheeler.FourVModelRepo;
import com.example.biservice.repository.FuelTypeRepo;
import com.example.biservice.repository.threewheeler.ThreeVModelRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuelTypeServiceImp implements FuelTypeService{

    private final ModelMapper modelMapper;

    private final FuelTypeRepo fuelTypeRepo;

    private final FourVModelRepo fourVModelRepo;

    private final ThreeVModelRepo threeVModelRepo;


    @Override
    public List<FuelTypeDto> findAllFuelTypesByFourVModel(Integer modelId) {
        this.fourVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot find fuel types - no model found with id "+modelId));
        List<FuelType> fuelTypes = this.fuelTypeRepo.findFuelTypesByFourVModel(modelId);
        return fuelTypes.stream().map((fuelType)->this.modelMapper.map(fuelType, FuelTypeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FuelTypeDto> findAllFuelTypesByThreeVModel(Integer modelId) {
        this.threeVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot find fuel types - no model found with id "+modelId));
        List<FuelType> fuelTypes = this.fuelTypeRepo.findFuelTypesByThreeVModel(modelId);
        return fuelTypes.stream().map((fuelType)->this.modelMapper.map(fuelType, FuelTypeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FuelTypeDto> findAllFuelTypes() {
        List<FuelType> fuelTypes = this.fuelTypeRepo.findAll();
        return fuelTypes.stream().map((fuelType)->this.modelMapper.map(fuelType, FuelTypeDto.class)).collect(Collectors.toList());
    }

    @Override
    public FuelTypeDto findFuelById(Integer fuelTypeId) {
        FuelType fuelType = this.fuelTypeRepo.findById(fuelTypeId).orElseThrow(()->new ResourceNotFoundException("Fuel type not found with id "+fuelTypeId));
        return this.modelMapper.map(fuelType, FuelTypeDto.class);
    }
}
