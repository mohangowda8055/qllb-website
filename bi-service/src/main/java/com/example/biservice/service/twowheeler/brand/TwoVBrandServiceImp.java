package com.example.biservice.service.twowheeler.brand;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.twowheeler.TwoVBrandDto;
import com.example.biservice.repository.twowheeler.TwoVBrandRepo;
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
public class TwoVBrandServiceImp implements TwoVBrandService{

    private final ModelMapper modelMapper;
    private final TwoVBrandRepo twoVBrandRepo;


    @Override
    public TwoVBrandDto createBrand(TwoVBrandDto twoVBrandDto) {
        TwoVBrand twoVBrand = this.modelMapper.map(twoVBrandDto, TwoVBrand.class);
        TwoVBrand savedTwoVBrand;
        try {
            savedTwoVBrand = this.twoVBrandRepo.save(twoVBrand);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the two wheeler brand - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedTwoVBrand, TwoVBrandDto.class);
    }

    @Override
    public List<TwoVBrandDto> findAllBrands() {
        Sort sort = Sort.by("brandName");
        List<TwoVBrand> twoVBrands = this.twoVBrandRepo.findAll(sort);
        return twoVBrands.stream().map((brand)-> this.modelMapper.map(brand, TwoVBrandDto.class)).collect(Collectors.toList());
    }

    @Override
    public TwoVBrandDto findBrandById(Integer brandId) {
        TwoVBrand twoVBrand = this.twoVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Two wheeler brand not found with id "+brandId));
        return this.modelMapper.map(twoVBrand, TwoVBrandDto.class);
    }

    @Override
    public TwoVBrandDto updateBrand(Integer brandId, TwoVBrandDto twoVBrandDto) {
        Optional<TwoVBrand> twoVBrandOptional = this.twoVBrandRepo.findById(brandId);
        if(twoVBrandOptional.isPresent()) {
            TwoVBrand twoVBrand = this.modelMapper.map(twoVBrandDto, TwoVBrand.class);
            TwoVBrand updatedBrand;
            try {
                updatedBrand = this.twoVBrandRepo.save(twoVBrand);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the two wheeler brand - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBrand, TwoVBrandDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update two wheeler brand - no existing two wheeler brand found with id "+brandId);
        }
    }

    @Override
    public void deleteBrand(Integer brandId) {
        TwoVBrand twoVBrand = this.twoVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot delete two wheeler brand - no existing two wheeler delete found with id "+brandId));
        this.twoVBrandRepo.delete(twoVBrand);
    }
}
