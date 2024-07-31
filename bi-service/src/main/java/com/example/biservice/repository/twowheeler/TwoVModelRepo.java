package com.example.biservice.repository.twowheeler;

import com.example.biservice.entity.twowheeler.TwoVBrand;
import com.example.biservice.entity.twowheeler.TwoVModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwoVModelRepo extends JpaRepository<TwoVModel, Integer> {

    List<TwoVModel> findByTwoVBrandOrderByModelName(TwoVBrand twoVBrand);
}
