package com.example.biservice.service.threewheeler.brand;

import com.example.biservice.entity.threewheeler.ThreeVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.threewheeler.ThreeVBrandDto;
import com.example.biservice.repository.threewheeler.ThreeVBrandRepo;
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
public class ThreeVBrandServiceImp implements ThreeVBrandService{

    private final ModelMapper modelMapper;

    private final ThreeVBrandRepo threeVBrandRepo;


    @Override
    public ThreeVBrandDto createBrand(ThreeVBrandDto threeVBrandDto) {
        ThreeVBrand threeVBrand = this.modelMapper.map(threeVBrandDto, ThreeVBrand.class);
        ThreeVBrand savedThreeVBrand;
        try {
            savedThreeVBrand = this.threeVBrandRepo.save(threeVBrand);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the three wheeler brand - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedThreeVBrand, ThreeVBrandDto.class);
    }

    @Override
    public List<ThreeVBrandDto> findAllBrands() {
        Sort sort = Sort.by("brandName");
        List<ThreeVBrand> threeVBrands = this.threeVBrandRepo.findAll(sort);
        return threeVBrands.stream().map((brand)->this.modelMapper.map(brand, ThreeVBrandDto.class)).collect(Collectors.toList());
    }

    @Override
    public ThreeVBrandDto findBrandById(Integer brandId) {
        ThreeVBrand threeVBrand = this.threeVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Two wheeler brand not found with id "+brandId));
        return this.modelMapper.map(threeVBrand, ThreeVBrandDto.class);
    }

    @Override
    public ThreeVBrandDto updateBrand(Integer brandId, ThreeVBrandDto threeVBrandDto) {
        Optional<ThreeVBrand> threeVBrandOptional = this.threeVBrandRepo.findById(brandId);
        if(threeVBrandOptional.isPresent()) {
            ThreeVBrand threeVBrand = this.modelMapper.map(threeVBrandDto, ThreeVBrand.class);
            ThreeVBrand updatedBrand;
            try {
                updatedBrand = this.threeVBrandRepo.save(threeVBrand);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the three wheeler brand - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBrand, ThreeVBrandDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update three wheeler brand - no existing three wheeler brand found with id "+brandId);
        }
    }

    @Override
    public void deleteBrand(Integer brandId) {
        ThreeVBrand threeVBrand = this.threeVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot delete three wheeler brand - no existing three wheeler brand found with id "+brandId));
        this.threeVBrandRepo.delete(threeVBrand);
    }
}
