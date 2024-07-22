package org.tmarlon.springcloud.msvc.cursos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;
import org.tmarlon.springcloud.msvc.cursos.repository.CursoRepository;
import org.tmarlon.springcloud.msvc.cursos.service.CursoService;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Override
    @Transactional(readOnly = true)//le estamos diciendo que es de lectura
    public List<Curso> findAll() {
        //tenemos que hacer el cast
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)//le estamos diciendo que es de lectura
    public Optional<Curso> findById( Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return repository.save( curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        repository.deleteById( id);

    }
}
