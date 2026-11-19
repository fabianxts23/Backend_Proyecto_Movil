package Backend.Proyecto_Movil_Backend.model;

/** Los estados finales solo los puede fijar un SUPERVISOR (regla que aplica la app móvil). */
public enum EstadoOportunidad {
    EVENTO_IDENTIFICADO(false), CONTACTO_PENDIENTE(false), CONTACTO_REALIZADO(false),
    PROPUESTA_ENVIADA(false), EN_ESPERA_RESPUESTA(false), EN_EVALUACION(false),
    INFORMACION_INCOMPLETA(false), REQUIERE_SEGUIMIENTO(false),
    PARTICIPACION_CONFIRMADA(true), OPORTUNIDAD_DESCARTADA(true),
    ACTIVACION_REALIZADA(true), ACTIVACION_CANCELADA(true);

    private final boolean esFinal;

    EstadoOportunidad(boolean esFinal) { this.esFinal = esFinal; }

    public boolean esFinal() { return esFinal; }
}
