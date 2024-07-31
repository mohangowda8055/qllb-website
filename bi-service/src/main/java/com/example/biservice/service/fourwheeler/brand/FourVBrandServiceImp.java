package com.example.biservice.service.fourwheeler.brand;

import com.example.biservice.entity.fourwheeler.FourVBrand;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.fourwheeler.FourVBrandDto;
import com.example.biservice.repository.fourwheeler.FourVBrandRepo;
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
public class FourVBrandServiceImp implements FourVBrandService{

    private final ModelMapper modelMapper;

    private final FourVBrandRepo fourVBrandRepo;


    @Override
    public FourVBrandDto createBrand(FourVBrandDto fourVBrandDto) {
        FourVBrand fourVBrand = this.modelMapper.map(fourVBrandDto, FourVBrand.class);
        FourVBrand savedFourVBrand;
        try {
            savedFourVBrand = this.fourVBrandRepo.save(fourVBrand);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving the four wheeler brand - Duplicate Entry detected");
        }
        return this.modelMapper.map(savedFourVBrand, FourVBrandDto.class);
    }

    @Override
    public List<FourVBrandDto> findAllBrands() {
        Sort sort = Sort.by("brandName");
        List<FourVBrand> fourVBrands = this.fourVBrandRepo.findAll(sort);
        return fourVBrands.stream().map((brand)->this.modelMapper.map(brand, FourVBrandDto.class)).collect(Collectors.toList());
    }

    @Override
    public FourVBrandDto findBrandById(Integer brandId) {
        FourVBrand fourVBrand = this.fourVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Two wheeler brand not found with id "+brandId));
        return this.modelMapper.map(fourVBrand, FourVBrandDto.class);
    }

    @Override
    public FourVBrandDto updateBrand(Integer brandId, FourVBrandDto fourVBrandDto) {
        Optional<FourVBrand> fourVBrandOptional = this.fourVBrandRepo.findById(brandId);
        if(fourVBrandOptional.isPresent()) {
            FourVBrand fourVBrand = this.modelMapper.map(fourVBrandDto, FourVBrand.class);
            FourVBrand updatedBrand;
            try {
                updatedBrand = this.fourVBrandRepo.save(fourVBrand);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the four wheeler brand - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedBrand, FourVBrandDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update four wheeler brand - no existing four wheeler brand found with id "+brandId);
        }
    }

    @Override
    public void deleteBrand(Integer brandId) {
        FourVBrand fourVBrand = this.fourVBrandRepo.findById(brandId).orElseThrow(()->new ResourceNotFoundException("Cannot delete four wheeler brand - no existing four wheeler brand found with id "+brandId));
        this.fourVBrandRepo.delete(fourVBrand);
    }
}
