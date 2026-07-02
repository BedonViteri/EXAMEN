package ec.edu.uteq.mercado.catalogo.services;

import ec.edu.uteq.mercado.catalogo.models.Producto;
import ec.edu.uteq.mercado.catalogo.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public boolean existeSku(String sku) {
        return repository.existsBySku(sku);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}