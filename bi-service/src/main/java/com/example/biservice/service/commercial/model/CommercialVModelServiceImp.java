package com.example.biservice.service.commercial.model;

import com.example.biservice.entity.commercial.CommercialVBattery;
import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVModel;
import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVModelDto;
import com.example.biservice.repository.commercial.CommercialVBatteryRepo;
import com.example.biservice.repository.commercial.CommercialVBrandRepo;
import com.example.biservice.repository.commercial.CommercialVModelRepo;
import com.example.biservice.repository.commercial.CommercialVSegmentRepo;
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
public class CommercialVModelServiceImp implements CommercialVModelService{

    private final ModelMapper modelMapper;

    private final CommercialVBrandRepo commercialVBrandRepo;

    private final CommercialVModelRepo commercialVModelRepo;

    private final CommercialVSegmentRepo commercialVSegmentRepo;

    private final CommercialVBatteryRepo commercialVBatteryRepo;


    @Override
    public CommercialVModelDto createModel(CommercialVModelDto commercialVModelDto, Integer brandId, Integer segmentId) {
        CommercialVBrand commercialVBrand = this.commercialVBrandRepo.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Cannot insert commercial vehicle brand - commercial vehicle brand not found with id " + brandId));
        CommercialVSegment commercialVSegment = this.commercialVSegmentRepo.findById(segmentId).orElseThrow(() -> new ResourceNotFoundException("Cannot insert commercial vehicle model - commercial vehicle segment not found with id " + segmentId));
        CommercialVModel commercialVModel = this.modelMapper.map(commercialVModelDto, CommercialVModel.class);
        commercialVModel.setCommercialVBrand(commercialVBrand);
        commercialVModel.setCommercialVSegment(commercialVSegment);
        CommercialVModel savedCommercialVModel;
        try {
            savedCommercialVModel = this.commercialVModelRepo.save(commercialVModel);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the commercial vehicle model - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedCommercialVModel, CommercialVModelDto.class);
    }

    @Override
    public List<CommercialVModelDto> findAllModelsByBrandAndSegment(Integer brandId, Integer segmentId) {
        CommercialVBrand commercialVBrand = this.commercialVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot find commercial vehicle models - no existing brand found with id "+brandId));
        CommercialVSegment commercialVSegment = this.commercialVSegmentRepo.findById(segmentId).orElseThrow(()->new ResourceNotFoundException("Cannot find commercial vehicle models - no existing segment found with id "+segmentId));
        List<CommercialVModel> commercialVModels = this.commercialVModelRepo.findAllByCommercialVBrandAndCommercialVSegmentOrderByModelName(commercialVBrand, commercialVSegment);
        return commercialVModels.stream().map((model)->this.modelMapper.map(model, CommercialVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommercialVModelDto> findAllModels() {
        Sort sort = Sort.by("modelName");
        List<CommercialVModel> commercialVModels = this.commercialVModelRepo.findAll(sort);
        return commercialVModels.stream().map((model)->this.modelMapper.map(model, CommercialVModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommercialVModelDto findModelById(Integer modelId) {
        CommercialVModel commercialVModel = this.commercialVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Commercial vehicle model not found with id "+modelId));
        return this.modelMapper.map(commercialVModel, CommercialVModelDto.class);
    }

    @Override
    public CommercialVModelDto updateModel(Integer modelId, CommercialVModelDto commercialVModelDto) {
        Optional<CommercialVModel> commercialVModelOptional = this.commercialVModelRepo.findById(modelId);
        if(commercialVModelOptional.isPresent()) {
            CommercialVModel commercialVModel = this.modelMapper.map(commercialVModelDto, CommercialVModel.class);
            commercialVModel.setCommercialVBrand(commercialVModelOptional.get().getCommercialVBrand());
            commercialVModel.setCommercialVSegment(commercialVModelOptional.get().getCommercialVSegment());
            commercialVModel.setCommercialVBatteries(commercialVModelOptional.get().getCommercialVBatteries());
            CommercialVModel updatedModel;
            try {
                updatedModel = this.commercialVModelRepo.save(commercialVModel);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the commercial vehicle model - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedModel, CommercialVModelDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update commercial vehicle model - no existing commercial vehicle model found with id "+modelId);
        }
    }

    @Override
    public void deleteModel(Integer modelId) {
        CommercialVModel commercialVModel = this.commercialVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot delete commercial vehicle model - no existing commercial vehicle model found with id "+modelId));
        this.commercialVModelRepo.delete(commercialVModel);
    }

    @Override
    public void addBatteryToModel(Integer modelId, Integer batteryId) {
        CommercialVModel commercialVModel = this.commercialVModelRepo.findById(modelId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to commercial vehicle model - no existing commercial vehicle model found with id "+modelId));
        CommercialVBattery commercialVBattery = this.commercialVBatteryRepo.findById(batteryId).orElseThrow(()->new ResourceNotFoundException("Cannot map battery to commercial vehicle model - no existing commercial vehicle battery found with id "+batteryId));
        if(commercialVModel.getCommercialVBatteries()==null){
            commercialVModel.setCommercialVBatteries(new ArrayList<>());
        }
        commercialVModel.getCommercialVBatteries().add(commercialVBattery);
        try {
            this.commercialVModelRepo.save(commercialVModel);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the commercial vehicle model and battery - Duplicate Entry detected");
        }
    }
}
