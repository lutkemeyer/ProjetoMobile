package com.example.guilhermeeyng.projetomobile.adapters;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.enums.LogoMarcaAutomotiva;

import java.util.ArrayList;

public class AdapterVeiculo extends BaseAdapter{

    private Activity activity;
    private ArrayList<Veiculo> veiculos;
    private int selecionadoIndex;
    private RadioButton[] radioButtons;
    private int corTexto;
    private int corDestaque;

    public AdapterVeiculo(Activity activity, ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
        this.activity = activity;
        this.radioButtons = new RadioButton[veiculos.size()];
        this.selecionadoIndex = -1;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos){
        this.veiculos = veiculos;
        this.radioButtons = new RadioButton[veiculos.size()];
        notifyDataSetChanged();
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

        Spannable spannablerText = new SpannableString(v.getModelo());
        spannablerText.setSpan(new ForegroundColorSpan( corDestaque ), 0, spannablerText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lblModelo.setText( spannablerText );
        lblModelo.setTextColor( corDestaque == 0 ? activity.getResources().getColor(R.color.colorPrimaryLight) : corDestaque);
        lblDetalhes.setText( (v.getVersao() == null ? "Padrão" : v.getVersao()) + " | " + v.getMotor() + " | " + v.getTipoCombustivel().getNome());
        lblModelo.setTextColor( corTexto == 0 ? activity.getResources().getColor(R.color.colorLight) : corTexto);
        lblAno.setText( v.getAno() );
        lblAno.setTextColor( corTexto == 0 ? activity.getResources().getColor(R.color.colorLight) : corTexto);

        Drawable drawable = LogoMarcaAutomotiva.valueOf(v.getMarca().getNome().replace(" ","").replace("-", "").toUpperCase()).getLogo(activity);
        drawable.setTint( corTexto == 0 ? activity.getResources().getColor(R.color.colorLight) : corTexto);

        ivMarca.setImageDrawable( drawable );

        rbSelecionado.setButtonTintList(new ColorStateList(
                new int[][]{ new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked} },
                new int[]{ Color.DKGRAY, corDestaque}
        ));

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

    public void setCorTexto(int corTexto) {
        this.corTexto = corTexto;
    }

    public void setCorDestaque(int corDestaque){
        this.corDestaque = corDestaque;
    }
}
