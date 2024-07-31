package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoVBrandRepo extends JpaRepository<TwoVBrand, Integer> {
}
