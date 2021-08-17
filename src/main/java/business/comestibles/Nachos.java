package business.comestibles;

public class Nachos implements TipoComestible{
    private double precio;

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Nachos() {}

    @Override
    public double obtenerPrecio() {
        return this.getPrecio();
    }
}
