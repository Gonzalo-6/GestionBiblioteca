package biblioteca.simple.modelo;

public class Usuario {
    private int id;
    private String nombre;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
