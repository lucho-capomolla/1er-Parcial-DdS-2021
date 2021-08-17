package business.pelicula;

public class Promocion implements EstadoPelicula{
    @Override
    public double calcularPrecio(double precioEntrada) {
        return precioEntrada / 2;
    }
}
