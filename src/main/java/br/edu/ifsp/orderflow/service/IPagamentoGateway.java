package br.edu.ifsp.orderflow.service;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.ResultadoPagamento;

public interface IPagamentoGateway {
    ResultadoPagamento pagar(Pedido pedido);
}
