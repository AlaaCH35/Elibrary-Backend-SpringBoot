package com.bezkoder.springjwt.models.Dto.product;


import com.bezkoder.springjwt.models.Dto.Wishlist.WishlistDto;
import com.bezkoder.springjwt.models.Entity.ImageModel;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.Entity.WishList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class ProductDto {

    private Integer id;
    private @NotNull String name;

    private  ImageModel image;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;


    public ProductDto(Product product) {
 this.setId(product.getId());
        this.setName(product.getName());
        this.setImage(product.getImageModel());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setCategoryId(product.getCategory().getId());
    }

    public static ProductDto fromEntity(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImageModel())
                .categoryId(product.getCategory().getId())
                .build();
    }




    public static Product toEntity (ProductDto productDto){
Product product =new Product();
product.setId(productDto.getId());
product.setName(productDto.getName());
product.setImageModel(productDto.getImage());
product.setPrice(productDto.getPrice());


        return product;



    }






    public ProductDto(@NotNull String name, @NotNull ImageModel image, @NotNull double price, @NotNull String description, @NotNull Integer categoryId) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public ProductDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
