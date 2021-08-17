package business;

import business.pelicula.Disponibilidad;

public class Butaca {
    private int numeroButaca;
    private Disponibilidad disponibilidad;

    // Getters and Setters
    public int getNumeroButaca() {
        return numeroButaca;
    }

    public void setNumeroButaca(int numeroButaca) {
        this.numeroButaca = numeroButaca;
    }

    public void ocuparButaca() { this.disponibilidad = Disponibilidad.OCUPADA; }

    public void liberarButaca() { this.disponibilidad = Disponibilidad.LIBRE; }

    public boolean isLibre() { return this.disponibilidad.equals(Disponibilidad.LIBRE); }

    // Constructor
    public Butaca(int numeroButaca) {
        this.numeroButaca = numeroButaca;
        this.disponibilidad = Disponibilidad.LIBRE;
    }
}

