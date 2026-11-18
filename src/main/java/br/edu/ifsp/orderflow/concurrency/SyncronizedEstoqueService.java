package br.edu.ifsp.orderflow.concurrency;

import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.service.IEstoqueService;

import java.util.HashMap;
import java.util.Map;

public class SyncronizedEstoqueService implements IEstoqueService {

    private final Map<String, Integer> estoque = new HashMap<>();

    @Override
    public synchronized void adicionarEstoque(Produto produto, int quantidade) {
        int estoqueAtual = this.estoque.getOrDefault(produto.getId(), 0);
        this.estoque.put(produto.getId(), estoqueAtual + quantidade);
    }

    @Override
    public synchronized int quantidadeDisponivel(Produto produto) {
        return this.estoque.getOrDefault(produto.getId(), 0);
    }

    @Override
    public synchronized boolean reservar(Pedido pedido) {

        for (ItemPedido item : pedido.getItens()) {

            int qtdDisponivel = this.estoque.getOrDefault(item.getProduto().getId(), 0);

            if (qtdDisponivel < item.getQuantidade()) {
                return false;
            }
        }

        sleep(50);

        for (ItemPedido item : pedido.getItens()) {
            int qtdAtual = this.estoque.getOrDefault(item.getProduto().getId(), 0);
            this.estoque.put(item.getProduto().getId(), qtdAtual - item.getQuantidade());
        }

        return true;
    }

    @Override
    public synchronized void liberar(Pedido pedido) {

        for (ItemPedido item : pedido.getItens()) {
            int qtdAtual = this.estoque.getOrDefault(item.getProduto().getId(), 0);
            this.estoque.put(item.getProduto().getId(), qtdAtual + item.getQuantidade());
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
