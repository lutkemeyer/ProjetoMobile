package com.example.guilhermeeyng.projetomobile.entidades;

public class Veiculo {

    public static final String NOME_TABELA = "veiculos";

    public static final String ID = "_id";
    public static final String MODELO = "modelo";
    public static final String VERSAO = "versao";
    public static final String CONS_ETANOL_CIDADE = "cons_etanol_cidade";
    public static final String CONS_ETANOL_ESTRADA = "cons_etanol_estrada";
    public static final String CONS_GAS_DIESEL_CIDADE = "cons_gasolina_diesel_cidade";
    public static final String CONS_GAS_DIESEL_ESTRADA = "cons_gasolina_diesel_estrada";

    public static final String MARCA = "id_marca";
    public static final String TIPO_COMBUSTIVEL = "id_tipo_combustivel";
    public static final String MOTOR = "id_motor";
    public static final String ANO = "id_ano";

    public static final String[] CAMPOS = {ID,MODELO,VERSAO,CONS_ETANOL_CIDADE,CONS_ETANOL_ESTRADA,CONS_GAS_DIESEL_CIDADE,CONS_GAS_DIESEL_ESTRADA,MARCA,TIPO_COMBUSTIVEL,MOTOR,ANO};

    private Marca marca;
    private TipoCombustivel tipoCombustivel;
    private Motor motor;
    private Ano ano;
    
    private int id;
    private String modelo;
    private String versao;
    
    private double consEtanolCidade;
    private double consEtanolEstrada;
    private double consGasolinaDieselCidade;
    private double consGasolinaDieselEstrada;

    public Veiculo(Marca marca, TipoCombustivel tipoCombustivel, Motor motor, Ano ano, int id, String modelo, String versao, double consEtanolCidade, double consEtanolEstrada, double consGasDieselCidade, double consGasDieselEstrada) {
        this.marca = marca;
        this.tipoCombustivel = tipoCombustivel;
        this.motor = motor;
        this.ano = ano;
        this.id = id;
        this.modelo = modelo;
        this.versao = versao;
        this.consEtanolCidade = consEtanolCidade;
        this.consEtanolEstrada = consEtanolEstrada;
        this.consGasolinaDieselCidade = consGasDieselCidade;
        this.consGasolinaDieselEstrada = consGasDieselEstrada;
    }

    public Veiculo() {
    }

    public Marca getMarca() {
        return marca;
    }
    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }
    public String getMotor() {
        return motor.getNome();
    }
    public String getAno() {
        return ano.getNome();
    }
    public int getId() {
        return id;
    }
    public String getModelo() {
        return modelo;
    }
    public String getVersao() {
        return versao;
    }
    public double getConsEtanolCidade() {
        return consEtanolCidade;
    }
    public double getConsEtanolEstrada() {
        return consEtanolEstrada;
    }
    public double getConsGasDieselCidade() {
        return consGasolinaDieselCidade;
    }
    public double getConsGasDieselEstrada() {
        return consGasolinaDieselEstrada;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
    public void setMotor(Motor motor) {
        this.motor = motor;
    }
    public void setAno(Ano ano) {
        this.ano = ano;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setVersao(String versao) {
        this.versao = versao;
    }
    public void setConsEtanolCidade(double consEtanolCidade) {
        this.consEtanolCidade = consEtanolCidade;
    }
    public void setConsEtanolEstrada(double consEtanolEstrada) {
        this.consEtanolEstrada = consEtanolEstrada;
    }
    public void setConsGasDieselCidade(double consGasDieselCidade) {
        this.consGasolinaDieselCidade = consGasDieselCidade;
    }
    public void setConsGasDieselEstrada(double consGasDieselEstrada) {
        this.consGasolinaDieselEstrada = consGasDieselEstrada;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "marca=" + marca +
                ", tipoCombustivel=" + tipoCombustivel +
                ", motor=" + motor +
                ", ano=" + ano +
                ", id=" + id +
                ", modelo='" + modelo + '\'' +
                ", versao='" + versao + '\'' +
                ", consEtanolCidade=" + consEtanolCidade +
                ", consEtanolEstrada=" + consEtanolEstrada +
                ", consGasDieselCidade=" + consGasolinaDieselCidade +
                ", consGasDieselEstrada=" + consGasolinaDieselEstrada +
                '}';
    }
}
