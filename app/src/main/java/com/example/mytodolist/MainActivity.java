package com.example.mytodolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

   private Button login;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(ApiClient.LOCAL_STORAGE, MODE_PRIVATE);
        String id = sharedPreferences.getString("jsonResponse", null);
        boolean loginStatus = sharedPreferences.getBoolean("loginStatus", false);
            if (loginStatus) {
                Intent intent = new Intent(this, DashBooardActivity.class);
                intent.putExtra("jsonResponse", id);
                startActivity(intent);
                finish();
            } else {
                login = findViewById(R.id.loginPage);
                signUp = findViewById(R.id.signUpPage);
            }
        }


    public void loginPages(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void signUpPages(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity1.class);
        startActivity(intent);
    }





}