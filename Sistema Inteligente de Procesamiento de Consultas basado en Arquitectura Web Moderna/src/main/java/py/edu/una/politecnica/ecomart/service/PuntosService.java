package py.edu.una.politecnica.ecomart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.una.politecnica.ecomart.model.BolsaPuntos;
import py.edu.una.politecnica.ecomart.model.CabeceraUsoPuntos;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.model.ConceptoUso;
import py.edu.una.politecnica.ecomart.model.DetalleUsoPuntos;
import py.edu.una.politecnica.ecomart.model.ParametroVencimiento;
import py.edu.una.politecnica.ecomart.repository.BolsaPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.CabeceraUsoPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.ClienteRepository;
import py.edu.una.politecnica.ecomart.repository.ConceptoUsoRepository;
import py.edu.una.politecnica.ecomart.repository.DetalleUsoPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.ParametroVencimientoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PuntosService {

    private static final Logger log = LoggerFactory.getLogger(PuntosService.class);

    private final BolsaPuntosRepository bolsaRepository;
    private final CabeceraUsoPuntosRepository cabeceraRepository;
    private final DetalleUsoPuntosRepository detalleRepository;
    private final ClienteRepository clienteRepository;
    private final ConceptoUsoRepository conceptoRepository;
    private final ReglaPuntosService reglaPuntosService;
    private final ParametroVencimientoRepository vencimientoRepository;
    private final ClienteService clienteService;

    public PuntosService(BolsaPuntosRepository bolsaRepository,
                         CabeceraUsoPuntosRepository cabeceraRepository,
                         DetalleUsoPuntosRepository detalleRepository,
                         ClienteRepository clienteRepository,
                         ConceptoUsoRepository conceptoRepository,
                         ReglaPuntosService reglaPuntosService,
                         ParametroVencimientoRepository vencimientoRepository,
                         ClienteService clienteService) {
        this.bolsaRepository = bolsaRepository;
        this.cabeceraRepository = cabeceraRepository;
        this.detalleRepository = detalleRepository;
        this.clienteRepository = clienteRepository;
        this.conceptoRepository = conceptoRepository;
        this.reglaPuntosService = reglaPuntosService;
        this.vencimientoRepository = vencimientoRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public BolsaPuntos cargarPuntos(Long clienteId, Double montoOperacion) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Integer puntos = reglaPuntosService.calcularPuntosPorMonto(montoOperacion);

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
        bolsa.setMontoOperacion(montoOperacion);
        bolsa.setVencida(false);
        bolsa = bolsaRepository.save(bolsa);

        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + puntos);
        clienteRepository.save(cliente);
        clienteService.actualizarNivel(clienteId);

        return bolsa;
    }

    @Transactional
    public Map<String, Object> usarPuntos(Long clienteId, Long conceptoUsoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        ConceptoUso concepto = conceptoRepository.findById(conceptoUsoId)
                .orElseThrow(() -> new RuntimeException("Concepto de uso no encontrado"));

        int puntosNecesarios = concepto.getPuntosRequeridos();

        if (cliente.getPuntosAcumulados() < puntosNecesarios) {
            throw new RuntimeException("Puntos insuficientes. Disponible: " + cliente.getPuntosAcumulados()
                    + ", necesario: " + puntosNecesarios);
        }

        List<BolsaPuntos> bolsasActivas = bolsaRepository.findBolsasActivasPorCliente(clienteId);

        int puntosRestantes = puntosNecesarios;
        List<Map<String, Object>> detalles = new ArrayList<>();

        CabeceraUsoPuntos cabecera = new CabeceraUsoPuntos();
        cabecera.setClienteId(clienteId);
        cabecera.setPuntajeUtilizado(puntosNecesarios);
        cabecera.setFecha(LocalDateTime.now());
        cabecera.setConceptoUsoId(conceptoUsoId);
        cabecera = cabeceraRepository.save(cabecera);

        for (BolsaPuntos bolsa : bolsasActivas) {
            if (puntosRestantes <= 0) break;

            int disponible = bolsa.getSaldoPuntos();
            int aUsar = Math.min(disponible, puntosRestantes);

            bolsa.setPuntajeUtilizado(bolsa.getPuntajeUtilizado() + aUsar);
            bolsa.setSaldoPuntos(bolsa.getSaldoPuntos() - aUsar);
            bolsaRepository.save(bolsa);

            DetalleUsoPuntos detalle = new DetalleUsoPuntos();
            detalle.setCabeceraId(cabecera.getId());
            detalle.setPuntajeUtilizado(aUsar);
            detalle.setBolsaPuntosId(bolsa.getId());
            detalleRepository.save(detalle);

            Map<String, Object> det = new HashMap<>();
            det.put("bolsaId", bolsa.getId());
            det.put("puntosUsados", aUsar);
            det.put("saldoRestante", bolsa.getSaldoPuntos());
            detalles.add(det);

            puntosRestantes -= aUsar;
        }

        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - puntosNecesarios);
        clienteRepository.save(cliente);
        clienteService.actualizarNivel(clienteId);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("cabeceraId", cabecera.getId());
        resultado.put("clienteId", clienteId);
        resultado.put("clienteEmail", cliente.getEmail());
        resultado.put("puntosUtilizados", puntosNecesarios);
        resultado.put("concepto", concepto.getDescripcion());
        resultado.put("detalles", detalles);
        resultado.put("mensaje", "Uso de puntos registrado. Se envió comprobante a " + cliente.getEmail());

        log.info("Comprobante de uso de puntos - Cliente: {}, Puntos: {}, Concepto: {}, Email: {}",
                clienteId, puntosNecesarios, concepto.getDescripcion(), cliente.getEmail());

        return resultado;
    }

    public int consultarEquivalencia(Double monto) {
        return reglaPuntosService.calcularPuntosPorMonto(monto);
    }

    public List<BolsaPuntos> listarBolsasPorCliente(Long clienteId) {
        return bolsaRepository.findByClienteId(clienteId);
    }

    public List<BolsaPuntos> listarBolsasPorRangoPuntos(Integer min, Integer max) {
        return bolsaRepository.findByRangoPuntos(min, max);
    }

    public List<BolsaPuntos> listarBolsasAVencer(int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        return bolsaRepository.findBolsasAVencer(fechaLimite);
    }

    public List<BolsaPuntos> listarBolsasPorClienteAVencer(Long clienteId, int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        return bolsaRepository.findBolsasPorClienteAVencer(clienteId, fechaLimite);
    }

    public List<CabeceraUsoPuntos> consultarUsoPorConcepto(Long conceptoUsoId) {
        return cabeceraRepository.findByConceptoUsoId(conceptoUsoId);
    }

    public List<CabeceraUsoPuntos> consultarUsoPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return cabeceraRepository.findByFechaBetween(inicio, fin);
    }

    public List<CabeceraUsoPuntos> consultarUsoPorCliente(Long clienteId) {
        return cabeceraRepository.findByClienteId(clienteId);
    }

    public List<CabeceraUsoPuntos> consultarUsoPorClienteYFecha(Long clienteId, LocalDateTime inicio, LocalDateTime fin) {
        return cabeceraRepository.findByClienteIdAndFechaBetween(clienteId, inicio, fin);
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void procesarVencimientos() {
        log.info("Iniciando proceso planificado de vencimiento de puntos...");
        List<BolsaPuntos> bolsasVencidas = bolsaRepository.findBolsasAVencer(LocalDate.now());

        for (BolsaPuntos bolsa : bolsasVencidas) {
            bolsa.setVencida(true);
            int saldo = bolsa.getSaldoPuntos();
            bolsa.setSaldoPuntos(0);
            bolsaRepository.save(bolsa);

            Cliente cliente = clienteRepository.findById(bolsa.getClienteId()).orElse(null);
            if (cliente != null) {
                cliente.setPuntosAcumulados(Math.max(0, cliente.getPuntosAcumulados() - saldo));
                clienteRepository.save(cliente);
                clienteService.actualizarNivel(cliente.getId());
            }
        }

        if (!bolsasVencidas.isEmpty()) {
            log.info("Proceso completado. {} bolsas vencidas procesadas.", bolsasVencidas.size());
        }
    }
}
