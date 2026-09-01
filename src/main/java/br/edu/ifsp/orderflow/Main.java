package br.edu.ifsp.orderflow;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.infra.ConsoleNoticacaoService;
import br.edu.ifsp.orderflow.infra.FakePagamentoGateway;
import br.edu.ifsp.orderflow.infra.InMemoryEstoqueService;
import br.edu.ifsp.orderflow.infra.InMemoryPedidoRepository;
import br.edu.ifsp.orderflow.service.*;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Ponto de partida do OrderFlow — LP3 · IFSP-SPO · 2026/2.
 *
 * Este arquivo existe apenas para conferir o ambiente (JDK, IDE e Maven).
 * O sistema começa a ser construído no Encontro 3 — e este Main será o
 * primeiro arquivo a crescer.
 */
public class Main {

    public static void main(String[] args) {

        IEstoqueService estoqueService = new InMemoryEstoqueService();
        IPedidoRepository pedidoRepository = new InMemoryPedidoRepository();
        IPagamentoGateway pagamentoGateway = new FakePagamentoGateway();
        INoticacaoService notificacaoService = new ConsoleNoticacaoService();

        PedidoService pedidoService = new PedidoService(estoqueService, pedidoRepository, pagamentoGateway, notificacaoService);

        Produto mouse = new Produto("SKU-1", "Mouse sem fio", new BigDecimal("120.00"));
        Produto teclado = new Produto("SKU-2", "Teclado Mecânico", new BigDecimal("350.00"));
        Produto monitor = new Produto("SKU-3", "Monitor 27 pol", new BigDecimal("1800.00"));

        estoqueService.adicionarEstoque(mouse, 10);
        estoqueService.adicionarEstoque(teclado, 6);
        estoqueService.adicionarEstoque(monitor, 2);


        Cliente ana = new Cliente("ana", "ana@email.com");
        Cliente bruno = new Cliente("bruno", "bruno@email.com");

        Pedido pedido1 = new Pedido(ana);
        pedido1.adicionarItens(new ItemPedido(mouse, 2));
        pedido1.adicionarItens(new ItemPedido(teclado, 1));

        Pedido pedido = pedidoService.processar(pedido1);

        System.out.println(pedido);
    }
}
