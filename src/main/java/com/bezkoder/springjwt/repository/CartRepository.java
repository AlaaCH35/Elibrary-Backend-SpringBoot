package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Entity.Cart;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

 void deleteAllByUser (User user);
    List<Cart> findByUserId(Long userId);
      Cart findByUser(User user);
    void deleteById(Integer id);
   Cart  findByUserAndProduct(User user, Product product);

    List<Cart> findAllByOrderByCreatedDateDesc();

}

