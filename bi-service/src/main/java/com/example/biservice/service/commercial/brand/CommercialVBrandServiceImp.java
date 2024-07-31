package com.example.biservice.service.commercial.brand;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVBrandDto;
import com.example.biservice.repository.commercial.CommercialVBrandRepo;
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
public class CommercialVBrandServiceImp implements CommercialVBrandService {

    private final ModelMapper modelMapper;

    private final CommercialVBrandRepo commercialVBrandRepo;

    private  final CommercialVSegmentRepo commercialVSegmentRepo;


    @Override
    public CommercialVBrandDto createBrand(CommercialVBrandDto commercialVBrandDto) {
        CommercialVBrand commercialVBrand = this.modelMapper.map(commercialVBrandDto, CommercialVBrand.class);
        CommercialVBrand savedCommercialVBrand;
        try {
            savedCommercialVBrand = this.commercialVBrandRepo.save(commercialVBrand);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the commercial vehicle brand - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedCommercialVBrand, CommercialVBrandDto.class);
    }

    @Override
    public List<CommercialVBrandDto> findAllBrands() {
        Sort sort = Sort.by("brandName");
        List<CommercialVBrand> commercialVBrands = this.commercialVBrandRepo.findAll(sort);
        return commercialVBrands.stream().map((brand)->this.modelMapper.map(brand, CommercialVBrandDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommercialVBrandDto findBrandById(Integer brandId) {
        CommercialVBrand commercialVBrand = this.commercialVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Two wheeler brand not found with id "+brandId));
        return this.modelMapper.map(commercialVBrand, CommercialVBrandDto.class);
    }

    @Override
    public CommercialVBrandDto updateBrand(Integer brandId, CommercialVBrandDto commercialVBrandDto) {
        Optional<CommercialVBrand> commercialVBrandOptional = this.commercialVBrandRepo.findById(brandId);
        if(commercialVBrandOptional.isPresent()) {
            CommercialVBrand commercialVBrand = this.modelMapper.map(commercialVBrandDto, CommercialVBrand.class);
            commercialVBrand.setCommercialVSegments(commercialVBrandOptional.get().getCommercialVSegments());
            commercialVBrand.setCommercialVModels(commercialVBrandOptional.get().getCommercialVModels());
            CommercialVBrand updatedBrand;
            try {
                updatedBrand = this.commercialVBrandRepo.save(commercialVBrand);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the commercial vehicle brand - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBrand, CommercialVBrandDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update commercial wheeler brand - no existing commercial wheeler brand found with id "+brandId);
        }
    }

    @Override
    public void deleteBrand(Integer brandId) {
        CommercialVBrand commercialVBrand = this.commercialVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot delete commercial wheeler brand - no existing commercial wheeler brand found with id "+brandId));
        this.commercialVBrandRepo.delete(commercialVBrand);
    }

    @Override
    public void addSegmentToBrand(Integer brandId, Integer segmentId) {
        CommercialVBrand commercialVBrand = this.commercialVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot map segment to commercial vehicle brand - no existing commercial vehicle brand found with id "+brandId));
        CommercialVSegment commercialVSegment = this.commercialVSegmentRepo.findById(segmentId).orElseThrow(()->new ResourceNotFoundException("Cannot map segment to commercial vehicle brand - no existing commercial vehicle segment found with id "+segmentId));
        if(commercialVBrand.getCommercialVSegments()==null){
            commercialVBrand.setCommercialVSegments(new ArrayList<>());
        }
        commercialVBrand.getCommercialVSegments().add(commercialVSegment);
        try {
            this.commercialVBrandRepo.save(commercialVBrand);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintException("An error occurred while mapping the commercial vehicle brand and segment - Duplicate Entry detected");
        }
    }
}
