package com.vansh.E_commerceApi.repo;

import com.vansh.E_commerceApi.model.Product;
import com.vansh.E_commerceApi.model.Review;
import com.vansh.E_commerceApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
    List<Review> findByUser(User user);
    List<Review> findByProduct(Product product);
}
