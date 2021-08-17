package business;

import business.pelicula.Pelicula;

import java.time.LocalTime;
import java.util.Date;

public class Entrada implements Compra {
    private Date fechaEmision;
    private LocalTime horarioFuncion;
    private Pelicula pelicula;
    private Butaca butaca;
    private int sala;

    // Getters and Setters
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalTime getHorarioFuncion() {
        return horarioFuncion;
    }

    public void setHorarioFuncion(LocalTime horarioFuncion) {
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

    public Entrada(Date fechaEmision, LocalTime horarioFuncion, Pelicula pelicula, Butaca butaca, int sala) {
        this.fechaEmision = fechaEmision;
        this.horarioFuncion = horarioFuncion;
        this.pelicula = pelicula;
        this.butaca = butaca;
        this.sala = sala;
    }


    public void elegirButaca(int numeroButaca) {

    }

    public void elegirPelicula(Pelicula pelicula) {

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
