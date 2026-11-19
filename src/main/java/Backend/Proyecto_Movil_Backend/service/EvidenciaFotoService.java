package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.EvidenciaFoto;
import Backend.Proyecto_Movil_Backend.repository.EvidenciaFotoRepository;
import Backend.Proyecto_Movil_Backend.repository.OportunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EvidenciaFotoService {

    @Autowired
    private EvidenciaFotoRepository evidenciaRepository;

    @Autowired
    private OportunidadRepository oportunidadRepository;

    public List<EvidenciaFoto> listarPorOportunidad(Long oportunidadId) {
        return evidenciaRepository.findByOportunidadIdOrderByFechaDesc(oportunidadId);
    }

    public Optional<EvidenciaFoto> obtenerPorId(Long id) {
        return evidenciaRepository.findById(id);
    }

    public EvidenciaFoto guardar(EvidenciaFoto evidencia) {
        evidencia.setId(null);
        if (evidencia.getOportunidadId() == null || !oportunidadRepository.existsById(evidencia.getOportunidadId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La oportunidad indicada no existe.");
        }
        if (evidencia.getFecha() == null) evidencia.setFecha(System.currentTimeMillis());
        return evidenciaRepository.save(evidencia);
    }

    public void eliminar(Long id) {
        if (!evidenciaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, la evidencia no existe.");
        }
        evidenciaRepository.deleteById(id);
    }
}
