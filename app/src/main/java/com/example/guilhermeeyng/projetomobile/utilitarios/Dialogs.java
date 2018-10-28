package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.TelaConsumo;

/*
classe responsavel por mostrar os popups normais da aplicação (não envolve popups personalizados)
popups personalizados são criadas classes especificas para gerenciar de forma mais clara
 */
public class Dialogs {

    public static void naoInseriuConsumo(final Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle(R.string.titulo_dialog_nao_informou_consumo)
                .setMessage(R.string.mensagem_dialog_nao_informou_consumo)
                .setPositiveButton(R.string.botao_positivo_dialog_nao_informou_consumo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent it = new Intent(activity, TelaConsumo.class);
                        activity.startActivity(it);
                    }
                }).setNegativeButton(R.string.botao_negativo_dialog_nao_informou_consumo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create().show();
    }

}
