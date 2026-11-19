package Backend.Proyecto_Movil_Backend.controller;

import Backend.Proyecto_Movil_Backend.model.EvidenciaFoto;
import Backend.Proyecto_Movil_Backend.service.EvidenciaFotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Base: http://localhost:8080/v1/api/evidencias */
@RestController
@RequestMapping("/v1/api/evidencias")
public class EvidenciaFotoController {

    @Autowired
    private EvidenciaFotoService evidenciaService;

    // GET /v1/api/evidencias?oportunidadId=1
    @GetMapping
    public ResponseEntity<List<EvidenciaFoto>> listarPorOportunidad(@RequestParam Long oportunidadId) {
        return ResponseEntity.ok(evidenciaService.listarPorOportunidad(oportunidadId));
    }

    // GET /v1/api/evidencias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EvidenciaFoto> obtenerPorId(@PathVariable Long id) {
        return evidenciaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /v1/api/evidencias -> 201 | 400
    @PostMapping
    public ResponseEntity<EvidenciaFoto> crear(@Valid @RequestBody EvidenciaFoto evidencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evidenciaService.guardar(evidencia));
    }

    // DELETE /v1/api/evidencias/{id} -> 204 | 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        evidenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
