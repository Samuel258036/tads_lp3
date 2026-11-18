package br.edu.ifsp.orderflow.events.handlers;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.events.IEventHandler;
import br.edu.ifsp.orderflow.events.PagamentoRecusado;
import br.edu.ifsp.orderflow.service.INotificacaoService;
import br.edu.ifsp.orderflow.service.IPedidoRepository;

import java.util.Optional;

public class PagamentoRecusadoNotificacaoHandler implements IEventHandler<PagamentoRecusado> {

    private final IPedidoRepository pedidoRepository;
    private final INotificacaoService notificacaoService;

    public PagamentoRecusadoNotificacaoHandler(
        IPedidoRepository pedidoRepository,
        INotificacaoService notificacaoService
    ) {
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void handle(PagamentoRecusado event) {

        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(event.pedidoId());

        if (pedidoEncontrado.isPresent()) {

            Pedido pedido = pedidoEncontrado.get();

            this.notificacaoService.notificar(
                pedido.getCliente(),
                "Pedido recusado (" + event.motivo() + "). Pedido "
                    + pedido.getIdCurto() + " cancelado."
            );
        }
    }

    @Override
    public Class<PagamentoRecusado> eventType() {
        return PagamentoRecusado.class;
    }
}
