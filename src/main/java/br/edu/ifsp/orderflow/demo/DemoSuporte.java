package br.edu.ifsp.orderflow.demo;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.events.IEventBus;
import br.edu.ifsp.orderflow.events.SimpleEventBus;
import br.edu.ifsp.orderflow.events.handlers.*;
import br.edu.ifsp.orderflow.infra.ConsoleNotificacaoService;
import br.edu.ifsp.orderflow.infra.FakePagamentoGateway;
import br.edu.ifsp.orderflow.infra.InMemoryEstoqueService;
import br.edu.ifsp.orderflow.infra.InMemoryPedidoRepository;
import br.edu.ifsp.orderflow.service.*;

import java.math.BigDecimal;

public class DemoSuporte {

    public static final Produto MOUSE =
            new Produto("SKU-1", "Mouse sem fio", new BigDecimal("120.00"));

    public static final Produto TECLADO =
            new Produto("SKU-2", "Teclado Mecânico", new BigDecimal("350.00"));

    public static final Produto MONITOR =
            new Produto("SKU-3", "Monitor 27 pol", new BigDecimal("1800.00"));

    public static final Cliente ANA =
            new Cliente("Ana", "ana@email.com");

    public static class App {

        public final IEstoqueService estoqueService;
        public final IPagamentoGateway pagamentoGateway;
        public final INotificacaoService notificacaoService;
        public final IPedidoRepository pedidoRepository;
        public final IEventBus eventBus;
        public final Metricas metricas;
        public final PedidoService pedidoService;

        App(
            IEstoqueService estoqueService,
            IPagamentoGateway pagamentoGateway,
            INotificacaoService notificacaoService,
            IPedidoRepository pedidoRepository,
            IEventBus eventBus,
            PedidoService pedidoService,
            Metricas metricas
        ){
            this.estoqueService = estoqueService;
            this.pagamentoGateway = pagamentoGateway;
            this.notificacaoService = notificacaoService;
            this.pedidoRepository = pedidoRepository;
            this.eventBus = eventBus;
            this.pedidoService = pedidoService;
            this.metricas = metricas;
        }
    }

    public static App novoApp() {

        IEstoqueService estoqueService = new InMemoryEstoqueService();
        IPagamentoGateway pagamentoGateway = new FakePagamentoGateway();
        INotificacaoService notificacaoService = new ConsoleNotificacaoService();
        IPedidoRepository pedidoRepository = new InMemoryPedidoRepository();

        IEventBus eventBus = new SimpleEventBus();
        Metricas metricas = new Metricas();

        eventBus.register(new PedidoCriadoLogHandler());
        eventBus.register(new PagamentoAprovadoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PagamentoRecusadoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PedidoCanceladoNotificacaoHandler(pedidoRepository, notificacaoService));
        eventBus.register(new PagamentoAprovadoMetricasHandler(metricas));
        eventBus.register(new PagamentoRecusadoMetricasHandler(metricas));

        PedidoService pedidoService = new PedidoService(
                estoqueService,
                pedidoRepository,
                pagamentoGateway,
                eventBus
        );

        estoqueService.adicionarEstoque(MOUSE, 1000);
        estoqueService.adicionarEstoque(TECLADO, 1000);

        return new App(
            estoqueService,
            pagamentoGateway,
            notificacaoService,
            pedidoRepository,
            eventBus,
            pedidoService,
            metricas
        );
    }

    public static Pedido pedidoValido(int mouses) {

        Pedido pedido = new Pedido(ANA);
        pedido.adicionarItem(new ItemPedido(MOUSE, mouses));

        return pedido;
    }
}
