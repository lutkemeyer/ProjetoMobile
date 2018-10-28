package com.example.guilhermeeyng.projetomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;

import java.util.ArrayList;

public class AdapterModelo extends BaseAdapter{

    private String hint;
    private Context context;
    private ArrayList<String> modelos;

    public AdapterModelo(Context context, String hint, ArrayList<String> modelos) {
        this.modelos = modelos;
        this.hint = hint;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelos.size() + 1;
    }

    @Override
    public String getItem(int posicao) {
        return ( posicao == 0 ) ? null : modelos.get( posicao - 1 );
    }

    @Override
    public long getItemId(int posicao) { return 0; }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.sp_item, parent, false);
        ((TextView)view.findViewById(R.id.lblTexto)).setText( ( posicao == 0 )? hint : modelos.get( posicao - 1) );
        return view;
    }
}
