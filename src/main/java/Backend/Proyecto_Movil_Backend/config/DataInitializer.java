package Backend.Proyecto_Movil_Backend.config;

import Backend.Proyecto_Movil_Backend.model.*;
import Backend.Proyecto_Movil_Backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Datos de demostración (100% ficticios), los mismos que precarga la app móvil.
 * Usuarios: se insertan solo los que NO existen (por correo).
 * Organizaciones, contactos, oportunidades y seguimientos: solo si la tabla de organizaciones está vacía.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatosDemo(UsuarioRepository repo,
                                      OrganizacionRepository orgRepo,
                                      ContactoRepository contactoRepo,
                                      OportunidadRepository oportunidadRepo,
                                      SeguimientoRepository seguimientoRepo) {
        return args -> {
            crearSiNoExiste(repo, "Sofía Herrera", "supervisor@ficticio.cl", "duoc123", Rol.SUPERVISOR, "Convenios Corporativos", "Santiago");
            crearSiNoExiste(repo, "Javier Muñoz", "ejecutivo1@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Santiago");
            crearSiNoExiste(repo, "Carla Rojas", "ejecutivo2@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Concepción");
            crearSiNoExiste(repo, "Andrés Paredes", "ejecutivo3@ficticio.cl", "duoc123", Rol.EJECUTIVO, "Ventas", "Puente Alto");

            crearSiNoExiste(repo, "Carlos Muñoz", "carlos.munoz@duoc.cl", "123456", Rol.SUPERVISOR, "Informatica", "Santiago");
            crearSiNoExiste(repo, "María Urrutia", "maria.urrutia@duoc.cl", "123456", Rol.EJECUTIVO, "Administracion", "Valparaiso");
            crearSiNoExiste(repo, "Antonia Saez", "antonia.saez@duoc.cl", "123456", Rol.EJECUTIVO, "Ventas", "Concepcion");

            if (orgRepo.count() == 0) {
                cargarOportunidadesDemo(repo, orgRepo, contactoRepo, oportunidadRepo, seguimientoRepo);
            }
        };
    }

    private void crearSiNoExiste(UsuarioRepository repo, String nombre, String correo, String password,
                                 Rol rol, String area, String zona) {
        if (!repo.existsByCorreo(correo)) {
            repo.save(new Usuario(null, nombre, correo, password, rol, area, zona, true, System.currentTimeMillis()));
        }
    }

    /** Epoch en ms de "hoy + nDias" a la hora indicada. */
    private static long diaEn(int nDias, int hora) {
        return LocalDateTime.now().plusDays(nDias).withHour(hora).withMinute(0).withSecond(0).withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static long diaEn(int nDias) {
        return diaEn(nDias, 9);
    }

    private void cargarOportunidadesDemo(UsuarioRepository usuarios, OrganizacionRepository orgRepo,
                                         ContactoRepository contactoRepo, OportunidadRepository opRepo,
                                         SeguimientoRepository segRepo) {
        Long ej1 = usuarios.findByCorreo("ejecutivo1@ficticio.cl").orElseThrow().getId();
        Long ej2 = usuarios.findByCorreo("ejecutivo2@ficticio.cl").orElseThrow().getId();
        Long ej3 = usuarios.findByCorreo("ejecutivo3@ficticio.cl").orElseThrow().getId();

        // ----- Organizaciones -----
        Organizacion muni = orgRepo.save(Organizacion.builder().nombre("Municipalidad de San Ficticio")
                .tipo(TipoOrganizacion.MUNICIPALIDAD).rubro("Municipal").comuna("Puente Alto").region("Metropolitana")
                .sitioWeb("https://sanficticio-ejemplo.cl").publicoAsociado("Vecinos y trabajadores municipales")
                .observaciones("Feria anual de beneficios para funcionarios.").build());
        Organizacion andina = orgRepo.save(Organizacion.builder().nombre("Empresa Andina SpA")
                .tipo(TipoOrganizacion.INSTITUCION_PRIVADA).rubro("Logística").comuna("Providencia").region("Metropolitana")
                .sitioWeb("https://andina-ejemplo.cl").publicoAsociado("Colaboradores de planta y oficina")
                .observaciones("Jornadas de bienestar semestrales.").build());
        Organizacion boreal = orgRepo.save(Organizacion.builder().nombre("Corporación Boreal")
                .tipo(TipoOrganizacion.INSTITUCION_PUBLICA).rubro("Educación").comuna("Concepción").region("Biobío")
                .sitioWeb("https://boreal-ejemplo.cl").publicoAsociado("Académicos y administrativos")
                .observaciones("Interés en convenio de salud dental.").build());
        Organizacion robles = orgRepo.save(Organizacion.builder().nombre("Centro Comercial Los Robles")
                .tipo(TipoOrganizacion.CENTRO_COMERCIAL).rubro("Retail").comuna("Temuco").region("La Araucanía")
                .sitioWeb("https://losrobles-ejemplo.cl").publicoAsociado("Visitantes y trabajadores del mall")
                .observaciones("Espacios para activaciones comerciales.").build());
        Organizacion gremial = orgRepo.save(Organizacion.builder().nombre("Asociación Gremial del Sur")
                .tipo(TipoOrganizacion.ASOCIACION).rubro("Gremial").comuna("Valdivia").region("Los Ríos")
                .sitioWeb("https://gremialsur-ejemplo.cl").publicoAsociado("Socios gremiales y familias")
                .observaciones("Eventos anuales con alta afluencia.").build());

        // ----- Contactos -----
        contactoRepo.save(Contacto.builder().organizacionId(muni.getId()).nombre("Marcela Fuentes").cargo("Coordinadora")
                .area(AreaContacto.EVENTOS).correo("m.fuentes@sanficticio-ejemplo.cl").telefono("+56 9 5555 0101")
                .medioPreferente("Correo").estado("Activo").fechaUltimoContacto(diaEn(-4)).build());
        contactoRepo.save(Contacto.builder().organizacionId(andina.getId()).nombre("Rodrigo Campos").cargo("Jefe de Bienestar")
                .area(AreaContacto.BIENESTAR).correo("r.campos@andina-ejemplo.cl").telefono("+56 9 5555 0202")
                .medioPreferente("Teléfono").estado("Activo").build());
        contactoRepo.save(Contacto.builder().organizacionId(boreal.getId()).nombre("Paula Soto").cargo("Analista RRHH")
                .area(AreaContacto.RECURSOS_HUMANOS).correo("p.soto@boreal-ejemplo.cl").telefono("+56 9 5555 0303")
                .medioPreferente("Correo").estado("Activo").build());
        contactoRepo.save(Contacto.builder().organizacionId(robles.getId()).nombre("Ignacio Vera").cargo("Gerente de Marketing")
                .area(AreaContacto.MARKETING).correo("i.vera@losrobles-ejemplo.cl").telefono("+56 9 5555 0404")
                .medioPreferente("Correo").estado("Activo").build());

        // ----- Oportunidades -----
        Oportunidad op1 = opRepo.save(Oportunidad.builder()
                .nombre("Feria de Beneficios Municipal 2026").tipo(TipoOportunidad.FERIA_INSTITUCIONAL)
                .descripcion("Feria anual de beneficios para funcionarios municipales.")
                .fuente(FuenteInformacion.SITIO_WEB).fechaDeteccion(diaEn(-15))
                .eventoNombre("Feria de Beneficios San Ficticio").eventoTipo("Feria institucional")
                .fechaInicio(diaEn(5)).fechaTermino(diaEn(6, 18)).horario("10:00 - 18:00")
                .direccion("Plaza de Armas (ficticia)").comuna("Puente Alto").region("Metropolitana")
                .organizacionId(muni.getId())
                .publicoEsperado("Trabajadores municipales y familias").publicoObjetivo("Trabajadores con cobertura de salud")
                .cantidadAsistentesEstimada(800)
                .condiciones("Stand de 3x3 incluido").requisitos("Presentación de propuesta")
                .costosReferenciales(250000.0).espaciosDisponibles("10 stands")
                .prioridad(Prioridad.ALTA).potencialComercial(4).estado(EstadoOportunidad.PROPUESTA_ENVIADA)
                .responsableId(ej1).propuestaEnviada(true).fechaPropuesta(diaEn(-9))
                .fechaUltimaActualizacion(diaEn(-9)).build());
        Oportunidad op2 = opRepo.save(Oportunidad.builder()
                .nombre("Jornada de Bienestar Andina").tipo(TipoOportunidad.JORNADA_BENEFICIOS)
                .descripcion("Jornada de bienestar para colaboradores de Empresa Andina.")
                .fuente(FuenteInformacion.CONTACTO_DIRECTO).fechaDeteccion(diaEn(-8))
                .eventoNombre("Bienestar Andina 2026").eventoTipo("Jornada de beneficios")
                .fechaInicio(diaEn(12)).fechaTermino(diaEn(12, 17)).horario("09:00 - 17:00")
                .direccion("Casa matriz (ficticia)").comuna("Providencia").region("Metropolitana")
                .organizacionId(andina.getId())
                .publicoEsperado("Colaboradores de oficina").publicoObjetivo("Colaboradores 25-50 años")
                .cantidadAsistentesEstimada(350)
                .condiciones("Stand en patio central").requisitos("Confirmación por correo")
                .costosReferenciales(120000.0).espaciosDisponibles("4 espacios")
                .prioridad(Prioridad.MEDIA).potencialComercial(3).estado(EstadoOportunidad.CONTACTO_REALIZADO)
                .responsableId(ej2).build());
        opRepo.save(Oportunidad.builder()
                .nombre("Actividad Anual Gremial del Sur").tipo(TipoOportunidad.ACTIVIDAD_TRABAJADORES)
                .descripcion("Actividad de fin de año para socios y familias.")
                .fuente(FuenteInformacion.REDES_SOCIALES).fechaDeteccion(diaEn(-3))
                .eventoNombre("Cierre de Año Gremial").eventoTipo("Actividad de trabajadores")
                .fechaInicio(diaEn(30)).fechaTermino(diaEn(30, 22)).horario("15:00 - 22:00")
                .direccion("Centro de eventos (ficticio)").comuna("Valdivia").region("Los Ríos")
                .organizacionId(gremial.getId())
                .publicoEsperado("Socios y familias").publicoObjetivo("Familias")
                .cantidadAsistentesEstimada(500)
                .condiciones("Por definir").requisitos("Por definir")
                .costosReferenciales(0.0).espaciosDisponibles("Por definir")
                .prioridad(Prioridad.BAJA).potencialComercial(2).estado(EstadoOportunidad.EVENTO_IDENTIFICADO)
                .responsableId(ej3).build());
        opRepo.save(Oportunidad.builder()
                .nombre("Activación Los Robles Primavera").tipo(TipoOportunidad.ACTIVACION_COMERCIAL)
                .descripcion("Activación comercial en centro comercial.")
                .fuente(FuenteInformacion.REFERENCIA).fechaDeteccion(diaEn(-40))
                .eventoNombre("Primavera en Los Robles").eventoTipo("Activación comercial")
                .fechaInicio(diaEn(-10)).fechaTermino(diaEn(-10, 20)).horario("11:00 - 20:00")
                .direccion("Patio central (ficticio)").comuna("Temuco").region("La Araucanía")
                .organizacionId(robles.getId())
                .publicoEsperado("Visitantes del mall").publicoObjetivo("Público general")
                .cantidadAsistentesEstimada(1200)
                .condiciones("Arriendo de espacio").requisitos("Contrato de arriendo ficticio")
                .costosReferenciales(400000.0).espaciosDisponibles("1 patio central")
                .prioridad(Prioridad.CRITICA).potencialComercial(5).estado(EstadoOportunidad.ACTIVACION_REALIZADA)
                .responsableId(ej1)
                .resEstadoFinal("Exitosa").resFechaRealizacion(diaEn(-10)).resAsistentes(1100)
                .resContactosGenerados(240).resPersonasInteresadas(95).resInscripciones(42)
                .resObservaciones("Alta afluencia en horario de tarde.").resEvaluacion(5)
                .resAprendizajes("Contratar segundo ejecutivo de stand.").build());

        // ----- Seguimientos -----
        segRepo.save(Seguimiento.builder().oportunidadId(op1.getId()).usuarioId(ej1)
                .tipoAccion(TipoAccion.PRIMER_CONTACTO).fechaHora(diaEn(-12))
                .resultado("Se coordinó llamada con coordinadora de eventos.")
                .proximaAccion("Enviar propuesta formal").fechaProximo(diaEn(-9)).build());
        segRepo.save(Seguimiento.builder().oportunidadId(op1.getId()).usuarioId(ej1)
                .tipoAccion(TipoAccion.ENVIO_PROPUESTA).fechaHora(diaEn(-9))
                .resultado("Propuesta enviada al correo ficticio de la organización.")
                .proximaAccion("Esperar respuesta y hacer seguimiento").fechaProximo(diaEn(3)).build());
        segRepo.save(Seguimiento.builder().oportunidadId(op2.getId()).usuarioId(ej2)
                .tipoAccion(TipoAccion.REUNION).fechaHora(diaEn(-5))
                .resultado("Reunión con jefe de bienestar, interés confirmado.")
                .proximaAccion("Solicitar antecedentes formales").fechaProximo(diaEn(-1)).build());
    }
}
