package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guilhermeeyng.projetomobile.entidades.Preferencia;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Locale;

/*
classe reponsavel por conter metodos que auxiliam no app, geralmente conversores
 */
public class Util {
    public static String toMonetary(double v){
        return NumberFormat.getCurrencyInstance(new Locale("pt","BR")).format(v);
    }
    public static String toLitros(double v){
        return String.format("%.2f", v ) + " L";
    }
    public static String toConsumo(double v){
        return String.format("%.2f", v ) + " km/L";
    }
    public static void setCursorColor(EditText view, @ColorInt int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
