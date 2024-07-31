package com.example.biservice.service.twowheeler.model;

import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.entity.twowheeler.TwoVModel;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.twowheeler.TwoVModelDto;
import com.example.biservice.repository.twowheeler.TwoVBatteryRepo;
import com.example.biservice.repository.twowheeler.TwoVBrandRepo;
import com.example.biservice.repository.twowheeler.TwoVModelRepo;
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
public class TwoVModelServiceImp implements TwoVModelService{

    private final ModelMapper modelMapper;

    private final TwoVModelRepo twoVModelRepo;

    private final TwoVBrandRepo twoVBrandRepo;

    private final TwoVBatteryRepo twoVBatteryRepo;


    @Override
    public TwoVModelDto createModel(TwoVModelDto twoVModelDto, Integer brandId) {
        TwoVBrand twoVBrand = this.twoVBrandRepo.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Cannot insert two wheeler model - two wheeler brand not found with id " + brandId));
        TwoVModel twoVModel = this.modelMapper.map(twoVModelDto, TwoVModel.class);
        twoVModel.setTwoVBrand(twoVBrand);
        TwoVModel savedTwoVModel;
        try {
            savedTwoVModel = this.twoVModelRepo.save(twoVModel);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the two wheeler model - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedTwoVModel, TwoVModelDto.class);
    }

    @Override
    public List<TwoVModelDto> findAllModelsByBrand(Integer brandId) {
        TwoVBrand twoVBrand = this.twoVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot find two wheeler models - no existing brand found with id "+brandId));
        List<TwoVModel> twoVModels = this.twoVModelRepo.findByTwoVBrandOrderByModelName(twoVBrand);
        return twoVModels.stream().map(twoVModel -> this.modelMapper.map(twoVModel, TwoVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TwoVModelDto> findAllModels() {
        Sort sort = Sort.by("modelName");
        List<TwoVModel> twoVModels = this.twoVModelRepo.findAll(sort);
        return twoVModels.stream().map(twoVModel -> this.modelMapper.map(twoVModel, TwoVModelDto.class)).collect(Collectors.toList());
    }


    @Override
    public TwoVModelDto findModelById(Integer modelId) {
       TwoVModel twoVModel = this.twoVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Two wheeler model not found with id "+modelId));
        return this.modelMapper.map(twoVModel, TwoVModelDto.class);
    }

    @Override
    public TwoVModelDto updateModel(Integer modelId, TwoVModelDto twoVModelDto) {
        Optional<TwoVModel> twoVModelOptional = this.twoVModelRepo.findById(modelId);
        if(twoVModelOptional.isPresent()) {
            TwoVModel twoVModel = this.modelMapper.map(twoVModelDto, TwoVModel.class);
            twoVModel.setTwoVBrand(twoVModelOptional.get().getTwoVBrand());
            twoVModel.setTwoVBatteries(twoVModelOptional.get().getTwoVBatteries());
            TwoVModel updatedModel;
            try {
                updatedModel = this.twoVModelRepo.save(twoVModel);
            }catch (DataIntegrityViolationException e){
                throw new UniqueConstraintException("An error occurred while updating the two wheeler model - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedModel, TwoVModelDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update two wheeler model - no existing two wheeler model found with id "+modelId);
        }
    }

    @Override
    public void deleteModel(Integer modelId) {
        TwoVModel twoVModel = this.twoVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot delete two wheeler model - no existing two wheeler model found with id "+modelId));
        this.twoVModelRepo.delete(twoVModel);
    }

    @Override
    public void addBatteryToModel(Integer modelId, Integer batteryId) {
        TwoVModel twoVModel = this.twoVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to two wheeler model - no existing two wheeler model found with id "+modelId));
        TwoVBattery twoVBattery = this.twoVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to two wheeler model - no existing two wheeler battery found with id "+batteryId));
        if(twoVModel.getTwoVBatteries()==null){
            twoVModel.setTwoVBatteries(new ArrayList<>());
        }
        twoVModel.getTwoVBatteries().add(twoVBattery);
        try {
            this.twoVModelRepo.save(twoVModel);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the two wheeler model and battery - Duplicate Entry detected");
        }
    }
}
