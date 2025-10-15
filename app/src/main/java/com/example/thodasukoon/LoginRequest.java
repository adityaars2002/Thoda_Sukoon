package com.example.thodasukoon;

public class LoginRequest {
    public String email;
    public String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
