package co.com.mypet.mypet.modelos;

import co.com.mypet.mypet.core.Datos;

public class Usuario {
    public String foto;
    public String id;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public String segundoNombre;
    public String genero;


    public Usuario() {
    }

    public Usuario(String foto, String id, String nombre, String primerApellido, String segundoApellido, String segundoNombre, String genero) {
        this.foto = foto;
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.segundoNombre = segundoNombre;
        this.genero = genero;
    }

    public Usuario(String id){
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public void guardar(){
        Datos.agregarUsuario(this);
    }

    public void editar(){
        Datos.editarUsuario(this);
    }
}
