package Backend.Proyecto_Movil_Backend.service;

import Backend.Proyecto_Movil_Backend.model.Usuario;
import Backend.Proyecto_Movil_Backend.repository.OportunidadRepository;
import Backend.Proyecto_Movil_Backend.repository.SeguimientoRepository;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OportunidadRepository oportunidadRepository;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    private static String normalizar(String correo) {
        return correo == null ? null : correo.trim().toLowerCase();
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /** Crea un usuario. Correo único (409) y contraseña obligatoria (400). */
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setId(null); // evita que un POST con "id" termine actualizando otro usuario
        usuario.setCorreo(normalizar(usuario.getCorreo()));
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria.");
        }
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya se encuentra registrado.");
        }
        // Mejora futura: encriptar la contraseña con BCrypt antes de guardar.
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario detalles) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con el ID: " + id));

        String correo = normalizar(detalles.getCorreo());
        // El correo nuevo no puede pertenecer a OTRO usuario
        usuarioRepository.findByCorreo(correo).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "El correo electrónico ya se encuentra registrado.");
            }
        });

        usuario.setNombre(detalles.getNombre());
        usuario.setCorreo(correo);
        usuario.setRol(detalles.getRol());
        usuario.setArea(detalles.getArea());
        usuario.setZona(detalles.getZona());
        usuario.setCuentaActiva(detalles.isCuentaActiva());

        // La contraseña solo cambia si se envía una nueva
        if (detalles.getPassword() != null && !detalles.getPassword().isBlank()) {
            usuario.setPassword(detalles.getPassword());
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar, el usuario no existe.");
        }
        // FK RESTRICT: un usuario con oportunidades o seguimientos no se puede borrar (se desactiva)
        if (oportunidadRepository.existsByResponsableId(id) || seguimientoRepository.existsByUsuarioId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: el usuario tiene oportunidades o seguimientos asociados. Desactive la cuenta.");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Valida credenciales. 401 si son incorrectas, 403 si la cuenta está inactiva.
     * El mismo 401 para "correo no existe" y "clave incorrecta" evita revelar qué correos existen.
     */
    public Usuario login(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(normalizar(correo))
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Credenciales incorrectas."));
        if (!usuario.isCuentaActiva()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La cuenta está inactiva.");
        }
        return usuario;
    }
}
