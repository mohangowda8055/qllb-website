package com.example.biservice.repository.commercial;

import com.example.biservice.entity.commercial.CommercialVBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialVBrandRepo extends JpaRepository<CommercialVBrand, Integer> {
}
