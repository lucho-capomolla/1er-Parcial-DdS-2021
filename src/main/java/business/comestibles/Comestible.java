package business.comestibles;

public class Comestible extends Producto {
    private TipoComestible tipoComestible;

    // Getters and Setters
    public TipoComestible getTipoComestible() { return tipoComestible; }

    public void setTipoComestible(TipoComestible tipoComestible) { this.tipoComestible = tipoComestible; }

    // Constructor
    public Comestible(TipoComestible tipoComestible) { this.tipoComestible = tipoComestible; }

    // Metodos
    @Override
    public double obtenerPrecio() {
        return tipoComestible.obtenerPrecio();
    }

}
