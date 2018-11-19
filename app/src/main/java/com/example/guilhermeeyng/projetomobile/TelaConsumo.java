package com.example.guilhermeeyng.projetomobile;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterAno;
import com.example.guilhermeeyng.projetomobile.adapters.AdapterMarca;
import com.example.guilhermeeyng.projetomobile.adapters.AdapterModelo;
import com.example.guilhermeeyng.projetomobile.adapters.AdapterVeiculo;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;
import com.example.guilhermeeyng.projetomobile.entidades.Tema;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.utilitarios.ActionMenuTelaConsumo;

import java.util.ArrayList;

public class TelaConsumo extends AppCompatActivity {

    private Spinner spMarca, spModelo, spAno;
    private TextView lblModelo, lblAno;
    private Button btnInserirConsumoManualmente;
    private ActionMenuTelaConsumo actionMenu;
    private ListView lstVeiculos;

    private MenuItem botaoMenu;

    public static Marca marcaSelecionada;
    public static Ano anoSelecionado;
    public static String modeloSelecionado;

    private Tema temaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consumo);

        ActionBar actionBar = getSupportActionBar();

        final LinearLayout conteudoLayout = findViewById(R.id.conteudoLayoutSelecaoCarro);

        // gerencia a animação do menu
        actionMenu = new ActionMenuTelaConsumo(conteudoLayout, new BounceInterpolator());

        actionBar.setElevation(0);
        actionBar.setTitle(getResources().getString(R.string.titulo_tela_selecao_carro));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        spMarca = findViewById(R.id.spMarca);
        spModelo = findViewById(R.id.spModelo);
        spAno = findViewById(R.id.spAno);
        lblModelo = findViewById(R.id.lblModelo);
        lblAno = findViewById(R.id.lblAno);
        lstVeiculos = findViewById(R.id.lstVeiculos);
        btnInserirConsumoManualmente = findViewById(R.id.btnInserirConsumoManualmente);

        // pega todas as marcas do banco
        spMarca.setAdapter(new AdapterMarca(TelaConsumo.this, "Selecione a marca", new Dao(TelaConsumo.this).getAllMarcas()));

        // deixa a seta do spinner na cor branca
        spMarca.getBackground().setColorFilter(getResources().getColor(R.color.colorLight), PorterDuff.Mode.SRC_ATOP);
        spModelo.getBackground().setColorFilter(getResources().getColor(R.color.colorLight), PorterDuff.Mode.SRC_ATOP);
        spAno.getBackground().setColorFilter(getResources().getColor(R.color.colorLight), PorterDuff.Mode.SRC_ATOP);

        lstVeiculos.setAdapter(new AdapterVeiculo(TelaConsumo.this, new ArrayList<Veiculo>()));

        listeners();

        invalidateOptionsMenu();
    }

    // PERSONALIZA O LAYOUT
    @Override
    protected void onResume() {
        super.onResume();

        Window window = TelaConsumo.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Drawable ic_voltar = getDrawable(R.drawable.ic_voltar);
        ActionBar actionBar = getSupportActionBar();
        Button btnInserirConsumoManualmente = findViewById(R.id.btnInserirConsumoManualmente);
        Spinner spMarca = findViewById(R.id.spMarca);
        ListView lstVeiculos = findViewById(R.id.lstVeiculos);

        temaUsuario = new Dao(TelaConsumo.this).getTemaUsuario();

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

        // cor do botao configuracoes de consumo
        btnInserirConsumoManualmente.getBackground().setTint( temaUsuario.getCorDestaqueClaraInt() );
        btnInserirConsumoManualmente.setTextColor( temaUsuario.getCorSecundariaInt() );

        // cor do icone no spinner de marcas
        ((AdapterMarca)spMarca.getAdapter()).setCorIcone( temaUsuario.getCorDestaqueInt() );

        //((AdapterVeiculo)lstVeiculos.getAdapter()).setCorIcone( temaUsuario.getCorDestaqueInt() );
        ((AdapterVeiculo)lstVeiculos.getAdapter()).setCorDestaque( temaUsuario.getCorDestaqueClaraInt() );
    }


    /*
    quando seleciona item do spinner de marca, carrega todos os modelos daquela marca no spinner de modelos
    quando seleciona item do spinner de modelo, carrega todos os anos daquele modelo no spinner de anos
    quando seleciona item do spinner de ano, carrega todos os veiculos daquele ano no listview
    quando seleciona um item no listview de veiculos, altera o botao superior para "salvar" permitindo que seja salvo
    */
    public void listeners(){
        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionMenu.fechar();
                Marca marcaSelecionada = (Marca)spMarca.getAdapter().getItem(position);
                if(marcaSelecionada != null){
                    TelaConsumo.marcaSelecionada = marcaSelecionada;
                    lblModelo.setVisibility(View.VISIBLE);
                    spModelo.setVisibility(View.VISIBLE);
                    spModelo.setAdapter(new AdapterModelo(TelaConsumo.this, "Selecione o modelo", new Dao(TelaConsumo.this).getAllModelos(marcaSelecionada)));
                }else{
                    lblModelo.setVisibility(View.GONE);
                    lblAno.setVisibility(View.GONE);
                    spModelo.setVisibility(View.GONE);
                    spAno.setVisibility(View.GONE);
                    lstVeiculos.setVisibility(View.GONE);
                    botaoMenu.setTitle(getResources().getString(R.string.btn_menu_nao_encontrei));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionMenu.fechar();
                String modeloSelecionado = (String)spModelo.getAdapter().getItem(position);
                if(modeloSelecionado != null){
                    TelaConsumo.modeloSelecionado = modeloSelecionado;
                    lblAno.setVisibility(View.VISIBLE);
                    spAno.setVisibility(View.VISIBLE);
                    spAno.setAdapter(new AdapterAno(TelaConsumo.this, "Selecione o ano", new Dao(TelaConsumo.this).getAnos( marcaSelecionada, modeloSelecionado) ));
                }else{
                    lblAno.setVisibility(View.GONE);
                    spAno.setVisibility(View.GONE);
                    lstVeiculos.setVisibility(View.GONE);
                    botaoMenu.setTitle(getResources().getString(R.string.btn_menu_nao_encontrei));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actionMenu.fechar();
                Ano anoSelecionado = (Ano)spAno.getAdapter().getItem(position);
                if(anoSelecionado != null){
                    TelaConsumo.anoSelecionado = anoSelecionado;
                    ((AdapterVeiculo)lstVeiculos.getAdapter()).setVeiculos(new Dao(TelaConsumo.this).getVeiculos(marcaSelecionada, modeloSelecionado, anoSelecionado));
                    lstVeiculos.setVisibility(View.VISIBLE);
                }else{
                    lstVeiculos.setVisibility(View.GONE);
                    botaoMenu.setTitle(getResources().getString(R.string.btn_menu_nao_encontrei));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        lstVeiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((AdapterVeiculo)lstVeiculos.getAdapter()).selecionar(position);
                botaoMenu.setTitle(getResources().getString(R.string.btn_menu_salvar));
            }
        });

    }
    /*
    carrega o menu "não encontrei" no topo
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.consumo_menu, menu);
        this.botaoMenu = menu.getItem(0);
        return true;
    }

    /*
    colore o menu "não encontrei" no topo
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        View view = findViewById(R.id.itemMenu);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTextColor( temaUsuario.getCorDestaqueClaraInt() );
        }
        return true;
    }

    /*
    adiciona ação de voltar no botao de voltar e
    caso o botao superior esteja como "nao encontrei" ele abre o menu de selecao manual de consumo
    e caso esteja como salvar, ele salva no banco, o carro selecionado
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.itemMenu:
                if(item.getTitle().equals(getResources().getString(R.string.btn_menu_nao_encontrei))){
                    actionMenu.abrirOuFechar();
                }else if(item.getTitle().equals(getResources().getString(R.string.btn_menu_salvar))){
                    salvar();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    metodo chamado quando clica em voltar tanto no celular, quanto no icone superior de voltar
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_front,R.anim.out_left);
    }
    /*
    metodo chamado quando clica no botao de inserir consumo manualmente
     */
    public void onClickInserirManualmente(View view){
        DialogInserirConsumoManualmente dialog = new DialogInserirConsumoManualmente(TelaConsumo.this, temaUsuario);
        dialog.show();
        actionMenu.fechar();
    }
    /*
    metodo que salva no banco de dados o veiculo selecionado
     */
    private void salvar() {
        Veiculo veiculo = ((AdapterVeiculo)lstVeiculos.getAdapter()).getVeiculoSelecionado();
        new Dao(TelaConsumo.this).salvarConsumo(veiculo);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        finish();

    }
}
