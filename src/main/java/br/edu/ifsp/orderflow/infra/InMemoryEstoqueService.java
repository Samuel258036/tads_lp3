package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.service.IEstoqueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEstoqueService implements IEstoqueService {

    private final Map<String, Integer> estoque = new HashMap<>();

    @Override
    public void adicionarEstoque(Produto produto, int quantidade) {
        int qtdAtual = this.estoque.getOrDefault(produto.getId(), 0);
        this.estoque.put(produto.getId(), quantidade + qtdAtual);
    }

    @Override
    public int quantidadeDisponivel(Produto produto) {
        return this.estoque.getOrDefault(produto.getId(), 0);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean reservar(Pedido pedido) {

        List<ItemPedido> listaDeItens = pedido.getItens();

        // Conferir se todos os produtos têm estoque
        for (ItemPedido item : listaDeItens) {

            int disponivel = this.quantidadeDisponivel(item.getProduto());

            if (item.getQuantidade() > disponivel) {
                return false;
            }
        }

        this.sleep(50);

        for (ItemPedido item : listaDeItens) {

            Produto produto = item.getProduto();
            String produtoId = produto.getId(); // item.getProduto().getId()
            int quantidadeAtual = this.estoque.getOrDefault(produtoId, 0);
            this.estoque.put(produtoId, quantidadeAtual - item.getQuantidade());
        }

        return true;
    }

    @Override
    public void liberar(Pedido pedido) {

//        List<ItemPedido> itens = pedido.getItens();
//
//        for (ItemPedido item : itens) {
//
//            Produto produto = item.getProduto();
//            int disponivel = this.quantidadeDisponivel(item.getProduto());
//            this.estoque.put(produto.getId(), disponivel + item.getQuantidade());
//        }

        for (ItemPedido item : pedido.getItens()) {
            this.adicionarEstoque(item.getProduto(), item.getQuantidade());
        }
    }
}
