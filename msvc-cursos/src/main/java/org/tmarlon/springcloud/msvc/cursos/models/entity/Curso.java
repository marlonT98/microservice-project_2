package org.tmarlon.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.tmarlon.springcloud.msvc.cursos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    //es un atributo que no estara mapeada en la tabla
    //es solo un campo para poder poblar los datos de los usuarios completos
    @Transient
    private List<Usuario> usuarios;


    //creamos un instancia para cursos_usuarios gracias a un constructor vacio
    public Curso() {
        cursoUsuarios= new ArrayList<>();
        usuarios = new ArrayList<>();
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
