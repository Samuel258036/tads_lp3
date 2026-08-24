package br.edu.ifsp.orderflow.domain;

import java.util.UUID;

public class Cliente {

    private final String nome;
    private final String email;
    private final String id;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.id = (UUID.randomUUID().toString());
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return this.nome + " <" + this.email + ">";
    }
}
