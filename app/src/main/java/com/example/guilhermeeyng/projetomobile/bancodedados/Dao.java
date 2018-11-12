package com.example.guilhermeeyng.projetomobile.bancodedados;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.guilhermeeyng.projetomobile.R;
import com.example.guilhermeeyng.projetomobile.entidades.Ano;
import com.example.guilhermeeyng.projetomobile.entidades.Endereco;
import com.example.guilhermeeyng.projetomobile.entidades.Marca;
import com.example.guilhermeeyng.projetomobile.entidades.Motor;
import com.example.guilhermeeyng.projetomobile.entidades.Preferencia;
import com.example.guilhermeeyng.projetomobile.entidades.TipoCombustivel;
import com.example.guilhermeeyng.projetomobile.entidades.Veiculo;
import com.example.guilhermeeyng.projetomobile.enums.Tema;
import com.example.guilhermeeyng.projetomobile.enums.TipoMapa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dao {

    private Context context;
    private Banco banco;

    public Dao(Context context) {
        this.context = context;
        this.banco = new Banco(context);
    }

    public ArrayList<Marca> getAllMarcas() { // certo
        ArrayList<Marca> marcas = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query(Marca.NOME_TABELA, Marca.CAMPOS, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(Marca.ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(Marca.NOME));
                    marcas.add(new Marca(id, nome));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return marcas;
    }
    public ArrayList<Ano> getAnos(Marca marcaSelecionada, String modeloSelecionado) {
        ArrayList<Ano> anos = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT " + Ano.NOME_TABELA + "." + Ano.ID + ", " + Ano.NOME_TABELA + "." + Ano.NOME +
                " FROM " + Ano.NOME_TABELA +
                " WHERE " + Ano.NOME_TABELA + "." + Ano.ID +
                " IN " + "(SELECT " + Veiculo.NOME_TABELA + "." + Veiculo.ANO +
                " FROM " + Veiculo.NOME_TABELA +
                " INNER JOIN " + Ano.NOME_TABELA +
                " WHERE " + Veiculo.NOME_TABELA + "." + Veiculo.MODELO + " = '" + modeloSelecionado + "' AND " + Veiculo.NOME_TABELA + "." + Veiculo.MARCA + " = " + marcaSelecionada.getId() + " GROUP BY 1)" +
                " ORDER BY 1 DESC";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(Ano.ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(Ano.NOME));
                    anos.add(new Ano(id, nome));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return anos;
    }
    public ArrayList<Veiculo> getVeiculos(Marca marcaSelecionada, String modeloSelecionado, Ano anoSelecionado) {
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT " + Veiculo.NOME_TABELA + "." + Veiculo.ID + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.MARCA + ", " +
                Marca.NOME_TABELA + "." + Marca.NOME + " AS 'nome_marca', " +
                Veiculo.NOME_TABELA + "." + Veiculo.MODELO + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.MOTOR + ", " +
                Motor.NOME_TABELA + "." + Motor.NOME + " AS 'nome_motor', " +
                Veiculo.NOME_TABELA + "." + Veiculo.VERSAO + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.TIPO_COMBUSTIVEL + ", " +
                TipoCombustivel.NOME_TABELA + "." + TipoCombustivel.NOME + " AS 'nome_combustivel', " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_ETANOL_CIDADE + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_ETANOL_ESTRADA + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_GAS_DIESEL_CIDADE + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_GAS_DIESEL_ESTRADA + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.ANO + ", " +
                Ano.NOME_TABELA + "." + Ano.NOME + " AS 'nome_ano'" +
                " FROM " + Veiculo.NOME_TABELA + " INNER JOIN " + Marca.NOME_TABELA + " ON " + Veiculo.NOME_TABELA + "." + Veiculo.MARCA + " = " + Marca.NOME_TABELA + "." + Marca.ID +
                " INNER JOIN " + Motor.NOME_TABELA + " ON " + Motor.NOME_TABELA + "." + Motor.ID + " = " + Veiculo.NOME_TABELA + "." + Veiculo.MOTOR +
                " INNER JOIN " + TipoCombustivel.NOME_TABELA + " ON " + TipoCombustivel.NOME_TABELA + "." + TipoCombustivel.ID + " = " + Veiculo.NOME_TABELA + "." + Veiculo.TIPO_COMBUSTIVEL +
                " INNER JOIN " + Ano.NOME_TABELA + " ON " + Ano.NOME_TABELA + "." + Ano.ID + " = " + Veiculo.NOME_TABELA + "." + Veiculo.ANO +
                " WHERE " + Marca.NOME_TABELA + "." + Marca.ID + " = " + marcaSelecionada.getId() + " AND " + Veiculo.NOME_TABELA + "." + Veiculo.MODELO + " = '" + modeloSelecionado + "' AND " + Veiculo.NOME_TABELA + "." + Veiculo.ANO + " = " + anoSelecionado.getId();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.ID));
                    int idMarca = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.MARCA));
                    String nomeMarca = cursor.getString(cursor.getColumnIndexOrThrow("nome_marca"));
                    String modelo = cursor.getString(cursor.getColumnIndexOrThrow(Veiculo.MODELO));
                    int idMotor = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.MOTOR));
                    String nomeMotor = cursor.getString(cursor.getColumnIndexOrThrow("nome_motor"));
                    String versao = cursor.getString(cursor.getColumnIndexOrThrow(Veiculo.VERSAO));
                    int idTipoCombustivel = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.TIPO_COMBUSTIVEL));
                    String nomeTipoCombustivel = cursor.getString(cursor.getColumnIndexOrThrow("nome_combustivel"));
                    double consEtanolCidade = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_ETANOL_CIDADE));
                    double consEtanolEstrada = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_ETANOL_ESTRADA));
                    double consGasDieselCidade = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_GAS_DIESEL_CIDADE));
                    double consGasDieselEstrada = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_GAS_DIESEL_ESTRADA));
                    int idAno = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.ANO));
                    String nomeAno = cursor.getString(cursor.getColumnIndexOrThrow("nome_ano"));

                    Marca marca = new Marca(idMarca, nomeMarca);
                    Motor motor = new Motor(idMotor, nomeMotor);
                    TipoCombustivel tipoCombustivel = new TipoCombustivel(idTipoCombustivel, nomeTipoCombustivel);
                    Ano ano = new Ano(idAno, nomeAno);

                    Veiculo veiculo = new Veiculo(marca, tipoCombustivel, motor, ano, id, modelo, versao, consEtanolCidade, consEtanolEstrada, consGasDieselCidade, consGasDieselEstrada);

                    veiculos.add(veiculo);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return veiculos;
    }
    public ArrayList<String> getAllModelos(Marca marcaSelecionada) {
        ArrayList<String> modelos = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query(true, Veiculo.NOME_TABELA, new String[]{Veiculo.MODELO}, Veiculo.MARCA + " = ?", new String[]{String.valueOf(marcaSelecionada.getId())}, "1", null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String modelo = cursor.getString(cursor.getColumnIndexOrThrow(Veiculo.MODELO));
                    modelos.add(modelo);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return modelos;
    }
    public void addEnderecos(Endereco... enderecos) {
        SQLiteDatabase db = banco.getWritableDatabase();
        for (Endereco end : enderecos) {
            if (end.getId() != 0) { // atualiza a qtd de um endereco que ja está no banco
                ContentValues valores = new ContentValues();
                valores.put(Endereco.NOME, end.getNome());
                valores.put(Endereco.QTD, end.getQtd() + 1);
                long resultado = db.update(Endereco.NOME_TABELA, valores, Endereco.ID + " = " + end.getId(), null);
            } else { // insere no banco um endereco novo
                ContentValues valores = new ContentValues();
                valores.put(Endereco.NOME, end.getNome());
                valores.put(Endereco.QTD, 1);
                long resultado = db.insert(Endereco.NOME_TABELA, null, valores);
            }
        }
        db.close();
    }
    public ArrayList<Endereco> getAllEnderecos() {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT * FROM " + Endereco.NOME_TABELA + " ORDER BY " + Endereco.QTD + " DESC;";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(Endereco.ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(Endereco.NOME));
                    int qtd = cursor.getInt(cursor.getColumnIndexOrThrow(Endereco.QTD));
                    enderecos.add(new Endereco(id, nome, qtd));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return enderecos;
    }
    public boolean existeBanco() {
        int qtdItens = getLinhasTabela(Ano.NOME_TABELA) + getLinhasTabela(Marca.NOME_TABELA) + getLinhasTabela(TipoCombustivel.NOME_TABELA) + getLinhasTabela(Motor.NOME_TABELA) + getLinhasTabela(Veiculo.NOME_TABELA);

        if (qtdItens == 0) {
            new PopularBanco(context).execute();
        }

        return qtdItens > 0;
    }
    public int getLinhasTabela(String nomeTabela) {
        SQLiteDatabase db = banco.getReadableDatabase();
        int qtd = 0;
        Cursor c = db.rawQuery("SELECT count(*) from " + nomeTabela + ";", null);
        if (c != null) {
            c.moveToFirst();
            qtd = c.getInt(0);
        }
        db.close();
        return qtd;
    }
    /*
    pega o veiculo do usuario, se estiver vazia indica que o usuario
    ainda nao selecionou o consumo
     */
    public boolean selecionouConsumo() {
        Veiculo v = getVeiculoUsuario();
        return v.getConsEtanolEstrada() > 0.0 || v.getConsEtanolCidade() > 0.0 || v.getConsGasDieselEstrada() > 0.0 || v.getConsGasDieselCidade() > 0.0;
    }
    public void salvarConsumo(Veiculo veiculo) {
        ContentValues valores = new ContentValues();
        valores.put(Veiculo.ID, 1);
        valores.put(Veiculo.TIPO_COMBUSTIVEL, veiculo.getTipoCombustivel().getId());

        switch (veiculo.getTipoCombustivel().getNome().toUpperCase()) {
            case "FLEX":
                valores.put(Veiculo.CONS_ETANOL_CIDADE, veiculo.getConsEtanolCidade());
                valores.put(Veiculo.CONS_ETANOL_ESTRADA, veiculo.getConsEtanolEstrada());
                valores.put(Veiculo.CONS_GAS_DIESEL_CIDADE, veiculo.getConsGasDieselCidade());
                valores.put(Veiculo.CONS_GAS_DIESEL_ESTRADA, veiculo.getConsGasDieselEstrada());
                break;
            case "GASOLINA":
                valores.put(Veiculo.CONS_GAS_DIESEL_CIDADE, veiculo.getConsGasDieselCidade());
                valores.put(Veiculo.CONS_GAS_DIESEL_ESTRADA, veiculo.getConsGasDieselEstrada());
                break;
            case "ETANOL":
                valores.put(Veiculo.CONS_ETANOL_CIDADE, veiculo.getConsEtanolCidade());
                valores.put(Veiculo.CONS_ETANOL_ESTRADA, veiculo.getConsEtanolEstrada());
                break;
            case "DIESEL":
                valores.put(Veiculo.CONS_GAS_DIESEL_CIDADE, veiculo.getConsGasDieselCidade());
                valores.put(Veiculo.CONS_GAS_DIESEL_ESTRADA, veiculo.getConsGasDieselEstrada());
                break;
        }

        SQLiteDatabase db = banco.getWritableDatabase();
        long resultado = db.update(Veiculo.NOME_TABELA, valores, Veiculo.ID + "=1", null);
        db.close();
    }
    /*
    verifica a primeira linha da tabela de veiculos, que é onde é armazenado o veiculo do usuario
     */
    public Veiculo getVeiculoUsuario() {
        Veiculo veiculo = new Veiculo();
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT " + Veiculo.NOME_TABELA + "." + Veiculo.CONS_ETANOL_CIDADE + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_ETANOL_ESTRADA + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_GAS_DIESEL_CIDADE + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.CONS_GAS_DIESEL_ESTRADA + ", " +
                Veiculo.NOME_TABELA + "." + Veiculo.TIPO_COMBUSTIVEL + ", " +
                TipoCombustivel.NOME_TABELA + "." + TipoCombustivel.NOME + " " +
                "FROM " + Veiculo.NOME_TABELA + " INNER JOIN " + TipoCombustivel.NOME_TABELA + " " +
                "ON " + Veiculo.NOME_TABELA + "." + Veiculo.TIPO_COMBUSTIVEL + " = " + TipoCombustivel.NOME_TABELA + "." + TipoCombustivel.ID + " " +
                "WHERE " + Veiculo.NOME_TABELA + "." + Veiculo.ID + " = 1";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int idTipoCombustivel = cursor.getInt(cursor.getColumnIndexOrThrow(Veiculo.TIPO_COMBUSTIVEL));
                String nomeCombustivel = cursor.getString(cursor.getColumnIndexOrThrow(TipoCombustivel.NOME));
                double consEtanolCidade = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_ETANOL_CIDADE));
                double consEtanolEstrada = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_ETANOL_ESTRADA));
                double consGasDieselCidade = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_GAS_DIESEL_CIDADE));
                double consGasDieselEstrada = cursor.getDouble(cursor.getColumnIndexOrThrow(Veiculo.CONS_GAS_DIESEL_ESTRADA));
                veiculo.setTipoCombustivel(new TipoCombustivel(idTipoCombustivel, nomeCombustivel));
                veiculo.setConsEtanolCidade(consEtanolCidade);
                veiculo.setConsEtanolEstrada(consEtanolEstrada);
                veiculo.setConsGasDieselCidade(consGasDieselCidade);
                veiculo.setConsGasDieselEstrada(consGasDieselEstrada);
            }
        }

        db.close();

        return veiculo;
    }

    public TipoMapa getTipoMapaUsuario() {
        return TipoMapa.TERRENO;
    }

    public void salvarTema(Tema tema){
        ContentValues valor1 = new ContentValues();
        valor1.put(Preferencia.VALOR, tema.getCorDestaque());

        ContentValues valor2 = new ContentValues();
        valor2.put(Preferencia.VALOR, tema.getCorDestaqueClaro());

        ContentValues valor3 = new ContentValues();
        valor3.put(Preferencia.VALOR, tema.getCorSecundaria());

        ContentValues valor4 = new ContentValues();
        valor4.put(Preferencia.VALOR, tema.getCorSecundariaClaro());

        SQLiteDatabase db = banco.getWritableDatabase();

        long resultado1 = db.update(Preferencia.NOME_TABELA, valor1, Preferencia.ID + " =? ",  new String[]{String.valueOf( Preferencia.ID_COR_DESTAQUE )});
        long resultado2 = db.update(Preferencia.NOME_TABELA, valor2, Preferencia.ID + " =? ",  new String[]{String.valueOf( Preferencia.ID_COR_DESTAQUE_CLARO )});
        long resultado3 = db.update(Preferencia.NOME_TABELA, valor3, Preferencia.ID + " =? ",  new String[]{String.valueOf( Preferencia.ID_COR_SECUNDARIA )});
        long resultado4 = db.update(Preferencia.NOME_TABELA, valor4, Preferencia.ID + " =? ",  new String[]{String.valueOf( Preferencia.ID_COR_SECUNDARIA_CLARO )});

        Log.i("Script", "salvando tema: " + resultado1 + " " + resultado2 + " " + resultado3 + " " + resultado4);
        db.close();
    }

    public void salvarPreferencia(Preferencia preferencia){
        ContentValues valores = new ContentValues();
        valores.put(Preferencia.ID, preferencia.getValor());

        SQLiteDatabase db = banco.getWritableDatabase();
        long resultado = db.update(Preferencia.NOME_TABELA, valores, Preferencia.ID + " =? ",  new String[]{String.valueOf( preferencia.getId())});

        Log.i("Script", "salvando preferencia: " + preferencia.getId() + " com valor: " + preferencia.getValor() + " com resultado: " + resultado);
        db.close();
    }

    public Preferencia getPreferencia(int idPreferencia){
        Preferencia preferencia = new Preferencia(idPreferencia);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query(Preferencia.NOME_TABELA, new String[]{Preferencia.VALOR}, Preferencia.ID+"=?", new String[]{String.valueOf( preferencia.getId())}, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int valor = cursor.getInt(cursor.getColumnIndex(Preferencia.VALOR));
                preferencia.setValor(valor);
            }
        }
        db.close();
        return preferencia;
    }

    /*
    inner class responsavel por popular o banco, e mostrar em tempo real a inserção
     */
    public class PopularBanco extends AsyncTask<Void, Void, Void> {

        private Banco banco;
        private Context context;
        private ProgressDialog progressDialog;
        private int linhasPopuladas = 0, linhasTotais = 0;
        private String nomeTabelaPopulada = "";

        public PopularBanco(Context context) {
            this.context = context;
            this.banco = new Banco(context);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(context.getString(R.string.title_progress_popular_banco));
            progressDialog.setMessage(context.getString(R.string.msg_progress_popular_banco));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = this.banco.getWritableDatabase();
            ArrayList<String> lista = getAllLinhas("inserts.txt");
            linhasTotais = lista.size();
            for (int i = 0; i < lista.size(); i++) {
                db.execSQL(lista.get(i));
                this.linhasPopuladas = i;
                // pega o nome da tabela a partir do sql
                this.nomeTabelaPopulada = lista.get(i).split(" ")[2].replace("`", "");
                publishProgress();
            }

            db.close();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(context.getString(R.string.msg_progress_popular_banco_dados_inseridos) + linhasPopuladas + "/" + linhasTotais + "\n" +
                    "Tabela: " + nomeTabelaPopulada);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.setMessage("Banco populado!");
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }

        public ArrayList<String> getAllLinhas(String arquivo) {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream;
            ArrayList<String> linhas = new ArrayList<>();
            try {
                inputStream = assetManager.open(arquivo);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String recebe_string;
                while ((recebe_string = bufferedReader.readLine()) != null) {
                    linhas.add(recebe_string);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return linhas;
        }
    }


    public Tema getTemaUsuario(){
        return Tema.PADRAO;
    }
}