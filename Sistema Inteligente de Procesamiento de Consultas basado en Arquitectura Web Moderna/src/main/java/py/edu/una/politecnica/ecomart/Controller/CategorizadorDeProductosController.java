package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.edu.una.politecnica.ecomart.service.CategorizadorService;

@RestController
@RequestMapping("/categorizador")
public class CategorizadorDeProductosController {

    private final CategorizadorService categorizadorService;

    public CategorizadorDeProductosController(CategorizadorService categorizadorService) {
        this.categorizadorService = categorizadorService;
    }

    @GetMapping
    public String categorizarProductos(String producto) {
        return categorizadorService.categorizarProducto(producto);
    }
}
