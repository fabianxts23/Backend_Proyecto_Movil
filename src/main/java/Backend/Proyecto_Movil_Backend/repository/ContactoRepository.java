package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    List<Contacto> findByOrganizacionIdOrderByNombreAsc(Long organizacionId);
}
