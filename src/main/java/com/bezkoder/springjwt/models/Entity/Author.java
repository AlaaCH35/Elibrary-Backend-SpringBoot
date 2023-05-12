package com.bezkoder.springjwt.models.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="author")
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue
    private Long id;


    private  String name;
    private String Image;


    private  String bio;




}

