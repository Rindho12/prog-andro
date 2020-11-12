package com.example.myapplication.data.model;

public class tb_user {

    private int userId;
    private String displayName;
    private String username;
    private String password;

    public tb_user() {
        this.userId = 0;
        this.displayName = "";
        this.username = "";
        this.password = "";
    }

    public tb_user(int userId, String displayName, String username, String password) {
        this.userId = userId;
        this.displayName = displayName;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
