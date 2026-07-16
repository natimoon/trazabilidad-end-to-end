package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.edu.una.politecnica.ecomart.model.*;
import py.edu.una.politecnica.ecomart.repository.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GamificacionService {

    private final DesafioRepository desafioRepository;
    private final InsigniaRepository insigniaRepository;
    private final ProgresoDesafioRepository progresoRepository;
    private final InsigniaClienteRepository insigniaClienteRepository;
    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;
    private final CanjePuntosRepository canjeRepository;
    private final BolsaPuntosRepository bolsaRepository;
    private final ClienteService clienteService;

    public GamificacionService(DesafioRepository desafioRepository,
                               InsigniaRepository insigniaRepository,
                               ProgresoDesafioRepository progresoRepository,
                               InsigniaClienteRepository insigniaClienteRepository,
                               ClienteRepository clienteRepository,
                               CompraRepository compraRepository,
                               CanjePuntosRepository canjeRepository,
                               BolsaPuntosRepository bolsaRepository,
                               ClienteService clienteService) {
        this.desafioRepository = desafioRepository;
        this.insigniaRepository = insigniaRepository;
        this.progresoRepository = progresoRepository;
        this.insigniaClienteRepository = insigniaClienteRepository;
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
        this.canjeRepository = canjeRepository;
        this.bolsaRepository = bolsaRepository;
        this.clienteService = clienteService;
    }

    // ===== DESAFIOS =====
    public List<Desafio> listarDesafios() {
        return desafioRepository.findAll();
    }

    public Desafio crearDesafio(Desafio d) {
        return desafioRepository.save(d);
    }

    public void eliminarDesafio(Long id) {
        desafioRepository.deleteById(id);
    }

    // ===== INSIGNIAS =====
    public List<Insignia> listarInsignias() {
        return insigniaRepository.findAll();
    }

    public Insignia crearInsignia(Insignia i) {
        return insigniaRepository.save(i);
    }

    public void eliminarInsignia(Long id) {
        insigniaRepository.deleteById(id);
    }

    // ===== PROGRESO =====
    @Transactional
    public Map<String, Object> verificarProgreso(Long clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Desafio> desafiosActivos = desafioRepository.findActivos(LocalDate.now());
        List<Map<String, Object>> resultados = new ArrayList<>();
        int totalPuntosGanados = 0;
        List<String> insigniasGanadas = new ArrayList<>();

        for (Desafio desafio : desafiosActivos) {
            Map<String, Object> res = procesarDesafio(clienteId, desafio);
            resultados.add(res);
            if (Boolean.TRUE.equals(res.get("completadoAhora"))) {
                totalPuntosGanados += desafio.getPuntosRecompensa();
                if (desafio.getInsigniaRecompensa() != null) {
                    insigniasGanadas.add(desafio.getInsigniaRecompensa());
                }
            }
        }

        // Asignar puntos de recompensa
        if (totalPuntosGanados > 0) {
            Cliente c = clienteRepository.findById(clienteId).orElseThrow();
            c.setPuntosAcumulados(c.getPuntosAcumulados() + totalPuntosGanados);
            clienteRepository.save(c);
            clienteService.actualizarNivel(clienteId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("resultados", resultados);
        response.put("puntosGanados", totalPuntosGanados);
        response.put("insigniasGanadas", insigniasGanadas);
        return response;
    }

    private Map<String, Object> procesarDesafio(Long clienteId, Desafio desafio) {
        Optional<ProgresoDesafio> opt = progresoRepository.findByClienteIdAndDesafioId(clienteId, desafio.getId());
        ProgresoDesafio prog = opt.orElseGet(() -> {
            ProgresoDesafio p = new ProgresoDesafio();
            p.setClienteId(clienteId);
            p.setDesafioId(desafio.getId());
            p.setProgreso(0);
            p.setCompletado(false);
            p.setRecompensaRecibida(false);
            return p;
        });

        if (Boolean.TRUE.equals(prog.getCompletado())) {
            Map<String, Object> r = new HashMap<>();
            r.put("desafio", desafio.getNombre());
            r.put("progreso", prog.getProgreso());
            r.put("objetivo", desafio.getObjetivo());
            r.put("completado", true);
            r.put("completadoAhora", false);
            return r;
        }

        int progreso = 0;
        switch (desafio.getTipo()) {
            case "COMPRAS_CANTIDAD":
                progreso = compraRepository.findByClienteId(clienteId).size();
                break;
            case "COMPRAS_MONTO":
                progreso = (int) Math.floor(
                    compraRepository.findByClienteId(clienteId).stream()
                        .filter(c -> c.getMonto() != null)
                        .mapToDouble(Compra::getMonto).sum() / 1000
                );
                break;
            case "CANJES":
                progreso = canjeRepository.findByClienteId(clienteId).size();
                break;
            case "PUNTOS_ACUMULADOS":
                Cliente c = clienteRepository.findById(clienteId).orElse(null);
                progreso = c != null && c.getPuntosAcumulados() != null ? c.getPuntosAcumulados() : 0;
                break;
            default:
                progreso = 0;
        }

        prog.setProgreso(progreso);
        boolean completo = progreso >= desafio.getObjetivo();
        prog.setCompletado(completo);
        if (completo && prog.getFechaCompletado() == null) {
            prog.setFechaCompletado(LocalDateTime.now());
        }

        progresoRepository.save(prog);

        Map<String, Object> r = new HashMap<>();
        r.put("desafio", desafio.getNombre());
        r.put("progreso", progreso);
        r.put("objetivo", desafio.getObjetivo());
        r.put("completado", completo);
        r.put("completadoAhora", completo && opt.isEmpty());
        return r;
    }

    // ===== INSIGNIAS DEL CLIENTE =====
    public List<Map<String, Object>> obtenerInsigniasCliente(Long clienteId) {
        List<InsigniaCliente> rels = insigniaClienteRepository.findByClienteId(clienteId);
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (InsigniaCliente r : rels) {
            insigniaRepository.findById(r.getInsigniaId()).ifPresent(ins -> {
                Map<String, Object> item = new HashMap<>();
                item.put("id", r.getId());
                item.put("insigniaId", ins.getId());
                item.put("nombre", ins.getNombre());
                item.put("descripcion", ins.getDescripcion());
                item.put("icono", ins.getIcono() != null ? ins.getIcono() : "🏅");
                item.put("fecha", r.getFechaObtenida());
                resultado.add(item);
            });
        }
        return resultado;
    }

    // ===== RANKING =====
    public List<Map<String, Object>> obtenerRanking(int limite) {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.sort((a, b) -> {
            int pa = a.getPuntosAcumulados() != null ? a.getPuntosAcumulados() : 0;
            int pb = b.getPuntosAcumulados() != null ? b.getPuntosAcumulados() : 0;
            return Integer.compare(pb, pa);
        });
        List<Map<String, Object>> ranking = new ArrayList<>();
        int pos = 1;
        for (Cliente c : clientes) {
            if (pos > limite) break;
            Map<String, Object> item = new HashMap<>();
            item.put("posicion", pos++);
            item.put("clienteId", c.getId());
            item.put("nombre", c.getNombre() + " " + (c.getApellido() != null ? c.getApellido() : ""));
            item.put("puntos", c.getPuntosAcumulados() != null ? c.getPuntosAcumulados() : 0);
            item.put("nivel", c.getNivel());
            long insignias = insigniaClienteRepository.findByClienteId(c.getId()).size();
            item.put("insignias", insignias);
            ranking.add(item);
        }
        return ranking;
    }
}
