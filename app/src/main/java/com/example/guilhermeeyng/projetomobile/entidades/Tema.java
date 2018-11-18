package com.example.guilhermeeyng.projetomobile.entidades;

import android.graphics.Color;

import java.io.Serializable;

public class Tema implements Serializable {

    public static final String NOME_TABELA = "tema";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String COR_DESTAQUE = "cor_destaque";
    public static final String COR_DESTAQUE_CLARA = "cor_destaque_clara";
    public static final String COR_SECUNDARIA = "cor_secundaria";

    private int id;
    private String nome;
    private String corDestaque;
    private String corDestaqueClara;
    private String corSecundaria;

    public Tema(int id, String nome, String corDestaque, String corDestaqueClara, String corSecundaria) {
        this.id = id;
        this.nome = nome;
        this.corDestaque = corDestaque;
        this.corDestaqueClara = corDestaqueClara;
        this.corSecundaria = corSecundaria;
    }

    public Tema(int id, String nome) {
        this(id,nome,"#000000","#000000","#000000");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCorDestaque() {
        return corDestaque;
    }

    public void setCorDestaque(String corDestaque) {
        this.corDestaque = corDestaque;
    }

    public String getCorDestaqueClara() {
        return corDestaqueClara;
    }

    public void setCorDestaqueClara(String corDestaqueClara) {
        this.corDestaqueClara = corDestaqueClara;
    }

    public String getCorSecundaria() {
        return corSecundaria;
    }

    public void setCorSecundaria(String corSecundaria) {
        this.corSecundaria = corSecundaria;
    }

    public int getCorDestaqueInt(){
        return getCor(corDestaque);
    }

    public int getCorDestaqueClaraInt(){
        return getCor(corDestaqueClara);
    }

    public int getCorSecundariaInt(){
        return getCor(corSecundaria);
    }

    private int getCor(String cor){
        return Color.parseColor(cor);
    }

}
