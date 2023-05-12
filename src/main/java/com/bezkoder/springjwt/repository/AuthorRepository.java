package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {





    void deleteById(Integer id);


}
