package com.example.guilhermeeyng.projetomobile.enums;

import com.example.guilhermeeyng.projetomobile.entidades.Preferencia;

public enum Tema {

    PADRAO(1, -16757093,-6109697,-14606047,-14079703),
    CLARO(2, -16757093,-6109697,-14606047,-14079703),
    ESCURO(3, -16757093,-6109697,-14606047,-14079703),
    CUSTOMIZADO(4,0,0,0,0);

    private int tipo;

    private int corDestaque, corDestaqueClaro, corSecundaria, corSecundariaClaro;

    Tema(int tipo, int corDestaque, int corDestaqueClaro, int corSecundaria, int corSecundariaClaro){
        this.tipo = tipo;
        this.corDestaque = corDestaque;
        this.corDestaqueClaro = corDestaqueClaro;
        this.corSecundaria = corSecundaria;
        this.corSecundariaClaro = corSecundariaClaro;
    }

    public boolean isPadrao(Preferencia... preferencias){
        return confere(preferencias);
    }

    public boolean isClaro(Preferencia... preferencias){
        return confere(preferencias);
    }

    public boolean isEscuro(Preferencia... preferencias){
        return confere(preferencias);
    }

    public boolean isCustomizado(Preferencia... preferencias){
        return !isClaro(preferencias) && !isEscuro(preferencias) && !isPadrao(preferencias);
    }

    private boolean confere(Preferencia... preferencias){
        int corDestaque = preferencias[0].getValor();
        int corDestaqueClara = preferencias[1].getValor();
        int corSecundaria = preferencias[2].getValor();
        int corSecundariaClara = preferencias[3].getValor();

        return corDestaque==this.corDestaque &&
                corDestaqueClara==this.corDestaqueClaro &&
                corSecundaria==this.corSecundaria &&
                corSecundariaClara==this.corSecundariaClaro;
    }

    public int getCorDestaque() {
        return corDestaque;
    }

    public int getCorDestaqueClaro() {
        return corDestaqueClaro;
    }

    public int getCorSecundaria() {
        return corSecundaria;
    }

    public int getCorSecundariaClaro() {
        return corSecundariaClaro;
    }
}
