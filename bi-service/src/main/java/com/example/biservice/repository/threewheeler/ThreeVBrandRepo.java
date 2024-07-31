package com.example.biservice.repository.threewheeler;

import com.example.biservice.entity.threewheeler.ThreeVBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreeVBrandRepo extends JpaRepository<ThreeVBrand, Integer> {
}
