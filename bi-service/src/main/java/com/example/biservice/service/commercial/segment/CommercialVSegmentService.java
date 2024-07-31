package com.example.biservice.service.commercial.segment;

import com.example.biservice.payload.commercial.CommercialVSegmentDto;

import java.util.List;

public interface CommercialVSegmentService {

    CommercialVSegmentDto createSegment(CommercialVSegmentDto commercialVSegmentDto);

    List<CommercialVSegmentDto> findAllSegmentsByBrand(Integer brandId);

    List<CommercialVSegmentDto> findAllSegments();

    CommercialVSegmentDto findSegmentById(Integer segmentId);

    CommercialVSegmentDto updateSegment(Integer segmentId, CommercialVSegmentDto commercialVSegmentDto);

    void deleteSegment(Integer segmentId);

}
