package com.example.forum.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    private String email;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken authObject() {
        return new UsernamePasswordAuthenticationToken(getEmail(), getPassword());
    }
}
