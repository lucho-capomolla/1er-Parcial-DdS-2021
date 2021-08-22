package domain.business.comestibles;

public class Comestible extends Producto {
    private String nombreArticulo;
    private TipoComestible tipoComestible;

    public String getArticulo() { return nombreArticulo; }

    public void setArticulo(String articulo) {
        this.nombreArticulo = articulo;
    }

    // Constructor
    public Comestible(TipoComestible tipoComestible) { this.tipoComestible = tipoComestible; }

    // Metodos
    @Override
    public double obtenerPrecio() {
        return tipoComestible.obtenerPrecio();
    }
}
