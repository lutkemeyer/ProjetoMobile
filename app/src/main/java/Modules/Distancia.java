package Modules;

import java.io.Serializable;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public class Distancia implements Serializable{

    private String texto;
    private int valor; // valor em metros

    public Distancia(String texto, int valor) {
        setTexto(texto);
        setValor(valor);
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
