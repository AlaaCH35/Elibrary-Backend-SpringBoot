package com.bezkoder.springjwt.models.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="authors")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private  String name;

    public Author() {

    }


    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", products=" + products +
                '}';
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Author(Integer id, String name, String bio, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.products = products;
    }
    @Column(length = 1000)
    private  String bio;


    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    Set<Product> products;



}

