package Backend.Proyecto_Movil_Backend.dto;

import jakarta.validation.constraints.NotBlank;

/** Cuerpo de POST /v1/api/usuarios/login */
public record LoginRequest(
        @NotBlank(message = "El correo es obligatorio") String correo,
        @NotBlank(message = "La contraseña es obligatoria") String password
) {
}
