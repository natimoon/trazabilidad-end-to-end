package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.Producto;
import py.edu.una.politecnica.ecomart.model.ProductoCatalogo;
import py.edu.una.politecnica.ecomart.repository.ProductoCatalogoRepository;
import py.edu.una.politecnica.ecomart.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoCatalogoService {

    private final ProductoCatalogoRepository repository;
    private final ProductoRepository productoRepository;

    public ProductoCatalogoService(ProductoCatalogoRepository repository,
                                   ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoRepository = productoRepository;
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

    public int sincronizarDesdeProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<String> nombresExistentes = repository.findAll().stream()
                .map(ProductoCatalogo::getNombre).toList();
        int contador = 0;
        for (Producto p : productos) {
            if (!nombresExistentes.contains(p.getNombre())) {
                ProductoCatalogo cat = new ProductoCatalogo();
                cat.setNombre(p.getNombre());
                cat.setDescripcion(p.getDescripcion());
                cat.setPuntosRequeridos(10);
                cat.setStock(5);
                repository.save(cat);
                contador++;
            }
        }
        return contador;
    }
}
