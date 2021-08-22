package domain.business.comestibles;

import domain.business.Compra;

public abstract class Producto implements Compra {
    private String articulo;

    // Getters and Setters
    public String getArticulo() { return articulo; }

    public void setArticulo(String articulo) { this.articulo = articulo; }

    // Metodos
    @Override
    public String obtenerNombre() { return this.getArticulo(); }

    @Override
    public void mostrarCompra() { System.out.println(this.getArticulo()); }
}
