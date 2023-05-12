package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.exceptions.AuthenticationFailException;
import com.bezkoder.springjwt.models.Dto.User.UserDto;
import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.PasswordResetToken;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private JavaMailSender emailSender;
  @Autowired
  UserRepository userRepository;
  @Autowired
  private PasswordResetTokenRepository passwordResetTokenRepository;



  public UserDetailsServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }


  public boolean isUserNameValid(String userName) {
    // Your validation logic goes here
    // For example, check if the username meets some criteria, such as minimum length, allowed characters, etc.
    return userName.length() >= 5 && userName.matches("^[a-zA-Z0-9]+$");
  }

  public List<User> getAllUsersByRole(ERole role) {
    return userRepository.findByRoles_Name(role);
  }




  // rest of the service methods...

  public void sendPasswordResetEmail(String email, String resetUrl) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("Password Reset Request");
    message.setText("Please click on the following link to reset your password: " + resetUrl);
    emailSender.send(message);
  }





  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }


  public void createPasswordResetTokenForUser(User user, String token) {
    PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
    passwordResetTokenRepository.save(passwordResetToken);
  }


  public PasswordResetToken getPasswordResetToken(String token) {
    return passwordResetTokenRepository.findByToken(token);
  }


  public void changeUserPassword(User user, String password) {
    user.setPassword(password);
    userRepository.save(user);
  }

  public static User getUserFromDto(UserDto userDto) {
    User user = new User(userDto);
    return user;
  }}


















