package Backend.Proyecto_Movil_Backend.config;

import Backend.Proyecto_Movil_Backend.model.*; // Asegúrate de importar tu enum Rol
import Backend.Proyecto_Movil_Backend.model.Usuario;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatosDemo(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                // Usamos el constructor o los setters para asignar todos los campos obligatorios de la entidad actual
                Usuario usuario1 = new Usuario(
                        null,
                        "Carlos Muñoz",           // nombre
                        "carlos.munoz@duoc.cl",   // correo (obligatorio y único)
                        "123456",                 // password
                        Rol.SUPERVISOR,                // rol (Asumiendo que Rol es un Enum, ej: ADMIN o USER)
                        "Informatica",            // area
                        "Santiago",               // zona
                        true,                     // cuentaActiva
                        System.currentTimeMillis() // fechaCreacion
                );

                Usuario usuario2 = new Usuario(
                        null,
                        "María Urrutia",
                        "maria.urrutia@duoc.cl",
                        "123456",
                        Rol.EJECUTIVO,
                        "Administracion",
                        "Valparaiso",
                        true,
                        System.currentTimeMillis()
                );

                Usuario usuario3 = new Usuario(
                        null,
                        "Antonia Saez",
                        "antonia.saez@duoc.cl",
                        "123456",
                        Rol.EJECUTIVO,
                        "Ventas",
                        "Concepcion",
                        true,
                        System.currentTimeMillis()
                );

                usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3));
            }
        };
    }
}