package Backend.Proyecto_Movil_Backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Rol rol;

    @Column(length = 50)
    private String area;

    @Column(length = 50)
    private String zona;

    @Column(name = "cuenta_activa", nullable = false)
    private boolean cuentaActiva = true;

    @Column(name = "fecha_creacion", nullable = false)
    private Long fechaCreacion = System.currentTimeMillis();
}
