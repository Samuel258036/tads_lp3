package br.edu.ifsp.orderflow.concurrency;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.service.IEstoqueService;

public class ReservarTask implements Runnable {

    private final IEstoqueService estoqueService;
    private final Pedido pedido;

    public boolean sucesso;

    public ReservarTask(IEstoqueService service, Pedido pedido) {
        this.estoqueService = service;
        this.pedido = pedido;
    }

    @Override
    public void run() {
        this.sucesso = this.estoqueService.reservar(this.pedido);
    }
}
