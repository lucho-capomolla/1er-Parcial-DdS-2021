package business;

public class DiaDeEstreno implements EstadoPelicula{

    @Override
    public double calcularPrecio(double precioEntrada) {
        return precioEntrada * 2;
    }
}
