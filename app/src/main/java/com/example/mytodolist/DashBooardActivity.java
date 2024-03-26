package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;

import okhttp3.Response;

public class DashBooardActivity extends AppCompatActivity {
    LoginActivity loginActivity = new LoginActivity();
    HorizontalScrollView horizontalScrollView;
    static JsonObject[] response = new JsonObject[1];
    TextView textLoginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_booard);
        horizontalScrollView = findViewById(R.id.taskView);
        textLoginResponse = findViewById(R.id.testLoginResponse);
//        View newLayout = getLayoutInflater().inflate(R.layout.activity_dash_booard, null);
//        horizontalScrollView.addView(newLayout);
        response[0] = loginActivity.userLogin();
        textLoginResponse.setText(response.toString());
        System.out.println(response[0]);

    }

//    public static void main(String[] args) {
//        System.out.println("hello world");
//    }

}