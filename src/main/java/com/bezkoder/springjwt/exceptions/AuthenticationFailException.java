package com.bezkoder.springjwt.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {
    Long id;
    public AuthenticationFailException(String msg) {
        super(msg);
    }
    public AuthenticationFailException(String msg, String idd,Long id) {

        super(msg);
        this.id=id;
    }



}
