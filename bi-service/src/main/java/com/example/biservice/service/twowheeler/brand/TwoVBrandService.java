package com.example.biservice.service.twowheeler.brand;

import com.example.biservice.payload.twowheeler.TwoVBrandDto;

import java.util.List;


public interface TwoVBrandService {

    TwoVBrandDto createBrand(TwoVBrandDto twoVBrandDto);

    List<TwoVBrandDto> findAllBrands();

    TwoVBrandDto findBrandById(Integer brandId);

    TwoVBrandDto updateBrand(Integer brandId, TwoVBrandDto twoVBrandDto);

    void deleteBrand(Integer brandId);
}
