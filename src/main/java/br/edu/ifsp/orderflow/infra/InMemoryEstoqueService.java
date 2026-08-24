package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.service.IEstoqueService;

import java.util.HashMap;
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

    @Override
    public boolean reservar(Pedido pedido) {
        return true;
    }

    @Override
    public void liberar(Pedido pedido) {

    }
}
