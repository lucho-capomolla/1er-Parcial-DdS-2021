package domain.business.pelicula;

public class EnCartelera implements EstadoPelicula {

    @Override
    public double calcularPrecio(double precioEntrada) {
        return precioEntrada;
    }
}
