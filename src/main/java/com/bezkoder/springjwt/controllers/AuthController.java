package com.bezkoder.springjwt.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.springjwt.common.ApiResponse;
import com.bezkoder.springjwt.exceptions.AuthenticationFailException;
import com.bezkoder.springjwt.exceptions.TokenRefreshException;
import com.bezkoder.springjwt.models.Dto.ChangePassword.ChangePasswordRequest;
import com.bezkoder.springjwt.models.Dto.PasswordResetRequest;
import com.bezkoder.springjwt.models.Dto.User.UserDto;
import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Category;
import com.bezkoder.springjwt.models.Entity.ImageModel;
import com.bezkoder.springjwt.models.Entity.RefreshToken;
import com.bezkoder.springjwt.payload.request.TokenRefreshRequest;
import com.bezkoder.springjwt.payload.response.TokenRefreshResponse;
import com.bezkoder.springjwt.security.services.RefreshTokenService;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;


  @Autowired
  UserDetailsServiceImpl userDetailsService ;
  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
                         userDetails.getId(),
                         userDetails.getUsername(),
                         userDetails.getEmail(), 
                         roles));
  }



  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok(new JwtResponse(jwt));
  }


  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              String token = jwtUtils.generateTokenFromUsername(user.getUsername());
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not in database!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);

    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

  @GetMapping ("val/{userName}")
  public ResponseEntity<Map<String, Boolean>> validateUserName(@PathVariable String userName) {
    Map<String, Boolean> response = new HashMap<>();
    response.put("isValid", userDetailsService.isUserNameValid(userName));
    return ResponseEntity.ok(response);
  }
  @GetMapping("/byRole/{role}")
  public List<User> getUsersByRole(@PathVariable ERole role) {
    return userRepository.findByRoles_Name(role);
  }
  @PutMapping("/users/{id}/password")
  public ResponseEntity<?> changePassword(@PathVariable Long id,
                                          @RequestBody ChangePasswordRequest changePasswordRequest) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new AuthenticationFailException("User", "id", id));

    // Check if the old password is correct
    if (!BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword())) {
      return ResponseEntity.badRequest().body(new ApiResponse(false, "Old password is incorrect"));
    }

    // Update the password
    user.setPassword(BCrypt.hashpw(changePasswordRequest.getNewPassword(), BCrypt.gensalt()));
    userRepository.save(user);

    return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
  }




  @PostMapping("/request-pass")
  public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) throws UsernameNotFoundException{
    User user = userRepository.findByUsername(passwordResetRequest.getUsername())  .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + passwordResetRequest.getUsername()));
    if (user == null) {
      return ResponseEntity.badRequest().body("No user found with this username");
    }

    String token = UUID.randomUUID().toString();
    userDetailsService.createPasswordResetTokenForUser(user, token);

    String resetUrl = passwordResetRequest.getResetUrl() + "?token=" + token;
    userDetailsService.sendPasswordResetEmail(user.getEmail(), resetUrl);

    return ResponseEntity.ok("Password reset email sent successfully");
  }






  // Add other user-related endpoints here
}



