package com.example.guilhermeeyng.projetomobile.entidades;

public class Preferencia {

    public static final String NOME_TABELA = "preferencias";
    public static final String ID = "_id";
    public static final String VALOR = "valor";

    public static final String[] CAMPOS = new String[]{ID, VALOR};

    public static final int ID_COR_DESTAQUE = 1;
    public static final int ID_COR_DESTAQUE_CLARO = 2;
    public static final int ID_COR_SECUNDARIA = 3;
    public static final int ID_COR_SECUNDARIA_CLARO = 4;
    public static final int ID_TIPO_MAPA = 5;

    private int id;
    private int valor;

    public Preferencia(int id, int valor) {
        this.id = id;
        this.valor = valor;
    }
    public Preferencia(int id){
        this(id,0);
    }
    public Preferencia(){
        this(0);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Preferencia{" +
                "id=" + id +
                ", valor=" + valor +
                '}';
    }
}
