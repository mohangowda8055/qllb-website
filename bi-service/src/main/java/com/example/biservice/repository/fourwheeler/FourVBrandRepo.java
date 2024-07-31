package com.example.biservice.repository.fourwheeler;

import com.example.biservice.entity.fourwheeler.FourVBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FourVBrandRepo extends JpaRepository<FourVBrand, Integer> {
}
