package business.comestibles;

public class Balde extends Pochoclos{

    public Balde(double precio) {
        super(precio);
    }

    @Override
    public void rellenar() {

    }

    @Override
    public double obtenerPrecio() {
        return 400;
    }
}
