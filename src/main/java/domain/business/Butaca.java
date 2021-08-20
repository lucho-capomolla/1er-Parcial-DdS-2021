package domain.business;

import domain.business.pelicula.Pelicula;

public class Butaca {
    private int numeroButaca;
    private String horario;
    private Pelicula pelicula;
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

    public boolean estaLibre() { return this.disponibilidad.equals(Disponibilidad.LIBRE); }

    public String getHorario() { return horario; }

    public void setHorario(String horario) { this.horario = horario; }

    public Pelicula getPelicula() { return pelicula; }

    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }

    // Constructor
    public Butaca(int numeroButaca, String horario, Pelicula pelicula) {
        this.setNumeroButaca(numeroButaca);
        this.setPelicula(pelicula);
        this.setHorario(horario);
        this.liberarButaca();
    }
}

