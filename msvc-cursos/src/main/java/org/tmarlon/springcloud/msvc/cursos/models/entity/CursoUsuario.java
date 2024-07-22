package org.tmarlon.springcloud.msvc.cursos.models.entity;


import jakarta.persistence.*;

//tabla intermedia registra los id de los usuarios de un curso en particular
@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;

    //crea un indice unico (no habra el mismo usuario en el curso )
    @Column(name = "usuario_id" ,unique = true)
    private Long usuarioId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {

        //si este objeto es igual al objeto pasado
        if ( this == o ){
            return true;//retornamos true
        }
        //si el objeto  no es una instancia del cursUsuario
        if (!(o instanceof CursoUsuario)  ){
            return false;//retornamos false
        }

        //el objeto sera igual cuando comparemos con los id del usuario
        CursoUsuario obj = (CursoUsuario) o;
        return this.usuarioId != null && this.usuarioId.equals( obj.usuarioId );

    }



}
