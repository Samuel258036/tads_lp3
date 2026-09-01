package br.edu.ifsp.orderflow.service;

import br.edu.ifsp.orderflow.domain.Cliente;

public interface INoticacaoService {

    void notificar(Cliente cliente, String mensagem);

}
