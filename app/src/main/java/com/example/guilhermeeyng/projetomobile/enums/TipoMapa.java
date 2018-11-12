package com.example.guilhermeeyng.projetomobile.enums;

public enum TipoMapa {

    NORMAL(1),
    SATELITE(2),
    TERRENO(3),
    HIBRIDO(4);

    private int id;

    TipoMapa(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
