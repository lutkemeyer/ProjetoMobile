package com.example.guilhermeeyng.projetomobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.R;

public class AdapterImagem extends PagerAdapter {

    private Context context;
    private int[] imagensTipoMapa;

    public AdapterImagem(Context context){
        this.context = context;
        imagensTipoMapa = new int[]{R.drawable.tipo_mapa_normal, R.drawable.tipo_mapa_satelite, R.drawable.tipo_mapa_terreno, R.drawable.tipo_mapa_hibrido};
    }

    @Override
    public int getCount() {
        return imagensTipoMapa.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.TOP);
        container.addView(ll);

        ImageView iv = new ImageView(context);

        iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageResource(imagensTipoMapa[position]);
        iv.setAdjustViewBounds(true);
        ll.addView(iv);

        return ll;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
