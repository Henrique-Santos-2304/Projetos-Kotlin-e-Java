package com.multitech.agenda.project.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
    private int id = 0;
    private  String valorNome;
    private  String valorTelefone;
    private  String valorEmail;

    public Aluno() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValorNome(String valorNome) {
        this.valorNome = valorNome;
    }

    public void setValorTelefone(String valorTelefone) {
        this.valorTelefone = valorTelefone;
    }

    public void setValorEmail(String valorEmail) {
        this.valorEmail = valorEmail;
    }

    public String getValorNome() {
        return valorNome;
    }


    public String getValorTelefone() {
        return valorTelefone;
    }


    public String getValorEmail() {
        return valorEmail;
    }

    @NonNull
    @Override
    public String toString() {
        return valorNome;
    }

    public Aluno(String valorNome, String valorTelefone, String valorEmail) {
        this.valorNome = valorNome;
        this.valorTelefone = valorTelefone;
        this.valorEmail = valorEmail;
    }


    public boolean temIdValido() {
        return id > 0 ;
    }
}
