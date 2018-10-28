package Modules;

public class Duracao {

    private String texto;
    private int valor;

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
