package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity1 extends AppCompatActivity {
private final MainActivity mainActivity;

    public SignUpActivity1(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
    }

    public void loginPage(View view){
        mainActivity.loginPage(view);
    }

}