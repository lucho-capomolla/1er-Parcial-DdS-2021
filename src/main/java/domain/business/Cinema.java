package domain.business;

import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.ApiMovies;
import domain.security.TipoRol;
import domain.security.Usuario;
import domain.database.PreciosDAO;
import domain.database.UsuarioDAO;
import domain.database.UsuariosDAO;
import domain.security.password.ValidadorPassword;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Cinema {
    private static Cinema instance;
    private static List<Sala> salas = new ArrayList<>();
    private static List<String> horarios = Arrays.asList("10:00", "13:00", "16:00", "21:00");
    private static List<Ticket> ventas = new ArrayList<>();

    // Getters and Setters
    public static void agregarTicket(Ticket nuevoTicket) { Cinema.ventas.add(nuevoTicket); }


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

        Usuario nuevoUsuario = new Usuario(email, contrasenia, TipoRol.USER, 0);
        return nuevoUsuario;
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


    // Salas y Horarios
    // Van a haber 5 salas, y 4 horarios = {"10:00","13:00","16:00","21:00"}
    // Como en este cine hay 20 peliculas, se pasan 4 peliculas por sala, una por cada horario
    public void prepararCine() throws IOException {
        ApiMovies apiMovies = new ApiMovies();
        List<Pelicula> pelis = apiMovies.obtenerPeliculas();

        Sala sala1 = new Sala(1, 30);
        sala1.prepararButacas(horarios.get(0), pelis.get(0));
        sala1.prepararButacas(horarios.get(1), pelis.get(1));
        sala1.prepararButacas(horarios.get(2), pelis.get(2));
        sala1.prepararButacas(horarios.get(3), pelis.get(3));
        this.salas.add(sala1);

        Sala sala2 = new Sala(2, 30);
        sala2.prepararButacas(horarios.get(0), pelis.get(4));
        sala2.prepararButacas(horarios.get(1), pelis.get(5));
        sala2.prepararButacas(horarios.get(2), pelis.get(6));
        sala2.prepararButacas(horarios.get(3), pelis.get(7));
        this.salas.add(sala2);

        Sala sala3 = new Sala(3, 30);
        sala3.prepararButacas(horarios.get(0), pelis.get(8));
        sala3.prepararButacas(horarios.get(1), pelis.get(9));
        sala3.prepararButacas(horarios.get(2), pelis.get(10));
        sala3.prepararButacas(horarios.get(3), pelis.get(11));
        this.salas.add(sala3);

        Sala sala4 = new Sala(4, 30);
        sala4.prepararButacas(horarios.get(0), pelis.get(12));
        sala4.prepararButacas(horarios.get(1), pelis.get(13));
        sala4.prepararButacas(horarios.get(2), pelis.get(14));
        sala4.prepararButacas(horarios.get(3), pelis.get(15));
        this.salas.add(sala4);

        Sala sala5 = new Sala(5, 30);
        sala5.prepararButacas(horarios.get(0), pelis.get(16));
        sala5.prepararButacas(horarios.get(1), pelis.get(17));
        sala5.prepararButacas(horarios.get(2), pelis.get(18));
        sala5.prepararButacas(horarios.get(3), pelis.get(19));
        this.salas.add(sala5);
    }

    public Sala buscarSalaXPelicula(Pelicula peliculaBuscada) throws NullPointerException{
        try{
            for(Sala salaBuscada : this.salas) {
                System.out.println("N° sala: " + salaBuscada.getNumeroSala());
                System.out.println("Pelicula buscada: " + peliculaBuscada.getTitulo());
                for(Butaca butaca : salaBuscada.getButacas()) {
                    if(butaca.getPelicula().getTitulo().equals(peliculaBuscada.getTitulo())) {
                        return salaBuscada;
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            System.out.println("No se puede obtener la Sala con la Película buscada.");
            return null;
        }
    }
}
