package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.models.Entity.Cart;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.CartRepository;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
public  int removeCartItem (Long userId,Integer bookId){
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Product product = productRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    Cart existingCartItem = cartRepository.findByUserAndProduct(user, product);
    if (existingCartItem != null) {
        existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
        Cart updatedCartItem = cartRepository.save(existingCartItem);
        return updatedCartItem.getId();

}
    else  return 0;}

    @Transactional
    public  int deleteAllItem (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


          cartRepository.deleteAllByUser(user);

        return 0;

    }





    public int addBookToCart(Long userId, Integer bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Cart existingCartItem = cartRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            Cart updatedCartItem = cartRepository.save(existingCartItem);
            return updatedCartItem.getId();
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);

            Cart savedCartItem = cartRepository.save(cart);

            return savedCartItem.getId();
        }
    }
    public List<Cart> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }


    public int getCartItemCount(Long userId) {

        List<Cart> cartList = cartRepository.findByUserId(userId);

        return cartList.size();

    }

}
