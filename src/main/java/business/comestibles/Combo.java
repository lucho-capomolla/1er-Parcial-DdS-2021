package business.comestibles;

import java.util.ArrayList;

public class Combo extends Producto {
    private ArrayList<Producto> productosDelCombo = new ArrayList<>();

    // Getters and Setters
    public void agregarProducto(Producto nuevoProducto) {
        productosDelCombo.add(nuevoProducto);
    }

    public void quitarProducto(Producto productoASacar) {
        productosDelCombo.remove(productoASacar);
    }

    public ArrayList obtenerProductosDelCombo() {
        ArrayList<String> nombreProductos = new ArrayList<>();
        for (Producto producto : productosDelCombo) {
            nombreProductos.add(producto.getArticulo());
        }
        return nombreProductos;
    }


    // Constructor
    public Combo() {}

    public Combo(ArrayList<Producto> productosDelCombo) {
        this.productosDelCombo = productosDelCombo;
    }


    // Metodos
    @Override
    public double obtenerPrecio() {
        double precioTotal = 0;
        if(productosDelCombo.size() >= 3) {
            for (Producto producto : productosDelCombo) {
                precioTotal += producto.obtenerPrecio();
            }
            return precioTotal - (precioTotal * 0.10);
        }
        else {
            for (Producto producto : productosDelCombo) {
                precioTotal += producto.obtenerPrecio();
            }
            return precioTotal;
        }

    }
}
