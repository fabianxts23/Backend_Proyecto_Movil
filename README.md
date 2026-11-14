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

## Endpoints (`/v1/api/usuarios`)
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/v1/api/usuarios` | Lista usuarios |
| GET | `/v1/api/usuarios/{id}` | Un usuario |
| POST | `/v1/api/usuarios/login` | `{correo, password}` → 200 / 401 / 403 |
| POST | `/v1/api/usuarios` | Crea (201, 400, 409 correo repetido) |
| PUT | `/v1/api/usuarios/{id}` | Actualiza |
| DELETE | `/v1/api/usuarios/{id}` | Elimina |

La contraseña se acepta al escribir pero nunca se devuelve en las respuestas.

## Conexión con la app Android
- Emulador: la app usa `http://10.0.2.2:8080/` (10.0.2.2 = tu PC visto desde el emulador).
- Celular físico: cambia `BASE_URL` en `RetrofitUsuario.kt` por la IP de tu PC (ej. `http://192.168.1.50:8080/`)
  y agrega esa IP en `res/xml/network_security_config.xml`.
- Cuentas de prueba (las mismas de la app): `supervisor@ficticio.cl` / `duoc123`, `ejecutivo1@ficticio.cl` / `duoc123`.
