package domain.business;

import domain.business.pelicula.Pelicula;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Entrada implements Compra {
    private LocalDate fechaEmision;
    private String horarioFuncion;
    private Pelicula pelicula;
    private Butaca butaca;
    private int sala;

    // Getters and Setters
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getHorarioFuncion() {
        return horarioFuncion;
    }

    public void setHorarioFuncion(String horarioFuncion) {
        this.horarioFuncion = horarioFuncion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Butaca getButaca() {
        return butaca;
    }

    public void setButaca(Butaca butaca) {
        this.butaca = butaca;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }


    // Constructor
    public Entrada(){}


    // Metodos
    public void elegirButaca(int numeroButaca) {
        // Todo: en Views, el Sistema deberia mostrar las butacas disponibles, y el usuario elige el numero de asiento deseado
    }

    public void elegirPelicula(Pelicula pelicula) {
        // Todo: en Views, el Sistema deberia mostrar las peliculas disponibles, y el usuario elige dicha pelicula y envia en este metodo
    }

    @Override
    public double obtenerPrecio() {
        return this.getPelicula().calcularPrecio();
    }

    @Override
    public void mostrarCompra() {
        System.out.println(this.getPelicula().getTitulo());
        System.out.println(this.getHorarioFuncion());
    }
}