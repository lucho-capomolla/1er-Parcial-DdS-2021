package business.pelicula;

public class DiaDeEstreno implements EstadoPelicula {

    @Override
    public double calcularPrecio(double precioEntrada) {
        return precioEntrada * 2;
    }
}
