package com.example.thodasukoon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thodasukoon.MainActivity;
import com.example.thodasukoon.ApiClient;
import com.example.thodasukoon.ApiService;
import com.example.thodasukoon.LoginRequest;
import com.example.thodasukoon.LoginResponse;
import com.example.thodasukoon.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;

    TextView toLogin;
    private Button btnSignIn;
    private PrefManager prefManager;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = findViewById(R.id.toLogin);

        toLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        prefManager = new PrefManager(this);
        apiService = ApiClient.getClient("").create(ApiService.class);

        btnSignIn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest request = new LoginRequest(email, password);

        btnSignIn.setEnabled(false);
        Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();

        apiService.registerUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnSignIn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();

                    if (response.code() == 201) {
                        prefManager.saveToken(response.body().getToken());

                        Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();



                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (response.code() == 400) {
                        Toast.makeText(Register.this, res.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, res.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnSignIn.setEnabled(true);
                Toast.makeText(Register.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
