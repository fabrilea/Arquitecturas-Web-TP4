package edu.tudai.microserviciousuario.controller;

import edu.tudai.microserviciousuario.dto.MonopatinDTO;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCreated = usuarioService.save(usuario);
        if (usuarioCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(usuarioCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.findById(id);

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setCelular(usuario.getCelular());
        usuarioExistente.setEmail(usuario.getEmail());

        Usuario usuarioUpdated = usuarioService.update(usuarioExistente);

        return ResponseEntity.ok(usuarioUpdated);
    }

    /*******************************************************************/

    @GetMapping("/monopatin/cercanos")
    public ResponseEntity<List<MonopatinDTO>> obtenerMonopatinesCercanos(@RequestParam double latitud, @RequestParam double longitud, @RequestParam double radio){
        List<MonopatinDTO> monopatines = usuarioService.obtenerMonopatinesCercanos(latitud, longitud, radio);
        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

}
