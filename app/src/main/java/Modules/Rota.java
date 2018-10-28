package Modules;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class Rota {

    private Distancia distancia;
    private Duracao duracao;
    private String enderecoFinal;
    private LatLng localFinal;
    private String enderecoInicial;
    private LatLng localInicial;

    private List<LatLng> pontos;

    public Rota(Distancia distancia, Duracao duracao, String enderecoFinal, LatLng localFinal, String enderecoInicial, LatLng localInicial, List<LatLng> pontos) {
        this.distancia = distancia;
        this.duracao = duracao;
        this.enderecoFinal = enderecoFinal;
        this.localFinal = localFinal;
        this.enderecoInicial = enderecoInicial;
        this.localInicial = localInicial;
        this.pontos = pontos;
    }
    public Rota() {
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public void setDistancia(Distancia distancia) {
        this.distancia = distancia;
    }

    public Duracao getDuracao() {
        return duracao;
    }

    public void setDuracao(Duracao duracao) {
        this.duracao = duracao;
    }

    public String getEnderecoFinal() {
        return enderecoFinal;
    }

    public void setEnderecoFinal(String enderecoFinal) {
        this.enderecoFinal = enderecoFinal;
    }

    public LatLng getLocalFinal() {
        return localFinal;
    }

    public void setLocalFinal(LatLng localFinal) {
        this.localFinal = localFinal;
    }

    public String getEnderecoInicial() {
        return enderecoInicial;
    }

    public void setEnderecoInicial(String enderecoInicial) {
        this.enderecoInicial = enderecoInicial;
    }

    public LatLng getLocalInicial() {
        return localInicial;
    }

    public void setLocalInicial(LatLng localInicial) {
        this.localInicial = localInicial;
    }

    public List<LatLng> getPontos() {
        return pontos;
    }

    public void setPontos(List<LatLng> pontos) {
        this.pontos = pontos;
    }
}
