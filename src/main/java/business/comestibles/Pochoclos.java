package business.comestibles;

public abstract class Pochoclos implements TipoComestible {
    private double precioEstandar;

    // Getters and Setters
    public double getPrecioEstandar() {
        return precioEstandar;
    }

    public void setPrecioEstandar(double precio) {
        this.precioEstandar = precio;
    }

    // Constructor
    public Pochoclos(double precioEstandar) { this.precioEstandar = precioEstandar; }
}
