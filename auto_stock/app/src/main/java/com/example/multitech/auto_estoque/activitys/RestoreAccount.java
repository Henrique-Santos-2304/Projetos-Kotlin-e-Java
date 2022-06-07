package com.example.multitech.auto_estoque.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.repository.FirebaseHelper;

public class RestoreAccount extends AppCompatActivity {
    private ProgressBar progressBarRestore;
    private EditText inputRestoreEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_account);

        this.setComponents();
        this.listeningOnClick();
    }

    private void  sendEmail(String email){
        FirebaseHelper
                .getAuth()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    this.progressBarRestore.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Mensagem enviada com sucesso \n Siga as intrução na sua caixa de entrada", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                    else if (task.isCanceled()){
                        Toast.makeText(this, "Ação Cancelada", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(this, "Erro ao realizar essa ação", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(err -> {
                    this.progressBarRestore.setVisibility(View.GONE);
                    Log.e("AUTOSTOCK", err.getMessage() );
                    Toast.makeText(this, "Error ao realizar essa ação \n " + err.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void validatorData(){
        String email = this.inputRestoreEmail.getText().toString();
        if(email.trim().isEmpty()){
            this.inputRestoreEmail.requestFocus();
            this.inputRestoreEmail.setError("Digite seu email");
        }else{
            this.progressBarRestore.setVisibility(View.VISIBLE);
            this.sendEmail(email);
        }
    }

    private void setComponents(){
        this.progressBarRestore = findViewById(R.id.progress_bar_restore);
        this.inputRestoreEmail = findViewById(R.id.input_restore_email);
    }

    private void listeningOnClick(){
        findViewById(R.id.btn_restore_account).setOnClickListener(view -> {
            this.validatorData();
        });
    }
}