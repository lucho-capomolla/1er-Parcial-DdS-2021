package business;

import java.util.Date;

public class Pelicula {
    private String titulo;
    private int duracionEnMin;
    private Genero genero;
    private Date fechaDeEstreno;
    private double precioEntrada;
    private EstadoPelicula estadoPelicula;

    // Getters and Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionEnMin() {
        return duracionEnMin;
    }

    public void setDuracionEnMin(int duracionEnMin) {
        this.duracionEnMin = duracionEnMin;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getFechaDeEstreno() {
        return fechaDeEstreno;
    }

    public void setFechaDeEstreno(Date fechaDeEstreno) {
        this.fechaDeEstreno = fechaDeEstreno;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public void cambiarEstado(EstadoPelicula nuevoEstado) {
        this.estadoPelicula = nuevoEstado;
    }

    // Constructor
    public Pelicula(String titulo, int duracionEnMin, Genero genero, Date fechaDeEstreno, double precioEntrada) {
        this.titulo = titulo;
        this.duracionEnMin = duracionEnMin;
        this.genero = genero;
        this.fechaDeEstreno = fechaDeEstreno;
        this.precioEntrada = precioEntrada;
    }

    // Metodos
    public double calcularPrecio() {
        return this.estadoPelicula.calcularPrecio(precioEntrada);
    }
}
