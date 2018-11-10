package com.example.guilhermeeyng.projetomobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.enums.Tema;

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

        Tema temaUsuario = new Dao(TelaConfiguracoes.this).getTemaUsuario();

        switch (temaUsuario){
            case PADRAO:
                swTemaPadrao.setChecked(true);

                break;
            case CLARO:
                swTemaClaro.setChecked(true);

                break;
            case ESCURO:
                swTemaEscuro.setChecked(true);

                break;
            case CUSTOMIZADO:
                swTemaCustomizado.setChecked(true);

                break;

        }





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
                    aplicarTema(Tema.PADRAO);
                }else{
                    if(!swTemaClaro.isChecked() && !swTemaEscuro.isChecked() && !swTemaCustomizado.isChecked()){
                        swTemaPadrao.setChecked(true);
                    }
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
                    aplicarTema(Tema.CLARO);
                }else{
                    if(!swTemaPadrao.isChecked() && !swTemaEscuro.isChecked() && !swTemaCustomizado.isChecked()){
                        swTemaClaro.setChecked(true);
                    }
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
                    aplicarTema(Tema.ESCURO);
                }else{
                    if(!swTemaClaro.isChecked() && !swTemaPadrao.isChecked() && !swTemaCustomizado.isChecked()){
                        swTemaEscuro.setChecked(true);
                    }
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
                    aplicarTema(Tema.CUSTOMIZADO);
                    containerTemaCustomizado.setVisibility(View.VISIBLE);
                }else{
                    if(!swTemaClaro.isChecked() && !swTemaEscuro.isChecked() && !swTemaPadrao.isChecked()){
                        swTemaCustomizado.setChecked(true);
                    }else{
                        containerTemaCustomizado.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    public void aplicarTema(Tema tema){

    }
}
