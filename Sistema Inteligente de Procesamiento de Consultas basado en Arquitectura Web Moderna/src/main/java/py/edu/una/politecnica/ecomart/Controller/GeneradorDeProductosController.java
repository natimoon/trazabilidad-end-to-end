package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.edu.una.politecnica.ecomart.service.GeneradorService;

@RestController
@RequestMapping("/generador")
public class GeneradorDeProductosController {

    private final GeneradorService generadorService;

    public GeneradorDeProductosController(GeneradorService generadorService) {
        this.generadorService = generadorService;
    }

    @GetMapping
    public String generadorDeProductos() {
        return generadorService.generarProductos();
    }
}
