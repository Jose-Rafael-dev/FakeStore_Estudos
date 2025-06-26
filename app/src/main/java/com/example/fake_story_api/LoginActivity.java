package com.example.fake_story_api;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        rememberMeCheckBox = findViewById(R.id.rememberMe);

        loginButton.setOnClickListener(v -> checkLogin());

        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");

        if (!email.isEmpty() && !password.isEmpty()) {
            emailEditText.setText(email);
            passwordEditText.setText(password);
            rememberMeCheckBox.setChecked(true);
        }


    }

    private void checkLogin() {
        String inputEmail = emailEditText.getText().toString().trim();
        String inputPassword = passwordEditText.getText().toString().trim();


        if (rememberMeCheckBox.isChecked()) {
            SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", inputEmail);
            editor.putString("password", inputPassword);
            editor.apply();
        } else {
            SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("email");
            editor.remove("password");
            editor.apply();
        }

        ApiService apiService = RetrofitInstance.getApiService();
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    boolean found = false;
                    for (User user : response.body()) {
                        if (user.getEmail().equals(inputEmail) &&
                                user.getPassword().equals(inputPassword)) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}