package com.example.biservice.repository.inverter;

import com.example.biservice.entity.inverter.Inverter;
import com.example.biservice.entity.inverter.InverterCapacity;
import com.example.biservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface InverterRepo extends ProductRepository<Inverter> {

    Page<Inverter> findAllByInverterCapacity(InverterCapacity inverterCapacity, Pageable pageable);
}
