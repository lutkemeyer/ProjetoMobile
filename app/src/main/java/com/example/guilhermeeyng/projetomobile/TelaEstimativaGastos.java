package com.example.guilhermeeyng.projetomobile;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Tema;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.utilitarios.Calculos;
import com.example.guilhermeeyng.projetomobile.utilitarios.TextChangeListener;

import Modules.Distancia;
import Modules.Duracao;

import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.toConsumo;
import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.toLitros;
import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.toMonetary;

public class TelaEstimativaGastos extends AppCompatActivity {

    private EditText txtValorCombustivel1, txtValorCombustivel2;
    private TextView lblTipoCombustivel1, lblTipoCombustivel2;

    private TextView lblOrigem, lblDestino;
    private TextView lblDistancia,  lblDuracao;

    private TextView lblConsumo1, lblResultadoConsumo1;
    private TextView lblConsumo2, lblResultadoConsumo2;

    private TextView lblEstimativaLitros1, lblResultadoLitros1;
    private TextView lblEstimativaGastos1, lblResultadoGastos1;
    private TextView lblEstimativaLitros2, lblResultadoLitros2;
    private TextView lblEstimativaGastos2, lblResultadoGastos2;

    private LinearLayout llValorComb2, llConsumo2, llEstimativa2;

    private double valorLitro1, valorLitro2, consumo1, litros1, consumo2, litros2, valorGasto1, valorGasto2;

    private Distancia distancia;
    private Duracao duracao;

    private Veiculo veiculoUsuario;
    private String origem, destino;

