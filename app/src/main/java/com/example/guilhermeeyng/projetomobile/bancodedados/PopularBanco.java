package com.example.guilhermeeyng.projetomobile.bancodedados;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;
import com.example.guilhermeeyng.projetomobile.entidades.Motor;
import com.example.guilhermeeyng.projetomobile.entidades.TipoCombustivel;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PopularBanco extends AsyncTask<Void,Void,Void> {

    private Banco banco;
    private Context context;
    private ProgressDialog progressDialog;
    private int linhasPopuladas = 0;

    public PopularBanco(Context context){
        this.context = context;
        this.banco = new Banco(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Populando o banco... (titulo)");
        progressDialog.setMessage("Populando o banco... (msg)");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int linhas = linhasTabelaAnos();
        linhas += linhasTabelaMarcas();
        linhas += linhasTabelaTiposCombustivel();
        linhas += linhasTabelaTiposMotores();
        linhas += linhasTabelaVeiculos();

        // melhorar contagem de dados (com sql especifico talvez)

        SQLiteDatabase db = this.banco.getWritableDatabase();
        if(linhas == 0){
            ArrayList<String> lista =  getAllLinhas("inserts.txt");
            for(int i = 0; i < lista.size(); i++){
                db.execSQL(lista.get(i));
                this.linhasPopuladas = i;
                publishProgress();
            }
        }
        db.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage("Dados inseridos: " + linhasPopuladas + "");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.setMessage("Banco populado!");
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }

    public ArrayList<String> getAllLinhas(String arquivo){
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream;
        ArrayList<String> linhas = new ArrayList<>();
        try {
            inputStream = assetManager.open(arquivo);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebe_string;
            while((recebe_string = bufferedReader.readLine())!=null){
                linhas.add(recebe_string);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    public int linhasTabelaAnos(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + Ano.NOME_TABELA + ";", null);
        int linhas;
        if(cursor != null){
            if(cursor.getCount() > 0){
                linhas = cursor.getCount();
            }else{
                linhas = 0;
            }
        }else{
            linhas = -1;
        }
        db.close();
        return linhas;
    }
    public int linhasTabelaMarcas(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + Marca.NOME_TABELA + ";", null);
        int linhas;
        if(cursor != null){
            if(cursor.getCount() > 0){
                linhas = cursor.getCount();
            }else{
                linhas = 0;
            }
        }else{
            linhas = -1;
        }
        db.close();
        return linhas;
    }
    public int linhasTabelaTiposMotores(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + Motor.NOME_TABELA + ";", null);
        int linhas;
        if(cursor != null){
            if(cursor.getCount() > 0){
                linhas = cursor.getCount();
            }else{
                linhas = 0;
            }
        }else{
            linhas = -1;
        }
        db.close();
        return linhas;
    }
    public int linhasTabelaTiposCombustivel(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TipoCombustivel.NOME_TABELA + ";", null);
        int linhas;
        if(cursor != null){
            if(cursor.getCount() > 0){
                linhas = cursor.getCount();
            }else{
                linhas = 0;
            }
        }else{
            linhas = -1;
        }
        db.close();
        return linhas;
    }
    public int linhasTabelaVeiculos(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + Veiculo.NOME_TABELA + ";", null);
        int linhas;
        if(cursor != null){
            if(cursor.getCount() > 0){
                linhas = cursor.getCount();
            }else{
                linhas = 0;
            }
        }else{
            linhas = -1;
        }
        db.close();
        return linhas;
    }
}
