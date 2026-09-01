package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.service.INoticacaoService;

public class ConsoleNoticacaoService implements INoticacaoService {

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        this.sleep(800);
        System.out.println("[NOTIFICACAO] para " + cliente.getEmail() + ": " + mensagem);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
