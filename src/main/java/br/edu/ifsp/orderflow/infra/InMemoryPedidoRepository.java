package br.edu.ifsp.orderflow.infra;

import br.edu.ifsp.orderflow.domain.Pedido;
import br.edu.ifsp.orderflow.service.IPedidoRepository;

import java.util.*;

public class InMemoryPedidoRepository implements IPedidoRepository {

    private final Map<String, Pedido> pedidos = new HashMap<>();

    @Override
    public void save(Pedido pedido) {
        this.pedidos.put(pedido.getId(), pedido);
    }

    @Override
    public Optional<Pedido> findById(String id) {

        if (this.pedidos.containsKey(id) == false) {
            return Optional.empty();
        }

        Pedido pedido = this.pedidos.get(id);

        return Optional.of(pedido);
    }

    @Override
    public List<Pedido> findAll() {

        List<Pedido> todos = new ArrayList<>();

        for (Pedido item : this.pedidos.values()) {
            todos.add(item);
        }

        return todos;
    }
}
