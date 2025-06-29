package com.example.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.data.User;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etNome, etEmail;
    private TextView tvResult;
    private final String AES_KEY = "1234567890123456"; // chave de 16 bytes
    private UserManager userManager;

    private ImageView imgFoto;
    private Bitmap fotoBitmap;
    private Uri imageUri;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private static final int CAMERA_PERMISSION_CODE = 100;

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

        imgFoto = findViewById(R.id.imgFoto);
        imageUri = createUri();
        registerPictureLauncher();

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String username = getText(etUsername);
            String password = getText(etPassword);
            String nome = getText(etNome);
            String email = getText(etEmail);

            if (username.isEmpty() || password.isEmpty() || nome.isEmpty() || email.isEmpty()) {
                tvResult.setText("Todos os campos são obrigatórios.");
                return;
            }

            if (fotoBitmap == null) {
                tvResult.setText("Tire uma foto para continuar.");
                return;
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] fotoBytes = stream.toByteArray();

            userManager.registerUser(username, nome, email, password, fotoBytes, result -> runOnUiThread(() -> {
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

    private Uri createUri() {
        File imageFile = new File(getFilesDir(), "user_photo.jpg");
        return FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                imageFile
        );
    }

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        imgFoto.setImageURI(null);
                        imgFoto.setImageURI(imageUri);
                        try {
                            fotoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void capturarImagem(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            takePictureLauncher.launch(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureLauncher.launch(imageUri);
        }
    }
}
