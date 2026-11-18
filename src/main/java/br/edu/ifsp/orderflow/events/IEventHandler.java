package br.edu.ifsp.orderflow.events;

/**
 * Algo que irá consumir/reagir a um tipo específico de evento
 * (uma class que implementa IDomainEvent).
 *
 * O parâmetro de tipo E garante, em TEMPO DE COMPILAÇÃO, que
 * um handler de PagamentoAprovado nunca receba um PagamentoRecusado.
 */
public interface IEventHandler<E extends IDomainEvent> {

    void handle(E event);

    /**
     * Qual tipo de evento este handler trata/consome?
     *
     * Necessário por causa do apagamento de tipo do Java (type erasure).
     * @return
     */
    Class<E> eventType();
}
