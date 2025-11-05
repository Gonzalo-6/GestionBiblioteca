package biblioteca.simple.modelo;

public abstract class Producto {
    protected int id;
    protected String titulo;
    protected String anho;
    protected Formato formato;

    //constructor para recibir objetos de la bbdd
    protected Producto(int id, String titulo, String anho, Formato formato) {
        this.id = id;
        this.titulo = titulo;
        this.anho = anho;
        this.formato = formato;
    }

    //constructor para crear nuevos productos desde la apli
    protected Producto( String titulo, String anho, Formato formato) {
        this.titulo = titulo;
        this.anho = anho;
        this.formato = formato;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAnho() {
        return anho;
    }

    public Formato getFormato() {
        return formato;
    }



    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anho='" + anho + '\'' +
                ", formato=" + formato +
                '}';
    }
}
