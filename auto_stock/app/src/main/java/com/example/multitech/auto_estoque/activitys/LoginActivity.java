package com.example.multitech.auto_estoque.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multitech.auto_estoque.R;
import com.example.multitech.auto_estoque.repository.FirebaseHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setComponents();
        this.linteningEventClick();
    }

    private Boolean checkInputEmpty(String txt, EditText input, String msg){
        if(txt.trim().isEmpty()){
            input.requestFocus();
            input.setError(msg);
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    private void sendLogin(String email, String password){
        FirebaseHelper
                .getAuth()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> { loginCompleteTask(task);})
                .addOnFailureListener(err -> { loginFailTask(err);});
    }

    private void loginFailTask(Exception err) {
        this.progressBarLogin.setVisibility(View.GONE);
        Log.e("AUTOSTOCK", err.getMessage() );
        Toast.makeText(this, "Falha ao realizar o login " + err.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void loginCompleteTask(Task<AuthResult> task) {
        if(task.isSuccessful()){
            this.progressBarLogin.setVisibility(View.GONE);
            Toast.makeText(this, "Usuario logado com sucesso, redirecionando", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else if(task.isCanceled()){
            this.progressBarLogin.setVisibility(View.GONE);
            Toast.makeText(this, "Ação Cancelada" , Toast.LENGTH_LONG).show();
        }
    }

    private void validatorData(){
        String email = this.loginEmail.getText().toString();
        String password = this.loginPassword.getText().toString();

        boolean passwordIsEmpty = this.checkInputEmpty(password, this.loginPassword, "Informe sua senha!!");
        boolean emailIsEmpty = this.checkInputEmpty(email, this.loginEmail, "Informe seu email!");

        if(!passwordIsEmpty && !emailIsEmpty ){
            this.progressBarLogin.setVisibility(View.VISIBLE);
            this.sendLogin(email, password);
        }
    }

    private void setComponents(){
        this.loginEmail = findViewById(R.id.input_login_email);
        this.loginPassword = findViewById(R.id.input_login_password);
        this.progressBarLogin = findViewById(R.id.progress_bar_login);
    }

    private void linteningEventClick(){
        findViewById(R.id.sign_up).setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
        findViewById(R.id.btn_login).setOnClickListener(view -> { this.validatorData();});
        findViewById(R.id.restore_account).setOnClickListener(view -> {
            startActivity(new Intent(this, RestoreAccount.class));
        });

    }
}