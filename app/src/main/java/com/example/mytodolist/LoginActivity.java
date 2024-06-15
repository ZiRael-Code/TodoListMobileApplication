package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    TextView bodyView;
   private ApiClient apiClient = new ApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loginProgress);
        username = findViewById(R.id.loginUsernameEdit);
        password = findViewById(R.id.PasswordEdit);
        bodyView = findViewById(R.id.bodyView);

    }

    public void loginSubmit(View view) throws InterruptedException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username.getText().toString());
        jsonObject.addProperty("password",password.getText().toString());

        RequestBody requestBody = FormBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        apiClient.makeLoginRequest(requestBody, LoginActivity.this, bodyView);
        progressBar.setVisibility(View.VISIBLE);

        finish();
    }

    public void dontHaveAcc(View view){
        Intent intent = new Intent(this, SignUpActivity1.class);
        startActivity(intent);
    }


}