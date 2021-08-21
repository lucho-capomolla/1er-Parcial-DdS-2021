package views;

import domain.business.*;
import domain.business.comestibles.*;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.ApiMovies;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;
import domain.business.pelicula.apiPelicula.entidades.Movie;
import domain.security.User;
import domain.security.Usuario;
import domain.database.ClienteDAO;
import domain.database.UsuarioDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class MenuInicio {
    private AdminView adminView = AdminView.getInstance();
    private UserView userView = UserView.getInstance();

    public void iniciarMenu() throws IOException {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        miCinema.prepararCine();

        boolean salir = false;
        int opcionElegida;

        while(!salir) {
            System.out.println("---------------¡Bienvenido a Cinema!---------------");
            System.out.println("- Si desea Iniciar Sesión, ingrese 1.");
            System.out.println("- Si no tiene un Usuario, puede Crearlo ingresando 2.");
            System.out.println("- Si quiere Consultar las películas que estan en Cartelera, ingrese 3.");
            System.out.println("- Si quiere Consultar los próximos Estrenos, ingrese 4.");
            System.out.println("- Si desea Salir, ingrese 5.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
            System.out.println("-----------------------------------------------------");

            switch(opcionElegida) {
                case 1: // Iniciar Sesion
                    this.iniciarSesion();
                    break;
                case 2: // Crear Usuario
                    this.crearUsuario();
                    break;
                case 3: // Mostrar Peliculas en Cartelera
                    userView.mostrarPeliculas();
                    break;
                case 4: // Mostrar Próximos Estrenos
                    this.mostrarProximosEstrenos();
                    break;
                case 5: // Salir del Sistema
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING] La opción que ha elegido es incorrecta.");
                    break;
            }
        }
        System.out.println("¡Gracias por utilizar Cinema! Lo esperamos nuevamente.");
        exit(0);
    }

    public void iniciarSesion() throws IOException {
        // Una vez que inicio sesion, voy a tener diferentes opciones de acuerdo al Rol que tenga
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        int contador = 3;

        System.out.print("Ingrese su email: ");
        System.out.print("> ");
        String email = entrada.nextLine();
        System.out.print("Ingrese su contraseña: ");
        System.out.print("> ");
        String contrasenia = entrada.nextLine();
        contador--;
        while(!miCinema.validarUsuario(email, contrasenia)) {
            System.out.println("Datos incorrectos. Le quedan " + contador + " intentos restantes.");
            System.out.print("Ingrese su email: ");
            System.out.print("> ");
            email = entrada.nextLine();
            System.out.print("Ingrese su contraseña: ");
            System.out.print("> ");
            contrasenia = entrada.nextLine();

            if(contador == 0) {
                System.out.println("[ERROR] Se acabaron los intentos, volviendo al Menú Principal...");
                return;
            }
            contador--;
        }
        Usuario usuarioLogin = miCinema.buscarUsuario(email);
        ClienteDAO clienteDAO = new ClienteDAO();

        if(usuarioLogin.getRol().puedoAdministrarPrecios()) {
            // Es para indicar que ingresaste como ADMIN
            System.out.println("");
            System.out.println("                       -----ADMIN-----");
            adminView.inicioAdmin();
        }
        else {
            // Es para indicar que ingresaste como USER
            System.out.println("");
            System.out.println("                       -----USER-----");
            userView.inicioCliente(usuarioLogin);
        }
    }

    private void crearUsuario() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        System.out.println("Ingrese un email para crear el usuario: ");
        System.out.print("> ");
        String email = entrada.nextLine();

        while(miCinema.buscarUsuario(email) != null) {
            System.out.println("Ese email ya está en uso. Ingrese otro email: ");
            System.out.print("> ");
            email = entrada.nextLine();
        }

        System.out.println("Ingrese una contraseña: ");
        System.out.print("> ");
        String contrasenia = entrada.nextLine();

        while(!miCinema.validarContrasenia(contrasenia)) {
            System.out.println("La contraseña no cumple con los parámetros de seguridad. Ingrese una nueva contraseña: ");
            System.out.print("> ");
            contrasenia = entrada.nextLine();
        }

        System.out.println("Confirme la contraseña: ");
        System.out.print("> ");
        String contraseniaCheck = entrada.nextLine();

        while(!contrasenia.equals(contraseniaCheck)){
            System.out.println("La contraseña no coincide. Confirme la contraseña nuevamente: ");
            System.out.print("> ");
            contraseniaCheck = entrada.nextLine();
        };

        System.out.println("A continuación, le pediremos los datos para crear la Cuenta en nuestro Sistema: ");
        Usuario nuevoUsuario = miCinema.crearUsuario(email, contrasenia);

        int idCliente = this.crearCliente(email);
        ClienteDAO clienteDAO = new ClienteDAO();
        nuevoUsuario.setCliente(clienteDAO.buscarCliente(idCliente));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.insertIDCliente(idCliente, email);
    }

    private int crearCliente(String email) {
        Scanner entrada = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();

        System.out.println("    - Ingrese su nombre: ");
        System.out.print("> ");
        String nombre = entrada.nextLine();
        System.out.println("    - Ingrese su apellido: ");
        System.out.print("> ");
        String apellido = entrada.nextLine();
        System.out.println("    - Ingrese su fecha de nacimiento: ");
        System.out.print("> ");
        String fechaNacimiento = entrada.nextLine();
        System.out.println("    - Ingrese su documento: ");
        System.out.print("> ");
        int documento = entrada.nextInt();

        return clienteDAO.crearCliente(email, nombre, apellido, fechaNacimiento, documento);
    }

    public void mostrarProximosEstrenos() throws IOException {
        int contador = 0;
        ApiMovies apiMovies = new ApiMovies();
        List<Pelicula> estrenos = apiMovies.obtenerEstrenos();

        for(Pelicula pelicula : estrenos) {
            System.out.println("ID Película: " + contador);
            System.out.println("    - Titulo: " + pelicula.getTitulo());
            System.out.println("    - Título original: " + pelicula.getTituloOriginal());
            System.out.println("    - Trama: " + pelicula.getTrama());
            System.out.println("    - Genero: " + pelicula.getGeneros());
            System.out.println("    - Fecha de Estreno: " + pelicula.getFechaDeEstreno());
            System.out.println("    - Lenguaje original: " + pelicula.getIdioma());
            System.out.println();
            contador++;
        }
    }

}
