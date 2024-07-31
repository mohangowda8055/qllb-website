package com.example.biservice.service.fourwheeler.model;

import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.entity.fourwheeler.FourVModel;
import com.example.biservice.entity.FuelType;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.fourwheeler.FourVModelDto;
import com.example.biservice.repository.fourwheeler.FourVBatteryRepo;
import com.example.biservice.repository.fourwheeler.FourVBrandRepo;
import com.example.biservice.repository.fourwheeler.FourVModelRepo;
import com.example.biservice.repository.FuelTypeRepo;
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
public class FourVModelServiceImp implements FourVModelService{

    private final ModelMapper modelMapper;

    private final FourVModelRepo fourVModelRepo;

    private final FourVBrandRepo fourVBrandRepo;

    private final FuelTypeRepo fuelTypeRepo;

    private final FourVBatteryRepo fourVBatteryRepo;


    @Override
    public FourVModelDto createModel(FourVModelDto fourVModelDto, Integer brandId) {
        FourVBrand fourVBrand = this.fourVBrandRepo.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Cannot insert four wheeler model - four wheeler brand not found with id " + brandId));
        FourVModel fourVModel = this.modelMapper.map(fourVModelDto, FourVModel.class);
        fourVModel.setFourVBrand(fourVBrand);
        FourVModel savedFourVModel;
        try {
            savedFourVModel = this.fourVModelRepo.save(fourVModel);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the four wheeler model - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedFourVModel, FourVModelDto.class);
    }

    @Override
    public List<FourVModelDto> findAllModelsByBrand(Integer brandId) {
        FourVBrand fourVBrand = this.fourVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot find four wheeler models - no existing brand found with id "+brandId));
        List<FourVModel> fourVModels = this.fourVModelRepo.findAllByFourVBrandOrderByModelName(fourVBrand);
        return fourVModels.stream().map((model)->this.modelMapper.map(model, FourVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<FourVModelDto> findAllModels() {
        Sort sort = Sort.by("modelName");
        List<FourVModel> fourVModels = this.fourVModelRepo.findAll(sort);
        return fourVModels.stream().map((model)->this.modelMapper.map(model, FourVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public FourVModelDto findModelById(Integer modelId) {
        FourVModel fourVModel = this.fourVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Four wheeler model not found with id "+modelId));
        return this.modelMapper.map(fourVModel, FourVModelDto.class);
    }

    @Override
    public FourVModelDto updateModel(Integer modelId, FourVModelDto fourVModelDto) {
        Optional<FourVModel> fourVModelOptional = this.fourVModelRepo.findById(modelId);
        if(fourVModelOptional.isPresent()) {
            FourVModel fourVModel = this.modelMapper.map(fourVModelDto, FourVModel.class);
            fourVModel.setFourVBrand(fourVModelOptional.get().getFourVBrand());
            fourVModel.setFourVBatteries(fourVModelOptional.get().getFourVBatteries());
            fourVModel.setFuelTypes(fourVModelOptional.get().getFuelTypes());
            FourVModel updatedModel;
            try {
                updatedModel = this.fourVModelRepo.save(fourVModel);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the four wheeler model - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedModel, FourVModelDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update four wheeler model - no existing four wheeler model found with id "+modelId);
        }
    }

    @Override
    public void deleteModel(Integer modelId) {
        FourVModel fourVModel = this.fourVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot delete four wheeler model - no existing four wheeler model found with id "+modelId));
        this.fourVModelRepo.delete(fourVModel);
    }

    @Override
    public void addFuelTypeToModel(Integer modelId, Integer fuelTypeId) {
        FourVModel fourVModel = this.fourVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to four wheeler model - no existing four wheeler model found with id "+modelId));
        FuelType fuelType = this.fuelTypeRepo.findById(fuelTypeId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to four wheeler model - no existing fuel type found with id "+fuelTypeId));
        if(fourVModel.getFuelTypes() == null){
            fourVModel.setFuelTypes(new ArrayList<>());
        }
        fourVModel.getFuelTypes().add(fuelType);
        try {
            fourVModelRepo.save(fourVModel);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the four wheeler model and fuel - Duplicate Entry detected");
        }
    }

    @Override
    public void addBatteryToModel(Integer modelId, Integer fuelTypeId, Integer batteryId){
      this.fourVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to four wheeler model - no existing four wheeler model found with id "+modelId));
      this.fuelTypeRepo.findById(fuelTypeId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to four wheeler model - no existing fuel type found with id "+fuelTypeId));
      this.fourVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to four wheeler model - no existing four wheeler battery found with id "+batteryId));
      try {
          this.fourVModelRepo.addBatteryToModel(modelId, fuelTypeId, batteryId);
      }catch (DataIntegrityViolationException e){
          throw new UniqueConstraintException("An error occurred while mapping the four wheeler model, fuel and battery - Duplicate Entry detected");
      }
    }
}
