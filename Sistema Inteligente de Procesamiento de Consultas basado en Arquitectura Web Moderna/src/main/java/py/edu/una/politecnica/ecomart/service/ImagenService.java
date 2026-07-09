package py.edu.una.politecnica.ecomart.service;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.stereotype.Service;

@Service
public class ImagenService {

    private final ImageModel imageModel;

    public ImagenService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public String generarImagen(String prompt) {
        var options = ImageOptionsBuilder.builder()
                .withHeight(1024)
                .withWidth(1024)
                .build();
        var response = imageModel.call(new ImagePrompt(prompt, options));
        return response.getResult().getOutput().getUrl();
    }
}
