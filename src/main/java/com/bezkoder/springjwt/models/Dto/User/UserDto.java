package com.bezkoder.springjwt.models.Dto.User;


import com.bezkoder.springjwt.models.Entity.ImageModel;
import com.bezkoder.springjwt.models.Entity.Product;
import com.bezkoder.springjwt.models.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Builder
public class UserDto {
    private Long id;



    private  String name;
    private  String phone;
    private  String address;


    @Size(max = 50)
    @Email
    private String email;


    @Size(max = 120)
    private String password;

    private ImageModel image;



    public UserDto(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setPhone(user.getPhone());
        this.setEmail(user.getEmail());
        this.setAddress(user.getAddress());
        this.setImage(user.getImageModel());

    }

    public static UserDto fromEntity(User user ) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .image(user.getImageModel())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())

                .build();
    }

    public UserDto() {
    }

    public UserDto(Long id, String name, String phone, String address, String email, String password, ImageModel image) {
        this.id = id;

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }




}
