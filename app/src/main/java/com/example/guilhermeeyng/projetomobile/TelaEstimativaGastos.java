package com.example.guilhermeeyng.projetomobile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.utilitarios.Calculos;
import com.example.guilhermeeyng.projetomobile.utilitarios.TextChangeListener;

import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.converteTempo;
import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.toConsumo;
import static com.example.guilhermeeyng.projetomobile.utilitarios.Util.toKm;
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

    private double valorLitro1, valorLitro2, consumo1, litros1, consumo2, litros2, valorGasto1, valorGasto2, distancia;

    private Veiculo veiculoUsuario;
    private String origem, destino;
    private int duracao;

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
        actionBar.setTitle( getString(R.string.titulo_tela_selecao_carro));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // carrega o veiculo do usuario para que possa ser calculado o rendimento
        veiculoUsuario = new Dao(TelaEstimativaGastos.this).getVeiculoUsuario();
        Log.i("Script", "Carro usuario: " + veiculoUsuario);
        getDados();
        calcularGastos();
        preencheDados();
        listeners();
    }
    /*
    pega os dados que foram passados pela tela MainActivity, como origem, destino, duracao e distancia
     */
    public void getDados() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            this.origem = bundle.getString( getResources().getString(R.string.bundle_origem) );
            this.destino = bundle.getString( getResources().getString(R.string.bundle_destino) );
            this.distancia = Calculos.km( bundle.getInt( getResources().getString(R.string.bundle_distancia) ) );
            this.duracao = bundle.getInt( getResources().getString(R.string.bundle_duracao) );
        }

    }
    /*
    seta para cada variavel os dados calculados
     */
    private void calcularGastos() {
        if(veiculoUsuario.getTipoCombustivel().getNome().equalsIgnoreCase("FLEX")){
            this.consumo1 = Calculos.consumo(veiculoUsuario, "GASOLINA");
            this.consumo2 = Calculos.consumo(veiculoUsuario, "ETANOL");
            this.litros1 = Calculos.litros( consumo1, distancia );
            this.litros2 = Calculos.litros( consumo2, distancia );
            this.valorGasto1 = Calculos.valorGasto( litros1, valorLitro1);
            this.valorGasto2 = Calculos.valorGasto( litros2, valorLitro2);
        }else{
            this.consumo1 = Calculos.consumo(veiculoUsuario, veiculoUsuario.getTipoCombustivel().getNome().toUpperCase());
            this.litros1 = Calculos.litros( consumo1, distancia );
            this.valorGasto1 = Calculos.valorGasto(litros1, valorLitro1);
        }
    }
    /*
    seta nas labels os valores das variaveis
     */
    private void preencheDados() {

        lblOrigem.setText(origem);
        lblDestino.setText(destino);
        lblDistancia.setText( toKm(distancia) );

        Log.i("Script", "duracao: "+duracao);
        Log.i("Script", "convertido: "+converteTempo( duracao ));
        lblDuracao.setText( converteTempo(duracao) );

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

            txtValorCombustivel1.setHint(R.string.hint_txt_valor_combustivel_um);
            txtValorCombustivel2.setHint(R.string.hint_txt_valor_combustivel_dois);

            lblConsumo1.setText(R.string.lbl_consumo_usando_gasolina);
            lblConsumo2.setText(R.string.lbl_consumo_usando_etanol);
            lblEstimativaLitros1.setText(R.string.lbl_estimativa_litros_gasolina);
            lblEstimativaGastos1.setText(R.string.lbl_estimativa_gasto_gasolina);
            lblEstimativaLitros2.setText(R.string.lbl_estimativa_litros_etanol);
            lblEstimativaGastos2.setText(R.string.lbl_estimativa_gasto_etanol);
        }else{

            lblTipoCombustivel1.setText(getString(R.string.litros_de) + " " + veiculoUsuario.getTipoCombustivel().getNome().toLowerCase());

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