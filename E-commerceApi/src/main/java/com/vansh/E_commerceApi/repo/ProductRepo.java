package com.vansh.E_commerceApi.repo;

import com.vansh.E_commerceApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findByCategoryName(String name);

    Optional<Product> findByName(String name);
}
