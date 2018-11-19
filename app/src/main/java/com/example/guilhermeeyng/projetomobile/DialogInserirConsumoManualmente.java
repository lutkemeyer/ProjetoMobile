package com.example.guilhermeeyng.projetomobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Tema;
import com.example.guilhermeeyng.projetomobile.entidades.TipoCombustivel;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.utilitarios.TextChangeListener;

import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.setCursorColor;

public class DialogInserirConsumoManualmente extends AlertDialog {

    private Context ctx;
    private Button btnSalvar, btnCancelar;
    private EditText txtConsumo1, txtConsumo2;
    private RadioButton rbFlex, rbGasolina, rbEtanol, rbDiesel;
    private LinearLayout container2;
    private Tema temaUsuario;

    private double consumo1;
    private double consumo2;

    protected DialogInserirConsumoManualmente(@NonNull Context ctx, Tema tema) {
        super(ctx);
        this.ctx = ctx;
        this.temaUsuario = tema;
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.dialog_inserir_consumo_manualmente);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);
        txtConsumo1 = findViewById(R.id.txtConsumo1);
        txtConsumo2 = findViewById(R.id.txtConsumo2);
        rbFlex = findViewById(R.id.rbFlex);
        rbGasolina = findViewById(R.id.rbGasolina);
        rbEtanol = findViewById(R.id.rbEtanol);
        rbDiesel = findViewById(R.id.rbDiesel);
        container2 = findViewById(R.id.container2);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        listeners();

        TextView lblTitulo = findViewById(R.id.lblTitulo);
        TextView lblSelecioneOCombustivel = findViewById(R.id.lblSelecioneOCombustivel);
        TextView lblKmL1 = findViewById(R.id.lblKmL1);
        TextView lblKmL2 = findViewById(R.id.lblKmL2);

        lblTitulo.setTextColor(temaUsuario.getCorDestaqueInt());
        lblSelecioneOCombustivel.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblKmL1.setTextColor(temaUsuario.getCorSecundariaClaraInt());
        lblKmL2.setTextColor(temaUsuario.getCorSecundariaClaraInt());

        ColorStateList coresPadroesRadioButton = new ColorStateList(
                new int[][]{ new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked} },
                new int[]{ Color.DKGRAY, temaUsuario.getCorDestaqueInt()}
        );

        rbFlex.setButtonTintList(coresPadroesRadioButton);
        rbGasolina.setButtonTintList(coresPadroesRadioButton);
        rbEtanol.setButtonTintList(coresPadroesRadioButton);
        rbDiesel.setButtonTintList(coresPadroesRadioButton);

        txtConsumo1.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueInt() ));
        txtConsumo1.invalidate();

        txtConsumo2.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueInt() ));
        txtConsumo2.invalidate();

        // cor do cursor dos campos consumo
        setCursorColor(txtConsumo1, temaUsuario.getCorDestaqueInt());
        setCursorColor(txtConsumo2, temaUsuario.getCorDestaqueInt());

        btnCancelar.setTextColor( temaUsuario.getCorDestaqueInt() );
        btnSalvar.setTextColor( temaUsuario.getCorDestaqueInt() );



    }

    private void listeners() {
        // se clicar no radiobutton flex, exibe os campos de gasolina e etanol
        rbFlex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean marcado) {
                if(marcado){
                    container2.setVisibility(View.VISIBLE);
                    txtConsumo1.setHint( getContext().getResources().getString( R.string.hint_txt_consumo_medio_gasolina ) );
                    txtConsumo2.setHint( getContext().getResources().getString( R.string.hint_txt_consumo_medio_etanol ) );
                }else{
                    container2.setVisibility(View.GONE);
                }
            }
        });
        // se clicar no radiobutton gasolina, exibe o campo de gasolina
        rbGasolina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean marcado) {
                if(marcado){
                    txtConsumo1.setHint( getContext().getResources().getString( R.string.hint_txt_consumo_medio_gasolina ) );
                }
            }
        });
        // se clicar no radiobutton etanol, exibe o campo de etanol
        rbEtanol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean marcado) {
                if(marcado){
                    txtConsumo1.setHint( getContext().getResources().getString( R.string.hint_txt_consumo_medio_etanol ) );
                }
            }
        });
        // se clicar no radiobutton diesel, exibe o campo de diesel
        rbDiesel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean marcado) {
                if(marcado){
                    txtConsumo1.setHint( getContext().getResources().getString( R.string.hint_txt_consumo_medio_diesel ) );
                }
            }
        });

        txtConsumo1.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confereSeEstaCerto(String.valueOf(s), txtConsumo2.getText().toString());
            }
        });

        txtConsumo2.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confereSeEstaCerto(txtConsumo1.getText().toString(), String.valueOf(s));
            }
        });
    }

    public void salvar(){
        try {
            consumo1 = Double.parseDouble(txtConsumo1.getText().toString());
            consumo2 = Double.parseDouble(txtConsumo2.getText().toString());
        }catch (NumberFormatException nf){

        }
        Veiculo veiculo = new Veiculo();
        if(rbFlex.isChecked()){
            veiculo.setTipoCombustivel(new TipoCombustivel(1, "FLEX"));
            veiculo.setConsGasDieselCidade(consumo1);
            veiculo.setConsGasDieselEstrada(consumo1);
            veiculo.setConsEtanolCidade(consumo2);
            veiculo.setConsEtanolEstrada(consumo2);
        }else if(rbEtanol.isChecked()){
            veiculo.setTipoCombustivel(new TipoCombustivel(2, "ETANOL"));
            veiculo.setConsEtanolCidade(consumo1);
            veiculo.setConsEtanolEstrada(consumo1);
        }else if(rbGasolina.isChecked()){
            veiculo.setTipoCombustivel(new TipoCombustivel(3, "GASOLINA"));
            veiculo.setConsGasDieselCidade(consumo1);
            veiculo.setConsGasDieselEstrada(consumo1);
        }else if(rbDiesel.isChecked()){
            veiculo.setTipoCombustivel(new TipoCombustivel(4, "DIESEL"));
            veiculo.setConsGasDieselCidade(consumo1);
            veiculo.setConsGasDieselEstrada(consumo1);
        }
        new Dao(ctx).salvarConsumo(veiculo);
        dismiss();
    }

    public void cancelar(){
        this.dismiss();
    }

    public void confereSeEstaCerto(String t1, String t2){
        if(rbFlex.isChecked()){ // SE FOR FLEX PRECISA DOS DOIS VALORES
            if(!t1.isEmpty() && !t2.isEmpty()){
                try{
                    double valor1 = Double.parseDouble( String.valueOf( t1 ) );
                    double valor2 = Double.parseDouble( String.valueOf( t2 ) );
                    if(valor1 > 0 && valor2 > 0){
                        consumo1 = valor1;
                        consumo2 = valor2;
                        btnSalvar.setEnabled(true);
                    }else{
                        btnSalvar.setEnabled(false);
                    }
                }catch(NumberFormatException e){
                    btnSalvar.setEnabled(false);
                }
            }else{
                btnSalvar.setEnabled(false);
            }
        }else{
            if(!t1.isEmpty()){ // SE NAO FOR FLEX É NECESSARIO SOMENTE O PRIMEIRO VALOR
                try{
                    double valor1 = Double.parseDouble( String.valueOf( t1 ) );
                    if(valor1 > 0){
                        consumo1 = valor1;
                        btnSalvar.setEnabled(true);
                    }else{
                        btnSalvar.setEnabled(false);
                    }
                }catch(NumberFormatException e){
                    btnSalvar.setEnabled(false);
                }
            }else{
                btnSalvar.setEnabled(false);
            }
        }
    }
}
