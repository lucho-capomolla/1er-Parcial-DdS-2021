package business.comestibles;

public class Bebida implements TipoComestible{
    private TipoBebida tipoDeBebida;
    private double precio;

    // Getters and Setters
    public TipoBebida getTipoDeBebida() {
        return tipoDeBebida;
    }

    public void setTipoDeBebida(TipoBebida tipoDeBebida) {
        this.tipoDeBebida = tipoDeBebida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Constructor
    public Bebida(TipoBebida tipoDeBebida, double precio) {
        this.tipoDeBebida = tipoDeBebida;
        this.precio = precio;
    }

    // Metodos
    @Override
    public double obtenerPrecio() {
        return this.getPrecio();
    }
}
