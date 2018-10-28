package Modules;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duracao implements Serializable{

    private String texto; // 1 dia e 2 horas
    private int valor; // valor em segundos

    public Duracao(String texto, int valor) {
        setTexto(texto);
        setValor(valor);
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        Matcher m = Pattern.compile("(.{0,})([\\D+])([\\s])([\\d+])(.{0,})", Pattern.UNICODE_CASE).matcher(texto);
        m.find();
        if(m.groupCount()==5){
            this.texto = m.group(1) + m.group(2) + " e " + m.group(4) + m.group(5);
        }else{
            this.texto = texto;
        }
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
