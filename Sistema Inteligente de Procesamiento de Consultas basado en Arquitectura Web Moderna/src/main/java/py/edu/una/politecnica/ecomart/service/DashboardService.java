package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.BolsaPuntos;
import py.edu.una.politecnica.ecomart.model.CabeceraUsoPuntos;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.model.EncuestaSatisfaccion;
import py.edu.una.politecnica.ecomart.repository.BolsaPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.CabeceraUsoPuntosRepository;
import py.edu.una.politecnica.ecomart.repository.CanjePuntosRepository;
import py.edu.una.politecnica.ecomart.repository.ClienteRepository;
import py.edu.una.politecnica.ecomart.repository.EncuestaSatisfaccionRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DashboardService {

    private final ClienteRepository clienteRepository;
    private final BolsaPuntosRepository bolsaRepository;
    private final CabeceraUsoPuntosRepository cabeceraRepository;
    private final CanjePuntosRepository canjeRepository;
    private final EncuestaSatisfaccionRepository encuestaRepository;

    public DashboardService(ClienteRepository clienteRepository,
                            BolsaPuntosRepository bolsaRepository,
                            CabeceraUsoPuntosRepository cabeceraRepository,
                            CanjePuntosRepository canjeRepository,
                            EncuestaSatisfaccionRepository encuestaRepository) {
        this.clienteRepository = clienteRepository;
        this.bolsaRepository = bolsaRepository;
        this.cabeceraRepository = cabeceraRepository;
        this.canjeRepository = canjeRepository;
        this.encuestaRepository = encuestaRepository;
    }

    public Map<String, Object> obtenerDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        List<Cliente> clientes = clienteRepository.findAll();
        List<BolsaPuntos> bolsas = bolsaRepository.findAll();
        List<CabeceraUsoPuntos> usos = cabeceraRepository.findAll();
        List<py.edu.una.politecnica.ecomart.model.CanjePuntos> canjes = canjeRepository.findAll();
        List<EncuestaSatisfaccion> encuestas = encuestaRepository.findAll();

        int totalClientes = clientes.size();
        int totalPuntosEmitidos = bolsas.stream()
                .filter(b -> b.getPuntajeAsignado() != null)
                .mapToInt(BolsaPuntos::getPuntajeAsignado).sum();
        int totalPuntosUtilizados = bolsas.stream()
                .filter(b -> b.getPuntajeUtilizado() != null)
                .mapToInt(BolsaPuntos::getPuntajeUtilizado).sum();
        int totalPuntosVigentes = bolsas.stream()
                .filter(b -> (b.getVencida() == null || !b.getVencida()) && b.getSaldoPuntos() != null)
                .mapToInt(BolsaPuntos::getSaldoPuntos).sum();
        int totalPuntosVencidos = bolsas.stream()
                .filter(b -> Boolean.TRUE.equals(b.getVencida()) && b.getSaldoPuntos() != null)
                .mapToInt(BolsaPuntos::getSaldoPuntos).sum();
        int totalCanjes = usos.size() + canjes.size();

        long bronce = clientes.stream().filter(c -> "Bronce".equals(c.getNivel())).count();
        long plata = clientes.stream().filter(c -> "Plata".equals(c.getNivel())).count();
        long oro = clientes.stream().filter(c -> "Oro".equals(c.getNivel())).count();
        long platino = clientes.stream().filter(c -> "Platino".equals(c.getNivel())).count();

        double puntuacionPromedio = 0;
        if (!encuestas.isEmpty()) {
            puntuacionPromedio = encuestas.stream()
                    .filter(e -> e.getPuntuacion() != null)
                    .mapToInt(EncuestaSatisfaccion::getPuntuacion).average().orElse(0);
        }

        List<BolsaPuntos> bolsasAVencer = bolsaRepository.findBolsasAVencer(LocalDate.now().plusDays(30));
        int clientesConPuntosAVencer = (int) bolsasAVencer.stream().map(BolsaPuntos::getClienteId).distinct().count();

        dashboard.put("totalClientes", totalClientes);
        dashboard.put("totalPuntosEmitidos", totalPuntosEmitidos);
        dashboard.put("totalPuntosUtilizados", totalPuntosUtilizados);
        dashboard.put("totalPuntosVigentes", totalPuntosVigentes);
        dashboard.put("puntosActivos", totalPuntosVigentes);
        dashboard.put("totalPuntosVencidos", totalPuntosVencidos);
        dashboard.put("puntosVencidos", totalPuntosVencidos);
        dashboard.put("totalCanjes", totalCanjes);
        dashboard.put("clientesBronce", bronce);
        dashboard.put("clientesPlata", plata);
        dashboard.put("clientesOro", oro);
        dashboard.put("clientesPlatino", platino);
        dashboard.put("puntuacionPromedioEncuestas", Math.round(puntuacionPromedio * 100.0) / 100.0);
        dashboard.put("clientesConPuntosAVencer30dias", clientesConPuntosAVencer);
        dashboard.put("tasaRetencion", clientes.isEmpty() ? 0 :
                Math.round((double) clientes.stream().filter(c -> c.getPuntosAcumulados() != null && c.getPuntosAcumulados() > 0).count() / totalClientes * 10000.0) / 100.0);

        return dashboard;
    }
}
