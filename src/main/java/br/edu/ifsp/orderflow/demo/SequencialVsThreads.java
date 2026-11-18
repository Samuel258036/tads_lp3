package br.edu.ifsp.orderflow.demo;

import br.edu.ifsp.orderflow.concurrency.ProcessarPedidoTask;
import br.edu.ifsp.orderflow.domain.Pedido;

import java.util.ArrayList;
import java.util.List;

public class SequencialVsThreads {

    private static final int TOTAL_PEDIDOS = 6;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Rodada 1: sequencial ===");

        DemoSuporte.App appA = DemoSuporte.novoApp();

        long inicioSeq = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_PEDIDOS; i++) {
            Pedido pedido = DemoSuporte.pedidoValido(1);
            appA.pedidoService.processar(pedido);
        }

        long tempoSeq = System.currentTimeMillis() - inicioSeq;

        System.out.println();
        System.out.println("=== Rodada 2: uma thread por pedido ===");

        DemoSuporte.App appB = DemoSuporte.novoApp();

        List<Thread> threads = new ArrayList<>();
        long initThr = System.currentTimeMillis();

        for (int i = 0; i < TOTAL_PEDIDOS; i++) {
            Pedido pedido = DemoSuporte.pedidoValido(1);
            ProcessarPedidoTask task = new ProcessarPedidoTask(appB.pedidoService, pedido);
            Thread thread = new Thread(task, "cliente-" + (i + 1));

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long tempoThr = System.currentTimeMillis() - initThr;

        System.out.println();
        System.out.printf("Sequencial:          %.1f s\n", tempoSeq / 1000.0);
        System.out.printf("Thread por pedido:   %.1f s\n", tempoThr / 1000.0);
        System.out.println(appB.metricas.resumo());
    }
}
