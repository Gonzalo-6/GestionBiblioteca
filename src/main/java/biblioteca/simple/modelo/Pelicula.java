package biblioteca.simple.modelo;

import biblioteca.simple.contratos.Prestable;

public class Pelicula extends Producto implements Prestable {

    private String director;
    private int minutosDuracion;
    private boolean prestado;
    private Usuario prestadoA;


    public Pelicula(int id, String titulo, String anho, Formato formato, String director, int minutosDuracion) {
        super(id, titulo, anho, formato);
        this.director = director;
        this.minutosDuracion = minutosDuracion;
    }

    public Pelicula(String titulo, String anho, Formato formato, String director, int minutosDuracion) {
        super(titulo, anho, formato);
        this.director = director;
        this.minutosDuracion = minutosDuracion;
    }

    public String getDirector() {
        return director;
    }

    public int getMinutosDuracion() {
        return minutosDuracion;
    }

    @Override public void prestar(Usuario u){
        if(prestado) throw new IllegalStateException("Ya esta prestado.");
        this.prestado = true;
        this.prestadoA = u;
    }

    @Override public void devolver(){
        this.prestado = false;
        this.prestadoA = null;
    }

    @Override public boolean estaPrestado() {return this.prestado; }

    @Override
    public String toString() {
        return "Pelicula{" +
                "director='" + director + '\'' +
                ", minutosDuracion=" + minutosDuracion +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anho='" + anho + '\'' +
                ", formato=" + formato +
                '}';
    }
}
