package Backend.Proyecto_Movil_Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizaciones")
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizacion")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El tipo de organización es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoOrganizacion tipo;

    @Size(max = 100)
    @Column(length = 100)
    private String rubro;

    @Size(max = 80)
    @Column(length = 80)
    private String comuna;

    @Size(max = 80)
    @Column(length = 80)
    private String region;

    @Size(max = 200)
    @Column(name = "sitio_web", length = 200)
    private String sitioWeb;

    @Size(max = 200)
    @Column(name = "publico_asociado", length = 200)
    private String publicoAsociado;

    @Size(max = 1000)
    @Column(length = 1000)
    private String observaciones;
}
