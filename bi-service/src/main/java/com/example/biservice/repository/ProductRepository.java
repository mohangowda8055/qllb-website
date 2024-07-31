package com.example.biservice.repository;

import com.example.biservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProductRepository<T extends Product> extends JpaRepository<T, Integer> {
}
