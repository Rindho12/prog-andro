package com.example.myapplication.data;

import android.content.Context;

import com.example.myapplication.data.model.LoggedInUser;

import java.io.IOException;

import com.example.myapplication.data.model.tb_user;
import com.example.myapplication.utils.databaseManager.userTable;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(Context context, String username, String password) {

        try {
            userTable table = new userTable(context);
            tb_user result = table.getUserWithUsernamePassword(username, password);
            if(result == null) {
                return new Result.Error(new IOException("Error logging in"));
            } else {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                result.getUserId(),
                                result.getDisplayName(),
                                result.getUsername());
                return new Result.Success<>(fakeUser);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}