package com.example.mytodolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        bodyView = findViewById(R.id.bodyView);


    }

    public void loginSubmit(View view) throws InterruptedException {
        boolean isValid = Validators.throwEmptyNessError((EditText) findViewById(R.id.etPassword), (EditText)findViewById(R.id.etUsername));
        if (isValid) {
            progressBar.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", username.getText().toString());
            jsonObject.addProperty("password", password.getText().toString());

            RequestBody requestBody = FormBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            ApiService apiService = apiClient.returnApiService();
            Call<JsonObject> login = apiService.sendLoginReq(requestBody);
            login.enqueue(new Callback<JsonObject>() {
                @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    JsonObject responseBody = response.body();
                    SharedPreferences sp = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String userStr = sp.getString("user", null);
                    Gson gson = new Gson();
                    Intent intent;
                    if (userStr != null) {
                        String message = responseBody.get("message").getAsString();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> user = gson.fromJson(userStr, type);
                        user.replace("isLoggedIn", String.valueOf(true));
                        editor.apply();
                         intent = new Intent(LoginActivity.this, AppMain.class);
                        intent.putExtra("user", gson.toJson(user));
                    } else {
                        HashMap<String, String> user = new HashMap<>();
                        int id = responseBody.get("id").getAsInt();
                        user.put("isLoggedIn", String.valueOf(true));
                        user.put("id", String.valueOf(id));
                        editor.putString("user", gson.toJson(user));
                        intent = new Intent(LoginActivity.this, AppMain.class);
                        intent.putExtra("user", gson.toJson(user));
                    }
                    editor.apply();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Body is null ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "not successful ", Toast.LENGTH_SHORT).show();
                System.out.println(response.message());
                System.out.println(response.body());
                }
            }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {
//                    Toast toast = new Toast(LoginActivity.this);
//                    LinearLayout background = findViewById(R.id.server_down);
//                    toast.setView(background);
                    Toast.makeText(LoginActivity.this, "Server Is Down Please Try Again Later", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showPassword(View view){
        Validators.togglePasswordVisibility(password);
    }

    public void dontHaveAcc(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


}