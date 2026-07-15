package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.BolsaPuntos;
import py.edu.una.politecnica.ecomart.model.CabeceraUsoPuntos;
import py.edu.una.politecnica.ecomart.service.PuntosService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/puntos")
public class PuntosController {

    private final PuntosService puntosService;

    public PuntosController(PuntosService puntosService) {
        this.puntosService = puntosService;
    }

    @PostMapping("/cargar")
    public ResponseEntity<BolsaPuntos> cargarPuntos(@RequestBody Map<String, Object> body) {
        Long clienteId = Long.valueOf(body.get("clienteId").toString());
        Double monto = Double.valueOf(body.get("monto").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(puntosService.cargarPuntos(clienteId, monto));
    }

    @PostMapping("/usar")
    public ResponseEntity<Map<String, Object>> usarPuntos(@RequestBody Map<String, Object> body) {
        Long clienteId = Long.valueOf(body.get("clienteId").toString());
        Long conceptoUsoId = Long.valueOf(body.get("conceptoUsoId").toString());
        return ResponseEntity.ok(puntosService.usarPuntos(clienteId, conceptoUsoId));
    }

    @GetMapping("/equivalencia")
    public ResponseEntity<Map<String, Object>> consultarEquivalencia(@RequestParam Double monto) {
        int puntos = puntosService.consultarEquivalencia(monto);
        return ResponseEntity.ok(Map.of("monto", monto, "puntosEquivalentes", puntos));
    }

    @GetMapping("/bolsas/cliente/{clienteId}")
    public ResponseEntity<List<BolsaPuntos>> bolsasPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(puntosService.listarBolsasPorCliente(clienteId));
    }

    @GetMapping("/bolsas/rango")
    public ResponseEntity<List<BolsaPuntos>> bolsasPorRango(
            @RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(puntosService.listarBolsasPorRangoPuntos(min, max));
    }

    @GetMapping("/bolsas/a-vencer")
    public ResponseEntity<List<BolsaPuntos>> bolsasAVencer(@RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(puntosService.listarBolsasAVencer(dias));
    }

    @GetMapping("/bolsas/cliente/{clienteId}/a-vencer")
    public ResponseEntity<List<BolsaPuntos>> bolsasClienteAVencer(
            @PathVariable Long clienteId, @RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(puntosService.listarBolsasPorClienteAVencer(clienteId, dias));
    }

    @GetMapping("/usos/concepto/{conceptoId}")
    public ResponseEntity<List<CabeceraUsoPuntos>> usosPorConcepto(@PathVariable Long conceptoId) {
        return ResponseEntity.ok(puntosService.consultarUsoPorConcepto(conceptoId));
    }

    @GetMapping("/usos/fecha")
    public ResponseEntity<List<CabeceraUsoPuntos>> usosPorFecha(
            @RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(puntosService.consultarUsoPorFecha(inicio, fin));
    }

    @GetMapping("/usos/cliente/{clienteId}")
    public ResponseEntity<List<CabeceraUsoPuntos>> usosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(puntosService.consultarUsoPorCliente(clienteId));
    }
}
