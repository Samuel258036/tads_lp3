package br.edu.ifsp.orderflow.service;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;

public interface IEstoqueService {

    /**
     * Repõe unidades de um produto no estoque
     * @param produto
     * @param quantidade
     */
    public void adicionarEstoque(Produto produto, int quantidade);

    /**
     * Quantidade disponivel de um produto
     * @param produto
     * @return int
     */
    public int quantidadeDisponivel(Produto produto);

    /**
     * Tenta reservar o estoque de todos os itens do pedido
     * @param pedido
     * @return true se conseguir reservar, false se não
     */
    public boolean reservar(Pedido pedido);

    /**
     * Devolve ao estoque os itens de um pedido
     * @param pedido
     */
    void liberar(Pedido pedido);
}
