package Backend.Proyecto_Movil_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** Evidencia fotográfica. Guarda solo la referencia (uri) a la foto; el archivo no se sube a MySQL. */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evidencias_foto")
public class EvidenciaFoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
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

    @NotBlank(message = "La uri de la foto es obligatoria")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String uri;

    @Builder.Default
    @Column(nullable = false)
    private Long fecha = System.currentTimeMillis();
}