    private Tema temaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_estimativa_gastos);

        txtValorCombustivel1 = findViewById(R.id.txtValorCombustivel1);
        txtValorCombustivel2 = findViewById(R.id.txtValorCombustivel2);
        lblTipoCombustivel1 = findViewById(R.id.lblTipoCombustivel1);
        lblTipoCombustivel2 = findViewById(R.id.lblTipoCombustivel2);
        lblOrigem = findViewById(R.id.lblOrigem);
        lblDestino = findViewById(R.id.lblDestino);
        lblDistancia = findViewById(R.id.lblDistancia);
        lblDuracao = findViewById(R.id.lblDuracao);
        lblConsumo1 = findViewById(R.id.lblConsumo1);
        lblResultadoConsumo1 = findViewById(R.id.lblResultadoConsumo1);
        lblConsumo2 = findViewById(R.id.lblConsumo2);
        lblResultadoConsumo2 = findViewById(R.id.lblResultadoConsumo2);
        lblEstimativaLitros1 = findViewById(R.id.lblEstimativaLitros1);
        lblResultadoLitros1 = findViewById(R.id.lblResultadoLitros1);
        lblEstimativaLitros2 = findViewById(R.id.lblEstimativaLitros2);
        lblResultadoLitros2 = findViewById(R.id.lblResultadoLitros2);
        lblEstimativaGastos1 = findViewById(R.id.lblEstimativaGastos1);
        lblResultadoGastos1 = findViewById(R.id.lblResultadoGastos1);
        lblEstimativaGastos2 = findViewById(R.id.lblEstimativaGastos2);
        lblResultadoGastos2 = findViewById(R.id.lblResultadoGasto2);
        llValorComb2 = findViewById(R.id.llValorComb2);
        llConsumo2 = findViewById(R.id.llConsumo2);
        llEstimativa2 = findViewById(R.id.llEstimativa2);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setElevation(0);
        actionBar.setTitle( getString(R.string.titulo_tela_estimativa_gastos));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // carrega o veiculo do usuario para que possa ser calculado o rendimento
        veiculoUsuario = new Dao(TelaEstimativaGastos.this).getVeiculoUsuario();
        getDados();
        calcularGastos();
        preencheDados();
        listeners();
    }

    // PERSONALIZA O LAYOUT
    @Override
    protected void onResume() {
        super.onResume();

        Window window = TelaEstimativaGastos.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ActionBar actionBar = getSupportActionBar();
        Drawable ic_voltar = getDrawable(R.drawable.ic_voltar);

        TextView lblValorCombustivel = findViewById(R.id.lblValorCombustivel);
        EditText txtValorCombustivel1 = findViewById(R.id.txtValorCombustivel1);
        Drawable drawableDinheiro = getResources().getDrawable(R.drawable.ic_dinheiro);
        TextView lblTipoCombustivel1 = findViewById(R.id.lblTipoCombustivel1);
        TextView txtValorCombustivel2 = findViewById(R.id.txtValorCombustivel2);
        TextView lblTipoCombustivel2 = findViewById(R.id.lblTipoCombustivel2);

        TextView lblDe = findViewById(R.id.lblDe);
        TextView lblOrigem = findViewById(R.id.lblOrigem);
        TextView lblAte = findViewById(R.id.lblAte);
        TextView lblDestino = findViewById(R.id.lblDestino);

        LinearLayout llSeparador1 = findViewById(R.id.llSeparador1);
        TextView lbltxtDistancia = findViewById(R.id.lbltxtDistancia);
        ImageView imgDistancia = findViewById(R.id.imgDistancia);
        Drawable drawableDistancia = getDrawable(R.drawable.ic_distancia);
        TextView lblDistancia = findViewById(R.id.lblDistancia);

        LinearLayout llSeparador2 = findViewById(R.id.llSeparador2);
        TextView lbltxtDuracao = findViewById(R.id.lbltxtDuracao);
        ImageView imgDuracao = findViewById(R.id.imgDuracao);
        Drawable drawableDuracao = getDrawable(R.drawable.ic_tempo);
        TextView lblDuracao = findViewById(R.id.lblDuracao);

        TextView lblConsumo1 = findViewById(R.id.lblConsumo1);
        TextView lblResultadoConsumo1 = findViewById(R.id.lblResultadoConsumo1);
        TextView lblConsumo2 = findViewById(R.id.lblConsumo2);
        TextView lblResultadoConsumo2 = findViewById(R.id.lblResultadoConsumo2);

        View viewSeparador1 = findViewById(R.id.viewSeparador1);

        TextView lblEstimativaLitros1 = findViewById(R.id.lblEstimativaLitros1);
        TextView lblResultadoLitros1 = findViewById(R.id.lblResultadoLitros1);
        TextView lblEstimativaGastos1 = findViewById(R.id.lblEstimativaGastos1);
        TextView lblResultadoGastos1 = findViewById(R.id.lblResultadoGastos1);

        View viewSeparador2 = findViewById(R.id.viewSeparador2);

        TextView lblEstimativaLitros2 = findViewById(R.id.lblEstimativaLitros2);
        TextView lblResultadoLitros2 = findViewById(R.id.lblResultadoLitros2);
        TextView lblEstimativaGastos2 = findViewById(R.id.lblEstimativaGastos2);
        TextView lblResultadoGasto2 = findViewById(R.id.lblResultadoGasto2);

        temaUsuario = new Dao(TelaEstimativaGastos.this).getTemaUsuario();

        window.setStatusBarColor( temaUsuario.getCorDestaqueInt() );
        actionBar.setBackgroundDrawable(new ColorDrawable( temaUsuario.getCorDestaqueInt() ));

        // cor do icone voltar
        ic_voltar.setTint( temaUsuario.getCorDestaqueClaraInt() );
        actionBar.setHomeAsUpIndicator( ic_voltar );

        // cor do titulo da tela
        Spannable spannablerTitle = new SpannableString(actionBar.getTitle().toString());
        spannablerTitle.setSpan(new ForegroundColorSpan( temaUsuario.getCorDestaqueClaraInt() ), 0, spannablerTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(spannablerTitle);

        lblValorCombustivel.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        // cor do campo origem
        drawableDinheiro.setTint( temaUsuario.getCorDestaqueClaraInt() );
        txtValorCombustivel1.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        txtValorCombustivel1.setHintTextColor( temaUsuario.getCorDestaqueClaraInt() );
        txtValorCombustivel1.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueClaraInt() ));
        txtValorCombustivel1.setCompoundDrawablesRelative(drawableDinheiro,null,null,null);

        lblTipoCombustivel1.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        // cor do campo origem
        txtValorCombustivel2.setTextColor( temaUsuario.getCorSecundariaClaraInt() );
        txtValorCombustivel2.setHintTextColor( temaUsuario.getCorDestaqueClaraInt() );
        txtValorCombustivel2.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueClaraInt() ));
        txtValorCombustivel2.setCompoundDrawablesRelative(drawableDinheiro,null,null,null);

        lblTipoCombustivel2.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        lblDe.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblOrigem.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblAte.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblDestino.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        llSeparador1.getBackground().setTint(temaUsuario.getCorDestaqueClaraInt());
        lbltxtDistancia.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        drawableDistancia.setTint(temaUsuario.getCorSecundariaInt());
        imgDistancia.setImageDrawable(drawableDistancia);
        lblDistancia.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        GradientDrawable drawable = (GradientDrawable)llSeparador1.getBackground();
        drawable.setStroke(3, temaUsuario.getCorSecundariaClaraInt());
        llSeparador1.setBackground(drawable);

        llSeparador2.getBackground().setTint(temaUsuario.getCorDestaqueClaraInt());
        lbltxtDuracao.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        drawableDuracao.setTint(temaUsuario.getCorSecundariaInt());
        imgDuracao.setImageDrawable(drawableDistancia);
        lblDuracao.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        lblConsumo1.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblConsumo2.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblEstimativaLitros1.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblEstimativaGastos1.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblEstimativaLitros2.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblEstimativaGastos2.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        lblResultadoConsumo1.setTextColor(temaUsuario.getCorDestaqueClaraInt());
        lblResultadoConsumo2.setTextColor(temaUsuario.getCorDestaqueClaraInt());
        lblResultadoLitros1.setTextColor(temaUsuario.getCorDestaqueClaraInt());
        lblResultadoGastos1.setTextColor(temaUsuario.getCorDestaqueClaraInt());
        lblResultadoLitros2.setTextColor(temaUsuario.getCorDestaqueClaraInt());
        lblResultadoGasto2.setTextColor(temaUsuario.getCorDestaqueClaraInt());

        viewSeparador1.setBackground(new ColorDrawable(temaUsuario.getCorSecundariaClaraInt()));
        viewSeparador2.setBackground(new ColorDrawable(temaUsuario.getCorSecundariaClaraInt()));

    }

    /*
        pega os dados que foram passados pela tela MainActivity, como origem, destino, duracao e distancia
         */
    public void getDados() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.origem = bundle.getString( getResources().getString(R.string.bundle_origem) );
            this.destino = bundle.getString( getResources().getString(R.string.bundle_destino) );
            this.distancia = (Distancia)bundle.getSerializable( getResources().getString(R.string.bundle_distancia) );
            this.duracao = (Duracao)bundle.getSerializable( getResources().getString(R.string.bundle_duracao) );
        }
    }
    /*
    seta para cada variavel os dados calculados
     */
    private void calcularGastos() {
        if(veiculoUsuario.getTipoCombustivel().getNome().equalsIgnoreCase("FLEX")){
            this.consumo1 = Calculos.consumo(veiculoUsuario, "GASOLINA");
            this.consumo2 = Calculos.consumo(veiculoUsuario, "ETANOL");
            this.litros1 = Calculos.litros( consumo1, distancia.getValor() );
            this.litros2 = Calculos.litros( consumo2, distancia.getValor() );
            this.valorGasto1 = Calculos.valorGasto( litros1, valorLitro1);
            this.valorGasto2 = Calculos.valorGasto( litros2, valorLitro2);
        }else{
            this.consumo1 = Calculos.consumo(veiculoUsuario, veiculoUsuario.getTipoCombustivel().getNome().toUpperCase());
            this.litros1 = Calculos.litros( consumo1, distancia.getValor() );
            this.valorGasto1 = Calculos.valorGasto(litros1, valorLitro1);
        }
    }
    /*
    seta nas labels os valores das variaveis
     */
    private void preencheDados() {

        lblOrigem.setText(origem);
        lblDestino.setText(destino);
        lblDistancia.setText( distancia.getTexto() );
        lblDuracao.setText( duracao.getTexto() );

        lblResultadoConsumo1.setText( toConsumo(consumo1) );
        lblResultadoConsumo2.setText( toConsumo(consumo2) );
        lblResultadoLitros1.setText( toLitros(litros1) );
        lblResultadoGastos1.setText( toMonetary(valorGasto1) );
        lblResultadoLitros2.setText( toLitros(litros2) );
        lblResultadoGastos2.setText( toMonetary(valorGasto2) );

        if(veiculoUsuario.getTipoCombustivel().getNome().equalsIgnoreCase("FLEX")){

            lblTipoCombustivel1.setText(R.string.litros_de_gasolina);
            lblTipoCombustivel2.setText(R.string.litros_de_etanol);

            llValorComb2.setVisibility(View.VISIBLE);
            llConsumo2.setVisibility(View.VISIBLE);

            txtValorCombustivel1.setHint(getString( R.string.hint_txt_valor_combustivel ) + " gasolina");
            txtValorCombustivel2.setHint(getString( R.string.hint_txt_valor_combustivel ) + " etanol");

            lblConsumo1.setText(R.string.lbl_consumo_usando_gasolina);
            lblConsumo2.setText(R.string.lbl_consumo_usando_etanol);
            lblEstimativaLitros1.setText(R.string.lbl_estimativa_litros_gasolina);
            lblEstimativaGastos1.setText(R.string.lbl_estimativa_gasto_gasolina);
            lblEstimativaLitros2.setText(R.string.lbl_estimativa_litros_etanol);
            lblEstimativaGastos2.setText(R.string.lbl_estimativa_gasto_etanol);
        }else{

            lblTipoCombustivel1.setText(getString(R.string.por_litros_de) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase());
            txtValorCombustivel1.setHint(getString( R.string.hint_txt_valor_combustivel) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase());
            llValorComb2.setVisibility(View.GONE);
            llConsumo2.setVisibility(View.GONE);
            llEstimativa2.setVisibility(View.GONE);

            lblConsumo1.setText(getString(R.string.lbl_consumo_usando) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase() );
            lblEstimativaLitros1.setText(getString(R.string.lbl_estimativa_litros) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase() );
            lblEstimativaGastos1.setText(getString(R.string.lbl_estimativa_gasto_usando) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase() );

        }
    }
    /*
    conforme for digitando o valor dos combustiveis, vai calculando automaticamente as labels
     */
    private void listeners() {
        txtValorCombustivel1.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    valorLitro1 = Double.parseDouble(String.valueOf(s));
                }catch(Exception e){
                    valorLitro1 = 0.0;
                }finally {
                    calcularGastos();
                    preencheDados();
                }
            }
        });

        txtValorCombustivel2.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    valorLitro2 = Double.parseDouble(String.valueOf(s));
                }catch(Exception e){
                    valorLitro2 = 0.0;
                }finally {
                    calcularGastos();
                    preencheDados();
                }
            }
        });
    }
    /*
    adiciona acao de quando clica em voltar no botao superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    chamado quando clica em voltar, tanto no botao superior, quanto pelo próprio celular
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_front, R.anim.out_right);
    }
}