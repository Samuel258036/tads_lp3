package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.domain.ResultadoPagamento;
import br.edu.ifsp.orderflow.service.IPagamentoGateway;

import java.math.BigDecimal;
import java.util.UUID;

public class FakePagamentoGateway implements IPagamentoGateway {

    private static final BigDecimal CARD_LIMIT = new BigDecimal("5000.00");

    @Override
    public ResultadoPagamento pagar(Pedido pedido) {

        this.sleep(1500);

        BigDecimal totalPedido = pedido.calcularTotal();

        if (totalPedido.compareTo(CARD_LIMIT) > 0) {
            return ResultadoPagamento.recusado(
                "limite do cartão excedido"
            );
        }

        String idTransacao = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        return ResultadoPagamento.aprovado(idTransacao);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
