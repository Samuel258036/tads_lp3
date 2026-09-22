package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.Cliente;
import br.edu.ifsp.orderflow.service.INotificacaoService;

public class ConsoleNotificacaoService implements INotificacaoService {

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        this.sleep(800);
        System.out.printf("[NOTIFICACAO] para " + cliente.getEmail() + ": " + mensagem);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
