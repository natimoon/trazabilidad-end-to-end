package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.Promocion;
import py.edu.una.politecnica.ecomart.repository.PromocionRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class PromocionService {

    private final PromocionRepository repository;

    public PromocionService(PromocionRepository repository) {
        this.repository = repository;
    }

    public List<Promocion> listarTodas() {
        return repository.findAll();
    }

    public Promocion obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
    }

    public Promocion crear(Promocion promocion) {
        return repository.save(promocion);
    }

    public Promocion actualizar(Long id, Promocion actualizado) {
        Promocion p = obtenerPorId(id);
        p.setNombre(actualizado.getNombre());
        p.setDescripcion(actualizado.getDescripcion());
        p.setTipo(actualizado.getTipo());
        p.setValor(actualizado.getValor());
        p.setCategoria(actualizado.getCategoria());
        p.setProductoId(actualizado.getProductoId());
        p.setFechaInicio(actualizado.getFechaInicio());
        p.setFechaFin(actualizado.getFechaFin());
        p.setActivo(actualizado.getActivo() != null ? actualizado.getActivo() : p.getActivo());
        return repository.save(p);
    }

    public void eliminar(Long id) {
        repository.delete(obtenerPorId(id));
    }

    public int calcularPuntosConPromocion(int puntosBase, Double monto, String categoria) {
        List<Promocion> activas = repository.findActivas(LocalDate.now());
        int puntosExtra = 0;
        for (Promocion p : activas) {
            if (p.getCategoria() != null && !p.getCategoria().equals(categoria)) {
                continue;
            }
            if ("MULTIPLICADOR".equals(p.getTipo())) {
                puntosBase = (int) Math.floor(puntosBase * p.getValor());
            } else if ("PUNTOS_EXTRA".equals(p.getTipo())) {
                puntosExtra += p.getValor().intValue();
            }
        }
        return puntosBase + puntosExtra;
    }
}
