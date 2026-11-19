package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.EstadoOportunidad;
import Backend.Proyecto_Movil_Backend.model.Oportunidad;
import Backend.Proyecto_Movil_Backend.repository.OportunidadRepository;
import Backend.Proyecto_Movil_Backend.repository.OrganizacionRepository;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OportunidadService {

    @Autowired
    private OportunidadRepository oportunidadRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Lista con filtros opcionales (se aplica el primero que venga informado). */
    public List<Oportunidad> listar(EstadoOportunidad estado, Long responsableId, Long organizacionId) {
        if (estado != null) return oportunidadRepository.findByEstado(estado);
        if (responsableId != null) return oportunidadRepository.findByResponsableId(responsableId);
        if (organizacionId != null) return oportunidadRepository.findByOrganizacionId(organizacionId);
        return oportunidadRepository.findAll();
    }

    public Optional<Oportunidad> obtenerPorId(Long id) {
        return oportunidadRepository.findById(id);
    }

    public Oportunidad guardar(Oportunidad oportunidad) {
        oportunidad.setId(null);
        validar(oportunidad);
        oportunidad.setFechaUltimaActualizacion(System.currentTimeMillis());
        return oportunidadRepository.save(oportunidad);
    }

    public Oportunidad actualizar(Long id, Oportunidad detalles) {
        Oportunidad existente = oportunidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Oportunidad no encontrada con el ID: " + id));
        // Una oportunidad cerrada (estado final) no admite cambiar de estado
        if (existente.getEstado().esFinal() && detalles.getEstado() != existente.getEstado()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La oportunidad está cerrada (" + existente.getEstado() + ") y no admite más cambios de estado.");
        }
        validar(detalles);
        BeanUtils.copyProperties(detalles, existente, "id", "organizacion", "responsable");
        existente.setFechaUltimaActualizacion(System.currentTimeMillis());
        return oportunidadRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!oportunidadRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, la oportunidad no existe.");
        }
        oportunidadRepository.deleteById(id); // seguimientos y evidencias se eliminan en cascada
    }

    /** Reglas de integridad y coherencia de fechas (las mismas que valida la app). */
    private void validar(Oportunidad o) {
        if (o.getOrganizacionId() == null || !organizacionRepository.existsById(o.getOrganizacionId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La organización indicada no existe.");
        }
        if (o.getResponsableId() == null || !usuarioRepository.existsById(o.getResponsableId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El responsable indicado no existe.");
        }
        if (o.getFechaTermino() < o.getFechaInicio()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de término no puede ser anterior al inicio.");
        }
        if (o.getFechaInicio() < o.getFechaDeteccion()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El evento no puede iniciar antes de ser detectado.");
        }
    }
}
