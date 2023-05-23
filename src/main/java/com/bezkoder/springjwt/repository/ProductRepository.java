package com.bezkoder.springjwt.repository;



import com.bezkoder.springjwt.models.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_Id(Integer category_id);
Optional<Product> findById (Integer bookId);

    List<Product> findByAuthorId(Integer authorId);
}
