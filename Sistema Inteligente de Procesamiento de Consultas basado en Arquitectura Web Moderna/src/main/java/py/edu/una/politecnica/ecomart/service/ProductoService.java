package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.Producto;
import py.edu.una.politecnica.ecomart.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategorizadorService categorizadorService;

    public ProductoService(ProductoRepository productoRepository, CategorizadorService categorizadorService) {
        this.productoRepository = productoRepository;
        this.categorizadorService = categorizadorService;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Producto crear(Producto producto) {
        if (producto.getCategoria() == null || producto.getCategoria().isBlank()) {
            String categoria = categorizadorService.categorizarProducto(producto.getNombre());
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        if (productoActualizado.getCategoria() != null && !productoActualizado.getCategoria().isBlank()) {
            producto.setCategoria(productoActualizado.getCategoria());
        }
        producto.setImagenUrl(productoActualizado.getImagenUrl());
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
}
