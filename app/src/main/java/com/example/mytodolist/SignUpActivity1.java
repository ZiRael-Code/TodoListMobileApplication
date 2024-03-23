package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignUpActivity1 extends AppCompatActivity {
    ProgressBar progressBar;

    EditText password;
    EditText username;
    EditText email;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        email = findViewById(R.id.EmailAddressEditText);
        password = findViewById(R.id.editTextTextPassword);
        username = findViewById(R.id.UsernameEdit);
        textView = findViewById(R.id.error);
        ApiClient apiClient = new ApiClient();
        button = findViewById(R.id.submit);
        progressBar = findViewById(R.id.signUpprogressBar);


        button.setOnClickListener(x->{
            progressBar.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", username.getText().toString());
            jsonObject.addProperty("email", email.getText().toString());
            jsonObject.addProperty("password", password.getText().toString());
            RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            apiClient.makePostRequest(requestBody, textView, SignUpActivity1.this);


        });
    }





}
