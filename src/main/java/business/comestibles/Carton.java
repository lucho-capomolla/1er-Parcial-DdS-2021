package business.comestibles;

public class Carton extends Pochoclos{


    public Carton(double precio) {
        super(precio);
    }

    @Override
    public void rellenar() {

    }

    @Override
    public double obtenerPrecio() {
        return 250;
    }
}
