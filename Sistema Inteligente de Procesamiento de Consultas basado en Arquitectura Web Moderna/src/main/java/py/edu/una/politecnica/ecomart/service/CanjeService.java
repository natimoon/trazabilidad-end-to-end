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
import java.util.List;

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

    public List<CanjePuntos> listarPorCliente(Long clienteId) {
        return canjeRepository.findByClienteId(clienteId);
    }

    public List<CanjePuntos> listarTodos() {
        return canjeRepository.findAll();
    }
}
