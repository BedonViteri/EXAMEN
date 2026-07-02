package ec.edu.uteq.mercado.catalogo.repositories;

import ec.edu.uteq.mercado.catalogo.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findBySku(String sku);
    boolean existsBySku(String sku);
}