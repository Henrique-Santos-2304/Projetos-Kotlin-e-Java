package com.example.multitech.auto_estoque.activitys;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.models.User;
import com.example.multitech.auto_estoque.repository.FirebaseHelper;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputName, inputEmail, inputPassword;
    private ProgressBar progressBar;
    private User newUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setComponents();
        this.listeningEventClicks();
    }

    private Boolean checkInputEmpty(String txt, EditText input, String msg) {
        if (txt.trim().isEmpty()) {
            input.requestFocus();
            input.setError(msg);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void listeningEventClicks() {
        findViewById(R.id.btn_send_data).setOnClickListener(v -> {
            this.validatorData();
        });
        findViewById(R.id.go_back_login).setOnClickListener(v -> finish());
    }

    private void validatorData() {
        String name = this.inputName.getText().toString();
        String email = this.inputEmail.getText().toString();
        String password = this.inputPassword.getText().toString();

        boolean passwordIsEmpty = this.checkInputEmpty(password, this.inputPassword, "Informe sua senha!!");
        boolean emailIsEmpty = this.checkInputEmpty(email, this.inputEmail, "Informe seu email!");
        boolean nameIsEmpty = this.checkInputEmpty(name, this.inputName, "Informe seu nome!");

        if (!passwordIsEmpty && !emailIsEmpty && !nameIsEmpty) {
            this.progressBar.setVisibility(View.VISIBLE);
            this.newUser = new User();
            this.newUser.setName(name);
            this.newUser.setEmail(email);
            this.newUser.setPassword(password);
            this.saveUser();
        }

    }

    private void saveUser() {
        String email = this.newUser.getEmail();
        String password = this.newUser.getPassword();

        FirebaseHelper
                .getAuth()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    saveUserComplete(task);
                })
                .addOnFailureListener(err -> {
                    saveUserFailure(err);
                });

    }

    private void saveUserFailure(Exception err) {
        this.progressBar.setVisibility(View.GONE);
        Log.e("AUTOSTOCK", err.getMessage());
        Toast.makeText(this, "Falha ao salvar usuario" + err.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void saveUserComplete(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(this, "Usuario Cadastrado com sucesso", Toast.LENGTH_LONG).show();
            String id = task.getResult().getUser().getUid();
            this.newUser.setId(id);
            this.progressBar.setVisibility(View.GONE);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (task.isCanceled()) {
            this.progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Ação Cancelada", Toast.LENGTH_LONG).show();
        }
    }

    private void setComponents() {
        this.inputName = findViewById(R.id.input_signup_name);
        this.inputEmail = findViewById(R.id.input_signup_email);
        this.inputPassword = findViewById(R.id.input_signup_password);
        this.progressBar = findViewById(R.id.progrss_bar_signup);
    }


}