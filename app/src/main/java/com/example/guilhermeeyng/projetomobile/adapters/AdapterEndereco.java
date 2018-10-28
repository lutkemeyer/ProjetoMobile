package com.example.guilhermeeyng.projetomobile.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;
import com.example.guilhermeeyng.projetomobile.entidades.Endereco;

import java.util.ArrayList;
import java.util.List;

public class AdapterEndereco  extends ArrayAdapter<Endereco> {

    private Context context;
    private ArrayList<Endereco> enderecos;
    private boolean isOrigem;

    public AdapterEndereco(@NonNull Context context, int resource, @NonNull ArrayList<Endereco> enderecos, boolean isOrigem) {
        super(context, resource, enderecos);
        this.context = context;
        this.enderecos = enderecos;
        this.isOrigem = isOrigem;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filtroEndereco;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.ac_item, parent, false
            );
        }

        TextView lblEndereco = convertView.findViewById(R.id.lblEndereco);
        ImageView imgLocal = convertView.findViewById(R.id.imgLocal);

        Endereco endereco = getItem(position);

        if (endereco != null) {
            lblEndereco.setText(endereco.getNome());
            imgLocal.setImageResource( isOrigem ? R.drawable.ic_inicio : R.drawable.ic_fim );
        }

        return convertView;
    }

    private Filter filtroEndereco = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resultados = new FilterResults();
            List<Endereco> sugestoes = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                sugestoes.addAll(enderecos);
            } else {
                String padraoFiltro = constraint.toString().toLowerCase().trim();

                for (Endereco endereco : enderecos) {
                    if (endereco.getNome().toLowerCase().contains( padraoFiltro )) {
                        sugestoes.add(endereco);
                    }
                }
            }

            resultados.values = sugestoes;
            resultados.count = sugestoes.size();

            return resultados;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Endereco) resultValue).getNome();
        }
    };

    public boolean containsEndereco(String nome){
        boolean contains = false;
        for(Endereco end : enderecos){
            if(end.getNome().equals(nome.trim())){
                contains = true;
            }
        }
        return contains;
    }
}
