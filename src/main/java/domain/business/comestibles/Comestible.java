package domain.business.comestibles;

public class Comestible extends Producto {
    private TipoComestible tipoComestible;

    // Constructor
    public Comestible(TipoComestible tipoComestible) { this.tipoComestible = tipoComestible; }

    // Metodos
    @Override
    public double obtenerPrecio() {
        return tipoComestible.obtenerPrecio();
    }

}
