package domain.business.comestibles;

import domain.business.Compra;

public abstract class Producto implements Compra {
    private String nombreArticulo;

    // Getters and Setters
    public String getArticulo() { return nombreArticulo; }

    public void setArticulo(String articulo) {
        this.nombreArticulo = articulo;
    }

    public abstract double obtenerPrecio();

    @Override
    public String obtenerNombre() { return this.getArticulo(); }

    @Override
    public void mostrarCompra() {
        System.out.println(this.getArticulo());
    }
}
