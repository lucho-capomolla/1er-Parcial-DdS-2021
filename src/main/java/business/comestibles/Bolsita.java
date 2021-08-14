package business.comestibles;

public class Bolsita extends Pochoclos{


    public Bolsita(double precio) {
        super(precio);
    }

    @Override
    public void rellenar() {

    }

    @Override
    public double obtenerPrecio() {
        return 150;
    }
}
