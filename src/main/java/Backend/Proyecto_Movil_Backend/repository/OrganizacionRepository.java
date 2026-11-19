package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

    List<Organizacion> findAllByOrderByNombreAsc();
}
