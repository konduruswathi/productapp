package com.capgemini.productapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.productapp.entity.Product;
@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
