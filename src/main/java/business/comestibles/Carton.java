package business.comestibles;

public class Carton extends Pochoclos{

    public Carton(double precio) { super(precio); }

    @Override
    public double obtenerPrecio() {
        return this.getPrecioEstandar() * 2;
    }
}
