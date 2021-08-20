package domain.business.pelicula;

import domain.business.Cinema;
import domain.business.Sala;
import domain.business.pelicula.apiPelicula.ApiMovies;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;
import domain.business.pelicula.apiPelicula.entidades.Movie;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pelicula {
    private int idPelicula;
    private String titulo;
    private String tituloOriginal;
    private String fechaDeEstreno;
    private String trama;
    private String generos;
    private String idioma;

    private EstadoPelicula estadoPelicula;
    private Sala sala;


    // Getters and Setters
    public int getIdPelicula() { return idPelicula; }

    public void setIdPelicula(int idPelicula) { this.idPelicula = idPelicula; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTituloOriginal() { return tituloOriginal; }

    public void setTituloOriginal(String tituloOriginal) { this.tituloOriginal = tituloOriginal; }

    public String getFechaDeEstreno() { return fechaDeEstreno; }

    public void setFechaDeEstreno(String fechaDeEstreno) { this.fechaDeEstreno = fechaDeEstreno; }

    public String getTrama() { return trama; }

    public void setTrama(String trama) { this.trama = trama; }

    public String getGeneros() { return generos; }

    public void setGeneros(String generos) { this.generos = generos; }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }

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


    public Pelicula mappeoDAO(Movie movie) throws IOException {
        ApiMovies apiMovies = new ApiMovies();
        this.setIdPelicula(movie.getId());
        this.setTitulo(movie.getTitle());
        this.setTituloOriginal(movie.getOriginal_title());
        this.setFechaDeEstreno(movie.getRelease_date());
        this.setTrama(movie.getOverview());
        this.setGeneros(apiMovies.obtenerGenero(movie.getGenre_ids()));
        this.setIdioma(movie.getOriginal_language());
        this.setEstadoPelicula(new DiaDeEstreno());

        return this;
    }
}
