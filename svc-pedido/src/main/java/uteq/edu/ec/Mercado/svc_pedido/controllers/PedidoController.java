package ec.edu.uteq.mercado.pedidos.controllers;

import ec.edu.uteq.mercado.pedidos.models.Pedido;
import ec.edu.uteq.mercado.pedidos.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pedido> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido guardado = service.crearPedido(pedido);
        URI location = URI.create("/api/v1/pedidos/" + guardado.getId());
        return ResponseEntity.created(location).body(guardado); 
    }
}