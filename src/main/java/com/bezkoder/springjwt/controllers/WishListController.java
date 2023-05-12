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

        @PostMapping("/toggle/{userId}/{id}")
        public ResponseEntity<String> toggleWishlistItem(@PathVariable Long userId, @PathVariable Integer id) {

                try {
                        wishListService.addtoWishlist(userId, id);
                        String message = "no";
                        if (wishListService.isItemInWishlist(userId, id)) {
                                message = "Item added to your Wishlist";
                        } else {
                                message = "Item removed from your Wishlist";
                        }
                        return new ResponseEntity<String>(message, HttpStatus.OK);
                } catch (Exception e) {
                        return new ResponseEntity<String>("Error occurred while toggling item in wishlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
        @GetMapping("/get/{userId}")
        public List<Product> getWishlistItems(@PathVariable Long userId) {
                List<WishList> wishlists = wishlistRepository.findByUserId(userId);
                List<Product> products = new ArrayList<>();

                for (WishList wishlist : wishlists) {
                        products.add(wishlist.getProduct());
                }

                return products;
        }

        @GetMapping("/get/all")
        public List<WishList> getWishlistItems() {
                List<WishList> wishlists = wishlistRepository.findAll();

                return wishlists;
        }


}
