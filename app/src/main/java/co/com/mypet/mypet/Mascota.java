package co.com.mypet.mypet;

public class Mascota {
    private String id;
    private String nombre;
    private String raza;
    private String ciudad;
    private String edad;
    private String descripcion;
    private String foto;

    @Override
    public String toString() {
        return "Mascota{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", edad='" + edad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public Mascota(String id, String nombre, String raza, String ciudad, String edad, String descripcion, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.ciudad = ciudad;
        this.edad = edad;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Mascota() {
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void guardar(){
        Datos.agregar(this);
    }
}
