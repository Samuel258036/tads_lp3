package br.edu.ifsp.orderflow;

import br.edu.ifsp.orderflow.domain.Produto;

import java.math.BigDecimal;

/**
 * Ponto de partida do OrderFlow — LP3 · IFSP-SPO · 2026/2.
 *
 * Este arquivo existe apenas para conferir o ambiente (JDK, IDE e Maven).
 * O sistema começa a ser construído no Encontro 3 — e este Main será o
 * primeiro arquivo a crescer.
 */
public class Main {

    public static void main(String[] args) {

        Produto mouse = new Produto("SKU-1", "Mouse sem fio", new BigDecimal("120.00"));
        Produto teclado = new Produto("SKU-2", "Teclado Mecânico", new BigDecimal("350.00"));
        Produto monitor = new Produto("SKU-3", "Monitor 27 pol", new BigDecimal("1800.00"));

        System.out.println(mouse);
        System.out.println(teclado);
        System.out.println(monitor);




    }
}
