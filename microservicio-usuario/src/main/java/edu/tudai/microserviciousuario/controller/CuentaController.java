package edu.tudai.microserviciousuario.controller;

import edu.tudai.microserviciousuario.entity.Cuenta;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        List<Cuenta> cuentas = cuentaService.findAll();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable("id") Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaCreated = cuentaService.save(cuenta);
        if (cuentaCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuentaCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        cuentaService.delete(id);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable("id") Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaExistente = cuentaService.findById(id);

        if (cuentaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        cuentaExistente.setFechaAlta(cuenta.getFechaAlta());
        cuentaExistente.setSaldo(cuenta.getSaldo());

        Cuenta cuentaUpdated = cuentaService.update(cuentaExistente);

        return ResponseEntity.ok(cuentaUpdated);
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<Cuenta>  anularCuenta(@PathVariable("id") long id) {

        Cuenta cuenta = cuentaService.findById(id);

        cuentaService.anularCuenta(cuenta);

        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/activarUsuario/{id}")
    public ResponseEntity<Cuenta> activarUsuarioEnCuenta(@PathVariable("id") Long id, @RequestParam Long usuarioId) {
        Cuenta cuenta = cuentaService.activarUsuarioEnCuenta(id, usuarioId);
        if (cuenta == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuenta);
    }


}
