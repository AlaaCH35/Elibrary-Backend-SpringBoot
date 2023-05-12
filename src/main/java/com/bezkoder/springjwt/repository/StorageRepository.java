package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Integer> {


    Optional<ImageData> findByName(String fileName);
}