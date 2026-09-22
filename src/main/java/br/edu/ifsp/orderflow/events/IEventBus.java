package br.edu.ifsp.orderflow.events;

/**
 * UM IEventBus (ou barramento de evento) funciona como um quadro de aviso
 * onde serviços publicam eventos e os handlers registrados são avisados
 */

public interface IEventBus {
    <E extends IDomainEvent> void register(IEventHandler<E> handler);

    <E extends IDomainEvent> void publish(E event);

}
