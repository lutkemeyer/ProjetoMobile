package com.example.guilhermeeyng.projetomobile.entidades;

public class Endereco {

    public static final String NOME_TABELA = "enderecos";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String QTD = "qtd";

    public static final String[] CAMPOS = {ID,NOME,QTD};

    private int id;
    private String nome;
    private int qtd;

    public Endereco(int id, String nome, int qtd) {
        this.id = id;
        this.nome = nome;
        this.qtd = qtd;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public int getQtd() {
        return qtd;
    }
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", qtd=" + qtd +
                '}';
    }
}
