package br.edu.ifsp.orderflow.events;

import java.time.Instant;

public record PedidoCancelado (String pedidoId, String motivo, Instant ocorrisoEm) implements IDomainEvent {


    @Override
    public Instant ocorridoEm() {
        return null;
    }
}
