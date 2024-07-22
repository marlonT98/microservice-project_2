package org.tmarlon.springcloud.msvc.cursos.repository;

import org.springframework.data.repository.CrudRepository;
import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;

public interface CursoRepository extends CrudRepository<Curso , Long> {
}
