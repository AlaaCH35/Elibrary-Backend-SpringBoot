package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {





    void deleteById(Integer id);


  Author findByName(String authorName);
}
