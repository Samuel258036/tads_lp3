package br.edu.ifsp.orderflow.events;

import java.time.Instant;

/**
 * Representa um fato, algo que aconteceu no domínio como
 * PedidoCriado, PagamentoAprovado e etc. Quem publica um
 * evento não sabe, e nem precisa saber, quem vai consumir/reagir (a)
 * ele.
 */
public interface IDomainEvent {
    Instant ocorridoEm();
}
