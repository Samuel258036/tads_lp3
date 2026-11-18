package br.edu.ifsp.orderflow.events;

import java.time.Instant;

public record PedidoCriado(
    String pedidoId,
    Instant ocorridoEm
) implements IDomainEvent {
}
