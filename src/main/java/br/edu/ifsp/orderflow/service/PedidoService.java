package br.edu.ifsp.orderflow.service;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.ResultadoPagamento;
import br.edu.ifsp.orderflow.events.*;

import javax.xml.transform.Result;
import java.time.Instant;

public class PedidoService {

    private final IEstoqueService estoqueService;
    private final IPedidoRepository pedidoRepository;
    private final IPagamentoGateway pagamentoGateway;
    private final IEventBus eventBus;

    public PedidoService(
            IEstoqueService estoqueService,
            IPedidoRepository pedidoRepository,
            IPagamentoGateway pagamentoGateway,
            IEventBus eventBus
    ) {
        this.estoqueService = estoqueService;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoGateway = pagamentoGateway;
        this.eventBus = eventBus;
    }

    public Pedido processar(Pedido pedido) {

        this.eventBus.publish(new PedidoCriado(pedido.getId(), Instant.now()));

        boolean foiReservado = this.estoqueService.reservar(pedido);

        if (!foiReservado) {

            pedido.cancelar();
            this.pedidoRepository.save(pedido);

            this.eventBus.publish(
                    new PedidoCancelado(
                            pedido.getId(),
                            "estoque insuficiente",
                            Instant.now()
                    )
            );

            return pedido;
        }

        ResultadoPagamento resultado = this.pagamentoGateway.pagar(pedido);
        boolean naoAprovado = !resultado.isAprovado();

        if (naoAprovado) {

            this.estoqueService.liberar(pedido);
            pedido.cancelar();
            this.pedidoRepository.save(pedido);

            this.eventBus.publish(
                    new PagamentoRecusado(
                            pedido.getId(),
                            resultado.getMotivo(),
                            Instant.now()
                    )
            );

            return pedido;
        }

        pedido.marcarComoPago();
        this.pedidoRepository.save(pedido);

        this.eventBus.publish(
                new PagamentoAprovado(
                        pedido.getId(),
                        resultado.getIdTransacao(),
                        Instant.now()
                )
        );

        return pedido;
    }
}