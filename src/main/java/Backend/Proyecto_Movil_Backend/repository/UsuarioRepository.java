package Backend.Proyecto_Movil_Backend.repository;

import Backend.Proyecto_Movil_Backend.model.Rol;
import Backend.Proyecto_Movil_Backend.model.Usuario;
//import Backend.Proyecto_Movil_Backend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //  Correcto (solo Entidad y ID)

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    List<Usuario> findByArea(String area);

    // Esto funcionará perfecto porque Spring lee el tipo 'Rol' desde la entidad Usuario
    List<Usuario> findByRol(Rol rol);

    List<Usuario> findByCuentaActiva(boolean cuentaActiva);
}