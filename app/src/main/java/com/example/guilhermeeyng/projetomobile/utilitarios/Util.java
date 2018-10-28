package com.example.guilhermeeyng.projetomobile.utilitarios;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.NumberFormat;
import java.util.Locale;

/*
classe reponsavel por conter metodos que auxiliam no app, geralmente conversores
 */
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

}
