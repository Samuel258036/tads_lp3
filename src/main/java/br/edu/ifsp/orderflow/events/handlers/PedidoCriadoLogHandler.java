package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PedidoCriado;

public class PedidoCriadoLogHandler implements IEventHandler<PedidoCriado> {

    @Override
    public void handle(PedidoCriado event) {
        System.out.println("[log] novo pedido " + event.pedidoId() + " em " + event.ocorridoEm());
    }

    @Override
    public Class<PedidoCriado> eventType() {
        return PedidoCriado.class;
    }
}
