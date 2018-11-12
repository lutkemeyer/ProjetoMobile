package com.example.guilhermeeyng.projetomobile;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterImagem;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Preferencia;
import com.example.guilhermeeyng.projetomobile.enums.Tema;
import com.example.guilhermeeyng.projetomobile.enums.TipoMapa;
import com.example.guilhermeeyng.projetomobile.utilitarios.ColorPicker;
import com.flask.colorpicker.OnColorSelectedListener;

public class TelaConfiguracoes extends AppCompatActivity {

    private Switch swTemaClaro, swTemaEscuro, swTemaCustomizado, swTemaPadrao;
    private GridLayout containerTemaCustomizado;
    private FloatingActionButton btnCorDestaque, btnCorDestaqueClara, btnCorSecundaria, btnCorSecundariaClara;
    private RadioButton rbMapaNormal, rbMapaSatelite, rbMapaTerreno, rbMapaHibrido;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_preferencias);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setElevation(0);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.titulo_tela_selecao_carro));
        actionBar.setDisplayHomeAsUpEnabled(true);

        swTemaPadrao = findViewById(R.id.swTemaPadrao);
        //swTemaClaro = findViewById(R.id.swTemaClaro);
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

        //containerTemaCustomizado = findViewById(R.id.containerTemaCustomizado);
        viewPager = findViewById(R.id.viewPager);

        btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLight)));
        btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        btnCorSecundariaClara.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

        viewPager.setAdapter(new AdapterImagem(TelaConfiguracoes.this));



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

        listeners();

        TipoMapa tipoMapaUsuario = new Dao(TelaConfiguracoes.this).getTipoMapaUsuario();

        switch (tipoMapaUsuario){
            case NORMAL:
                rbMapaNormal.setChecked(true);
                break;
            case SATELITE:
                rbMapaSatelite.setChecked(true);
                break;
            case TERRENO:
                rbMapaTerreno.setChecked(true);
                break;
            case HIBRIDO:
                rbMapaHibrido.setChecked(true);
                break;
        }

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
                    //new Dao(TelaConfiguracoes.this).salvarPreferencia(new Preferencia(Preferencia.ID_TIPO_MAPA));
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
        new Dao(TelaConfiguracoes.this).salvarTema(tema);
    }

    public void onClickCor(final View view){
        int corDoBotao = view.getBackgroundTintList().getColorForState(new int[]{android.R.attr.state_pressed}, view.getBackgroundTintList().getDefaultColor());
        ColorPicker.mostra(TelaConfiguracoes.this, view, corDoBotao, new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int corSelecionada) {
                Preferencia preferencia = null;
                switch (view.getId()){
                    case R.id.btnCorDestaque:
                        preferencia = new Preferencia(Preferencia.ID_COR_DESTAQUE,corSelecionada);
                        break;
                    case R.id.btnCorDestaqueClara:
                        preferencia = new Preferencia(Preferencia.ID_COR_DESTAQUE_CLARO,corSelecionada);
                        break;
                    case R.id.btnCorSecundaria:
                        preferencia = new Preferencia(Preferencia.ID_COR_SECUNDARIA,corSelecionada);
                        break;
                    case R.id.btnCorSecundariaClara:
                        preferencia = new Preferencia(Preferencia.ID_COR_SECUNDARIA_CLARO,corSelecionada);
                        break;
                }
                new Dao(TelaConfiguracoes.this).salvarPreferencia(preferencia);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toast(String m){
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
}
