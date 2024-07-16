package org.tmarlon.springcloud.msvc.cursos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tmarlon.springcloud.msvc.cursos.entity.Curso;
import org.tmarlon.springcloud.msvc.cursos.service.CursoService;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController {


    @Autowired
    private CursoService service;


    @GetMapping
    public ResponseEntity<List<Curso>> listar() {

        return ResponseEntity.ok(service.findAll());


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.findById(id);

        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.get());
        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody Curso curso) {

        Curso cursoDb = service.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {

        //buscamos el curso a modificar
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {//si esta presente

            Curso cursoDB = cursoOptional.get();//pasamos el curso de la bdd
            //actualizamos
            cursoDB.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));

        }

        return ResponseEntity.notFound().build();


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()) {
            service.delete(cursoOptional.get().getId());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


}
