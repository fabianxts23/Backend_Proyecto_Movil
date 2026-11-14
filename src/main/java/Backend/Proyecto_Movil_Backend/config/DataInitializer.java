package Backend.Proyecto_Movil_Backend.config;

import Backend.Proyecto_Movil_Backend.model.Rol;
import Backend.Proyecto_Movil_Backend.model.Usuario;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Datos de demostración. Se insertan solo los que NO existen (por correo), así funciona
 * tanto con la base vacía como con una base que ya tenía usuarios de antes.
 * Las 4 primeras cuentas son las mismas que usa la app móvil (README de la app).
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatosDemo(UsuarioRepository repo) {
        return args -> {
            crearSiNoExiste(repo, "Sofía Herrera", "supervisor@ficticio.cl", "duoc123", Rol.SUPERVISOR, "Convenios Corporativos", "Santiago");
            crearSiNoExiste(repo, "Javier Muñoz", "ejecutivo1@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Santiago");
            crearSiNoExiste(repo, "Carla Rojas", "ejecutivo2@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Concepción");
            crearSiNoExiste(repo, "Andrés Paredes", "ejecutivo3@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Puente Alto");

            crearSiNoExiste(repo, "Carlos Muñoz", "carlos.munoz@duoc.cl", "123456", Rol.SUPERVISOR, "Informatica", "Santiago");
            crearSiNoExiste(repo, "María Urrutia", "maria.urrutia@duoc.cl", "123456", Rol.EJECUTIVO, "Administracion", "Valparaiso");
            crearSiNoExiste(repo, "Antonia Saez", "antonia.saez@duoc.cl", "123456", Rol.EJECUTIVO, "Ventas", "Concepcion");
        };
    }

    private void crearSiNoExiste(UsuarioRepository repo, String nombre, String correo, String password,
                                 Rol rol, String area, String zona) {
        if (!repo.existsByCorreo(correo)) {
            repo.save(new Usuario(null, nombre, correo, password, rol, area, zona, true, System.currentTimeMillis()));
        }
    }
}
