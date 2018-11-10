package Modules;

import com.example.guilhermeeyng.projetomobile.enums.TipoRetornoDirectionsAPI;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Rota> rotas);
    void resultadoRequisicao(TipoRetornoDirectionsAPI tipoRetornoDirectionsAPI);
}
