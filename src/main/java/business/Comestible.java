package business;

public class Comestible extends Producto{
    private TipoComestible tipoComestible;


    @Override
    public double obtenerPrecio() {
        return 1;
    }
}
