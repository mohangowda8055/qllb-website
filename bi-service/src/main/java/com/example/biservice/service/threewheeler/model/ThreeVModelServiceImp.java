package com.example.biservice.service.threewheeler.model;

import com.example.biservice.entity.FuelType;
import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.entity.threewheeler.ThreeVModel;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.threewheeler.ThreeVModelDto;
import com.example.biservice.repository.FuelTypeRepo;
import com.example.biservice.repository.threewheeler.ThreeVBatteryRepo;
import com.example.biservice.repository.threewheeler.ThreeVBrandRepo;
import com.example.biservice.repository.threewheeler.ThreeVModelRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreeVModelServiceImp implements ThreeVModelService{

    private final ModelMapper modelMapper;

    private final ThreeVModelRepo threeVModelRepo;

    private final ThreeVBrandRepo threeVBrandRepo;

    private final FuelTypeRepo fuelTypeRepo;

    private final ThreeVBatteryRepo threeVBatteryRepo;


    @Override
    public ThreeVModelDto createModel(ThreeVModelDto threeVModelDto, Integer brandId) {
        ThreeVBrand threeVBrand = this.threeVBrandRepo.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Cannot insert three wheeler model - three wheeler brand not found with id " + brandId));
        ThreeVModel threeVModel = this.modelMapper.map(threeVModelDto, ThreeVModel.class);
        threeVModel.setThreeVBrand(threeVBrand);
        ThreeVModel savedThreeVModel;
        try {
            savedThreeVModel = this.threeVModelRepo.save(threeVModel);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the three wheeler model - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedThreeVModel, ThreeVModelDto.class);
    }

    @Override
    public List<ThreeVModelDto> findAllModelsByBrand(Integer brandId) {
        ThreeVBrand threeVBrand = this.threeVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot find three wheeler models - no existing brand with id "+brandId));
        List<ThreeVModel> threeVModels = this.threeVModelRepo.findAllByThreeVBrandOrderByModelName(threeVBrand);
        return threeVModels.stream().map((model)->this.modelMapper.map(model, ThreeVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ThreeVModelDto> findAllModels() {
        Sort sort = Sort.by("modelName");
        List<ThreeVModel> threeVModels = this.threeVModelRepo.findAll(sort);
        return threeVModels.stream().map((model)->this.modelMapper.map(model, ThreeVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public ThreeVModelDto findModelById(Integer modelId) {
        ThreeVModel threeVModel = this.threeVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Three wheeler model not found with id "+modelId));
        return this.modelMapper.map(threeVModel, ThreeVModelDto.class);
    }

    @Override
    public ThreeVModelDto updateModel(Integer modelId, ThreeVModelDto threeVModelDto) {
        Optional<ThreeVModel> threeVModelOptional = this.threeVModelRepo.findById(modelId);
        if(threeVModelOptional.isPresent()) {
            ThreeVModel threeVModel = this.modelMapper.map(threeVModelDto, ThreeVModel.class);
            threeVModel.setThreeVBrand(threeVModelOptional.get().getThreeVBrand());
            threeVModel.setFuelTypes(threeVModelOptional.get().getFuelTypes());
            threeVModel.setThreeVBatteries(threeVModelOptional.get().getThreeVBatteries());
            ThreeVModel updatedModel;
            try {
                updatedModel = this.threeVModelRepo.save(threeVModel);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the three wheeler model - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedModel, ThreeVModelDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update three wheeler model - no existing three wheeler model found with id "+modelId);
        }
    }

    @Override
    public void deleteModel(Integer modelId) {
        ThreeVModel threeVModel = this.threeVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot delete three wheeler model - no existing three wheeler model found with id "+modelId));
        this.threeVModelRepo.delete(threeVModel);
    }

    @Override
    public void addFuelTypeToModel(Integer modelId, Integer fuelTypeId) {
        ThreeVModel threeVModel = this.threeVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to three wheeler model - no existing three wheeler model found with id "+modelId));
        FuelType fuelType = this.fuelTypeRepo.findById(fuelTypeId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to three wheeler model - no existing fuel type found with id "+fuelTypeId));
        if(threeVModel.getFuelTypes()==null){
            threeVModel.setFuelTypes(new ArrayList<>());
        }
        threeVModel.getFuelTypes().add(fuelType);
        try {
            threeVModelRepo.save(threeVModel);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the three wheeler model and fuel - Duplicate Entry detected");
        }
    }


    @Override
    public void addBatteryToModel(Integer modelId, Integer fuelTypeId, Integer batteryId) {
        this.threeVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to three wheeler model - no existing three wheeler model found with id "+modelId));
        this.fuelTypeRepo.findById(fuelTypeId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to three wheeler model - no existing fuel type found with id "+fuelTypeId));
        this.threeVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to three wheeler model - no existing three wheeler battery found with id "+batteryId));
        try {
            this.threeVModelRepo.addBatteryToModel(modelId, fuelTypeId, batteryId);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the three wheeler model, fuel and battery - Duplicate Entry detected");
        }
    }
}
