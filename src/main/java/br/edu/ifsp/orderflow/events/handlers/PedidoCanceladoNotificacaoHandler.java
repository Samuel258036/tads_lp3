package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PedidoCancelado;
import br.edu.ifsp.orderflow.service.INotificacaoService;
import br.edu.ifsp.orderflow.service.IPedidoRepository;

import java.util.Optional;

public class PedidoCanceladoNotificacaoHandler implements IEventHandler<PedidoCancelado> {

    private final IPedidoRepository pedidoRepository;
    private final INotificacaoService notificacaoService;

    public PedidoCanceladoNotificacaoHandler(
        IPedidoRepository pedidoRepository,
        INotificacaoService notificacaoService
    ) {
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void handle(PedidoCancelado event) {

        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(event.pedidoId());

        if (pedidoEncontrado.isPresent()) {

            Pedido pedido = pedidoEncontrado.get();

            this.notificacaoService.notificar(
                pedido.getCliente(),
                "Pedido " + pedido.getIdCurto() + " cancelado: " + event.motivo()
            );
        }
    }

    @Override
    public Class<PedidoCancelado> eventType() {
        return PedidoCancelado.class;
    }
}
