package Modules;

import java.io.Serializable;

public class Duracao implements Serializable{

    private String texto;
    private int valor; // valor em segundos

    public Duracao(String texto, int valor) {
        this.texto = texto;
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
