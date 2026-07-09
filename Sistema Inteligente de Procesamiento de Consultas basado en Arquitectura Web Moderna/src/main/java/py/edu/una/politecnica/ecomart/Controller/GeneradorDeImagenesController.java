package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.edu.una.politecnica.ecomart.service.ImagenService;

@RestController
@RequestMapping("/imagen")
public class GeneradorDeImagenesController {

    private final ImagenService imagenService;

    public GeneradorDeImagenesController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @GetMapping
    public String generadorDeImagenes(String prompt) {
        return imagenService.generarImagen(prompt);
    }
}
