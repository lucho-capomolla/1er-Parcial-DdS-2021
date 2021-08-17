package business.comestibles;

public class Balde extends Pochoclos{

    public Balde(double precio) { super(precio); }

    @Override
    public double obtenerPrecio() {
        return this.getPrecioEstandar() * 4;
    }
}
