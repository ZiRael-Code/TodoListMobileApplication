package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.R;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loginProgress);
    }

    public void loginPage(View view){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity1.class);
        progressBar.setVisibility(View.VISIBLE);
        startActivity(intent);
    }
}