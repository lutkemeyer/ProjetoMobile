package com.example.guilhermeeyng.projetomobile.bancodedados;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;
import com.example.guilhermeeyng.projetomobile.entidades.Endereco;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;
import com.example.guilhermeeyng.projetomobile.entidades.Motor;
import com.example.guilhermeeyng.projetomobile.entidades.TipoCombustivel;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
*   Classe responsável por criar  o banco e pelo versionamento do mesmo
 */
public class Banco extends SQLiteOpenHelper {

    private Context context;

    public static final String NOME_BANCO = "banco5.db";
    public static final int VERSAO = 1;

    public Banco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
        this.context = context;
    }

    /*
    * É chamado quando a aplicação cria o banco de dados pela primeira vez,
    * neste metodo devem ter todas as diretrizes de criação e população inicial do banco.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Ano.NOME_TABELA + "(" + Ano.ID + " INTEGER NOT NULL PRIMARY KEY, " + Ano.NOME + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Marca.NOME_TABELA + "(" + Marca.ID + " INTEGER NOT NULL PRIMARY KEY, " + Marca.NOME + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipoCombustivel.NOME_TABELA + "(" + TipoCombustivel.ID + " INTEGER NOT NULL PRIMARY KEY, " + TipoCombustivel.NOME + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Motor.NOME_TABELA + "(" + Motor.ID + " INTEGER NOT NULL PRIMARY KEY, " + Motor.NOME + " TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Veiculo.NOME_TABELA + "(" + Veiculo.ID + " INTEGER NOT NULL PRIMARY KEY, " + Veiculo.MARCA + " INTEGER, " + Veiculo.MODELO + " TEXT, " + Veiculo.MOTOR + " INTEGER, " + Veiculo.VERSAO + " TEXT, " + Veiculo.TIPO_COMBUSTIVEL + " INTEGER, " + Veiculo.CONS_ETANOL_CIDADE + " REAL, " + Veiculo.CONS_ETANOL_ESTRADA + " REAL, " + Veiculo.CONS_GAS_DIESEL_CIDADE + " REAL, " + Veiculo.CONS_GAS_DIESEL_ESTRADA + " REAL, " + Veiculo.ANO + " INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Endereco.NOME_TABELA + "(" + Endereco.ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + Endereco.NOME + " TEXT, " + Endereco.QTD + " INTEGER);");
    }

    /*
    * É responsável por atualizar o banco de dados com alguma informação estrutural
    * que tenha sido alterada. Ele sempre é chamado quando uma atualização é necessária,
    * para não ter nenhum tipo de inconsistência de dados entre o banco existente no aparelho
    * e o novo que a aplicação irá utilizar.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Ano.NOME_TABELA + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Marca.NOME_TABELA + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TipoCombustivel.NOME_TABELA + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Motor.NOME_TABELA + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Veiculo.NOME_TABELA + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Endereco.NOME_TABELA + ";");
        onCreate(db);
    }
}
