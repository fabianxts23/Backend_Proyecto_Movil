# Backend_Proyecto_Movil

API REST (Spring Boot 4, Java 21, MySQL) del proyecto móvil **Oportunidades Corporativas**.

## Requisitos
- JDK 21, MySQL en `localhost:3306` (usuario `root`, sin clave; cámbialo en `application.properties`).
- La base `bd_Movil` se crea sola al arrancar.

## Ejecutar
```
./mvnw spring-boot:run
```
Swagger: http://localhost:8080/swagger-ui/index.html

## Tablas MySQL (se crean solas con `ddl-auto=update`)
| Tabla | Equivale en la app (Room) | Relaciones |
|---|---|---|
| `usuarios` | UsuarioEntity | — |
| `organizaciones` | OrganizacionEntity | — |
| `contactos` | ContactoEntity | → organizaciones (CASCADE) |
| `oportunidades` | OportunidadEntity | → organizaciones (RESTRICT), → usuarios `responsable_id` (RESTRICT) |
| `seguimientos` | SeguimientoEntity | → oportunidades (CASCADE), → usuarios (RESTRICT) |
| `evidencias_foto` | EvidenciaFotoEntity | → oportunidades (CASCADE) |

Los enums se guardan como texto (`EnumType.STRING`) y las fechas como epoch en milisegundos (`Long`), igual que `fechaCreacion` de usuarios.
El JSON usa los mismos nombres de campo que la app (`organizacionId`, `responsableId`, `resAsistentes`, ...).

## Endpoints
Todos bajo `/v1/api/`. Errores: 400 validación / FK inexistente, 404 no existe, 409 conflicto.

| Recurso | Operaciones |
|---|---|
| `usuarios` | GET, GET `/{id}`, POST `/login` `{correo, password}` (200/401/403), POST, PUT `/{id}`, DELETE `/{id}` (409 si tiene oportunidades o seguimientos) |
| `organizaciones` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` (409 si tiene oportunidades; borra sus contactos) |
| `contactos` | GET `?organizacionId=`, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| `oportunidades` | GET `?estado=&responsableId=&organizacionId=`, GET `/{id}`, POST, PUT `/{id}` (409 si está cerrada y se cambia el estado), DELETE `/{id}` (borra seguimientos y evidencias) |
| `seguimientos` | GET `?oportunidadId=`, GET `/{id}`, POST (historial: no se edita ni se borra) |
| `evidencias` | GET `?oportunidadId=` (obligatorio), GET `/{id}`, POST, DELETE `/{id}` — guarda solo la `uri` de la foto |

La contraseña de usuarios se acepta al escribir pero nunca se devuelve en las respuestas.

## Conexión con la app Android
- Emulador: la app usa `http://10.0.2.2:8080/` (10.0.2.2 = tu PC visto desde el emulador).
- Celular físico: cambia `BASE_URL` en `RetrofitUsuario.kt` por la IP de tu PC (ej. `http://192.168.1.50:8080/`)
  y agrega esa IP en `res/xml/network_security_config.xml`.
- Cuentas de prueba (las mismas de la app): `supervisor@ficticio.cl` / `duoc123`, `ejecutivo1@ficticio.cl` / `duoc123`.
