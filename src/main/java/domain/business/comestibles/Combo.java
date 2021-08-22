package domain.business.comestibles;

import domain.business.Compra;

import java.util.ArrayList;

public class Combo implements Compra {
    private String nombreArticulo;
    private ArrayList<Compra> comprasDelCombo = new ArrayList<>();

    // Getters and Setters
    public void agregarProducto(Compra nuevaCompra) {
        comprasDelCombo.add(nuevaCompra);
    }

    public void quitarProducto(Compra compraASacar) {
        comprasDelCombo.remove(compraASacar);
    }

    public String getArticulo() { return nombreArticulo; }

    public void setArticulo(String articulo) {
        this.nombreArticulo = articulo;
    }

    public ArrayList obtenerProductosDelCombo() {
        ArrayList<String> nombreProductos = new ArrayList<>();
        for (Compra compra : comprasDelCombo) {
            nombreProductos.add(compra.obtenerNombre());
        }
        return nombreProductos;
    }


    // Constructor
    public Combo() {}

    public Combo(ArrayList<Compra> productosDelCombo) {
        this.comprasDelCombo = productosDelCombo;
    }


    // Metodos
    @Override
    public double obtenerPrecio() {
        double precioTotal = 0;
        if(comprasDelCombo.size() >= 3) {
            for (Compra compra : comprasDelCombo) {
                precioTotal += compra.obtenerPrecio();
            }
            return precioTotal - (precioTotal * 0.10);
        }
        else {
            for (Compra compra : comprasDelCombo) {
                precioTotal += compra.obtenerPrecio();
            }
            return precioTotal;
        }
    }

    @Override
    public String obtenerNombre() { return this.getArticulo(); }

    @Override
    public void mostrarCompra() { System.out.println(this.getArticulo()); }
}
