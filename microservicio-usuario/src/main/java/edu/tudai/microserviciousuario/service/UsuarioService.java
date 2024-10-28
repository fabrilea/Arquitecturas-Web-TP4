package edu.tudai.microserviciousuario.service;

import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
