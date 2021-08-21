package domain.business;

import domain.business.pelicula.Pelicula;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sala {
    private int numeroSala;
    private int cantidadButacas;
    private List<Butaca> butacas = new ArrayList<>();

    // Getters and Setters
    public int getCantidadButacas() {
        return cantidadButacas;
    }

    public void setCantidadButacas(int cantidadButacas) {
        this.cantidadButacas = cantidadButacas;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public void agregarButaca(Butaca butaca) {
        this.butacas.add(butaca);
    }


    // Constructor
    public Sala(int numeroSala, int cantidadButacas) {
        this.numeroSala = numeroSala;

        this.setCantidadButacas(cantidadButacas);
    }


    // Metodos
    public void prepararButacas(String horario, Pelicula pelicula) {
        for(int i=1; i<=cantidadButacas; i++) {
            Butaca butaca = new Butaca(i, horario, pelicula);
            this.agregarButaca(butaca);
        }
    }
}