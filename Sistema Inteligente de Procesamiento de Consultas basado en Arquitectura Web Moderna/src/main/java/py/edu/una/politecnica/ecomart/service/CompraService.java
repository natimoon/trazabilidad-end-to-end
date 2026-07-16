package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.una.politecnica.ecomart.model.BolsaPuntos;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.model.Compra;
import py.edu.una.politecnica.ecomart.model.ParametroVencimiento;
import py.edu.una.politecnica.ecomart.model.Producto;
import py.edu.una.politecnica.ecomart.repository.BolsaPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.ClienteRepository;
import py.edu.una.politecnica.ecomart.repository.CompraRepository;
import py.edu.una.politecnica.ecomart.repository.ParametroVencimientoRepository;
import py.edu.una.politecnica.ecomart.repository.ProductoRepository;
import py.edu.una.politecnica.ecomart.service.PromocionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ReglaPuntosService reglaPuntosService;
    private final ParametroVencimientoRepository vencimientoRepository;
    private final BolsaPuntosRepository bolsaRepository;
    private final ClienteService clienteService;
    private final PromocionService promocionService;

    public CompraService(CompraRepository compraRepository,
                         ClienteRepository clienteRepository,
                         ProductoRepository productoRepository,
                         ReglaPuntosService reglaPuntosService,
                         ParametroVencimientoRepository vencimientoRepository,
                         BolsaPuntosRepository bolsaRepository,
                         ClienteService clienteService,
                         PromocionService promocionService) {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.reglaPuntosService = reglaPuntosService;
        this.vencimientoRepository = vencimientoRepository;
        this.bolsaRepository = bolsaRepository;
        this.clienteService = clienteService;
        this.promocionService = promocionService;
    }

    @Transactional
    public Map<String, Object> comprar(Long clienteId, Long productoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Double monto = producto.getPrecio();
        if (monto == null) {
            throw new RuntimeException("El producto no tiene precio asignado");
        }

        Integer puntos = reglaPuntosService.calcularPuntosPorMonto(monto);
        String categoria = producto.getCategoria();
        int puntosConPromo = promocionService.calcularPuntosConPromocion(puntos, monto, categoria);
        if (puntosConPromo != puntos) {
            puntos = puntosConPromo;
        }

        ParametroVencimiento param = vencimientoRepository.findTopByOrderByFechaFinValidezDesc()
                .orElse(null);

        LocalDate fechaCaducidad = null;
        if (param != null && param.getDiasDuracion() != null) {
            fechaCaducidad = LocalDate.now().plusDays(param.getDiasDuracion());
        }

        BolsaPuntos bolsa = new BolsaPuntos();
        bolsa.setClienteId(clienteId);
        bolsa.setFechaAsignacion(LocalDateTime.now());
        bolsa.setFechaCaducidad(fechaCaducidad);
        bolsa.setPuntajeAsignado(puntos);
        bolsa.setPuntajeUtilizado(0);
        bolsa.setSaldoPuntos(puntos);
        bolsa.setMontoOperacion(monto);
        bolsa.setVencida(false);
        bolsaRepository.save(bolsa);

        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + puntos);
        clienteRepository.save(cliente);
        clienteService.actualizarNivel(clienteId);

        Compra compra = new Compra();
        compra.setClienteId(clienteId);
        compra.setProductoId(productoId);
        compra.setMonto(monto);
        compra.setPuntosGanados(puntos);
        compra.setFecha(LocalDateTime.now());
        compra = compraRepository.save(compra);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("compraId", compra.getId());
        resultado.put("clienteId", clienteId);
        resultado.put("producto", producto.getNombre());
        resultado.put("monto", monto);
        resultado.put("puntosGanados", puntos);
        resultado.put("totalCliente", cliente.getPuntosAcumulados());
        resultado.put("nivel", cliente.getNivel());
        resultado.put("puntosBase", reglaPuntosService.calcularPuntosPorMonto(monto));
        resultado.put("puntosPromocion", puntos - reglaPuntosService.calcularPuntosPorMonto(monto));
        resultado.put("mensaje", "Compra registrada. Ganaste " + puntos + " puntos!");
        return resultado;
    }

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public List<Compra> listarPorCliente(Long clienteId) {
        return compraRepository.findByClienteId(clienteId);
    }
}
