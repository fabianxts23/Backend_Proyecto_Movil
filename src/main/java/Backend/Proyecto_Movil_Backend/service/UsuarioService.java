package Backend.Proyecto_Movil_Backend.service;
import Backend.Proyecto_Movil_Backend.model.Usuario;
import Backend.Proyecto_Movil_Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los usuarios
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Guardar un nuevo usuario con validación de correo único
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado.");
        }

        // Nota: Aquí en el futuro puedes encriptar la contraseña usando BCrypt antes de guardar
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario actualizarUsuario(Long id, Usuario usuarioDetalles) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));

        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setCorreo(usuarioDetalles.getCorreo());
        usuario.setRol(usuarioDetalles.getRol());
        usuario.setArea(usuarioDetalles.getArea());
        usuario.setZona(usuarioDetalles.getZona());
        usuario.setCuentaActiva(usuarioDetalles.isCuentaActiva());

        // Actualizar contraseña solo si se proporciona una nueva
        if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()) {
            usuario.setPassword(usuarioDetalles.getPassword());
        }

        return usuarioRepository.save(usuario);
    }

    // Eliminar un usuario
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, el usuario no existe.");
        }
        usuarioRepository.deleteById(id);
    }
}