package com.bezkoder.springjwt.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.bezkoder.springjwt.exceptions.AuthenticationFailException;
import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.Entity.WishList;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class WishListService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    private final WishlistRepository wishListRepository;
 @Autowired
 private  ProductService productService ;
    @Autowired
    private   ProductRepository productRepository;

    public WishListService(WishlistRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }


    public void addtowishlist(Long userId, int productId) {
        User user = new User();
        user.setId(userId);
    WishList wishList =new WishList() ;
        ProductDto productDto =productService.findById(productId);





    }

    public List<Product> addtoWishlist2(Long userId, Integer productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationFailException("fzfzfze"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->  new AuthenticationFailException("fzfzfze"));

        Optional<WishList> wishlistItem =  wishListRepository.findByUserAndProduct(user, product);

        if (wishlistItem.isPresent()) {
            wishListRepository.delete(wishlistItem.get());
        } else {
            WishList newWishlistItem = new WishList(user, product);
            wishListRepository.save(newWishlistItem);
        }

        return  wishListRepository.findByUser(user)
                .stream()
                .map(WishList::getProduct)
                .collect(Collectors.toList());
    }




    public void addtoWishlist(Long userId, int productId) {
        User user = new User();
        user.setId(userId);

        Optional<WishList> wishlistItem = Optional.ofNullable(wishListRepository.findByProductId(productId));
        if (wishlistItem.isPresent()) {
            wishListRepository.delete(wishlistItem.get());
        } else {
            Product product = new Product();
            product.setId(productId);
            WishList newWishlistItem = new WishList(user, product);
            wishListRepository.save(newWishlistItem);

        }
    }

    public int clearWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationFailException("fzfzfze"));
        wishListRepository.deleteAllByUser(user);
        return 0;

    }


    public boolean isItemInWishlist(Long userId, Integer productId) {
        List<WishList> wishlists =  wishlistRepository.findByUserId(userId);
        for (WishList wishlist : wishlists) {
            if (wishlist.getProduct().getId().equals(productId)) {
                return true;
            }
        }
        return false;
    }



            }
            // get the updated wishlist for the user and return it


















