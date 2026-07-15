package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.ProductoCatalogo;
import py.edu.una.politecnica.ecomart.repository.ProductoCatalogoRepository;
import java.util.List;

@Service
public class ProductoCatalogoService {

    private final ProductoCatalogoRepository repository;

    public ProductoCatalogoService(ProductoCatalogoRepository repository) {
        this.repository = repository;
    }

    public List<ProductoCatalogo> listarTodos() { return repository.findAll(); }

    public List<ProductoCatalogo> listarDisponibles() {
        return repository.findByStockGreaterThan(0);
    }

    public ProductoCatalogo obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public ProductoCatalogo crear(ProductoCatalogo producto) { return repository.save(producto); }

    public ProductoCatalogo actualizar(Long id, ProductoCatalogo actualizado) {
        ProductoCatalogo producto = obtenerPorId(id);
        producto.setNombre(actualizado.getNombre());
        producto.setDescripcion(actualizado.getDescripcion());
        producto.setPuntosRequeridos(actualizado.getPuntosRequeridos());
        producto.setStock(actualizado.getStock());
        return repository.save(producto);
    }

    public void eliminar(Long id) {
        ProductoCatalogo producto = obtenerPorId(id);
        repository.delete(producto);
    }
}
