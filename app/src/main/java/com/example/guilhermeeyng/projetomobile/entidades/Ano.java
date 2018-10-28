package com.example.guilhermeeyng.projetomobile.entidades;

public class Ano {

    public static final String NOME_TABELA = "anos";
    public static final String ID = "_id";
    public static final String NOME = "nome";

    public static final String[] CAMPOS = {ID,NOME};

    private int id;
    private String nome;

    public Ano(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Ano{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
