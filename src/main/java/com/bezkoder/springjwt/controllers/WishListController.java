package com.bezkoder.springjwt.controllers;




import com.bezkoder.springjwt.Service.ProductService;
import com.bezkoder.springjwt.Service.WishListService;
import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.Entity.WishList;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
@Autowired
        WishlistRepository wishlistRepository ;
@Autowired
        WishListService wishListService;

        @Autowired
        ProductRepository productRepository;

        @PostMapping("/toggle/{userId}/{productId}")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public ResponseEntity<List<Product>> toggleWishlistItem(
                @PathVariable Long userId,
                @PathVariable Integer productId) {
                List<Product> wishlist = wishListService.addtoWishlist2(userId,productId);
                return ResponseEntity.ok(wishlist);
        }
        @GetMapping("/get/{userId}")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public List<Product> getWishlistItems(@PathVariable Long userId) {
                List<WishList> wishlists = wishlistRepository.findByUserId(userId);
                List<Product> products = new ArrayList<>();

                for (WishList wishlist : wishlists) {
                        products.add(wishlist.getProduct());
                }

                return products;
        }

        @GetMapping("/get/all")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public List<WishList> getWishlistItems() {
                List<WishList> wishlists = wishlistRepository.findAll();

                return wishlists;
        }
        @DeleteMapping("/{userId}")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public ResponseEntity<String> clearWishlist(@PathVariable Long userId) {
                try {
                        int count = wishListService.clearWishlist(userId);
                        if (count > 0) {
                                return ResponseEntity.ok("Wishlist cleared successfully.");
                        } else {
                                return ResponseEntity.ok("Wishlist is already empty.");
                        }
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while clearing wishlist: " + e.getMessage());
                }
        }



}
