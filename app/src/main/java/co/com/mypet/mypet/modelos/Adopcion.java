package co.com.mypet.mypet.modelos;

import co.com.mypet.mypet.core.Datos;

public class Adopcion {
    public String id;
    public String mascota;
    public String nombreUsuario;

    public Adopcion() {
    }

    public Adopcion(String id, String mascota, String nombreUsuario) {
        this.id = id;
        this.mascota = mascota;
        this.nombreUsuario = nombreUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void adoptar(){
        Datos.agregarAdopcion(this);
    }
}
