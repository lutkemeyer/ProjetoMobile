package com.example.guilhermeeyng.projetomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;

import java.util.ArrayList;

public class AdapterAno extends BaseAdapter{

    private String hint;
    private Context context;
    private ArrayList<Ano> anos;

    public AdapterAno(Context context, String hint, ArrayList<Ano> anos) {
        this.anos = anos;
        this.hint = hint;
        this.context = context;
    }

    @Override
    public int getCount() {
        return anos.size() + 1;
    }

    @Override
    public Ano getItem(int posicao) {
        return ( posicao == 0 ) ? null : anos.get( posicao - 1 );
    }

    @Override
    public long getItemId(int posicao) {
        return ( posicao == 0 ) ? -1 : anos.get( posicao - 1 ).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.sp_item, parent, false);
        ((TextView)view.findViewById(R.id.lblTexto)).setText( ( posicao == 0 )? hint : anos.get( posicao - 1 ).getNome() );
        return view;
    }
}
