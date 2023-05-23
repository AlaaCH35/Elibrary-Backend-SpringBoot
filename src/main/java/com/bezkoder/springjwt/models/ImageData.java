package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String type;


    @Lob
    @Column(name = "imagedata",length = 5000000)
    private byte[] imageData;
}