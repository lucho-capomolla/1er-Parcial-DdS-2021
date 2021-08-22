package domain.business.comestibles;

import domain.business.Compra;

public class Comestible implements Compra {
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

    @Override
    public String obtenerNombre() { return this.getArticulo(); }

    @Override
    public void mostrarCompra() { System.out.println(this.getArticulo()); }
}
