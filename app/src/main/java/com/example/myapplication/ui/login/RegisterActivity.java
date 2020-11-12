package com.example.myapplication.ui.login;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.HomeScreenActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Result;
import com.example.myapplication.data.model.LoggedInUser;
import com.example.myapplication.data.model.tb_user;
import com.example.myapplication.utils.MyBroadcastReceiver;
import com.example.myapplication.utils.databaseManager.userTable;

import org.sqlite.database.sqlite.SQLiteDatabase;

import java.io.IOException;


public class RegisterActivity extends AppCompatActivity {

    SharedPreferences session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!session.getString("displayName", "").isEmpty() && !session.getString("username", "").isEmpty()) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
            return;
        }

        BroadcastReceiver newBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        this.registerReceiver(newBroadcastReceiver, intentFilter);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProcess(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

    public void registerProcess(String username, String password) {
        userTable table = new userTable(getApplicationContext());
        tb_user result = table.getUserWithUsernamePassword(username, password);
        if(result == null) {
            tb_user user = new tb_user();
            user.setDisplayName(username);
            user.setUsername(username);
            user.setPassword(password);
            table.addRecord(user);
        }
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.putString("displayName", username);
        editor.putString("username", password);
        editor.apply();
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(intent);
    }
}