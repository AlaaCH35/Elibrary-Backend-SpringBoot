package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entity.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Integer> {

    Optional<Discount> findByCode(String code);
}
