package Backend.Proyecto_Movil_Backend.controller;

import Backend.Proyecto_Movil_Backend.model.Seguimiento;
import Backend.Proyecto_Movil_Backend.service.SeguimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Base: http://localhost:8080/v1/api/seguimientos (historial: solo crear y consultar) */
@RestController
@RequestMapping("/v1/api/seguimientos")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    // GET /v1/api/seguimientos[?oportunidadId=1]
    @GetMapping
    public ResponseEntity<List<Seguimiento>> listar(@RequestParam(required = false) Long oportunidadId) {
        return ResponseEntity.ok(oportunidadId != null
                ? seguimientoService.listarPorOportunidad(oportunidadId)
                : seguimientoService.listarTodos());
    }

    // GET /v1/api/seguimientos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> obtenerPorId(@PathVariable Long id) {
        return seguimientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /v1/api/seguimientos -> 201 | 400
    @PostMapping
    public ResponseEntity<Seguimiento> crear(@Valid @RequestBody Seguimiento seguimiento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.guardar(seguimiento));
    }
}
