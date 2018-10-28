package com.example.guilhermeeyng.projetomobile.utilitarios;

/*
enum criado para mapear o tipo do retorno do google maps com a mensagem
 */
public enum TipoRetornoDirectionsAPI {

    OK("Rota encontrada com sucesso"),
    NOT_FOUND("O endereço não pode ser geocodificado"),
    ZERO_RESULTS("Nenhuma rota encontrada"),
    MAX_WAYPOINTS_EXCEEDED("Limite de waypoints atingido"),
    MAX_ROUTE_LENGTH_EXCEEDED("A rota é muito longa"),
    INVALID_REQUEST("Solicitação inválida"),
    OVER_DAILY_LIMIT("Chave de API inválida"),
    OVER_QUERY_LIMIT("Limite de requisições atingido"),
    REQUEST_DENIED("Requisição negada pelo servidor"),
    UNKNOWN_ERROR("Erro interno do servidor. Tente novamente");

    private String mensagem;

    TipoRetornoDirectionsAPI(String mensagem){
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return mensagem;
    }
}
