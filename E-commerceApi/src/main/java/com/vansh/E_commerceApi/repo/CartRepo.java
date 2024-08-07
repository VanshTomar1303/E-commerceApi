package com.vansh.E_commerceApi.repo;

import com.vansh.E_commerceApi.model.Cart;
import com.vansh.E_commerceApi.model.Product;
import com.vansh.E_commerceApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUserAndCartItem(User user, Product product);

    List<Cart> findByUser(User user);
}
