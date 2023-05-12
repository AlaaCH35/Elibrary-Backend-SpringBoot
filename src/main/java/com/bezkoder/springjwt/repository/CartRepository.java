package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllById(Integer id );

    void deleteById(Integer id);

    List<Cart> findAllByOrderByCreatedDateDesc();

}

