package com.example.guilhermeeyng.projetomobile.entidades;

public class TipoCombustivel {

    public static final String NOME_TABELA = "tipos_combustiveis";
    public static final String ID = "_id";
    public static final String NOME = "nome";

    public static final String[] CAMPOS = {ID,NOME};

    private int id;
    private String nome;

    public TipoCombustivel(int id, String nome) {
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
        return "TipoCombustivel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
