package com.example.multitech.auto_estoque.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.repository.FirebaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::checkAuthUser, 5000);
    }

    private void checkAuthUser(){
        if(FirebaseHelper.userIsAuth()){
            Toast.makeText(this, "Usuario logado com sucesso", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }else{
            Toast.makeText(this, "Por favor faça login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}