package ec.edu.uteq.mercado.pedidos.repositories;

import ec.edu.uteq.mercado.pedidos.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}