package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.Entity.WishList;

import com.bezkoder.springjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WishlistRepository extends JpaRepository<WishList,Integer> {


       WishList   findByProductId(Integer id);
       Optional<WishList> findByUserAndProduct(User user, Product product);
       List<WishList> findByUser(User user);
       List<WishList>  findByUserId(Long id);
       void deleteAllByUser(User user);
//
//       List<WishList> findByUserId(Long userId);
}
