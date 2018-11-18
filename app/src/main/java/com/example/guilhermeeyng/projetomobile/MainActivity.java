package com.example.guilhermeeyng.projetomobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.example.guilhermeeyng.projetomobile.adapters.AdapterEndereco;
import com.example.guilhermeeyng.projetomobile.bancodedados.Dao;
import com.example.guilhermeeyng.projetomobile.customviews.DiagonalView;
import com.example.guilhermeeyng.projetomobile.entidades.Endereco;
import com.example.guilhermeeyng.projetomobile.entidades.Tema;
import com.example.guilhermeeyng.projetomobile.utilitarios.*;
import com.example.guilhermeeyng.projetomobile.enums.TipoRetornoDirectionsAPI;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.*;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener{

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

    private Endereco enderecoOrigemSelecionado, enderecoDestinoSelecionado;
    private Duracao duracao;
    private Distancia distancia;

    private Tema temaUsuario;

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
        actionBar.setTitle(getResources().getString(R.string.titulo_tela_principal));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

    // PERSONALIZA O LAYOUT
    @Override
    protected void onResume() {
        super.onResume();

        Window window = MainActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Drawable ic_menu_bolinhas = getDrawable(R.drawable.ic_menu_bolinhas);

        ActionBar actionBar = getSupportActionBar();
        DiagonalView diagonalView = findViewById(R.id.diagonalView);
        Button btnConfiguracoesDeConsumo = findViewById(R.id.btnConfiguracoesDeConsumo);
        LinearLayout conteudoLayoutMain = findViewById(R.id.conteudoLayoutMain);
        AutoCompleteTextView txtOrigem = findViewById(R.id.txtOrigem);
        AutoCompleteTextView txtDestino = findViewById(R.id.txtDestino);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        ImageView imgDistancia = findViewById(R.id.imgDistancia);
        TextView lblDistancia = findViewById(R.id.lblDistancia);
        ImageView imgDuracao = findViewById(R.id.imgDuracao);
        TextView lblDuracao = findViewById(R.id.lblDuracao);

        temaUsuario = new Dao(MainActivity.this).getTemaUsuario();

        // cor da status bar
        window.setStatusBarColor( temaUsuario.getCorDestaqueInt() );
        actionBar.setBackgroundDrawable(new ColorDrawable( temaUsuario.getCorDestaqueInt() ));

        // cor do icone que abre o menu
        ic_menu_bolinhas.setTint( temaUsuario.getCorDestaqueClaraInt() );
        actionBar.setHomeAsUpIndicator(ic_menu_bolinhas);

        // cor do titulo da tela
        Spannable spannablerTitle = new SpannableString(actionBar.getTitle().toString());
        spannablerTitle.setSpan(new ForegroundColorSpan( temaUsuario.getCorDestaqueClaraInt() ), 0, spannablerTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(spannablerTitle);

        // cor do fundo
        conteudoLayoutMain.getBackground().setTint( temaUsuario.getCorSecundariaInt() );
        diagonalView.getBackground().setTint( temaUsuario.getCorDestaqueInt() );

        // cor do botao configuracoes de consumo
        btnConfiguracoesDeConsumo.getBackground().setTint( temaUsuario.getCorDestaqueClaraInt() );
        btnConfiguracoesDeConsumo.setTextColor( temaUsuario.getCorSecundariaInt() );

        // cor dos campos
        txtOrigem.getCompoundDrawablesRelative()[0].setTint( temaUsuario.getCorDestaqueClaraInt() );
        //txtOrigem.setTextColor(  );
        txtOrigem.setHintTextColor( temaUsuario.getCorDestaqueClaraInt() );
        txtOrigem.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueClaraInt() ));
        txtDestino.getCompoundDrawablesRelative()[0].setTint( temaUsuario.getCorDestaqueClaraInt() );
        //txtOrigem.setTextColor(  );
        txtDestino.setHintTextColor( temaUsuario.getCorDestaqueClaraInt() );
        txtDestino.setBackgroundTintList(ColorStateList.valueOf( temaUsuario.getCorDestaqueClaraInt() ));

        // cor do botao calcular
        btnCalcular.getBackground().setTint( temaUsuario.getCorDestaqueClaraInt() );
        btnCalcular.setTextColor( temaUsuario.getCorDestaqueInt() );

        // cor das labels distancia e duracao
        imgDistancia.getDrawable().setTint( temaUsuario.getCorDestaqueClaraInt() );
        lblDistancia.setTextColor( temaUsuario.getCorDestaqueClaraInt() );

        imgDuracao.getDrawable().setTint( temaUsuario.getCorDestaqueClaraInt() );
        lblDuracao.setTextColor( temaUsuario.getCorDestaqueClaraInt() );

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
                break;
            case R.id.mn_preferencias:
                Intent it = new Intent(MainActivity.this, TelaPreferencias.class);
                startActivityForResult(it, 0);
                break;
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
        map.setMapType(new Dao(MainActivity.this).getTipoMapaUsuario().getId());
        LatLng local = new LatLng(-25.750386484247976, -53.06071836501361);
        map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(local).zoom(14.2933f).build()));
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
        for(int i=0; i<menu.size(); i++){
            menu.getItem(i).getIcon().setTint( temaUsuario.getCorDestaqueClaraInt() );
        }
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
