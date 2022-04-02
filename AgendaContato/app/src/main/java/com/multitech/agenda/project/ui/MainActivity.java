package com.multitech.agenda.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.multitech.agenda.R;
import com.multitech.agenda.project.dao.AlunoDao;
import com.multitech.agenda.project.model.Aluno;
import com.multitech.agenda.project.ui.adapters.ListAlunoAdapter;

public class MainActivity extends AppCompatActivity implements ConstantsActivitys {
    private ListView listAlunos;
    private ListAlunoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        abreFormAddNovoAluno();
        configListDeAlunos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizaAlunos(new AlunoDao().pegaTodosAlunos());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.aluno_escolha_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.remove_item_selected) abreDialogDeExclusaoAluno(item);

        return super.onContextItemSelected(item);
    }

    private void abreDialogDeExclusaoAluno(@NonNull MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Removendo Aluno")
                .setMessage("Tem certeza que quer deletar o aluno? ")
                .setPositiveButton("SIM", (dialogInterface, i) -> excluiAlunoAutorizado(item))
                .setNegativeButton("NÂO", (dialogInterface, i) -> fechaDialog(dialogInterface))
                .show();
    }

    private void abreFormAddNovoAluno() {
        findViewById(R.id.activity_main_float_button).setOnClickListener(v -> startActivity(new Intent(this, FormAlunoActivity.class)));
    }

    private void configListDeAlunos() {
        listAlunos = findViewById(R.id.activity_main_lista_alunos);
        adapter = new ListAlunoAdapter(getApplicationContext());
        listAlunos.setAdapter(adapter);

        manipulaAlteracoesPorItemSelecionado();
    }

    private void manipulaAlteracoesPorItemSelecionado() {
        listAlunos.setOnItemClickListener((parent, view, position, id) -> abreFormulariParaEdicaoDeAluno(parent, position));
        registerForContextMenu(listAlunos); //registra um contexto de menu

    }

    private void abreFormulariParaEdicaoDeAluno(android.widget.AdapterView<?> parent, int position) {
        Aluno alunoEscolhido = (Aluno) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, FormAlunoActivity.class);
        intent.putExtra(CHAVE_DADOS_ACTIVITYS, alunoEscolhido);
        startActivity(intent);
    }

    private void excluiAlunoAutorizado(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        AlunoDao.removeAluno(alunoEscolhido);
        adapter.remove(alunoEscolhido);

        Toast
                .makeText(this, "Aluno deletado com sucesso", Toast.LENGTH_LONG)
                .show();
    }

    private void fechaDialog(android.content.DialogInterface dialogInterface) {
        dialogInterface.dismiss();
    }

}
