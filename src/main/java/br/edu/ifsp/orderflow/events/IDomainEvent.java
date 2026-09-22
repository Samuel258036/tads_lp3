package br.edu.ifsp.orderflow.events;

import java.time.Instant;

/**
 * Representa fato, algo que aconteceu no dominio, ex:
 * PedidoCriado, PagamentoAprovado, etc
 */
public interface IDomainEvent {
    Instant ocorridoEm();
}
