package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.service.ClienteService;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Cliente>> buscarPorNombre(@RequestParam String q) {
        return ResponseEntity.ok(clienteService.buscarPorNombre(q));
    }

    @GetMapping("/buscar/apellido")
    public ResponseEntity<List<Cliente>> buscarPorApellido(@RequestParam String q) {
        return ResponseEntity.ok(clienteService.buscarPorApellido(q));
    }

    @GetMapping("/buscar/cumpleanos")
    public ResponseEntity<List<Cliente>> buscarPorCumpleanos(
            @RequestParam int mes, @RequestParam int dia) {
        return ResponseEntity.ok(clienteService.buscarPorCumpleanos(mes, dia));
    }

    @GetMapping("/{id}/referidos")
    public ResponseEntity<List<Cliente>> obtenerReferidos(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerReferidos(id));
    }

    @GetMapping("/segmentar/nivel")
    public ResponseEntity<List<Cliente>> segmentarPorNivel(@RequestParam String nivel) {
        return ResponseEntity.ok(clienteService.segmentarPorNivel(nivel));
    }

    @GetMapping("/segmentar/puntos")
    public ResponseEntity<List<Cliente>> segmentarPorRangoPuntos(
            @RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(clienteService.segmentarPorRangoPuntos(min, max));
    }

    @GetMapping("/segmentar/nacionalidad")
    public ResponseEntity<List<Cliente>> segmentarPorNacionalidad(@RequestParam String nacionalidad) {
        return ResponseEntity.ok(clienteService.segmentarPorNacionalidad(nacionalidad));
    }
}
