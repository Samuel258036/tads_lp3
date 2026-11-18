package br.edu.ifsp.orderflow.service;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.Produto;

public interface IEstoqueService {

    /**
     * Repõe unidades de um produto no estoque
     *
     * @param produto
     * @param quantidade
     * @return void
     */
    public void adicionarEstoque(Produto produto, int quantidade);

    /**
     * Quantidade disponível para um produto
     *
     * @param produto
     * @return int
     */
    public int quantidadeDisponivel(Produto produto);

    /**
     * Tenta reservar o estoque de todos os itens do pedido.
     *
     * @param pedido
     * @return true se conseguiu reservar, false do contrário
     */
    public boolean reservar(Pedido pedido);

    /**
     * Devolve ao estoque os itens de um pedido (ex.: pagamento recusado)
     * @param pedido
     */
    public void liberar(Pedido pedido);
}
