package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.una.politecnica.ecomart.model.CanjePuntos;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.model.ProductoCatalogo;
import py.edu.una.politecnica.ecomart.repository.CanjePuntosRepository;
import py.edu.una.politecnica.ecomart.repository.ClienteRepository;
import py.edu.una.politecnica.ecomart.repository.ProductoCatalogoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CanjeService {

    private final CanjePuntosRepository canjeRepository;
    private final ProductoCatalogoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public CanjeService(CanjePuntosRepository canjeRepository,
                        ProductoCatalogoRepository productoRepository,
                        ClienteRepository clienteRepository,
                        ClienteService clienteService) {
        this.canjeRepository = canjeRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public CanjePuntos canjear(Long clienteId, Long productoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        ProductoCatalogo producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() <= 0) {
            throw new RuntimeException("Producto sin stock");
        }

        if (cliente.getPuntosAcumulados() < producto.getPuntosRequeridos()) {
            throw new RuntimeException("Puntos insuficientes. Necesita: " + producto.getPuntosRequeridos()
                    + ", tiene: " + cliente.getPuntosAcumulados());
        }

        producto.setStock(producto.getStock() - 1);
        productoRepository.save(producto);

        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - producto.getPuntosRequeridos());
        clienteRepository.save(cliente);
        clienteService.actualizarNivel(clienteId);

        CanjePuntos canje = new CanjePuntos();
        canje.setClienteId(clienteId);
        canje.setProductoId(productoId);
        canje.setPuntosUtilizados(producto.getPuntosRequeridos());
        canje.setFecha(LocalDateTime.now());
        return canjeRepository.save(canje);
    }

    public List<Map<String, Object>> listarPorCliente(Long clienteId) {
        List<CanjePuntos> canjes = canjeRepository.findByClienteId(clienteId);
        return enriquecerCanjes(canjes);
    }

    public List<Map<String, Object>> listarTodos() {
        List<CanjePuntos> canjes = canjeRepository.findAll();
        return enriquecerCanjes(canjes);
    }

    private List<Map<String, Object>> enriquecerCanjes(List<CanjePuntos> canjes) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (CanjePuntos c : canjes) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", c.getId());
            item.put("clienteId", c.getClienteId());
            item.put("productoId", c.getProductoId());
            item.put("puntosUtilizados", c.getPuntosUtilizados());
            item.put("fecha", c.getFecha());
            String nombreProducto = productoRepository.findById(c.getProductoId())
                    .map(ProductoCatalogo::getNombre)
                    .orElse(null);
            item.put("productoNombre", nombreProducto);
            resultado.add(item);
        }
        return resultado;
    }
}
