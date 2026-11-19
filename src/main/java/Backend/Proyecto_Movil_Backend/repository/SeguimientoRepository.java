package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    List<Seguimiento> findByOportunidadIdOrderByFechaHoraDesc(Long oportunidadId);

    boolean existsByUsuarioId(Long usuarioId);
}
