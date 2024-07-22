package org.tmarlon.springcloud.msvc.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    //un solo curso puede tener muchos usuarios
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CursoUsuario> cursoUsuarios;

    //creamos un instancia para cursos_usuarios gracias a un constructor vacio
    public Curso() {
        cursoUsuarios= new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    //metodos para agregar un usuario y eliminar un usuario

    //agrega
    public void  addCursoUsuario( CursoUsuario  cursoUsuario  ){

        this.cursoUsuarios.add( cursoUsuario);

    }
    //alimina
    public void  deleteCursoUsuario( CursoUsuario cursoUsuario ){
        //cuando lo eliminemos comparara por el id del usuario
        //esto es gracias al equals del cursoUsuario
        this.cursoUsuarios.remove(cursoUsuario );

    }




}
