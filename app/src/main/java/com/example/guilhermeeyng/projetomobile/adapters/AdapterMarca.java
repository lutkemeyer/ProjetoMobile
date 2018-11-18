package com.example.guilhermeeyng.projetomobile.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;
import com.example.guilhermeeyng.projetomobile.enums.LogoMarcaAutomotiva;

import java.util.ArrayList;

public class AdapterMarca extends BaseAdapter{

    private Activity activity;
    private String hint;
    private ArrayList<Marca> marcas;
    private int corIcone;

    public AdapterMarca(Activity activity, String hint, ArrayList<Marca> marcas) {
        this.marcas = marcas;
        this.hint = hint;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return marcas.size() + 1;
    }

    @Override
    public Marca getItem(int posicao) {
        return ( posicao == 0 ) ? null : marcas.get( posicao - 1 );
    }

    @Override
    public long getItemId(int posicao) {
        return ( posicao == 0 ) ? -1 : marcas.get( posicao - 1 ).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.sp_item_marcas, parent, false);
        TextView lblTexto = view.findViewById(R.id.lblTexto);
        ImageView imgMarca = view.findViewById(R.id.imgMarca);
        if(posicao == 0){
            lblTexto.setText(hint);
            imgMarca.setVisibility(View.GONE);
        }else{
            lblTexto.setText(marcas.get( posicao - 1 ).getNome());
            Drawable drawable = LogoMarcaAutomotiva.valueOf(marcas.get( posicao - 1 ).getNome().replace(" ","").replace("-", "").toUpperCase()).getLogo(activity);
            drawable.setTint( corIcone == 0 ? activity.getResources().getColor(R.color.colorAccent) : corIcone);
            imgMarca.setImageDrawable(drawable);
        }
        return view;
    }

    public void setCorIcone(int corIcone) {
        this.corIcone = corIcone;
    }
}
