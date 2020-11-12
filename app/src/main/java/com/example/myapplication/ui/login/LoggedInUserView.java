package com.example.myapplication.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String username;
    //... other data fields that may be accessible to the UI


    public LoggedInUserView(String displayName, String username) {
        this.displayName = displayName;
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }
}