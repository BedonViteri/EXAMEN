package ec.edu.uteq.mercado.pedidos.clients;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CatalogoClient {

    private final RestClient restClient;

    public CatalogoClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000); 
        factory.setReadTimeout(2000);    

        this.restClient = RestClient.builder()
                .requestFactory(factory)
                .baseUrl("http://svc-catalogo:8081/api/v1/productos") 
                .build();
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        try {
            return restClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, (req, res) -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no existente en catálogo.");
                    })
                    .body(ProductoDTO.class);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El servicio de catálogo no responde (Timeout).");
        }
    }
}