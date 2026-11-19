package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.Organizacion;
import Backend.Proyecto_Movil_Backend.repository.OportunidadRepository;
import Backend.Proyecto_Movil_Backend.repository.OrganizacionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private OportunidadRepository oportunidadRepository;

    public List<Organizacion> listarTodas() {
        return organizacionRepository.findAllByOrderByNombreAsc();
    }

    public Optional<Organizacion> obtenerPorId(Long id) {
        return organizacionRepository.findById(id);
    }

    public Organizacion guardar(Organizacion organizacion) {
        organizacion.setId(null); // evita que un POST con "id" actualice otra organización
        return organizacionRepository.save(organizacion);
    }

    public Organizacion actualizar(Long id, Organizacion detalles) {
        Organizacion existente = organizacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Organización no encontrada con el ID: " + id));
        BeanUtils.copyProperties(detalles, existente, "id");
        return organizacionRepository.save(existente);
    }

    /** 409 si la organización tiene oportunidades asociadas (FK RESTRICT). */
    public void eliminar(Long id) {
        if (!organizacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, la organización no existe.");
        }
        if (oportunidadRepository.existsByOrganizacionId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: la organización tiene oportunidades asociadas.");
        }
        organizacionRepository.deleteById(id); // sus contactos se eliminan en cascada
    }
}
