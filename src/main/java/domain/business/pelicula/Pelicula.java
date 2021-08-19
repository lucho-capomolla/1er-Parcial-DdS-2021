package domain.business.pelicula;

import domain.business.Cinema;
import domain.business.Sala;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pelicula {
    private String idPelicula;
    private String titulo;
    private String tituloCompleto;
    private String fechaDeEstreno;
    private String trama;
    private String duracionEnMin;
    private String duracionTotal;
    private String generos;
    private String director;
    private String reparto;
    private String clasificacion;

    private EstadoPelicula estadoPelicula;
    private Sala sala;


    // Getters and Setters
    public String getIdPelicula() { return idPelicula; }

    public void setIdPelicula(String idPelicula) { this.idPelicula = idPelicula; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTituloCompleto() { return tituloCompleto; }

    public void setTituloCompleto(String tituloCompleto) { this.tituloCompleto = tituloCompleto; }

    public String getFechaDeEstreno() { return fechaDeEstreno; }

    public void setFechaDeEstreno(String fechaDeEstreno) { this.fechaDeEstreno = fechaDeEstreno; }

    public String getTrama() { return trama; }

    public void setTrama(String trama) { this.trama = trama; }

    public String getDuracionEnMin() { return duracionEnMin; }

    public void setDuracionEnMin(String duracionEnMin) { this.duracionEnMin = duracionEnMin; }

    public String getDuracionTotal() { return duracionTotal; }

    public void setDuracionTotal(String duracionTotal) { this.duracionTotal = duracionTotal; }

    public String getGeneros() { return generos; }

    public void setGeneros(String generos) { this.generos = generos; }

    public String getDirector() { return director; }

    public void setDirector(String director) { this.director = director; }

    public String getReparto() { return reparto; }

    public void setReparto(String reparto) { this.reparto = reparto; }

    public String getClasificacion() { return clasificacion; }

    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public void setEstadoPelicula(EstadoPelicula estadoPelicula) { this.estadoPelicula = estadoPelicula; }

    public EstadoPelicula getEstadoPelicula() { return estadoPelicula; }

    public void cambiarEstado(EstadoPelicula estadoPelicula) { this.estadoPelicula = estadoPelicula; }


    // Constructor
    public Pelicula() {}


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
        return this.estadoPelicula.calcularPrecio(elCinema.obtenerPrecioEntrada());
    }
}
