package br.edu.ifsp.orderflow;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.events.IEventBus;
import br.edu.ifsp.orderflow.events.PagamentoAprovado;
import br.edu.ifsp.orderflow.events.PagamentoRecusado;
import br.edu.ifsp.orderflow.events.SimpleEventBus;
import br.edu.ifsp.orderflow.events.handlers.*;
import br.edu.ifsp.orderflow.infra.*;
import br.edu.ifsp.orderflow.service.*;
import br.edu.ifsp.orderflow.service.PedidoService;

import java.math.BigDecimal;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {

        IEstoqueService estoqueService = new InMemoryEstoqueService();
        IPedidoRepository pedidoRepository = new InMemoryPedidoRepository();
        IPagamentoGateway pagamentoGateway = new FakePagamentoGateway();
        INotificacaoService notificacaoService = new ConsoleNotificacaoService();

        IEventBus eventBus = new SimpleEventBus();
        Metricas metricas = new Metricas();

        eventBus.register(new PedidoCriadoLogHandler());
        eventBus.register(new PagamentoAprovadoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PagamentoRecusadoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PedidoCanceladoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PagamentoAprovadoMetricasHandler(metricas));
        eventBus.register(new PagamentoRecusadoMetricasHandler(metricas));

        PedidoService pedidoService = new PedidoService(estoqueService,pedidoRepository,pagamentoGateway,eventBus);

        //============ Catalogo ==============//
        Produto mouse = new Produto("SKU-1", "mouse sem fio", new BigDecimal("120.00"));
        Produto teclado = new Produto("SKU-2", "teclado mecanico", new BigDecimal("350.00"));
        Produto monitor = new Produto("SKU-3", "monitor 27 pol", new BigDecimal("1800.00"));

        estoqueService.adicionarEstoque(mouse,10);
        estoqueService.adicionarEstoque(teclado,6);
        estoqueService.adicionarEstoque(monitor,2);

        Cliente ana = new Cliente("Ana", "ana@email.com");
        Cliente bruno = new Cliente("Bruno", "bruno@email.com");

        // ==== Pedido ==== //

        // pedido 1: deve ser aprovado!
        Pedido p1 = new Pedido(ana);
        p1.AdicionarItem(new ItemPedido(mouse, 2));
        p1.AdicionarItem(new ItemPedido(teclado, 1));

        // pedido 2: não será aprovado! Pagamento maior que 5k
        Pedido p2 = new Pedido(bruno);
        p2.AdicionarItem(new ItemPedido(monitor,2));
        p2.AdicionarItem(new ItemPedido(teclado, 5));

        // pedido 3: não será aprovado! Estoque insuficiente
        Pedido p3 = new Pedido(bruno);
        p3.AdicionarItem(new ItemPedido(monitor,5)); // só existe 2 monitores no estoque

        // ===== processamento ===== //
        long inicio = System.currentTimeMillis();

        processar(pedidoService, p1);
        processar(pedidoService, p2);
        processar(pedidoService, p3);

        long fim = System.currentTimeMillis();

        System.out.println();
        System.out.println("Pedidos no repositório: " + pedidoRepository.findAll().size());
        System.out.println(metricas.resumo());
        System.out.printf("Tempo total: %.1f s\n", (fim - inicio)/1000.0);
    }

    private static void processar(PedidoService service, Pedido pedido) {
        System.out.println();
        System.out.println("Processando " + pedido + " ...");
        Pedido resultado = service.processar(pedido);
        System.out.println("Resultado: " + resultado);

    }
    //Pedidos Aprovados: m/N
    //Pedidos Recusados: m/N
    //
    //Pagamentos Aprovados: m/N
    //Pagamentos Recusados: m/N
    //
    //Pedidos Rec. Estoque Insuf.: m/N


}
