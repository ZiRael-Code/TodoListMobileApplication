package com.example.mytodolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.example.mytodolist.ChatConnect.ApiService;
import com.example.mytodolist.VoidHelpers.Validators;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
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
        email = findViewById(R.id.emailEdit);
        password = findViewById(R.id.PasswordEdit);
        username = findViewById(R.id.UsernameEdit);
        textView = findViewById(R.id.error);
        ApiClient apiClient = new ApiClient();
        button = findViewById(R.id.submit);
        progressBar = findViewById(R.id.signUpprogressBar);

    button.setOnClickListener(x->{
        boolean isValid = Validators.throwEmptyNessError(password, username, email);
        if (isValid) {
            progressBar.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", username.getText().toString());
            jsonObject.addProperty("email", email.getText().toString());
            jsonObject.addProperty("password", password.getText().toString());
            RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            apiClient.registerReq(requestBody, textView, SignUpActivity.this);

            ApiService apiService = apiClient.returnApiService();
            Call<JsonObject> signup = apiService.sendLoginReq(requestBody);
            signup.enqueue(new Callback<JsonObject>() {
                @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Intent intent;
                        Gson gson = new Gson();
                        JsonObject responseBody = response.body();
                        SharedPreferences sp = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        HashMap<String, String> user = new HashMap<>();
                        int id = responseBody.get("id").getAsInt();
                        user.put("isLoggedIn", String.valueOf(false));
                        user.put("id", String.valueOf(id));
                        editor.putString("user", gson.toJson(user));
                        intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.putExtra("user", gson.toJson(user));
                        editor.apply();
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Body is null ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "not successful ", Toast.LENGTH_SHORT).show();
                    System.out.println(response.message());
                    System.out.println(response.body());
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Toast.makeText(SignUpActivity.this, "Server Is Down Please Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });
    }
    });
    }

    public void upLogAC(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void showPassword(View view){
        Validators.togglePasswordVisibility(password);
    }
}
