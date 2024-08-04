package com.example.mytodolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

   private Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        String userStr = sp.getString("user", null);
        Gson gson = new Gson();
        Intent intent;
        if (userStr != null) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> user = gson.fromJson(userStr, type);
            boolean logged = Boolean.getBoolean(Objects.requireNonNull(user.get("isLoggedIn")));
            if (logged) {
                intent = new Intent(this, AppMain.class);
                intent.putExtra("user", userStr);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
        }else {
            System.out.println("______________________\nthere a problem with user str");

        }
    }


    public void getStarted(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }




}