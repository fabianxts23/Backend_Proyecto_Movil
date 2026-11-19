package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.Seguimiento;
import Backend.Proyecto_Movil_Backend.repository.OportunidadRepository;
import Backend.Proyecto_Movil_Backend.repository.SeguimientoRepository;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/** El historial de seguimientos se conserva: solo se crea y se consulta. */
@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private OportunidadRepository oportunidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Seguimiento> listarTodos() {
        return seguimientoRepository.findAll();
    }

    public List<Seguimiento> listarPorOportunidad(Long oportunidadId) {
        return seguimientoRepository.findByOportunidadIdOrderByFechaHoraDesc(oportunidadId);
    }

    public Optional<Seguimiento> obtenerPorId(Long id) {
        return seguimientoRepository.findById(id);
    }

    public Seguimiento guardar(Seguimiento seguimiento) {
        seguimiento.setId(null);
        if (seguimiento.getOportunidadId() == null || !oportunidadRepository.existsById(seguimiento.getOportunidadId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La oportunidad indicada no existe.");
        }
        if (seguimiento.getUsuarioId() == null || !usuarioRepository.existsById(seguimiento.getUsuarioId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario indicado no existe.");
        }
        return seguimientoRepository.save(seguimiento);
    }
}
