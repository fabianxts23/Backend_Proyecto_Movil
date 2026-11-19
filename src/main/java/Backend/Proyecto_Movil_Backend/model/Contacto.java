package Backend.Proyecto_Movil_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Long id;

    /** FK a organizaciones (ON DELETE CASCADE: al borrar la organización se borran sus contactos). */
    @NotNull(message = "La organización es obligatoria")
    @Column(name = "organizacion_id", nullable = false)
    private Long organizacionId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organizacion organizacion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 100)
    @Column(length = 100)
    private String cargo;

    @NotNull(message = "El área es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AreaContacto area;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String correo;

    @Size(max = 30)
    @Column(length = 30)
    private String telefono;

    @Size(max = 30)
    @Column(name = "medio_preferente", length = 30)
    private String medioPreferente;

    @Size(max = 30)
    @Column(length = 30)
    private String estado;

    @Column(name = "fecha_ultimo_contacto")
    private Long fechaUltimoContacto;
}
