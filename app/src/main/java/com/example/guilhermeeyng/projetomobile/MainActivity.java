package com.example.guilhermeeyng.projetomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterEndereco;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.entidades.Endereco;
import com.example.guilhermeeyng.projetomobile.utilitarios.ActionMenuTelaMain;
import com.example.guilhermeeyng.projetomobile.utilitarios.Dialogs;
import com.example.guilhermeeyng.projetomobile.utilitarios.TextChangeListener;
import com.example.guilhermeeyng.projetomobile.utilitarios.TipoRetornoDirectionsAPI;
import com.example.guilhermeeyng.projetomobile.utilitarios.Util;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import Modules.*;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap map;
    private ArrayList<Marker> marcadoresDeOrigem = new ArrayList<>();
    private ArrayList<Marker> marcadoresDeDestino = new ArrayList<>();
    private ArrayList<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    private AutoCompleteTextView txtOrigem, txtDestino;
    private Button btnCalcular;

    private ActionMenuTelaMain actionMenuTelaMain;
    private String origem, destino;
    private Menu menu;

    private Endereco enderecoOrigemSelecionado,enderecoDestinoSelecionado;
    private Duracao duracao;
    private Distancia distancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // verifica se ja existe o banco e se ainda nao foi populado, insere todos os itens no banco
        new Dao(MainActivity.this).existeBanco();

        // gerencia a animacao do menu
        actionMenuTelaMain = new ActionMenuTelaMain(MainActivity.this);

        txtOrigem = findViewById(R.id.txtOrigem);
        txtDestino = findViewById(R.id.txtDestino);
        btnCalcular = findViewById(R.id.btnCalcular);

        // puxa os enderecos cadastrados no banco
        ArrayList<Endereco> enderecos = new Dao(MainActivity.this).getAllEnderecos();

        // insere os enderecos no autocomplete dos campos
        txtOrigem.setAdapter(new AdapterEndereco(MainActivity.this, android.R.layout.select_dialog_item, enderecos, true));
        txtDestino.setAdapter(new AdapterEndereco(MainActivity.this, android.R.layout.select_dialog_item, enderecos, false));

        ActionBar actionBar = getSupportActionBar();

        actionBar.setElevation(0);
        actionBar.setTitle(getResources().getString(R.string.titulo_tela_pirncipal));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bolinhas);


        /*
        o mapa só é colocado na tela neste momento, substituindo por um container setado no layout
        assim que o mapa é carregado, ele chama o OnMapReady, nesta mesma classe
         */

        SupportMapFragment mapFrag = SupportMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layoutContainer, mapFrag);
        ft.commit();

        mapFrag.getMapAsync(MainActivity.this);

        listeners();
    }
    /*
    pega o endereço que foi selecionado no campo, e aponta para
    variaveis que depois serão usadas para armazenar no banco
    verifica se pode calcular rota todas as vezes que o texto muda nos campos
     */
    private void listeners() {
        txtOrigem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enderecoOrigemSelecionado = (Endereco) txtOrigem.getAdapter().getItem(position);
            }
        });
        txtDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enderecoDestinoSelecionado = (Endereco)txtOrigem.getAdapter().getItem(position);
            }
        });
        txtOrigem.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                verificaSePodeCalcularRota();
            }
        });
        txtDestino.addTextChangedListener(new TextChangeListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                verificaSePodeCalcularRota();
            }
        });
    }

    /*
    chamado todas as vezes que alguem digita nos campos, se tiver algo nos dois campos, o botao é ativado
     */
    private void verificaSePodeCalcularRota() {
        btnCalcular.setEnabled(!txtOrigem.getText().toString().isEmpty() && !txtDestino.getText().toString().isEmpty());
    }

    /*
    chamado quando clica em calcular rota.
    verifica os campos de origem e destino
    adiciona no banco de dados, a origem e o destino
    para que na próxima execução seja indexado no autocomplete do campo.
    se o campo foi digitado, insere no banco, se foi selecionado no autocomplete
    atualiza o numero de usos no banco
     */
    public void onClickCalcularRota(View view) {

        this.origem = txtOrigem.getText().toString();
        this.destino = txtDestino.getText().toString();

        actionMenuTelaMain.fechar();

        if (origem.isEmpty() || destino.isEmpty()) {
            mostra("Insira o endereço de origem e destino!");
            return;
        }

        if(!((AdapterEndereco)txtOrigem.getAdapter()).containsEndereco(origem)){
            enderecoOrigemSelecionado = new Endereco(0, origem, 1);
        }

        if(!((AdapterEndereco)txtDestino.getAdapter()).containsEndereco(destino)){
            enderecoDestinoSelecionado = new Endereco(0, destino, 1);
        }

        new Dao(MainActivity.this).addEnderecos(enderecoOrigemSelecionado , enderecoDestinoSelecionado);

        try {
            new DirectionFinder(this, origem, destino).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /*
    chama a tela de configuracoes de consumo
     */
    public void onClickConfiguracoesDeConsumo(View view){
        Intent it = new Intent(MainActivity.this, TelaConsumo.class);
        startActivityForResult(it, 0);
        actionMenuTelaMain.fechar();
        overridePendingTransition(R.anim.in_left, R.anim.out_back);
    }
    /*
    quando alguem seleciona algum item do menu superior
    (botao de voltar ou botao de exibir estimativa de gastos
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                actionMenuTelaMain.abrirOuFechar();
                break;
            case R.id.mn_main:

                if(new Dao(MainActivity.this).selecionouConsumo()){
                    if (origem.isEmpty() || destino.isEmpty()) {
                        mostra(getString(R.string.msg_insira_endereco_origem_destino));
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString( getString(R.string.bundle_origem), origem);
                        bundle.putString( getString(R.string.bundle_destino), destino);
                        bundle.putSerializable( getString(R.string.bundle_distancia), distancia);
                        bundle.putSerializable( getString(R.string.bundle_duracao), duracao);

                        Intent it = new Intent(MainActivity.this, TelaEstimativaGastos.class);

                        it.putExtras(bundle);
                        startActivity(it);
                        overridePendingTransition(R.anim.in_right, R.anim.out_back);
                    }
                }else{
                    Dialogs.naoInseriuConsumo(MainActivity.this);
                }
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    executa no momento em que abre a tela e o mapa é gerado
    logo quando é gerado, vai para a longitude e latitude da utfpr, e foca nesse ponto
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(-25.703885, -53.097661);
        CameraPosition position = CameraPosition.builder().target(latLng).zoom(15).tilt(90).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }
    /*
    assim que a rota inicia o calculo, retira todos os pontos marcados no mapa e
    abre a barra de carregamento
     */
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, getString(R.string.title_progress_encontrando_caminho), getString(R.string.text_progress_encontrando_caminho), true);
        if (marcadoresDeOrigem != null) {
            for (Marker marker : marcadoresDeOrigem) {
                marker.remove();
            }
        }
        if (marcadoresDeDestino != null) {
            for (Marker marker : marcadoresDeDestino) {
                marker.remove();
            }
        }
        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }
    /*
    fecha a barra de carregamento
    e mostra a rota no mapa
     */
    @Override
    public void onDirectionFinderSuccess(List<Rota> rotas) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        marcadoresDeOrigem = new ArrayList<>();
        marcadoresDeDestino = new ArrayList<>();

        for (Rota rota : rotas) {

            this.distancia = rota.getDistancia();
            this.duracao = rota.getDuracao();

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(rota.getLocalInicial(), 16));

            marcadoresDeOrigem.add(map.addMarker(new MarkerOptions()
                    .title(rota.getEnderecoInicial())
                    .position(rota.getLocalInicial())));
            marcadoresDeDestino.add(map.addMarker(new MarkerOptions()
                    .title(rota.getEnderecoFinal())
                    .position(rota.getLocalFinal())));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color( getResources().getColor(R.color.colorAccent) ).
                    width(10);

            for (int i = 0; i < rota.getPontos().size(); i++) {
                polylineOptions.add(rota.getPontos().get(i));
            }

            polylinePaths.add(map.addPolyline(polylineOptions));

        }

        ((TextView) findViewById(R.id.lblDuracao)).setText( duracao.getTexto() );
        ((TextView) findViewById(R.id.lblDistancia)).setText( distancia.getTexto() );
    }
    /*
    assim que finaliza o calculo da rota, retorna o resultado
    atraves de um enumerador, com todos os possiveis resultados
    do calculo, como sucesso, rota nao encontrada etc
    se der tudo certo, ele exibe o botao superior que vai pra tela de estimativa de gastos
     */
    @Override
    public void resultadoRequisicao(TipoRetornoDirectionsAPI tipoRetornoDirectionsAPI) {
        if(tipoRetornoDirectionsAPI == null){
            return;
        }
        if(tipoRetornoDirectionsAPI.equals(TipoRetornoDirectionsAPI.OK)){
            menu.getItem(0).setVisible(true);
        }
        mostra(tipoRetornoDirectionsAPI.getMensagem());
    }
    /*
    insere botao de ir pra tela de estimativa de gastos no menu superior
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    /*
    chamado assim que uma outra tela finaliza e volta pra esta
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    metodo para mostrar um toast
     */
    public void mostra(String s){
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
