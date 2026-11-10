package br.edu.ifsp.orderflow.concurrency;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.service.PedidoService;

public class ProcessarPedidoTask implements Runnable {

    private final PedidoService service;
    private final Pedido pedido;

    public ProcessarPedidoTask(PedidoService service, Pedido pedido) {
        this.service = service;
        this.pedido = pedido;
    }

    @Override
    public void run() {
        this.service.processar(pedido);
    }
}
