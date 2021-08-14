package business;

import java.time.LocalTime;
import java.util.Date;

public class Ticket extends Compra{
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
    public Ticket(){}

    public Ticket(Date fechaEmision, LocalTime horarioFuncion, Pelicula pelicula, Butaca butaca, int sala) {
        this.fechaEmision = fechaEmision;
        this.horarioFuncion = horarioFuncion;
        this.pelicula = pelicula;
        this.butaca = butaca;
        this.sala = sala;
    }


    @Override
    public double obtenerPrecio() {
        return 0;
    }
}
