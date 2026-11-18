package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PagamentoAprovado;
import br.edu.ifsp.orderflow.service.INotificacaoService;
import br.edu.ifsp.orderflow.service.IPedidoRepository;

import java.util.Optional;

public class PagamentoAprovadoNotificacaoHandler implements IEventHandler<PagamentoAprovado> {

    private final IPedidoRepository pedidoRepository;
    private final INotificacaoService notificacaoService;

    public PagamentoAprovadoNotificacaoHandler(
        IPedidoRepository pedidoRepository,
        INotificacaoService notificacaoService
    ) {
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void handle(PagamentoAprovado event) {

        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(event.pedidoId());

        if (pedidoEncontrado.isPresent()) {

            Pedido pedido = pedidoEncontrado.get();

            this.notificacaoService.notificar(
                pedido.getCliente(),
                "Pagamento aprovado! Pedido " + pedido.getIdCurto()
                    + " confirmado (transação " + event.transacaoId() + ")"
            );
        }
    }

    @Override
    public Class<PagamentoAprovado> eventType() {
        return PagamentoAprovado.class;
    }
}
