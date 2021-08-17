package domain.business;

import java.util.List;
import java.util.stream.Collectors;

public class Sala {
    private int numeroSala;
    private int cantidadButacas;
    private List<Butaca> butacas;

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

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public void agregarButaca(Butaca butaca) {
        this.butacas.add(butaca);
    }

    public void setButacas(List<Butaca> butacas) {
        this.butacas = butacas;
    }


    // Constructor
    public Sala(int cantidadButacas) {
        this.setCantidadButacas(cantidadButacas);
        this.crearSala();
    }


    // Metodos
    private void crearSala() {
        for(int i=1; i<=cantidadButacas; i++) {
            Butaca butaca = new Butaca(i);
            this.agregarButaca(butaca);
        }
    }

    public List obtenerButacasLibres() {
        return this.butacas.stream().filter(butaca -> butaca.isLibre()).collect(Collectors.toList());
    }

    private Butaca buscarButaca(int numeroButaca) {
        for(Butaca butacaBuscada : butacas) {
            if(butacaBuscada.getNumeroButaca() == numeroButaca) {
                return butacaBuscada;
            }
        }
        return null;
    }

    public boolean reservarButaca(int numeroButaca) {
        Butaca butacaBuscada;
        butacaBuscada = this.buscarButaca(numeroButaca);
        if(butacaBuscada != null && butacaBuscada.isLibre()) {
            butacaBuscada.ocuparButaca();
            return true;
        }
        else {
            System.out.println("Esta butaca está ocupada o no existe.");
            return false;
        }
    }


}
