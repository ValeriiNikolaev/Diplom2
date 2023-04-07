package ru.practicum.yandex.model;

import java.util.Random;

public class User {
    private String name;
    private String password;
    private String email;
    private String accessToken;
    private String refreshToken;

    public User(String name, String password, String email, String accessToken, String refreshToken) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void enterUserData() {
        this.name = UserGeneration.randomName();
        this.password = UserGeneration.randomPassword();
        this.email = UserGeneration.randomEmail();

    }
}
