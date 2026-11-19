package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.Contacto;
import Backend.Proyecto_Movil_Backend.repository.ContactoRepository;
import Backend.Proyecto_Movil_Backend.repository.OrganizacionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    public List<Contacto> listarPorOrganizacion(Long organizacionId) {
        return contactoRepository.findByOrganizacionIdOrderByNombreAsc(organizacionId);
    }

    public Optional<Contacto> obtenerPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public Contacto guardar(Contacto contacto) {
        contacto.setId(null);
        validarOrganizacion(contacto.getOrganizacionId());
        normalizar(contacto);
        return contactoRepository.save(contacto);
    }

    public Contacto actualizar(Long id, Contacto detalles) {
        Contacto existente = contactoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Contacto no encontrado con el ID: " + id));
        validarOrganizacion(detalles.getOrganizacionId());
        normalizar(detalles);
        BeanUtils.copyProperties(detalles, existente, "id", "organizacion");
        return contactoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, el contacto no existe.");
        }
        contactoRepository.deleteById(id);
    }

    private void validarOrganizacion(Long organizacionId) {
        if (organizacionId == null || !organizacionRepository.existsById(organizacionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La organización indicada no existe.");
        }
    }

    private static void normalizar(Contacto c) {
        if (c.getCorreo() != null) c.setCorreo(c.getCorreo().trim().toLowerCase());
    }
}
