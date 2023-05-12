package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {




}
