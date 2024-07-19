package org.mtarrillo.springcloud.msvc.usuarios.controller;

import jakarta.validation.Valid;
import org.mtarrillo.springcloud.msvc.usuarios.models.entity.Usuario;
import org.mtarrillo.springcloud.msvc.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;//siempre trabajamos con la interface lo mas generico posible


    @GetMapping
    public List<Usuario> findAll( ){
        return  service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

         Optional<Usuario>  usuarioOptional =service.findById(id);

         if ( usuarioOptional.isPresent() ){
             return ResponseEntity.ok(usuarioOptional.get());//obtenemos el objeto usuario(200)
         }

        return ResponseEntity.notFound().build();//(404)

    }

    @PostMapping
    public ResponseEntity<?>  save(@Valid @RequestBody Usuario usuario, BindingResult result){

        //validando
        if ( result.hasErrors() ){
            return validar(result);
        }

        //si existe ese email ya hay un usuario con ese correo
        if ( !usuario.getEmail().isEmpty() &&   service.existsPorEmail( usuario.getEmail() ) ){

            return ResponseEntity.badRequest().body(Collections.singletonMap(
                    "error ", "Ya existe un usuario con ese email" )
            );


        }

      return   ResponseEntity.status(HttpStatus.CREATED ).body(service.save( usuario));

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update( @Valid @RequestBody Usuario usuario ,BindingResult result , @PathVariable Long id  ){



        //validando
        if ( result.hasErrors() ){
            return validar(result);
        }


        //busacamos a la base de datos
        Optional<Usuario> usuarioOptional =   service.findById(id);
        //modificamos con los datos que nos estan enviando
        if ( usuarioOptional.isPresent()){
            Usuario usuarioDb = usuarioOptional.get();//usuario de la bdd

            //si es diferente  de la bdd con el usuario pasado es para modificar , entonces buscamos en la bbd
            //que no sea igual a un usuario de ahi.
            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.findEmail( usuario.getEmail() ).isPresent()){

                return ResponseEntity.badRequest().body(Collections.singletonMap(
                        "error ", "Ya existe un usuario con ese email" )
                );


            }


            usuarioDb.setNombre( usuario.getNombre() );
            usuarioDb.setEmail( usuario.getEmail() );
            usuarioDb.setPassword( usuario.getPassword() );

            return  ResponseEntity.status(HttpStatus.CREATED).body( service.save( usuarioDb ) );

        }

        return  ResponseEntity.notFound().build();


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete( @PathVariable Long id ){

        Optional<Usuario> o = service.findById( id );

        if (o.isPresent()){//si existe los eliminamos
            service.delete( id );
            return  ResponseEntity.noContent().build();//no hay contenido 204
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
