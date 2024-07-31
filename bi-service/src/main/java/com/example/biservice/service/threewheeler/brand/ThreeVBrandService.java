package com.example.biservice.service.threewheeler.brand;

import com.example.biservice.payload.threewheeler.ThreeVBrandDto;

import java.util.List;

public interface ThreeVBrandService {

    ThreeVBrandDto createBrand(ThreeVBrandDto threeVBrandDto);

    List<ThreeVBrandDto> findAllBrands();

    ThreeVBrandDto findBrandById(Integer brandId);

    ThreeVBrandDto updateBrand(Integer brandId, ThreeVBrandDto threeVBrandDto);

    void deleteBrand(Integer brandId);
}
