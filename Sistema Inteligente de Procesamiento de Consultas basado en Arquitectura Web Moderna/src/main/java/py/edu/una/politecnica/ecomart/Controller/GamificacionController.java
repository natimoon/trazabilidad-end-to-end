package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.Desafio;
import py.edu.una.politecnica.ecomart.model.Insignia;
import py.edu.una.politecnica.ecomart.service.GamificacionService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gamificacion")
public class GamificacionController {

    private final GamificacionService service;

    public GamificacionController(GamificacionService service) { this.service = service; }

    @GetMapping("/desafios")
    public ResponseEntity<List<Desafio>> listarDesafios() {
        return ResponseEntity.ok(service.listarDesafios());
    }

    @PostMapping("/desafios")
    public ResponseEntity<Desafio> crearDesafio(@RequestBody Desafio desafio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearDesafio(desafio));
    }

    @DeleteMapping("/desafios/{id}")
    public ResponseEntity<Void> eliminarDesafio(@PathVariable Long id) {
        service.eliminarDesafio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/insignias")
    public ResponseEntity<List<Insignia>> listarInsignias() {
        return ResponseEntity.ok(service.listarInsignias());
    }

    @PostMapping("/insignias")
    public ResponseEntity<Insignia> crearInsignia(@RequestBody Insignia insignia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearInsignia(insignia));
    }

    @DeleteMapping("/insignias/{id}")
    public ResponseEntity<Void> eliminarInsignia(@PathVariable Long id) {
        service.eliminarInsignia(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verificar/{clienteId}")
    public ResponseEntity<Map<String, Object>> verificarProgreso(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.verificarProgreso(clienteId));
    }

    @GetMapping("/insignias/cliente/{clienteId}")
    public ResponseEntity<List<Map<String, Object>>> insigniasCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerInsigniasCliente(clienteId));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Map<String, Object>>> ranking(@RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(service.obtenerRanking(limite));
    }
}
