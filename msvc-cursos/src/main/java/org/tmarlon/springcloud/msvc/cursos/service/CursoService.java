package org.tmarlon.springcloud.msvc.cursos.service;

import org.tmarlon.springcloud.msvc.cursos.models.Usuario;
import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();
    Optional<Curso> findById( Long id);
    Curso save( Curso curso );
    void delete( Long id );

    //methodos remotos : que tendran una comunicacion con el otro microservicio
    Optional<Usuario> asignarUsuario( Usuario usuario , Long cursoId );
    Optional<Usuario> crearUsuario( Usuario usuario , Long cursoId );
    Optional<Usuario> eliminarUsuario( Usuario usuario , Long cursoId );



}
