package com.bezkoder.springjwt.models.Entity;

import com.bezkoder.springjwt.models.Dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "producte")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;

@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<WishList> wishListList;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;



    @OneToOne(cascade=CascadeType.ALL)
    private  ImageModel imageModel;




    public Product(ProductDto productDto, Category category) {
        this.name = productDto.getName();
        this.imageModel=productDto.getImage();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.category = category;
    }

    public Product(String name, ImageModel imageModel, double price, String description, Category category) {
        super();
        this.name = name;
    this.imageModel=imageModel;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public Product() {
    }


    public Product(Integer bookId) {
    }
}
