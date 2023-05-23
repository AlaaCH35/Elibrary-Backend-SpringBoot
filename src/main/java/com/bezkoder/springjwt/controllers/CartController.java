package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Service.CartService;
import com.bezkoder.springjwt.models.Entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
@Autowired
    CartService cartService;

    @PostMapping("/addToCart/{userId}/{bookId}")

    public ResponseEntity<Integer> addBookToCart(
            @PathVariable("userId") Long userId,
            @PathVariable("bookId") Integer bookId) {

        int cartItemId = cartService.addBookToCart(userId, bookId);

        return ResponseEntity.ok(cartItemId);
    }
    @PutMapping ("/remove/{userId}/{bookId}")

    public ResponseEntity<Integer> removeItemCart(
            @PathVariable("userId") Long userId,
            @PathVariable("bookId") Integer bookId) {

        int cartItemId = cartService.removeCartItem(userId, bookId);

        return ResponseEntity.ok(cartItemId);
    }
    @GetMapping("/get/{userId}")

    public ResponseEntity<List<Cart>> getCartItems(@PathVariable("userId") Long userId) {
        List<Cart> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
    @DeleteMapping ("/deleteAll/{userId}")


    public ResponseEntity<Integer> clear(@PathVariable Long userId) {
        try {
            int count = cartService.deleteAllItem(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/count/{userId}")

    public ResponseEntity<Integer> getCartItemCount(@PathVariable("userId") Long userId) {
        int cartItemCount = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(cartItemCount);
    }

}
