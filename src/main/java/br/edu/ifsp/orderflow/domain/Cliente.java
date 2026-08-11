package br.edu.ifsp.orderflow.domain;

public class Cliente {

    private final String nome;
    private final String email;
    private final String id;

    public Cliente(String nome, String email, String id) {
        this.nome = nome;
        this.email = email;
        this.id = id;
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
