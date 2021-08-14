package business.comestibles;

public class Bebida implements TipoComestible{
    private TipoBebida tipoDeBebida;
    private double precio;
    private double porcentajeLlenado;

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
    public void rellenar() {
        this.porcentajeLlenado = 100;
    }

    @Override
    public double obtenerPrecio() {
        return this.getPrecio();
    }
}
