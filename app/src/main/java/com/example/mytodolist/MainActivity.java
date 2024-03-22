package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
ProgressBar progressBar;
ApiClient apiClient;
    EditText email = findViewById(R.id.EmailAddressEditText);
    EditText password = findViewById(R.id.editTextTextPassword);

    EditText username = findViewById(R.id.UsernameEdit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void signUpPage(View view){
        progressBar = findViewById(R.id.progressBar);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();


        jsonObject.addProperty("username", username.getText().toString());
        jsonObject.addProperty("email", email.getText().toString());
        jsonObject.addProperty("password", password.getText().toString());
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        boolean[] confirmed =  apiClient.makePostRequest(requestBody);

        if (confirmed[0]) {
           Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
           startActivity(intent);
       }
    }

    public void loginPage(View view){
        progressBar = findViewById(R.id.progressBar);
        Intent intent = new Intent(MainActivity.this, SignUpActivity1.class);
            progressBar.setVisibility(View.VISIBLE);
            startActivity(intent);
    }

}