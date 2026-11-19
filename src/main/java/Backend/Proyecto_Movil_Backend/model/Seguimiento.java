package Backend.Proyecto_Movil_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** Historial de gestiones de una oportunidad. Se crea y se consulta; no se edita ni se borra. */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seguimientos", indexes = {
        @Index(name = "idx_seguimiento_fecha_proximo", columnList = "fecha_proximo")
})
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long id;

    /** FK a oportunidades (ON DELETE CASCADE). */
    @NotNull(message = "La oportunidad es obligatoria")
    @Column(name = "oportunidad_id", nullable = false)
    private Long oportunidadId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oportunidad_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Oportunidad oportunidad;

    /** FK a usuarios (ON DELETE RESTRICT). */
    @NotNull(message = "El usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    @NotNull(message = "El tipo de acción es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_accion", nullable = false, length = 30)
    private TipoAccion tipoAccion;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Column(name = "fecha_hora", nullable = false)
    private Long fechaHora;

    @Size(max = 1000)
    @Column(length = 1000)
    private String resultado;

    @Size(max = 500)
    @Column(name = "proxima_accion", length = 500)
    private String proximaAccion;

    @NotNull(message = "La fecha de la próxima acción es obligatoria")
    @Column(name = "fecha_proximo", nullable = false)
    private Long fechaProximo;
}
