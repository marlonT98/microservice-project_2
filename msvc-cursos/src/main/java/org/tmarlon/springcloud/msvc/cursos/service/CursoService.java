package org.tmarlon.springcloud.msvc.cursos.service;

import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();
    Optional<Curso> findById( Long id);
    Curso save( Curso curso );
    void delete( Long id );

}
