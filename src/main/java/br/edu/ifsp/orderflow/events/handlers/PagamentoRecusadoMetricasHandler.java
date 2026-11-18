package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PagamentoRecusado;

public class PagamentoRecusadoMetricasHandler implements IEventHandler<PagamentoRecusado> {

    private final Metricas metricas;

    public PagamentoRecusadoMetricasHandler(Metricas metricas) {
        this.metricas = metricas;
    }

    @Override
    public void handle(PagamentoRecusado event) {
        this.metricas.incrementarQtdReprovado();
    }

    @Override
    public Class<PagamentoRecusado> eventType() {
        return PagamentoRecusado.class;
    }
}
