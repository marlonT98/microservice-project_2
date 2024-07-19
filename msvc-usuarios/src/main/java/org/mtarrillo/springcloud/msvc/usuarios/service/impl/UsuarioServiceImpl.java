package org.mtarrillo.springcloud.msvc.usuarios.service.impl;

import org.mtarrillo.springcloud.msvc.usuarios.models.entity.Usuario;
import org.mtarrillo.springcloud.msvc.usuarios.repository.UsuarioRepository;
import org.mtarrillo.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl  implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existsPorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
