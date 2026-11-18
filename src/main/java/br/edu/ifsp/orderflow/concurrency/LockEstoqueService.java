package br.edu.ifsp.orderflow.concurrency;

import br.edu.ifsp.orderflow.domain.ItemPedido;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;
import br.edu.ifsp.orderflow.service.IEstoqueService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LockEstoqueService implements IEstoqueService {

    private final Map<String, Integer> estoque = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void adicionarEstoque(Produto produto, int quantidade) {

        this.lock.lock();

        try {
            int estoqueAtual = this.estoque.getOrDefault(produto.getId(), 0);
            this.estoque.put(produto.getId(), estoqueAtual + quantidade);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int quantidadeDisponivel(Produto produto) {
        this.lock.lock();

        try {
            return this.estoque.getOrDefault(produto.getId(), 0);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean reservar(Pedido pedido) {

        this.lock.lock();

         try {
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

         } finally {
             this.lock.unlock();
         }
    }

    @Override
    public void liberar(Pedido pedido) {

        this.lock.lock();

        try {
            for (ItemPedido item : pedido.getItens()) {
                int qtdAtual = this.estoque.getOrDefault(item.getProduto().getId(), 0);
                this.estoque.put(item.getProduto().getId(), qtdAtual + item.getQuantidade());
            }
        } finally {
            this.lock.unlock();
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
