package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PagamentoAprovado;

public class PagamentoAprovadoMetricasHandler implements IEventHandler<PagamentoAprovado> {

    private final Metricas metricas;

    PagamentoAprovadoMetricasHandler(Metricas metricas) {
        this.metricas = metricas;
    }

    @Override
    public void handle(PagamentoAprovado event) {
        this.metricas.incrementarQtdAprovado();
    }

    @Override
    public Class<PagamentoAprovado> eventType() {
        return PagamentoAprovado.class;
    }
}
