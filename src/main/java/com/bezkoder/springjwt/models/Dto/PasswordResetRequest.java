package com.bezkoder.springjwt.models.Dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;

public class PasswordResetRequest {

    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    @URL
    private String resetUrl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetUrl() {
        return resetUrl;
    }

    public void setResetUrl(String resetUrl) {
        this.resetUrl = resetUrl;
    }

    // add any other necessary methods or fields here

}
