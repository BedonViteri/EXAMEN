package ec.edu.uteq.mercado.pedidos.services;

import ec.edu.uteq.mercado.pedidos.clients.CatalogoClient;
import ec.edu.uteq.mercado.pedidos.clients.ProductoDTO;
import ec.edu.uteq.mercado.pedidos.models.Pedido;
import ec.edu.uteq.mercado.pedidos.models.PedidoItem;
import ec.edu.uteq.mercado.pedidos.repositories.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final CatalogoClient catalogoClient;

    public PedidoService(PedidoRepository repository, CatalogoClient catalogoClient) {
        this.repository = repository;
        this.catalogoClient = catalogoClient;
    }

    public List<Pedido> listarTodos() { return repository.findAll(); }

    public Optional<Pedido> obtenerPorId(Long id) { return repository.findById(id); }

    public Pedido crearPedido(Pedido pedido) {
        double totalGeneral = 0.0;

        for (PedidoItem item : pedido.getItems()) {
            ProductoDTO prod = catalogoClient.obtenerProductoPorId(item.getProductoId());

            if (prod.getStock() < item.getCantidad()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, 
                        "Stock insuficiente para el producto ID: " + item.getProductoId()); 
            }

            double subtotal = prod.getPrecio() * item.getCantidad();
            item.setSubtotal(subtotal);
            totalGeneral += subtotal;
        }

        pedido.setTotal(totalGeneral);
        pedido.setEstado("COMPLETADO");
        return repository.save(pedido);
    }
}