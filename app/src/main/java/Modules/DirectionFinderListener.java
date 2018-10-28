package Modules;

import com.example.guilhermeeyng.projetomobile.utilitarios.TipoRetornoDirectionsAPI;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Rota> rotas);
    void resultadoRequisicao(TipoRetornoDirectionsAPI tipoRetornoDirectionsAPI);
}
