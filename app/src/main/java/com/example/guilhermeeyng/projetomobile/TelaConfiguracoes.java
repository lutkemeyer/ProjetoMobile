package com.example.guilhermeeyng.projetomobile;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StateSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterImagem;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.enums.Tema;
import com.example.guilhermeeyng.projetomobile.utilitarios.ColorPicker;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class TelaConfiguracoes extends AppCompatActivity {

    private Switch swTemaClaro, swTemaEscuro, swTemaCustomizado, swTemaPadrao;
    private GridLayout containerTemaCustomizado;
    private FloatingActionButton btnCorDestaque, btnCorDestaqueClara, btnCorSecundaria, btnCorSecundariaClara;
    private RadioButton rbMapaNormal, rbMapaSatelite, rbMapaTerreno, rbMapaHibrido;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracoes);

        swTemaPadrao = findViewById(R.id.swTemaPadrao);
        swTemaClaro = findViewById(R.id.swTemaClaro);
        swTemaEscuro = findViewById(R.id.swTemaEscuro);
        swTemaCustomizado = findViewById(R.id.swTemaCustomizado);

        rbMapaNormal = findViewById(R.id.rbMapaNormal);
        rbMapaSatelite = findViewById(R.id.rbMapaSatelite);
        rbMapaTerreno = findViewById(R.id.rbMapaTerreno);
        rbMapaHibrido = findViewById(R.id.rbMapaHibrido);

        btnCorDestaque = findViewById(R.id.btnCorDestaque);
        btnCorDestaqueClara = findViewById(R.id.btnCorDestaqueClara);
        btnCorSecundaria = findViewById(R.id.btnCorSecundaria);
        btnCorSecundariaClara = findViewById(R.id.btnCorSecundariaClara);

        containerTemaCustomizado = findViewById(R.id.containerTemaCustomizado);
        viewPager = findViewById(R.id.viewPager);

        btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLight)));
        btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        btnCorSecundariaClara.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

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
                containerTemaCustomizado.setVisibility(View.VISIBLE);
                break;

        }

        viewPager.setAdapter(new AdapterImagem(TelaConfiguracoes.this));

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
        rbMapaNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(0, true);
                }else{

                }
            }
        });
        rbMapaSatelite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(1, true);
                }else{

                }
            }
        });
        rbMapaTerreno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(2, true);
                }else{

                }
            }
        });
        rbMapaHibrido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(3, true);
                }else{

                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0: // se a pagina for do tipo normal
                        rbMapaNormal.setChecked(true);
                        break;
                    case 1: // se a pagina for do tipo satelite
                        rbMapaSatelite.setChecked(true);
                        break;
                    case 2: // se a pagina for do tipo terreno
                        rbMapaTerreno.setChecked(true);
                        break;
                    case 3: // se a pagina for do tipo hibrido
                        rbMapaHibrido.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrolled(int i, float v, int i1) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }

    public void aplicarTema(Tema tema){

    }

    public void onClickCor(View view){
        int corDoBotao = view.getBackgroundTintList().getColorForState(new int[]{android.R.attr.state_pressed}, view.getBackgroundTintList().getDefaultColor());
        ColorPicker.mostra(TelaConfiguracoes.this, view, corDoBotao);
    }

    public void toast(String m){
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
}
