package br.edu.ifsp.orderflow.demo;

import br.edu.ifsp.orderflow.concurrency.LockEstoqueService;
import br.edu.ifsp.orderflow.concurrency.ReservarTask;
import br.edu.ifsp.orderflow.concurrency.SyncronizedEstoqueService;
import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.infra.InMemoryEstoqueService;
import br.edu.ifsp.orderflow.service.IEstoqueService;

public class CondicaoDeCorridaEstoque {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(
            "Cenário: 10 unidades em estoque; 2 clientes tentam comprar 8 ao mesmo tempo"
        );
        System.out.println("Esperado: UMA reserva bem-sucedida e estoque final = 2");
        System.out.println();

        rodada("InMemoryEstoqueService", new InMemoryEstoqueService());
        rodada("LockEstoqueService", new LockEstoqueService());
        rodada("SyncronizedEstoqueService", new SyncronizedEstoqueService());
    }

    private static void rodada(String nome, IEstoqueService service) throws InterruptedException {

        service.adicionarEstoque(DemoSuporte.MOUSE, 10);

        Pedido pedidoA = DemoSuporte.pedidoValido(8);
        Pedido pedidoB = DemoSuporte.pedidoValido(8);

        ReservarTask clienteA = new ReservarTask(service, pedidoA);
        ReservarTask clienteB = new ReservarTask(service, pedidoB);

        Thread threadA = new Thread(clienteA, "cliente-A");
        Thread threadB = new Thread(clienteB, "cliente-B");

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        int estoqueFinal = service.quantidadeDisponivel(DemoSuporte.MOUSE);

        System.out.println(nome + ":");
        System.out.println(" cliente A reservou? " + clienteA.sucesso);
        System.out.println(" cliente B reservou? " + clienteB.sucesso);

        if (estoqueFinal < 0) {
            System.out.println(
                " >>> vendemos " + (10 - estoqueFinal)
                + " unidades tendo 10 :/"
            );
        }

        System.out.println();
    }
}
