package Backend.Proyecto_Movil_Backend.controller;

import Backend.Proyecto_Movil_Backend.model.EstadoOportunidad;
import Backend.Proyecto_Movil_Backend.model.Oportunidad;
import Backend.Proyecto_Movil_Backend.service.OportunidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Base: http://localhost:8080/v1/api/oportunidades */
@RestController
@RequestMapping("/v1/api/oportunidades")
public class OportunidadController {

    @Autowired
    private OportunidadService oportunidadService;

    // GET /v1/api/oportunidades[?estado=PROPUESTA_ENVIADA | ?responsableId=2 | ?organizacionId=1]
    @GetMapping
    public ResponseEntity<List<Oportunidad>> listar(
            @RequestParam(required = false) EstadoOportunidad estado,
            @RequestParam(required = false) Long responsableId,
            @RequestParam(required = false) Long organizacionId) {
        return ResponseEntity.ok(oportunidadService.listar(estado, responsableId, organizacionId));
    }

    // GET /v1/api/oportunidades/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Oportunidad> obtenerPorId(@PathVariable Long id) {
        return oportunidadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /v1/api/oportunidades -> 201 | 400
    @PostMapping
    public ResponseEntity<Oportunidad> crear(@Valid @RequestBody Oportunidad oportunidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(oportunidadService.guardar(oportunidad));
    }

    // PUT /v1/api/oportunidades/{id} -> 200 | 400 | 404 | 409 (oportunidad cerrada)
    @PutMapping("/{id}")
    public ResponseEntity<Oportunidad> actualizar(@PathVariable Long id, @Valid @RequestBody Oportunidad oportunidad) {
        return ResponseEntity.ok(oportunidadService.actualizar(id, oportunidad));
    }

    // DELETE /v1/api/oportunidades/{id} -> 204 | 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        oportunidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
