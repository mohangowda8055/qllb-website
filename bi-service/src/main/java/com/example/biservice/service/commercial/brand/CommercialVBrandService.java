package com.example.biservice.service.commercial.brand;

import com.example.biservice.payload.commercial.CommercialVBrandDto;

import java.util.List;

public interface CommercialVBrandService {

    CommercialVBrandDto createBrand(CommercialVBrandDto commercialVBrandDto);

    List<CommercialVBrandDto> findAllBrands();

    CommercialVBrandDto findBrandById(Integer brandId);

    CommercialVBrandDto updateBrand(Integer brandId, CommercialVBrandDto commercialVBrandDto);

    void deleteBrand(Integer brandId);

    void addSegmentToBrand(Integer brandId, Integer segmentId);
}
