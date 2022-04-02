package com.multitech.agenda.project.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multitech.agenda.R;
import com.multitech.agenda.project.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListAlunoAdapter extends BaseAdapter {
    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;

    public ListAlunoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewInflate = criaViewList(viewGroup);

        setaDadosNaViewItem(posicao, viewInflate);

        return viewInflate;
    }

    private View criaViewList(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, viewGroup, false);
    }

    private void setaDadosNaViewItem(int posicao, View viewInflate) {
        TextView textNome = viewInflate.findViewById(R.id.item_aluno_nome);
        TextView textTelefone = viewInflate.findViewById(R.id.item_aluno_telefone);

        String nomeAlunoString = context.getString(R.string.aluno_nome_list);
        String telefoneAlunoString = context.getString(R.string.aluno_telefone_list);

        textNome.setText(String.format(nomeAlunoString, alunos.get(posicao).getValorNome()));
        textTelefone.setText(String.format(telefoneAlunoString, alunos.get(posicao).getValorTelefone()));
    }

    public void atualizaAlunos(List<Aluno> pegaTodosAlunos) {
        alunos.clear();
        alunos.addAll(pegaTodosAlunos);
        notifyDataSetChanged();
    }

    public void remove(Aluno alunoEscolhido) {

        alunos.remove(alunoEscolhido);
        notifyDataSetChanged();
    }
}
