package Backend.Proyecto_Movil_Backend.controller;

import Backend.Proyecto_Movil_Backend.model.Contacto;
import Backend.Proyecto_Movil_Backend.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Base: http://localhost:8080/v1/api/contactos */
@RestController
@RequestMapping("/v1/api/contactos")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    // GET /v1/api/contactos            -> todos
    // GET /v1/api/contactos?organizacionId=1 -> los de una organización
    @GetMapping
    public ResponseEntity<List<Contacto>> listar(@RequestParam(required = false) Long organizacionId) {
        return ResponseEntity.ok(organizacionId != null
                ? contactoService.listarPorOrganizacion(organizacionId)
                : contactoService.listarTodos());
    }

    // GET /v1/api/contactos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /v1/api/contactos -> 201 | 400
    @PostMapping
    public ResponseEntity<Contacto> crear(@Valid @RequestBody Contacto contacto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactoService.guardar(contacto));
    }

    // PUT /v1/api/contactos/{id} -> 200 | 400 | 404
    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long id, @Valid @RequestBody Contacto contacto) {
        return ResponseEntity.ok(contactoService.actualizar(id, contacto));
    }

    // DELETE /v1/api/contactos/{id} -> 204 | 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
