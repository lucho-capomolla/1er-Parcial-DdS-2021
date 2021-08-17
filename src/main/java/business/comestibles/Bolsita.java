package business.comestibles;

public class Bolsita extends Pochoclos{

    public Bolsita(double precio){ super(precio); }

    @Override
    public double obtenerPrecio() {
        return this.getPrecioEstandar();
    }
}
