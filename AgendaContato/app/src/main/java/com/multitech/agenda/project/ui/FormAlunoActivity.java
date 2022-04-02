package com.multitech.agenda.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.multitech.agenda.R;
import com.multitech.agenda.project.dao.AlunoDao;
import com.multitech.agenda.project.model.Aluno;

public class FormAlunoActivity extends AppCompatActivity implements ConstantsActivitys {

    private static final String TITULO_APPBAR_EDITAR_ALUNO = "Editar Aluno";
    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";

    private EditText inputNome, inputTelefone, inputEmail;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aluno);

        inicializacaoMetodos();

        carregaAlunoNoFormulario();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.salvar_aluno_form) configBtnSalvar();
        return super.onOptionsItemSelected(item);
    }

    private void inicializacaoMetodos() {
        inputNome = findViewById(R.id.activity_form_name_input);
        inputTelefone = findViewById(R.id.activity_form_telephone_input);
        inputEmail = findViewById(R.id.activity_form_email_input);
    }

    private void configBtnSalvar() {
            pegaAluno();
            if (aluno.temIdValido()) editaAluno(aluno);
            else salvarAluno(aluno);
    }

    private void pegaAluno() {
        String valorNome = inputNome.getText().toString();
        String valorTelefone = inputTelefone.getText().toString();
        String valorEmail = inputEmail.getText().toString();

        aluno.setValorNome(valorNome);
        aluno.setValorTelefone(valorTelefone);
        aluno.setValorEmail(valorEmail);
    }

    private void editaAluno(Aluno aluno) {
        new AlunoDao().editaAluno(aluno);
        //retorna para as Listas
        Toast.makeText(this, "Dados atualizados com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }

    private void salvarAluno(Aluno aluno) {
        new AlunoDao().salvaAluno(aluno);
        //retorna para as Listas
        Toast.makeText(this, "ALuno salvo com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }

    private void carregaAlunoNoFormulario() {
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_DADOS_ACTIVITYS)){
            setTitle(TITULO_APPBAR_EDITAR_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_DADOS_ACTIVITYS);

            inputNome.setText(aluno.getValorNome());
            inputTelefone.setText(aluno.getValorTelefone());
            inputEmail.setText(aluno.getValorEmail());

        }
        else{
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

}