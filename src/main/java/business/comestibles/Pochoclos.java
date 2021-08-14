package business.comestibles;

import business.TipoComestible;

public abstract class Pochoclos implements TipoComestible {
    private double precio;
    private double porcentajeLlenado;

    // Getters and Setters
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPorcentajeLlenado() {
        return porcentajeLlenado;
    }

    public void setPorcentajeLlenado(double porcentajeLlenado) {
        this.porcentajeLlenado = porcentajeLlenado;
    }

    // Constructor
    public Pochoclos(double precio) {
        this.precio = precio;
        this.porcentajeLlenado = 100;
    }
}
