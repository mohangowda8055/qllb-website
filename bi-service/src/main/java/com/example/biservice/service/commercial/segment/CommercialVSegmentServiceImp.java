package com.example.biservice.service.commercial.segment;

import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVSegmentDto;
import com.example.biservice.repository.commercial.CommercialVSegmentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommercialVSegmentServiceImp implements CommercialVSegmentService{

    private final ModelMapper modelMapper;

    private final CommercialVSegmentRepo commercialVSegmentRepo;


    @Override
    public CommercialVSegmentDto createSegment(CommercialVSegmentDto commercialVSegmentDto) {
        CommercialVSegment commercialVSegment = this.modelMapper.map(commercialVSegmentDto, CommercialVSegment.class);
        CommercialVSegment savedCommercialVSegment;
        try {
            savedCommercialVSegment = this.commercialVSegmentRepo.save(commercialVSegment);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the commercial vehicle segment - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedCommercialVSegment, CommercialVSegmentDto.class);
    }

    @Override
    public List<CommercialVSegmentDto> findAllSegmentsByBrand(Integer brandId) {
        Sort sort = Sort.by("segmentName");
        List<CommercialVSegment> commercialVSegments = this.commercialVSegmentRepo.findSegmentsByBrand(brandId,sort);
        return commercialVSegments.stream().map((model)->this.modelMapper.map(model, CommercialVSegmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommercialVSegmentDto> findAllSegments() {
        Sort sort = Sort.by("segmentName");
        List<CommercialVSegment> commercialVSegments = this.commercialVSegmentRepo.findAll(sort);
        return commercialVSegments.stream().map((model)->this.modelMapper.map(model, CommercialVSegmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommercialVSegmentDto findSegmentById(Integer segmentId) {
        CommercialVSegment commercialVSegment = this.commercialVSegmentRepo.findById(segmentId).orElseThrow(()->new ResourceNotFoundException("Commercial wheeler model not found with id"+segmentId));
        return this.modelMapper.map(commercialVSegment, CommercialVSegmentDto.class);
    }

    @Override
    public CommercialVSegmentDto updateSegment(Integer segmentId, CommercialVSegmentDto commercialVSegmentDto) {
        Optional<CommercialVSegment> commercialVSegmentOptional = this.commercialVSegmentRepo.findById(segmentId);
        if(commercialVSegmentOptional.isPresent()) {
            CommercialVSegment commercialVSegment = this.modelMapper.map(commercialVSegmentDto, CommercialVSegment.class);
            commercialVSegment.setCommercialVBrands(commercialVSegmentOptional.get().getCommercialVBrands());
            commercialVSegment.setCommercialVModels(commercialVSegmentOptional.get().getCommercialVModels());
            CommercialVSegment updatedSegment;
            try {
                updatedSegment = this.commercialVSegmentRepo.save(commercialVSegment);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating commercial vehicle segment - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedSegment, CommercialVSegmentDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update commercial vehicle segment - no existing commercial vehicle segment found with id "+segmentId);
        }
    }

    @Override
    public void deleteSegment(Integer segmentId) {
        CommercialVSegment commercialVSegment = this.commercialVSegmentRepo.findById(segmentId).orElseThrow(()->new ResourceNotFoundException("Cannot delete commercial vehicle segment - no existing commercial vehicle segment found with id "+segmentId));
        this.commercialVSegmentRepo.delete(commercialVSegment);
    }
}
