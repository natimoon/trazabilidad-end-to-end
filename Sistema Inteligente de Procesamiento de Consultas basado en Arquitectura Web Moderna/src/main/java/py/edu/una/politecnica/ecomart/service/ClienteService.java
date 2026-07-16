package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.Cliente;
import py.edu.una.politecnica.ecomart.repository.ClienteRepository;
import py.edu.una.politecnica.ecomart.repository.CompraRepository;
import py.edu.una.politecnica.ecomart.repository.ProductoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          CompraRepository compraRepository,
                          ProductoRepository productoRepository) {
        this.clienteRepository = clienteRepository;
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Cliente crear(Cliente cliente) {
        if (cliente.getCodigoReferido() == null || cliente.getCodigoReferido().isBlank()) {
            cliente.setCodigoReferido("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (cliente.getNivel() == null) cliente.setNivel("Bronce");
        if (cliente.getPuntosAcumulados() == null) cliente.setPuntosAcumulados(0);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> importarMasivo(List<Cliente> clientes) {
        return clientes.stream().map(this::crear).toList();
    }

    public Cliente actualizar(Long id, Cliente actualizado) {
        Cliente cliente = obtenerPorId(id);
        cliente.setNombre(actualizado.getNombre());
        cliente.setApellido(actualizado.getApellido());
        cliente.setNroDocumento(actualizado.getNroDocumento());
        cliente.setTipoDocumento(actualizado.getTipoDocumento());
        cliente.setCiudad(actualizado.getCiudad());
        cliente.setNacionalidad(actualizado.getNacionalidad());
        cliente.setEmail(actualizado.getEmail());
        cliente.setTelefono(actualizado.getTelefono());
        cliente.setFechaNacimiento(actualizado.getFechaNacimiento());
        if (actualizado.getReferidoPorId() != null) {
            cliente.setReferidoPorId(actualizado.getReferidoPorId());
        }
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Cliente> buscarPorApellido(String apellido) {
        return clienteRepository.findByApellidoContainingIgnoreCase(apellido);
    }

    public List<Cliente> buscarPorCumpleanos(int mes, int dia) {
        return clienteRepository.findByCumpleanos(mes, dia);
    }

    public List<Cliente> segmentarPorNivel(String nivel) {
        return clienteRepository.findByNivel(nivel);
    }

    public List<Cliente> segmentarPorRangoPuntos(int min, int max) {
        return clienteRepository.findByRangoPuntos(min, max);
    }

    public List<Cliente> segmentarPorNacionalidad(String nacionalidad) {
        return clienteRepository.findByNacionalidad(nacionalidad);
    }

    public List<Cliente> segmentarPorCiudad(String ciudad) {
        return clienteRepository.findByCiudadContainingIgnoreCase(ciudad);
    }

    public List<Cliente> segmentarPorCategoria(String categoria) {
        List<Long> productoIds = productoRepository.findByCategoria(categoria).stream()
                .map(p -> p.getId()).collect(Collectors.toList());
        if (productoIds.isEmpty()) return new ArrayList<>();
        List<Long> clienteIds = compraRepository.findClienteIdsByProductoIdIn(productoIds);
        return clienteIds.stream()
                .map(id -> clienteRepository.findById(id).orElse(null))
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }

    public List<Cliente> obtenerReferidos(Long clienteId) {
        return clienteRepository.findByReferidoPorId(clienteId);
    }

    public void actualizarNivel(Long clienteId) {
        Cliente cliente = obtenerPorId(clienteId);
        int puntos = cliente.getPuntosAcumulados();
        String nivel;
        if (puntos >= 10000) {
            nivel = "Platino";
        } else if (puntos >= 5000) {
            nivel = "Oro";
        } else if (puntos >= 1000) {
            nivel = "Plata";
        } else {
            nivel = "Bronce";
        }
        cliente.setNivel(nivel);
        clienteRepository.save(cliente);
    }
}
