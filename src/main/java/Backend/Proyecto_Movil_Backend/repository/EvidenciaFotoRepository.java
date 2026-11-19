package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.EvidenciaFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenciaFotoRepository extends JpaRepository<EvidenciaFoto, Long> {

    List<EvidenciaFoto> findByOportunidadIdOrderByFechaDesc(Long oportunidadId);
}
