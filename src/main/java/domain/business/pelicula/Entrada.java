package domain.business.pelicula;

import domain.business.Butaca;
import domain.business.Cinema;
import domain.business.Compra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Entrada implements Compra {
    private LocalDate fechaEmision;
    private String horarioFuncion;
    private Pelicula pelicula;
    private Butaca butaca;
    private int sala;
    private EstadoPelicula estadoPelicula;

    // Getters and Setters
    public LocalDate getFechaEmision() { return fechaEmision; }

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

    public int getSala() { return sala; }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public void setEstadoPelicula(EstadoPelicula estadoPelicula) { this.estadoPelicula = estadoPelicula; }

    public EstadoPelicula getEstadoPelicula() { return estadoPelicula; }

    public void cambiarEstado(EstadoPelicula estadoPelicula) { this.estadoPelicula = estadoPelicula; }

    // Constructor
    public Entrada(){}


    // Metodos
    @Override
    public String obtenerNombre() { return pelicula.getTitulo(); }

    @Override
    public double obtenerPrecio() { return this.calcularPrecio(); }

    public double calcularPrecio() {
        Date date = new Date();
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
        String fechaHoy = fecha.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);

        if(this.getPelicula().getFechaDeEstreno().equals(fechaHoy)) {
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

    @Override
    public void mostrarCompra() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Título: " + this.getPelicula().getTitulo());
        System.out.println("Horario: " + this.getHorarioFuncion());
        System.out.println("Sala: " + this.getSala());
        System.out.println("Butaca: " + this.getButaca().getNumeroButaca());
        System.out.println("------------------------------------------------------------");
    }
}
