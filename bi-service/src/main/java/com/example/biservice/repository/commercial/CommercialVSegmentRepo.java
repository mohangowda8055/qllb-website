package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVSegment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialVSegmentRepo extends JpaRepository<CommercialVSegment, Integer> {

    @Query("SELECT s FROM CommercialVSegment s JOIN s.commercialVBrands b WHERE b.brandId = :brandId ")
    List<CommercialVSegment> findSegmentsByBrand(@Param("brandId") Integer brandId, Sort sort);
}
