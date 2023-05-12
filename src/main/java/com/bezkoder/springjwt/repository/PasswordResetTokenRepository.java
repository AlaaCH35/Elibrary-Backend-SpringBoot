package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    void deleteByExpiryDateLessThan(Date expiryDate);
}