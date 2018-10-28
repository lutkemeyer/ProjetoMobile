package com.example.guilhermeeyng.projetomobile.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.utilitarios.LogoMarcaAutomotiva;

import java.util.ArrayList;

public class AdapterVeiculo extends BaseAdapter{

    private Activity activity;
    private ArrayList<Veiculo> veiculos;
    private int selecionadoIndex;
    private RadioButton[] radioButtons;

    public AdapterVeiculo(Activity activity, ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
        this.activity = activity;
        this.radioButtons = new RadioButton[veiculos.size()];
        this.selecionadoIndex = -1;
    }

    @Override
    public int getCount() {
        return veiculos.size();
    }

    @Override
    public Veiculo getItem(int posicao) {
        return veiculos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return veiculos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lv_item, parent, false);

        ImageView ivMarca = view.findViewById(R.id.ivMarca);
        TextView lblModelo = view.findViewById(R.id.lblModelo);
        TextView lblDetalhes = view.findViewById(R.id.lblDetalhes);
        TextView lblAno = view.findViewById(R.id.lblAno);
        RadioButton rbSelecionado = view.findViewById(R.id.rbSelecionado);

        Veiculo v = veiculos.get(posicao);

        lblModelo.setText( v.getModelo() );
        lblDetalhes.setText( (v.getVersao() == null ? "Padrão" : v.getVersao()) + " | " + v.getMotor() + " | " + v.getTipoCombustivel().getNome());
        lblAno.setText( v.getAno() );

        Drawable drawable = LogoMarcaAutomotiva.valueOf(v.getMarca().getNome().replace(" ","").replace("-", "").toUpperCase()).getLogo(activity);
        drawable.setTint(activity.getResources().getColor(R.color.colorLight));

        ivMarca.setImageDrawable( drawable );

        radioButtons[posicao] = rbSelecionado;

        return view;
    }

    public void selecionar(int posicao){
        if(selecionadoIndex >= 0) {
            radioButtons[selecionadoIndex].setChecked(false);
        }
        radioButtons[posicao].setChecked(true);
        this.selecionadoIndex = posicao;
    }

    public Veiculo getVeiculoSelecionado(){
        return (selecionadoIndex >= 0) ? veiculos.get(this.selecionadoIndex) : null;
    }
}
