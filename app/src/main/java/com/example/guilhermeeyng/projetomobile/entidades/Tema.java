package com.example.guilhermeeyng.projetomobile.entidades;

import android.graphics.Color;

import java.io.Serializable;

public class Tema implements Serializable {

    public static final String NOME_TABELA = "temas";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String COR_DESTAQUE = "cor_destaque";
    public static final String COR_DESTAQUE_CLARA = "cor_destaque_clara";
    public static final String COR_SECUNDARIA = "cor_secundaria";
    public static final String COR_SECUNDARIA_CLARA = "cor_secundaria_clara";

    private int id;
    private String nome;
    private String corDestaque;
    private String corDestaqueClara;
    private String corSecundaria;
    private String corSecundariaClara;

    public Tema(int id, String nome, String corDestaque, String corDestaqueClara, String corSecundaria, String corSecundariaClara) {
        this.id = id;
        this.nome = nome;
        this.corDestaque = corDestaque;
        this.corDestaqueClara = corDestaqueClara;
        this.corSecundaria = corSecundaria;
        this.corSecundariaClara = corSecundariaClara;
    }

    public Tema(int id, String nome) {
        this(id,nome,"#000000","#000000","#000000", "#000000");
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
        return toIntColor(corDestaque);
    }

    public int getCorDestaqueClaraInt(){
        return toIntColor(corDestaqueClara);
    }

    public int getCorSecundariaInt(){
        return toIntColor(corSecundaria);
    }

    public void setCorDestaqueInt(int cor){
        this.setCorDestaque(toHexStringColor(cor));
    }

    public void setCorDestaqueClaraInt(int cor){
        this.setCorDestaqueClara(toHexStringColor(cor));
    }

    public void setCorSecundariaInt(int cor){
        this.setCorSecundaria(toHexStringColor(cor));
    }

    public void setCorSecundariaClaraInt(int cor){
        this.setCorSecundariaClara(toHexStringColor(cor));
    }

    public int getCorSecundariaClaraInt(){
        return toIntColor(corSecundariaClara);
    }

    public String getCorSecundariaClara() {
        return corSecundariaClara;
    }

    public void setCorSecundariaClara(String corSecundariaClara) {
        this.corSecundariaClara = corSecundariaClara;
    }

    private int toIntColor(String cor){
        return Color.parseColor(cor);
    }

    private String toHexStringColor(int cor){
        return "#"+Integer.toHexString(cor);
    }

    @Override
    public String toString() {
        return "Tema{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", corDestaque='" + corDestaque + '\'' +
                ", corDestaqueClara='" + corDestaqueClara + '\'' +
                ", corSecundaria='" + corSecundaria + '\'' +
                ", corSecundariaClara='" + corSecundariaClara + '\'' +
                '}';
    }
}
