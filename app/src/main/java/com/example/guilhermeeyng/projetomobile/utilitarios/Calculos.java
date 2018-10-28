package com.example.guilhermeeyng.projetomobile.utilitarios;

import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;

/*
classe responsável por realizar os calculos especificos da aplicacao
 */
public class Calculos {

    private static final double TAXA_ESTRADA = 0.9;
    private static final double TAXA_CIDADE = 0.1;

    public static double litros(double consumo, double km){
        return km/consumo;
    }
    public static double valorGasto(double litros, double valorCombustivel){
        return litros * valorCombustivel;
    }
    public static double consumo(Veiculo v){
        switch (v.getTipoCombustivel().getNome()){
            case "GASOLINA":
                return v.getConsGasDieselCidade() * TAXA_CIDADE + v.getConsGasDieselEstrada() * TAXA_ESTRADA;
            case "ETANOL":
                return v.getConsEtanolCidade() * TAXA_CIDADE + v.getConsEtanolEstrada() * TAXA_ESTRADA;
            case "DIESEL":
                return v.getConsGasDieselCidade() * TAXA_CIDADE + v.getConsGasDieselEstrada() * TAXA_ESTRADA;
        }
        return 0.0;
    }
    public static double consumo(Veiculo v, String tipoCombustivel){
        if(v.getTipoCombustivel().getNome().equals("FLEX")){
            switch (tipoCombustivel) {
                case "GASOLINA":
                    return v.getConsGasDieselCidade() * TAXA_CIDADE + v.getConsGasDieselEstrada() * TAXA_ESTRADA;
                case "ETANOL":
                    return v.getConsEtanolCidade() * TAXA_CIDADE + v.getConsEtanolEstrada() * TAXA_ESTRADA;
            }
        }else{
            return consumo(v);
        }
        return 0.0;
    }
    public static double km(int metros){
        double km = Double.valueOf(metros)/1000;
        return km >= 0 ? km : 0.0;
    }
}
