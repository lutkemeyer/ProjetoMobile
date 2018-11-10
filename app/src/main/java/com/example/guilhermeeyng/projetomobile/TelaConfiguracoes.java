package com.example.guilhermeeyng.projetomobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

public class TelaConfiguracoes extends AppCompatActivity {

    private Switch swTemaClaro, swTemaEscuro, swTemaCustomizado, swTemaPadrao;
    private LinearLayout containerTemaCustomizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes);

        swTemaPadrao = findViewById(R.id.swTemaPadrao);
        swTemaClaro = findViewById(R.id.swTemaClaro);
        swTemaEscuro = findViewById(R.id.swTemaEscuro);
        swTemaCustomizado = findViewById(R.id.swTemaCustomizado);

        containerTemaCustomizado = findViewById(R.id.containerTemaCustomizado);







        listeners();

    }

    private void listeners() {
        swTemaPadrao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaClaro.setChecked(false);
                    swTemaEscuro.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                }else{

                }
            }
        });

        swTemaClaro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaPadrao.setChecked(false);
                    swTemaEscuro.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                }else{

                }
            }
        });

        swTemaEscuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaPadrao.setChecked(false);
                    swTemaClaro.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                }else{

                }
            }
        });

        swTemaCustomizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaPadrao.setChecked(false);
                    swTemaClaro.setChecked(false);
                    swTemaEscuro.setChecked(false);
                    containerTemaCustomizado.setVisibility(View.VISIBLE);
                }else{
                    containerTemaCustomizado.setVisibility(View.GONE);
                }
            }
        });

    }
}
