package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.View;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.TelaConfiguracoes;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPicker{
    public static void mostra(Context c, final View botao, int corInicial, final OnColorSelectedListener listener){
        ColorPickerDialogBuilder
                .with(c)
                .setTitle("Escolha a cor")
                .initialColor( corInicial )
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .lightnessSliderOnly()
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        botao.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
                        listener.onColorSelected(selectedColor);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
