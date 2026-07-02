package ec.edu.uteq.mercado.pedidos.clients;

public class ProductoDTO {
    private Long id;
    private String sku;
    private String nombre;
    private Double precio;
    private Integer stock;

    public ProductoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}