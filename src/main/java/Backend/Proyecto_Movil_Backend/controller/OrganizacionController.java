package Backend.Proyecto_Movil_Backend.controller;

import Backend.Proyecto_Movil_Backend.model.Organizacion;
import Backend.Proyecto_Movil_Backend.service.OrganizacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Base: http://localhost:8080/v1/api/organizaciones */
@RestController
@RequestMapping("/v1/api/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    // GET /v1/api/organizaciones
    @GetMapping
    public ResponseEntity<List<Organizacion>> listarTodas() {
        return ResponseEntity.ok(organizacionService.listarTodas());
    }

    // GET /v1/api/organizaciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Organizacion> obtenerPorId(@PathVariable Long id) {
        return organizacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /v1/api/organizaciones -> 201 | 400
    @PostMapping
    public ResponseEntity<Organizacion> crear(@Valid @RequestBody Organizacion organizacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizacionService.guardar(organizacion));
    }

    // PUT /v1/api/organizaciones/{id} -> 200 | 400 | 404
    @PutMapping("/{id}")
    public ResponseEntity<Organizacion> actualizar(@PathVariable Long id, @Valid @RequestBody Organizacion organizacion) {
        return ResponseEntity.ok(organizacionService.actualizar(id, organizacion));
    }

    // DELETE /v1/api/organizaciones/{id} -> 204 | 404 | 409 (tiene oportunidades)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        organizacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
