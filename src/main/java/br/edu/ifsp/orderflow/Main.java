package br.edu.ifsp.orderflow;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.events.IEventBus;
import br.edu.ifsp.orderflow.events.PagamentoAprovado;
import br.edu.ifsp.orderflow.events.SimpleEventBus;
import br.edu.ifsp.orderflow.events.handlers.PagamentoAprovadoNotificacaoHandler;
import br.edu.ifsp.orderflow.infra.*;
import br.edu.ifsp.orderflow.infra.PedidoService;
import br.edu.ifsp.orderflow.service.*;

import java.math.BigDecimal;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {

        IEstoqueService estoqueService = new InMemoryEstoqueService();
        IPedidoRepository pedidoRepository = new InMemoryPedidoRepository();
        IPagamentoGateway pagamentoGateway = new FakePagamentoGateway();
        INotificacaoService notificacaoService = new ConsoleNotificacaoService();
        IEventBus eventBus = new SimpleEventBus();

        PagamentoAprovadoNotificacaoHandler evento1Handler = new PagamentoAprovadoNotificacaoHandler(pedidoRepository, notificacaoService);

        eventBus.register(evento1Handler);

        eventBus.publish(new PagamentoAprovado("pedido-1", "transacao-1", Instant.now()));
//        PedidoService pedidoService = new PedidoService(
//                estoqueService,
//                pedidoRepository,
//                pagamentoGateway,
//                notificacaoService
//        );
//
//        Produto mouse = new Produto(
//                "SKU-1",
//                "Mouse sem fio",
//                new BigDecimal("120.00")
//        );
//
//        Produto teclado = new Produto(
//                "SKU-2",
//                "Teclado sem fio",
//                new BigDecimal("350.00")
//        );
//
//        Produto monitor = new Produto(
//                "SKU-3",
//                "Monitor 4K",
//                new BigDecimal("1800.00")
//        );
//
//        estoqueService.adicionarEstoque(mouse, 10);
//        estoqueService.adicionarEstoque(teclado, 6);
//        estoqueService.adicionarEstoque(monitor, 2);
//
//        Cliente ana = new Cliente("Ana", "ana@gmail.com");
//        Cliente bruno = new Cliente("Bruno", "bruno@gmail.com");
//
//        Pedido pedido1 = new Pedido(ana);
//        pedido1.AdicionarItem(new ItemPedido(mouse, 21));
//        pedido1.AdicionarItem(new ItemPedido(teclado, 2));
//
//        Pedido pedido = pedidoService.processar(pedido1);
//
//        System.out.println(pedido);
    }
}
