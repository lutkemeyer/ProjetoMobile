package com.example.guilhermeeyng.projetomobile;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterImagem;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Tema;
import com.example.guilhermeeyng.projetomobile.enums.TipoMapa;
import com.example.guilhermeeyng.projetomobile.utilitarios.ColorPicker;
import com.flask.colorpicker.OnColorSelectedListener;
import com.google.android.gms.vision.text.Line;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TelaPreferencias extends AppCompatActivity {

    private Switch swTemaEscuro, swTemaCustomizado, swTemaPadrao, swTemaClaro;
    private FloatingActionButton btnCorDestaque, btnCorDestaqueClara, btnCorSecundaria, btnCorSecundariaClara;
    private RadioButton rbMapaNormal, rbMapaSatelite, rbMapaTerreno, rbMapaHibrido;
    private ViewPager viewPager;

    private LinearLayout mn_llFundo, mn_llContainer, mn_llBotao;
    private ImageView mn_imgMenu, mn_imgPreferencias, mn_imgOrigem, mn_imgDestino, mn_imgDistancia, mn_imgDuracao;
    private TextView mn_lblTitulo, mn_lblDuracao, mn_lblDistancia, mn_txtBotao, mn_txtOrigem, mn_txtDestino;
    private View mn_viewOrigem, mn_viewDestino;

    private Tema temaPadrao, temaClaro, temaEscuro, temaCustomizado;
    private Tema temaUsuario;

    private TipoMapa tipoMapaUsuario;

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

        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new AdapterImagem(TelaPreferencias.this));

        mn_llFundo = findViewById(R.id.mn_llFundo);
        mn_llContainer = findViewById(R.id.mn_llContainer);
        mn_llBotao = findViewById(R.id.mn_llBotao);

        mn_imgMenu = findViewById(R.id.mn_imgMenu);
        mn_imgPreferencias = findViewById(R.id.mn_imgPreferencias);
        mn_imgOrigem = findViewById(R.id.mn_imgOrigem);
        mn_imgDestino = findViewById(R.id.mn_imgDestino);
        mn_imgDistancia = findViewById(R.id.mn_imgDistancia);
        mn_imgDuracao = findViewById(R.id.mn_imgDuracao);

        mn_txtOrigem = findViewById(R.id.mn_txtOrigem);
        mn_txtDestino = findViewById(R.id.mn_txtDestino);

        mn_txtBotao = findViewById(R.id.mn_txtBotao);

        mn_lblTitulo = findViewById(R.id.mn_lblTitulo);
        mn_lblDuracao = findViewById(R.id.mn_lblDuracao);
        mn_lblDistancia = findViewById(R.id.mn_lblDistancia);

        mn_viewOrigem = findViewById(R.id.mn_viewOrigem);
        mn_viewDestino = findViewById(R.id.mn_viewDestino);

        temaUsuario = new Dao(TelaPreferencias.this).getTemaUsuario();

        ArrayList<Tema> temas = new Dao(TelaPreferencias.this).getAllTemas();
        temaPadrao = temas.get(0);
        temaClaro = temas.get(1);
        temaEscuro = temas.get(2);
        temaCustomizado = temas.get(3);

        switch (temaUsuario.getNome().toUpperCase()){
            case "PADRÃO":
                swTemaPadrao.setChecked(true);
                colorirBotoes(temaPadrao);
                colorirViews(1, temaPadrao.getCorDestaqueInt());
                colorirViews(2, temaPadrao.getCorDestaqueClaraInt());
                colorirViews(3, temaPadrao.getCorSecundariaInt());
                colorirViews(4, temaPadrao.getCorSecundariaClaraInt());
                desativarBotoes();
                break;
            case "CLARO":
                swTemaClaro.setChecked(true);
                colorirBotoes(temaClaro);
                colorirViews(1, temaClaro.getCorDestaqueInt());
                colorirViews(2, temaClaro.getCorDestaqueClaraInt());
                colorirViews(3, temaClaro.getCorSecundariaInt());
                colorirViews(4, temaClaro.getCorSecundariaClaraInt());
                desativarBotoes();
                break;
            case "ESCURO":
                swTemaEscuro.setChecked(true);
                colorirBotoes(temaEscuro);
                colorirViews(1, temaEscuro.getCorDestaqueInt());
                colorirViews(2, temaEscuro.getCorDestaqueClaraInt());
                colorirViews(3, temaEscuro.getCorSecundariaInt());
                colorirViews(4, temaEscuro.getCorSecundariaClaraInt());
                desativarBotoes();
                break;
            case "CUSTOMIZADO":
                swTemaCustomizado.setChecked(true);
                colorirBotoes(temaCustomizado);
                colorirViews(1, temaCustomizado.getCorDestaqueInt());
                colorirViews(2, temaCustomizado.getCorDestaqueClaraInt());
                colorirViews(3, temaCustomizado.getCorSecundariaInt());
                colorirViews(4, temaCustomizado.getCorSecundariaClaraInt());
                break;
        }

        listeners();

        tipoMapaUsuario = new Dao(TelaPreferencias.this).getTipoMapaUsuario();

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

    @Override
    protected void onResume() {
        super.onResume();

        Window window = TelaPreferencias.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Drawable ic_voltar = getDrawable(R.drawable.ic_voltar);
        ActionBar actionBar = getSupportActionBar();
        TextView lblCorDestaque = findViewById(R.id.lblCorDestaque);
        TextView lblCorDestaqueClara = findViewById(R.id.lblCorDestaqueClara);
        TextView lblCorSecundaria = findViewById(R.id.lblCorSecundaria);
        TextView lblCorSecundariaClara = findViewById(R.id.lblCorSecundariaClara);
        TextView lblPersonalizacaoTema = findViewById(R.id.lblPersonalizacaoTema);
        TextView lblPersonalizacaoMapa = findViewById(R.id.lblPersonalizacaoMapa);
        LinearLayout llPreferencias = findViewById(R.id.llPreferencias);

        // cor da status bar
        window.setStatusBarColor( temaUsuario.getCorDestaqueInt() );
        actionBar.setBackgroundDrawable(new ColorDrawable( temaUsuario.getCorDestaqueInt() ));

        // cor do icone voltar
        ic_voltar.setTint( temaUsuario.getCorDestaqueClaraInt() );
        actionBar.setHomeAsUpIndicator( ic_voltar );

        // cor do titulo da tela
        Spannable spannablerTitle = new SpannableString(actionBar.getTitle().toString());
        spannablerTitle.setSpan(new ForegroundColorSpan( temaUsuario.getCorDestaqueClaraInt() ), 0, spannablerTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(spannablerTitle);

        llPreferencias.setBackgroundColor( temaUsuario.getCorDestaqueInt() );

        // cor dos radio buttons
        ColorStateList coresPadroesRadioButton = new ColorStateList(
                new int[][]{ new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked} },
                new int[]{ Color.DKGRAY, temaUsuario.getCorDestaqueClaraInt()}
        );
        rbMapaNormal.setButtonTintList(coresPadroesRadioButton);
        rbMapaSatelite.setButtonTintList(coresPadroesRadioButton);
        rbMapaTerreno.setButtonTintList(coresPadroesRadioButton);
        rbMapaHibrido.setButtonTintList(coresPadroesRadioButton);

        rbMapaNormal.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        rbMapaSatelite.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        rbMapaTerreno.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        rbMapaHibrido.setTextColor( temaUsuario.getCorSecundariaClaraInt() );

        swTemaPadrao.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        swTemaClaro.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        swTemaEscuro.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        swTemaCustomizado.setTextColor( temaUsuario.getCorSecundariaClaraInt() );

        if (Build.VERSION.SDK_INT == 23) {
            ColorStateList coresSwitch = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_enabled},new int[]{android.R.attr.state_checked},new int[]{}},new int[]{temaUsuario.getCorSecundariaClaraInt(),temaUsuario.getCorDestaqueClaraInt(),Color.DKGRAY});
            swTemaPadrao.setThumbTintList(coresSwitch);
            swTemaClaro.setThumbTintList(coresSwitch);
            swTemaEscuro.setThumbTintList(coresSwitch);
            swTemaCustomizado.setThumbTintList(coresSwitch);
        }else if (Build.VERSION.SDK_INT >= 24) {
            ColorStateList trackStates = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{}
                    },
                    new int[]{
                            temaUsuario.getCorDestaqueClaraInt(),
                            Color.LTGRAY
                    }
            );

            swTemaPadrao.setTrackTintList(trackStates);
            swTemaPadrao.setTrackTintMode(PorterDuff.Mode.OVERLAY);

            swTemaClaro.setTrackTintList(trackStates);
            swTemaClaro.setTrackTintMode(PorterDuff.Mode.OVERLAY);

            swTemaEscuro.setTrackTintList(trackStates);
            swTemaEscuro.setTrackTintMode(PorterDuff.Mode.OVERLAY);

            swTemaCustomizado.setTrackTintList(trackStates);
            swTemaCustomizado.setTrackTintMode(PorterDuff.Mode.OVERLAY);
        }

        lblPersonalizacaoTema.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        lblPersonalizacaoMapa.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        lblCorDestaque.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        lblCorDestaqueClara.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        lblCorSecundaria.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        lblCorSecundariaClara.setTextColor( temaUsuario.getCorSecundariaClaraInt() );

    }

    private void listeners() {
        swTemaPadrao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swTemaEscuro.setChecked(false);
                    swTemaClaro.setChecked(false);
                    swTemaCustomizado.setChecked(false);
                    ativarBotoes();
                    desativarBotoes();
                    colorirBotoes(temaPadrao);
                    colorirViews(1, temaPadrao.getCorDestaqueInt());
                    colorirViews(2, temaPadrao.getCorDestaqueClaraInt());
                    colorirViews(3, temaPadrao.getCorSecundariaInt());
                    colorirViews(4, temaPadrao.getCorSecundariaClaraInt());
                }else{
                    if(!swTemaEscuro.isChecked() && !swTemaCustomizado.isChecked() && !swTemaClaro.isChecked()){
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
                    desativarBotoes();
                    colorirBotoes(temaClaro);
                    colorirViews(1, temaClaro.getCorDestaqueInt());
                    colorirViews(2, temaClaro.getCorDestaqueClaraInt());
                    colorirViews(3, temaClaro.getCorSecundariaInt());
                    colorirViews(4, temaClaro.getCorSecundariaClaraInt());
                }else{
                    if(!swTemaPadrao.isChecked() && !swTemaCustomizado.isChecked() && !swTemaEscuro.isChecked()){
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
                    desativarBotoes();
                    colorirBotoes(temaEscuro);
                    colorirViews(1, temaEscuro.getCorDestaqueInt());
                    colorirViews(2, temaEscuro.getCorDestaqueClaraInt());
                    colorirViews(3, temaEscuro.getCorSecundariaInt());
                    colorirViews(4, temaEscuro.getCorSecundariaClaraInt());
                }else{
                    if(!swTemaPadrao.isChecked() && !swTemaCustomizado.isChecked() && !swTemaClaro.isChecked()){
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
                    ativarBotoes();
                    colorirBotoes(temaCustomizado);
                    colorirViews(1, temaCustomizado.getCorDestaqueInt());
                    colorirViews(2, temaCustomizado.getCorDestaqueClaraInt());
                    colorirViews(3, temaCustomizado.getCorSecundariaInt());
                    colorirViews(4, temaCustomizado.getCorSecundariaClaraInt());
                }else{
                    if(!swTemaEscuro.isChecked() && !swTemaPadrao.isChecked() && !swTemaClaro.isChecked()){
                        swTemaCustomizado.setChecked(true);
                    }
                }
            }
        });
        rbMapaNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(0, true);
                    tipoMapaUsuario = TipoMapa.NORMAL;
                }
            }
        });
        rbMapaSatelite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(1, true);
                    tipoMapaUsuario = TipoMapa.SATELITE;
                }
            }
        });
        rbMapaTerreno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(2, true);
                    tipoMapaUsuario = TipoMapa.TERRENO;
                }
            }
        });
        rbMapaHibrido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewPager.setCurrentItem(3, true);
                    tipoMapaUsuario = TipoMapa.HIBRIDO;
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
        btnCorSecundariaClara.setEnabled(false);
    }

    private void ativarBotoes() {
        btnCorDestaque.setEnabled(true);
        btnCorDestaqueClara.setEnabled(true);
        btnCorSecundaria.setEnabled(true);
        btnCorSecundariaClara.setEnabled(true);
    }

    private void colorirBotoes(Tema tema) {
        btnCorDestaque.setBackgroundTintList(ColorStateList.valueOf(tema.getCorDestaqueInt()));
        btnCorDestaqueClara.setBackgroundTintList(ColorStateList.valueOf(tema.getCorDestaqueClaraInt()));
        btnCorSecundaria.setBackgroundTintList(ColorStateList.valueOf(tema.getCorSecundariaInt()));
        btnCorSecundariaClara.setBackgroundTintList(ColorStateList.valueOf(tema.getCorSecundariaClaraInt()));
    }

    public void onClickCor(final View view){
        int corDoBotao = view.getBackgroundTintList().getColorForState(new int[]{android.R.attr.state_pressed}, view.getBackgroundTintList().getDefaultColor());
        ColorPicker.mostra(TelaPreferencias.this, view, corDoBotao, new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int corSelecionada) {
                int cor = Color.parseColor("#"+Integer.toHexString(corSelecionada));
                String btn = "";

                switch (view.getId()){
                    case R.id.btnCorDestaque:
                        colorirViews(1,cor);
                        temaCustomizado.setCorDestaqueInt(cor);
                        btn = "destaque";
                        break;
                    case R.id.btnCorDestaqueClara:
                        colorirViews(2,cor);
                        temaCustomizado.setCorDestaqueClaraInt(cor);
                        btn = "destaqueClara";
                        break;
                    case R.id.btnCorSecundaria:
                        colorirViews(3,cor);
                        temaCustomizado.setCorSecundariaInt(cor);
                        btn = "secundaria";
                        break;
                    case R.id.btnCorSecundariaClara:
                        colorirViews(4,cor);
                        temaCustomizado.setCorSecundariaClaraInt(cor);
                        btn = "secundariaClara";
                        break;
                }
                Log.i("Script", "Cor: [" + btn + "] = " + cor + " = #"+Integer.toHexString(corSelecionada));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferencias_menu, menu);
        for(int i=0; i<menu.size(); i++){
            menu.getItem(i).getIcon().setTint( temaUsuario.getCorDestaqueClaraInt() );
        }
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
        if(swTemaPadrao.isChecked()){
            new Dao(TelaPreferencias.this).salvarTemaUsuario(temaPadrao);
        }else if(swTemaClaro.isChecked()){
            new Dao(TelaPreferencias.this).salvarTemaUsuario(temaClaro);
        }else if(swTemaEscuro.isChecked()){
            new Dao(TelaPreferencias.this).salvarTemaUsuario(temaEscuro);
        }else if(swTemaCustomizado.isChecked()){
            new Dao(TelaPreferencias.this).salvarTemaCustomizado(temaCustomizado);
            new Dao(TelaPreferencias.this).salvarTemaUsuario(temaCustomizado);
        }
        new Dao(TelaPreferencias.this).salvarTipoMapa(tipoMapaUsuario);
        finish();
    }

    public void colorirViews(int qualCor, int cor){
        switch (qualCor){
            case 1: // cor destaque
                mn_llFundo.setBackground(new ColorDrawable(cor));
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
                mn_viewOrigem.setBackground(new ColorDrawable(cor));
                mn_viewDestino.setBackground(new ColorDrawable(cor));
                break;
            case 3: // cor secundaria
                mn_llContainer.getBackground().setTint(cor);
                viewPager.setBackgroundColor(cor);
                break;
            case 4: // cor secundaria clara
                mn_lblDuracao.setTextColor(cor);
                mn_lblDistancia.setTextColor(cor);
                mn_txtOrigem.setTextColor(cor);
                mn_txtDestino.setTextColor(cor);
                mn_txtBotao.setTextColor(cor);
        }
    }


}
