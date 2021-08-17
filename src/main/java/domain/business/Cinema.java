package domain.business;

import domain.business.pelicula.Pelicula;
import domain.security.Admin;
import domain.security.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cinema {
    private static Cinema instance;
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Sala> salas = new ArrayList<>();
    private static List<Pelicula> cartelera = new ArrayList<>();

    // Por ahora, los Admins vamos a ser nosotros dos
    public static Usuario lucho = new Usuario("lucho@hotmail.com", "Luchhoo7");
    {
        this.lucho.setRol(new Admin());
        this.usuarios.add(lucho);
    }

    private double precioPochoclos;
    private double precioBebida;
    private double precioNachos;
    private double precioEntrada;

    // Getters and Setters de Precios
    public double getPrecioPochoclos() {
        return precioPochoclos;
    }

    public void setPrecioPochoclos(double precioPochoclos) {
        this.precioPochoclos = precioPochoclos;
    }

    public double getPrecioBebida() {
        return precioBebida;
    }

    public void setPrecioBebida(double precioBebida) {
        this.precioBebida = precioBebida;
    }

    public double getPrecioNachos() {
        return precioNachos;
    }

    public void setPrecioNachos(double precioNachos) {
        this.precioNachos = precioNachos;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }


    // Singleton del Cinema
    public static Cinema getInstance() {
        if(instance == null) {
            instance = new Cinema();
        }
        return instance;
    }

    // Metodos
    public static Usuario obtenerUsuario(String email, String contrasenia) {
        return usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).collect(Collectors.toList()).get(0);
    }

    public boolean existeUsuario(String email, String contrasenia) {
        if(usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return false;
        }
        else {
            return usuarios.stream().anyMatch(usuario -> usuario.getEmail().equals(email) && usuario.getContrasenia().equals(contrasenia));
        }
    }


    /*public static void crearUsuario(String usuario, String contraseña, String email) {
        // TODO: validar que no exista el mismo Cliente ni Email; además validar la contraseña
    }*/
}
