package com.multitech.agenda.project.dao;

import androidx.annotation.Nullable;

import com.multitech.agenda.project.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {
    private final static List<Aluno> alunosLists = new ArrayList<>();
    private  static int contadorId = 1;

    public static void removeAluno(Aluno alunoEscolhido) {
        Aluno alunoEncontrado = buscaALunoPorId(alunoEscolhido);
        if(alunoEncontrado != null){
            alunosLists.remove(alunoEscolhido);
        }
    }

    public void salvaAluno(Aluno aluno) {
        aluno.setId(contadorId);
        alunosLists.add(aluno);
        contadorId++;
    }

    public void editaAluno(Aluno aluno){
        Aluno alunoEncontrado = buscaALunoPorId(aluno);
        //Atualiza os dados se os id corresponderem
        if(alunoEncontrado != null) alunosLists.set(alunosLists.indexOf(alunoEncontrado), aluno);
    }

    @Nullable
    private static Aluno buscaALunoPorId(Aluno aluno) {

        for (Aluno al: alunosLists) {
            if(al.getId() == aluno.getId()) return al;
        }
        return null;
    }

    public List<Aluno> pegaTodosAlunos() {
        return new ArrayList<>(alunosLists);
    }
}
