package com.example.hrms_android_3.login;

public class LoginModel {
    private String name, token, token_type, email, pictureUrl;
    private int image;
    private boolean status;

    public LoginModel(String name, String token, String token_type, String email, String pictureUrl, int image, boolean status) {
        this.name = name;
        this.token = token;
        this.token_type = token_type;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.image = image;
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", email='" + email + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", image=" + image +
                ", status=" + status +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
