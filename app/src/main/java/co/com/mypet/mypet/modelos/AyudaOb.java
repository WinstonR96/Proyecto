package co.com.mypet.mypet.modelos;

public class AyudaOb {
    private String id;
    private String title;
    private String description;
    private String imagen;

    public AyudaOb() {
    }

    public AyudaOb(String id, String title, String description, String imagen) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
