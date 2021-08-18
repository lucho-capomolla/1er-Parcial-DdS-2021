package domain.business;

import domain.business.pelicula.Pelicula;
import domain.security.Admin;
import domain.security.TipoRol;
import domain.security.Usuario;
import domain.security.database.PreciosDAO;
import domain.security.database.UsuarioDAO;
import domain.security.database.UsuariosDAO;
import domain.security.password.ValidadorPassword;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cinema {
    private static Cinema instance;
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Sala> salas = new ArrayList<>();
    private static List<Pelicula> cartelera = new ArrayList<>();

    // Getters and Setters
    public static List<Pelicula> getCartelera() {
        return cartelera;
    }

    public static void agregarPeliculaEnCartelera(Pelicula pelicula) {
        Cinema.cartelera.add(pelicula);
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void agregarUsuario(Usuario usuario) {
        Cinema.usuarios.add(usuario);
    }

    public static void setUsuarios(List<Usuario> usuarios) { Cinema.usuarios = usuarios; }


    // Singleton del Cinema
    public static Cinema getInstance() {
        if(instance == null) {
            instance = new Cinema();
        }
        return instance;
    }

    // Metodos
    public Usuario buscarUsuario(String emailBuscado) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuarioBuscado = usuarioDAO.buscarUsuario(emailBuscado);

        return usuarioBuscado;
    }

    public boolean validarUsuario(String email, String contrasenia) {
        Usuario usuarioBuscado = this.buscarUsuario(email);

        if(usuarioBuscado == null) {
            return false;
        }
        else {
            return (usuarioBuscado.getContrasenia().equals(contrasenia));
        }
    }

    public boolean validarContrasenia(String contrasenia) {
        ValidadorPassword validador = new ValidadorPassword();
        return validador.esValida(contrasenia);
    }

// Siempre que creo un nuevo usuario, va a tener un Rol de USER
    public Usuario crearUsuario(String email, String contrasenia) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioDAO.insert(email, contrasenia, TipoRol.USER);

        Usuario nuevoUsuario = new Usuario(email, contrasenia, TipoRol.USER);

        return nuevoUsuario;
    }

    private List<Usuario> obtenerUsuarios() {
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        return usuariosDAO.obtenerUsuarios();
    }


    // Consulto los Precios
    public double obtenerPrecioEntrada() {
        PreciosDAO preciosDAO = new PreciosDAO();
        return preciosDAO.obtenerPrecioEntrada();
    }

    public double obtenerPrecioPochoclos() {
        PreciosDAO preciosDAO = new PreciosDAO();
        return preciosDAO.obtenerPrecioPochoclos();
    }

    public double obtenerPrecioBebidas() {
        PreciosDAO preciosDAO = new PreciosDAO();
        return preciosDAO.obtenerPrecioBebidas();
    }

    public double obtenerPrecioNachos() {
        PreciosDAO preciosDAO = new PreciosDAO();
        return preciosDAO.obtenerPrecioNachos();
    }

    // Cambios de Precios
    public void cambiarPrecioPochoclos(double precioPochoclos) {
        PreciosDAO preciosDAO = new PreciosDAO();
        preciosDAO.cambiarPrecioPochoclos(precioPochoclos);
    }

    public void cambiarPrecioBebidas(double precioBebidas) {
        PreciosDAO preciosDAO = new PreciosDAO();
        preciosDAO.cambiarPrecioBebidas(precioBebidas);
    }

    public void cambiarPrecioNachos(double precioNachos) {
        PreciosDAO preciosDAO = new PreciosDAO();
        preciosDAO.cambiarPrecioNachos(precioNachos);
    }

    public void cambiarPrecioEntrada(double precioEntrada) {
        PreciosDAO preciosDAO = new PreciosDAO();
        preciosDAO.cambiarPrecioEntrada(precioEntrada);
    }

}
