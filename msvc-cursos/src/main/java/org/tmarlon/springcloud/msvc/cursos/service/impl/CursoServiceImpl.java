package org.tmarlon.springcloud.msvc.cursos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmarlon.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.tmarlon.springcloud.msvc.cursos.models.Usuario;
import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;
import org.tmarlon.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.tmarlon.springcloud.msvc.cursos.repository.CursoRepository;
import org.tmarlon.springcloud.msvc.cursos.service.CursoService;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)//le estamos diciendo que es de lectura
    public List<Curso> findAll() {
        //tenemos que hacer el cast
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)//le estamos diciendo que es de lectura
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);

    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

        //buscamos el curso por su id
        Optional<Curso> o = repository.findById(cursoId);
        //si esta presenta lo manejamos
        if (o.isPresent()) {
            //obtenemos el usuario del microservicio usuarios
            Usuario usuarioMscv = client.findById(usuario.getId());

            //obtenemos el curso del optionel de arriba
            Curso curso = o.get();
            //creamos una instancia de nuestra entidad relacional (relaciona el curso y el usuario)
            CursoUsuario cursoUsuario = new CursoUsuario();
            //pasamos el id del usuario
            cursoUsuario.setUsuarioId(usuarioMscv.getId());

            //asignar al curso
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);

            return Optional.of(usuarioMscv);


        }

        return Optional.empty();//si no esta presente enviamos un optional vacio
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {

        //buscamos el curso por su id
        Optional<Curso> o = repository.findById(cursoId);
        //si esta presenta lo manejamos
        if (o.isPresent()) {
            //creamos un usuario ->pero nos devuelve el nuevo usuario creado
            Usuario usuarioNuevoMscv = client.save(usuario);

            //obtenemos el curso del optionel de arriba
            Curso curso = o.get();
            //creamos una instancia de nuestra entidad relacional (relaciona el curso y el usuario)
            CursoUsuario cursoUsuario = new CursoUsuario();
            //pasamos el id del usuario
            cursoUsuario.setUsuarioId(usuarioNuevoMscv.getId());

            //asignar al curso
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);

            return Optional.of(usuarioNuevoMscv);


        }

        return Optional.empty();//si no esta presente enviamos un optional vacio

    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        //buscamos el curso por su id
        Optional<Curso> o = repository.findById(cursoId);
        //si esta presenta lo manejamos
        if (o.isPresent()) {
            //obtenemos el usuario del microservicio usuarios
            Usuario usuarioMscv = client.findById(usuario.getId());

            //obtenemos el curso del optional de arriba
            Curso curso = o.get();
            //creamos una instancia de nuestra entidad relacional (relaciona el curso y el usuario)
            CursoUsuario cursoUsuario = new CursoUsuario();
            //pasamos el id del usuario
            cursoUsuario.setUsuarioId(usuarioMscv.getId());

            //asignar al curso
            curso.deleteCursoUsuario(cursoUsuario);
            repository.save(curso);

            return Optional.of(usuarioMscv);


        }

        return Optional.empty();//si no esta presente enviamos un optional vacio


    }


}
