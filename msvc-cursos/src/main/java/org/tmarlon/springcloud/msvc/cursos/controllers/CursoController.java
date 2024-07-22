package org.tmarlon.springcloud.msvc.cursos.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tmarlon.springcloud.msvc.cursos.models.entity.Curso;
import org.tmarlon.springcloud.msvc.cursos.service.CursoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso , BindingResult bindingResult) {

        if ( bindingResult.hasErrors() ){
            return  validar( bindingResult);
        }

        Curso cursoDb = service.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar( @Valid @RequestBody Curso curso,BindingResult bindingResult , @PathVariable Long id) {

        if ( bindingResult.hasErrors() ){
            return  validar( bindingResult);
        }

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
    //metodo para validar
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map< String , String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put( err.getField() , "El campo "+err.getField()+" "+err.getDefaultMessage() );
        } );
        return ResponseEntity.badRequest().body(errores);
    }




}
