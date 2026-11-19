package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.EstadoOportunidad;
import Backend.Proyecto_Movil_Backend.model.Oportunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {

    List<Oportunidad> findByEstado(EstadoOportunidad estado);

    List<Oportunidad> findByResponsableId(Long responsableId);

    List<Oportunidad> findByOrganizacionId(Long organizacionId);

    boolean existsByOrganizacionId(Long organizacionId);

    boolean existsByResponsableId(Long responsableId);
}
