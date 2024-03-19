package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void signUpPage(View view){
        progressBar = findViewById(R.id.progressBar);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        try {
            Thread.sleep(1000);
            progressBar.setVisibility(View.VISIBLE);
            startActivity(intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void loginPage(View view){
        progressBar = findViewById(R.id.progressBar);
        Intent intent = new Intent(MainActivity.this, SignUpActivity1.class);
        try {
            Thread.sleep(1000);
            progressBar.setVisibility(View.VISIBLE);
            startActivity(intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}