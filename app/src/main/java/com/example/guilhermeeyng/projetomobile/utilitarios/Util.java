package com.example.guilhermeeyng.projetomobile.utilitarios;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    public static String toMonetary(double v){
        return NumberFormat.getCurrencyInstance(new Locale("pt","BR")).format(v);
    }
    public static String toLitros(double v){
        return String.format("%.2f", v ) + " L";
    }
    public static String toConsumo(double v){
        return String.format("%.2f", v ) + " km/L";
    }
    public static String toKm(double km){
        return (km >= 0 ? String.format("%.1f", km) : 0.0) + " km";
    }
    public static String converteTempo(int minutos){
        double horas = Double.valueOf(minutos) / 60;
        return (horas >= 0 ? String.format("%.1f", horas) : 0.0) + " hr" + (horas > 1 ? "s" : "");
    }
}
