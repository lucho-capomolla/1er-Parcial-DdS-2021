package domain.business.comestibles;

import domain.business.Compra;

import java.util.ArrayList;

public class Combo extends Producto {
    private String nombreArticulo;
    private ArrayList<Producto> comprasDelCombo = new ArrayList<>();

    // Getters and Setters
    public void agregarProducto(Producto nuevoProducto) {
        comprasDelCombo.add(nuevoProducto);
    }

    public void quitarProducto(Producto productoASacar) {
        comprasDelCombo.remove(productoASacar);
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

    public Combo(ArrayList<Producto> productosDelCombo) {
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
}
