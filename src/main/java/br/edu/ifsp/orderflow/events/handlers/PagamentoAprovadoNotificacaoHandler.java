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

    public PagamentoAprovadoNotificacaoHandler(IPedidoRepository pedidoRepository, INotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void handle(PagamentoAprovado event) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(event.pedidoId());

        if (pedidoEncontrado.isEmpty()) {
            return;
        }

        Pedido pedido = pedidoEncontrado.get();
        notificacaoService.notificar(
                pedido.getCliente(),
                "Pagamento aprovado!" + pedido.getIdCurto() + " confirmado"
        );
    }

    @Override
    public Class<PagamentoAprovado> eventType() {
        return PagamentoAprovado.class;
    }
}
