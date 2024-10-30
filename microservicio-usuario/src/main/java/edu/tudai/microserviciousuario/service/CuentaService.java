package edu.tudai.microserviciousuario.service;

import edu.tudai.microserviciousuario.entity.Cuenta;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.repository.CuentaRepository;
import edu.tudai.microserviciousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta update(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    /****************************************************************/

    public void anularCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setActiva(false);
        cuentaRepository.save(cuenta);
    }
}
