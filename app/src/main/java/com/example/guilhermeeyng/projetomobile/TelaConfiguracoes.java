package com.example.guilhermeeyng.projetomobile;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterImagem;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Preferencia;
import com.example.guilhermeeyng.projetomobile.enums.LogoMarcaAutomotiva;
import com.example.guilhermeeyng.projetomobile.enums.Tema;
import com.example.guilhermeeyng.projetomobile.enums.TipoMapa;
import com.example.guilhermeeyng.projetomobile.utilitarios.ColorPicker;
import com.flask.colorpicker.OnColorSelectedListener;

public class TelaConfiguracoes extends AppCompatActivity {

    private Switch swTemaEscuro, swTemaCustomizado, swTemaPadrao;
    private FloatingActionButton btnCorDestaque, btnCorDestaqueClara, btnCorSecundaria, btnCorSecundariaClara;
    private RadioButton rbMapaNormal, rbMapaSatelite, rbMapaTerreno, rbMapaHibrido;
    private ViewPager viewPager;

    private LinearLayout mn_llFundo, mn_llContainer, mn_llBotao;
    private ImageView mn_imgMenu, mn_imgPreferencias, mn_imgOrigem, mn_imgDestino, mn_imgDistancia, mn_imgDuracao;
    private TextView mn_lblTitulo, mn_lblOrigem, mn_lblDestino;
    private View mn_viewOrigem, mn_viewDestino;


    private int corDestaquePadrao, corDestaqueClaraPadrao, corSecundariaPadrao;
    private int corDestaqueEscuro, corDestaqueClaraEscuro, corSecundariaEscuro;
    private int corDestaqueCustomizado, corDestaqueClaraCustomizado, corSecundariaCustomizado;

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

        //containerTemaCustomizado = findViewById(R.id.containerTemaCustomizado);
        viewPager = findViewById(R.id.viewPager);

        btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryLight)));
        btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

        viewPager.setAdapter(new AdapterImagem(TelaConfiguracoes.this));

        mn_llFundo = findViewById(R.id.mn_llFundo);
        mn_llContainer = findViewById(R.id.mn_llContainer);
        mn_llBotao = findViewById(R.id.mn_llBotao);

        mn_imgMenu = findViewById(R.id.mn_imgMenu);
        mn_imgPreferencias = findViewById(R.id.mn_imgPreferencias);
        mn_imgOrigem = findViewById(R.id.mn_imgOrigem);
        mn_imgDestino = findViewById(R.id.mn_imgDestino);
        mn_imgDistancia = findViewById(R.id.mn_imgDistancia);
        mn_imgDuracao = findViewById(R.id.mn_imgDuracao);

        mn_lblTitulo = findViewById(R.id.mn_lblTitulo);
        mn_lblOrigem = findViewById(R.id.mn_lblOrigem);
        mn_lblDestino = findViewById(R.id.mn_lblDestino);

        mn_viewOrigem = findViewById(R.id.mn_viewOrigem);
        mn_viewDestino = findViewById(R.id.mn_viewDestino);

        corDestaquePadrao = -16757093;
        corDestaqueClaraPadrao = -6109697;
        corSecundariaPadrao = -14079703;

        corDestaqueEscuro = -16777216;
        corDestaqueClaraEscuro = -5197648;
        corSecundariaEscuro = -13750738;


        Tema temaUsuario = new Dao(TelaConfiguracoes.this).getTemaUsuario();

        switch (temaUsuario){
            case PADRAO:
                swTemaPadrao.setChecked(true);
                break;
            case ESCURO:
                swTemaEscuro.setChecked(true);
                break;
            case CUSTOMIZADO:
                swTemaCustomizado.setChecked(true);
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
                    swTemaEscuro.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                    aplicarTema(Tema.PADRAO);
                    colorirBotoes(Tema.PADRAO);
                    ativarBotoes();
                    desativarBotoes();
                    colorirBotoes(Tema.PADRAO);
                }else{
                    if(!swTemaEscuro.isChecked() && !swTemaCustomizado.isChecked()){
                        swTemaPadrao.setChecked(true);
                    }
                }
            }
        });

        swTemaEscuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaPadrao.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                    aplicarTema(Tema.ESCURO);
                    desativarBotoes();
                    colorirBotoes(Tema.ESCURO);
                }else{
                    if(!swTemaPadrao.isChecked() && !swTemaCustomizado.isChecked()){
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
                    swTemaEscuro.setChecked(false);
                    aplicarTema(Tema.CUSTOMIZADO);
                    colorirBotoes(Tema.CUSTOMIZADO);
                    ativarBotoes();
                }else{
                    if(!swTemaEscuro.isChecked() && !swTemaPadrao.isChecked()){
                        swTemaCustomizado.setChecked(true);
                    }else{

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

    private void desativarBotoes() {
        btnCorDestaque.setEnabled(false);
        btnCorDestaqueClara.setEnabled(false);
        btnCorSecundaria.setEnabled(false);
    }

    private void ativarBotoes() {
        btnCorDestaque.setEnabled(true);
        btnCorDestaqueClara.setEnabled(true);
        btnCorSecundaria.setEnabled(true);
    }

    private void colorirBotoes(Tema tema) {
        switch (tema){
            case PADRAO:
                btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(corDestaquePadrao));
                btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(corDestaqueClaraPadrao));
                btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(corSecundariaPadrao));
                break;
            case ESCURO:
                btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(corDestaqueEscuro));
                btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(corDestaqueClaraEscuro));
                btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(corSecundariaEscuro));
                break;
            case CUSTOMIZADO:
                btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(corDestaqueCustomizado));
                btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(corDestaqueClaraCustomizado));
                btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(corSecundariaCustomizado));
                break;
        }

    }

    public void aplicarTema(Tema tema){

        switch (tema){
            case PADRAO:
                colorirViews(1, corDestaquePadrao);
                colorirViews(2, corDestaqueClaraPadrao);
                colorirViews(3, corSecundariaPadrao);

                corDestaqueCustomizado = corDestaquePadrao;
                corDestaqueClaraCustomizado = corDestaqueClaraPadrao;
                corSecundariaCustomizado = corSecundariaPadrao;

                break;
            case ESCURO:
                colorirViews(1, corDestaqueEscuro);
                colorirViews(2, corDestaqueClaraEscuro);
                colorirViews(3, corSecundariaEscuro);

                corDestaqueCustomizado = corDestaqueEscuro;
                corDestaqueClaraCustomizado = corDestaqueClaraEscuro;
                corSecundariaCustomizado = corSecundariaEscuro;


                break;
            case CUSTOMIZADO:
                colorirViews(1, corDestaqueCustomizado);
                colorirViews(2, corDestaqueClaraCustomizado);
                colorirViews(3, corSecundariaCustomizado);
                break;
        }
    }

    public void onClickCor(final View view){
        int corDoBotao = view.getBackgroundTintList().getColorForState(new int[]{android.R.attr.state_pressed}, view.getBackgroundTintList().getDefaultColor());
        ColorPicker.mostra(TelaConfiguracoes.this, view, corDoBotao, new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int corSelecionada) {
                switch (view.getId()){
                    case R.id.btnCorDestaque:
                        colorirViews(1,corSelecionada);
                        break;
                    case R.id.btnCorDestaqueClara:
                        colorirViews(2,corSelecionada);
                        break;
                    case R.id.btnCorSecundaria:
                        colorirViews(3,corSelecionada);
                        break;
                }
                //new Dao(TelaConfiguracoes.this).salvarPreferencia(preferencia);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferencias_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.mn_salvar:
                onClickSalvar();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickSalvar() {
        toast("Salvar");
    }

    public void toast(String m){
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    public void colorirViews(int qualCor, int c){
        Log.i("Script", c+"");
        int cor = Color.parseColor("#"+Integer.toHexString(c));

        switch (qualCor){
            case 1: // cor destaque
                mn_llFundo.setBackgroundColor(cor);
                mn_viewOrigem.setBackgroundColor(cor);
                mn_viewDestino.setBackgroundColor(cor);
                break;
            case 2: // cor destaque clara
                mn_imgMenu.getDrawable().setTint(cor);
                mn_imgPreferencias.getDrawable().setTint(cor);
                mn_imgOrigem.getDrawable().setTint(cor);
                mn_imgDestino.getDrawable().setTint(cor);
                mn_imgDistancia.getDrawable().setTint(cor);
                mn_imgDuracao.getDrawable().setTint(cor);
                mn_llBotao.getBackground().setTint(cor);
                mn_lblTitulo.setTextColor(cor);
                mn_lblDestino.setTextColor(cor);
                mn_lblOrigem.setTextColor(cor);
                break;
            case 3: // cor secundaria
                mn_llContainer.getBackground().setTint(cor);
                viewPager.setBackgroundColor(cor);
                break;

        }
    }


}
