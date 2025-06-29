package com.example.finance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.data.User;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etNome, etEmail;
    private TextView tvResult;
    private final String AES_KEY = "1234567890123456"; // chave de 16 bytes
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = new UserManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        tvResult = findViewById(R.id.tvResult);

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String username = getText(etUsername);
            String password = getText(etPassword);
            String nome = getText(etNome);
            String email = getText(etEmail);

            if (username.isEmpty() || password.isEmpty() || nome.isEmpty() || email.isEmpty()) {
                tvResult.setText("Todos os campos são obrigatórios.");
                return;
            }

            userManager.registerUser(username, nome, email, password, result -> runOnUiThread(() -> {
                if (result) {
                    tvResult.setText("Usuário registrado com sucesso.");
                } else {
                    tvResult.setText("Usuário já existe.");
                }
            }));
        });

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String username = getText(etUsername);
            String password = getText(etPassword);

            if (username.isEmpty() || password.isEmpty()) {
                tvResult.setText("Preencha usuário e senha.");
                return;
            }

            userManager.loginUser(username, password, (success, user) -> runOnUiThread(() -> {
                if (success) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("username", user.username);
                    startActivity(intent);
                    finish();
                } else {
                    tvResult.setText("Usuário ou senha inválidos.");
                }
            }));
        });
    }

    private String getText(EditText et) {
        return et.getText().toString().trim();
    }
}
