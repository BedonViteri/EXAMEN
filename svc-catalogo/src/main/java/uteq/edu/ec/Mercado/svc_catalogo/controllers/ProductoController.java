package ec.edu.uteq.mercado.catalogo.controllers;

import ec.edu.uteq.mercado.catalogo.models.Producto;
import ec.edu.uteq.mercado.catalogo.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> obtenerProductos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return service.obtainPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        if (producto.getStock() == null || producto.getStock() < 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("El stock no puede ser negativo."); 
        }

       
        if (service.existeSku(producto.getSku())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El SKU ya existe en el catálogo."); 
        }

        Producto guardado = service.guardar(producto);
        URI location = URI.create("/api/v1/productos/" + guardado.getId());
        return ResponseEntity.created(location).body(guardado); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (service.obtainPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build(); 
        service.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }
}