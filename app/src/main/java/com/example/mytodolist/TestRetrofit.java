package com.example.mytodolist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TestRetrofit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_retrofit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ApiClient apiClient = new ApiClient();
        TextView testView = findViewById(R.id.sucessView);
        Button send = findViewById(R.id.button);
        send.setOnClickListener(x->{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", "username");
            jsonObject.addProperty("email", "Email");
            jsonObject.addProperty("password", "password");
            RequestBody requestBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            apiClient.textRegister(1, testView);
        });
    }
}