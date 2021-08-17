package business;

import business.pelicula.Pelicula;
import security.Usuario;

import java.util.List;

public class Cinema {
    private static Cinema instance;
    private static List<Usuario> usuarios;
    private static List salas;
    private static List<Pelicula> cartelera;

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
    public static boolean iniciarSesion(String usuario, String contraseña) {
       if(Cinema.buscarUsuario(usuario)) {
           return Cinema.verificarInicio(usuario, contraseña);
       }
       else {
           System.out.println("El usuario es incorrecto.");
           return false;
       }
    }

    public static boolean buscarUsuario(String usuarioBuscado) {
        return usuarios.stream().map(usuario -> usuario.getEmail()).equals(usuarioBuscado);
    }

    public static boolean verificarInicio(String usuario, String contraseña) {
        // Todo: aca podria ponerse un requerimiento No funcional, como que si no ingresa nada en los proximos 10 segundos -> OUT
        return false;
    }


    public static void crearUsuario(String usuario, String contraseña, String email) {
        // TODO: validar que no exista el mismo Cliente ni Email; además validar la contraseña
    }
}
