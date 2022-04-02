package com.multitech.agenda.project;

import android.app.Application;

import com.multitech.agenda.project.dao.AlunoDao;
import com.multitech.agenda.project.model.Aluno;

public class AppAplicattion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new AlunoDao().salvaAluno(new Aluno("Henrique", "966365190", "henrique.multitech@gmail.com"));
        new AlunoDao().salvaAluno(new Aluno("Jéssica", "966058874", "jessica.multitech@gmail.com"));
    }
}
