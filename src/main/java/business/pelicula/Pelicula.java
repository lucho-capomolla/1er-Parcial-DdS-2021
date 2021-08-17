package business.pelicula;

import business.Cinema;
import business.Sala;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pelicula {
    private String titulo;
    private int duracionEnMin;
    private Genero genero;
    private String fechaDeEstreno;
    private String clasificacion;
    private EstadoPelicula estadoPelicula;
    private Sala sala;


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

    public String getFechaDeEstreno() {
        return fechaDeEstreno;
    }

    public void setFechaDeEstreno(String fechaDeEstreno) {
        this.fechaDeEstreno = fechaDeEstreno;
    }

    public void cambiarEstado(EstadoPelicula nuevoEstado) {
        this.estadoPelicula = nuevoEstado;
    }

    // Constructor
    public Pelicula(String titulo, int duracionEnMin, Genero genero, String fechaDeEstreno, double precioEntrada) {
        this.titulo = titulo;
        this.duracionEnMin = duracionEnMin;
        this.genero = genero;
        this.fechaDeEstreno = fechaDeEstreno;
    }

    // Metodos
    public double calcularPrecio() {

        Date date = new Date();
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaHoy = fecha.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);

        if(this.getFechaDeEstreno().equals(fechaHoy)) {
            this.cambiarEstado(new DiaDeEstreno());
        }
        else if(diaDeLaSemana == 4){
            this.cambiarEstado(new Promocion());
        }
        else {
            this.cambiarEstado(new EnCartelera());
        }

        Cinema elCinema = Cinema.getInstance();
        return this.estadoPelicula.calcularPrecio(elCinema.getPrecioEntrada());
    }
}
