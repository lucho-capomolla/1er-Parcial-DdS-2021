package business;

import java.util.ArrayList;

public class Combo extends Producto{
    private ArrayList<Producto> productosDelCombo = new ArrayList<>();

    public void agregarProducto(Producto nuevoProducto) {
        productosDelCombo.add(nuevoProducto);
    }

    public void quitarProducto(Producto productoASacar) {
        productosDelCombo.remove(productoASacar);
    }

    public ArrayList obtenerProductos() {
        return productosDelCombo;
    }

    @Override
    public double obtenerPrecio() {
        double precioTotal = 0;
        for (Producto producto : productosDelCombo) {
            precioTotal += producto.obtenerPrecio();
        }
        return precioTotal;
    }
}
