package com.example.mytodolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.google.gson.JsonObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText username;
    EditText password;
   private ApiClient apiClient = new ApiClient();
    JsonObject[] responses = new JsonObject[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loginProgress);
        username = findViewById(R.id.loginUsernameEdit);
        password = findViewById(R.id.PasswordEdit);

    }

    public void loginSubmit(View view){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username.getText().toString());
        jsonObject.addProperty("password",password.getText().toString());

        RequestBody requestBody = FormBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        responses[0] = (apiClient.makelLoginRequest(requestBody, LoginActivity.this));
        progressBar.setVisibility(View.VISIBLE);

    }

    public JsonObject userLogin() {
        return responses[0];
    }

}