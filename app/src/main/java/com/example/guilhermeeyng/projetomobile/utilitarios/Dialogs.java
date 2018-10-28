package com.example.guilhermeeyng.projetomobile.utilitarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import com.example.guilhermeeyng.projetomobile.TelaConsumo;

public class Dialogs {

    public static void naoInseriuConsumo(final Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Você ainda não informou o consumo do seu veículo")
                .setMessage("Deseja informar o consumo agora?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent it = new Intent(activity, TelaConsumo.class);
                        activity.startActivity(it);
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create().show();
    }

}
