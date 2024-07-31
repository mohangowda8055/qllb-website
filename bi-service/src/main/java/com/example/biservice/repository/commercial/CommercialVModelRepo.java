package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVModel;
import com.example.biservice.entity.commercial.CommercialVSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialVModelRepo extends JpaRepository<CommercialVModel, Integer> {

    List<CommercialVModel> findAllByCommercialVBrandAndCommercialVSegmentOrderByModelName(CommercialVBrand commercialVBrand, CommercialVSegment commercialVSegment);
}
