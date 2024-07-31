package com.example.biservice.service.fourwheeler.brand;

import com.example.biservice.payload.fourwheeler.FourVBrandDto;

import java.util.List;

public interface FourVBrandService {

    FourVBrandDto createBrand(FourVBrandDto fourVBrandDto);

    List<FourVBrandDto> findAllBrands();

    FourVBrandDto findBrandById(Integer brandId);

    FourVBrandDto updateBrand(Integer brandId, FourVBrandDto fourVBrandDto);

    void deleteBrand(Integer brandId);
}
