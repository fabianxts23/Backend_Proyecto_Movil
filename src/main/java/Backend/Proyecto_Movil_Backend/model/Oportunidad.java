package Backend.Proyecto_Movil_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oportunidades", indexes = {
        @Index(name = "idx_oportunidad_estado", columnList = "estado"),
        @Index(name = "idx_oportunidad_fecha_inicio", columnList = "fecha_inicio")
})
public class Oportunidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oportunidad")
    private Long id;

    // ---------- Oportunidad ----------
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nombre;

    @NotNull(message = "El tipo de oportunidad es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoOportunidad tipo;

    @Size(max = 1000)
    @Column(length = 1000)
    private String descripcion;

    @NotNull(message = "La fuente de información es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private FuenteInformacion fuente;

    @NotNull(message = "La fecha de detección es obligatoria")
    @Column(name = "fecha_deteccion", nullable = false)
    private Long fechaDeteccion;

    // ---------- Evento asociado ----------
    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(max = 150)
    @Column(name = "evento_nombre", nullable = false, length = 150)
    private String eventoNombre;

    @Size(max = 80)
    @Column(name = "evento_tipo", length = 80)
    private String eventoTipo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private Long fechaInicio;

    @NotNull(message = "La fecha de término es obligatoria")
    @Column(name = "fecha_termino", nullable = false)
    private Long fechaTermino;

    @Size(max = 30)
    @Column(length = 30)
    private String horario;

    @Size(max = 200)
    @Column(length = 200)
    private String direccion;

    @Size(max = 80)
    @Column(length = 80)
    private String comuna;

    @Size(max = 80)
    @Column(length = 80)
    private String region;

    /** FK a organizaciones (ON DELETE RESTRICT). */
    @NotNull(message = "La organización es obligatoria")
    @Column(name = "organizacion_id", nullable = false)
    private Long organizacionId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", insertable = false, updatable = false)
    private Organizacion organizacion;

    // ---------- Público y evaluación ----------
    @Size(max = 200)
    @Column(name = "publico_esperado", length = 200)
    private String publicoEsperado;

    @Size(max = 200)
    @Column(name = "publico_objetivo", length = 200)
    private String publicoObjetivo;

    @PositiveOrZero(message = "La cantidad de asistentes no puede ser negativa")
    @Column(name = "cantidad_asistentes_estimada", nullable = false)
    private int cantidadAsistentesEstimada;

    // ---------- Participación ----------
    @Size(max = 500)
    @Column(length = 500)
    private String condiciones;

    @Size(max = 500)
    @Column(length = 500)
    private String requisitos;

    @PositiveOrZero(message = "El costo no puede ser negativo")
    @Column(name = "costos_referenciales", nullable = false)
    private double costosReferenciales;

    @Size(max = 200)
    @Column(name = "espacios_disponibles", length = 200)
    private String espaciosDisponibles;

    // ---------- Gestión ----------
    @NotNull(message = "La prioridad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridad prioridad;

    @Min(value = 1, message = "El potencial comercial debe estar entre 1 y 5")
    @Max(value = 5, message = "El potencial comercial debe estar entre 1 y 5")
    @Column(name = "potencial_comercial", nullable = false)
    private int potencialComercial;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOportunidad estado;

    /** FK a usuarios (ON DELETE RESTRICT). */
    @NotNull(message = "El responsable es obligatorio")
    @Column(name = "responsable_id", nullable = false)
    private Long responsableId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id", insertable = false, updatable = false)
    private Usuario responsable;

    @Builder.Default
    @Column(name = "fecha_ultima_actualizacion", nullable = false)
    private Long fechaUltimaActualizacion = System.currentTimeMillis();

    // ---------- Propuesta ----------
    @Builder.Default
    @Column(name = "propuesta_enviada", nullable = false)
    private boolean propuestaEnviada = false;

    @Column(name = "fecha_propuesta")
    private Long fechaPropuesta;

    // ---------- Resultado de activación (nulo si aún no se realiza) ----------
    @Size(max = 50)
    @Column(name = "res_estado_final", length = 50)
    private String resEstadoFinal;

    @Column(name = "res_fecha_realizacion")
    private Long resFechaRealizacion;

    @PositiveOrZero
    @Column(name = "res_asistentes")
    private Integer resAsistentes;

    @PositiveOrZero
    @Column(name = "res_contactos_generados")
    private Integer resContactosGenerados;

    @PositiveOrZero
    @Column(name = "res_personas_interesadas")
    private Integer resPersonasInteresadas;

    @PositiveOrZero
    @Column(name = "res_inscripciones")
    private Integer resInscripciones;

    @Size(max = 1000)
    @Column(name = "res_observaciones", length = 1000)
    private String resObservaciones;

    @Min(1)
    @Max(5)
    @Column(name = "res_evaluacion")
    private Integer resEvaluacion;

    @Size(max = 1000)
    @Column(name = "res_aprendizajes", length = 1000)
    private String resAprendizajes;
}
