package br.edu.ifsp.orderflow.events;

/**
 * consome/executa o handler para o tipo de evento que
 * estiver dentro do SimpleEventBus (implementacao do IEventBus)
 * @param <E>
 */
public interface IEventHandler<E extends IDomainEvent> {
    void handle(E event);

    /**
     * necessario por conta do type erasure
     * Wildcard <?>
     * @return
     */
    Class<E> eventType();
}
