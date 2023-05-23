package com.bezkoder.springjwt.models.Dto.Wishlist;

import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.Entity.WishList;
import com.bezkoder.springjwt.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private Integer id;
    private User user;
    private Date createdDate;
    private Product product;

    public static WishlistDto fromEntity(WishList wishList ) {

        return WishlistDto.builder()
                        .id(wishList.getId())
                                .user(wishList.getUser())
                                        .createdDate(wishList.getCreatedDate())
                                                .product(wishList.getProduct())
                                                        .build();


    }

    public static  WishList toEntity (WishlistDto wishlistDto){
        WishList wishList =new WishList();
        wishList.setId(wishlistDto.getId());
        wishList.setCreatedDate(wishlistDto.getCreatedDate());
        wishList.setProduct(wishlistDto.getProduct());


        return wishList;



    }


}
