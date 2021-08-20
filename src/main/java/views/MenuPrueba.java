package views;

import domain.business.*;
import domain.business.comestibles.*;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.ApiMovies;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;
import domain.business.pelicula.apiPelicula.entidades.Movie;
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

import static java.lang.System.exit;

public class MenuPrueba {

    public void iniciarMenu() throws IOException {
        Scanner entrada = new Scanner(System.in);
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
                    this.mostrarPeliculas();
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
            this.administrarPrecios();
        }
        else {
            // Es para indicar que ingresaste como USER
            System.out.println("");
            System.out.println("                       -----USER-----");
            this.inicioCliente(usuarioLogin);
        }
    }

    public void crearUsuario() {
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


    public void mostrarPeliculas() throws IOException {
        int contador = 0;
        ApiMovies apiMovies = new ApiMovies();
        List<Pelicula> pelis = apiMovies.obtenerPeliculas();

        for(Pelicula pelicula : pelis) {
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

    // SIENDO ADMIN
    private void administrarPrecios() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {
            System.out.println("---------------------------MENU PRINCIPAL--------------------------");
            System.out.println("• Para consultar los precios de los Comestibles, ingrese 1.");
            System.out.println("• Para consultar el precio de la Entrada, ingrese 2.");
            System.out.println("• Para cambiar los precios, ingrese 3.");
            System.out.println("• Para volver al Menú Principal, ingrese 4.");
            System.out.print("> ");
            System.out.print("");
            switch(entrada.nextInt()) {
                case 1: // Consultar precios Comestibles
                    this.consultarPreciosComestibles();
                    break;
                case 2: // Consultar precio Entrada
                    this.consultarPrecioEntrada();
                    break;
                case 3: // Cambiar precios
                    this.cambiarPrecios();
                    break;
                case 4: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    public void consultarPreciosComestibles() {
        Cinema miCinema = Cinema.getInstance();
        System.out.println("Los precios de los Comestibles son los siguientes:");
        System.out.println("    - Pochoclos: $" + miCinema.obtenerPrecioPochoclos());
        System.out.println("    - Bebidas: $" + miCinema.obtenerPrecioBebidas());
        System.out.println("    - Nachos: $" + miCinema.obtenerPrecioNachos());
        System.out.println();
    }

    public void consultarPrecioEntrada() {
        Cinema miCinema = Cinema.getInstance();
        System.out.println("El precio de la Entrada estándar es el siguiente:");
        System.out.println("    - Precio estándar de las Entradas: $" + miCinema.obtenerPrecioEntrada());
        System.out.println();
    }

    private void cambiarPrecios() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {
            System.out.println("• Para cambiar el precio de los Pochoclos, ingrese 1.");
            System.out.println("• Para cambiar el precio de las Bebidas, ingrese 2.");
            System.out.println("• Para cambiar el precio de los Nachos, ingrese 3.");
            System.out.println("• Para cambiar el precio estándar de las Entradas, ingrese 4.");
            System.out.println("• Para finalizar, ingrese 5.");
            System.out.print("> ");

            switch(entrada.nextInt()) {
                case 1: // Cambiar precio Pochoclos
                    System.out.println("Ingrese el nuevo precio de los Pochoclos: $");
                    System.out.print("> ");
                    miCinema.cambiarPrecioPochoclos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Pochoclos a $" + miCinema.obtenerPrecioPochoclos());
                    break;
                case 2: // Cambiar precio Bebidas
                    System.out.println("Ingrese el nuevo precio de las Bebidas: $");
                    System.out.print("> ");
                    miCinema.cambiarPrecioBebidas(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de las Bebidas a $" + miCinema.obtenerPrecioBebidas());
                    break;
                case 3: // Cambiar precio Nachos
                    System.out.println("Ingrese el nuevo precio de los Nachos: $");
                    System.out.print("> ");
                    miCinema.cambiarPrecioNachos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Nachos a $" + miCinema.obtenerPrecioNachos());
                    break;
                case 4: // Cambiar precio estándar de Entradas
                    System.out.println("Ingrese el nuevo precio estándar de las Entradas: $");
                    System.out.print("> ");
                    miCinema.cambiarPrecioEntrada(entrada.nextDouble());
                    System.out.println("Se actualizó el precio estándar de las Entradas a $" + miCinema.obtenerPrecioEntrada());
                    break;
                case 5: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }


    // SIENDO CLIENTE
    private void inicioCliente(Usuario usuarioLogin) throws IOException {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        while(!salir) {
            System.out.println("-----------------------MENÚ PRINCIPAL------------------------");
            System.out.println("• Si desea comprar una Entrada para una Película, ingrese 1.");
            System.out.println("• Si desea comprar alguna Bebida, Pochoclos, Nachos o un Combo, ingrese 2.");
            System.out.println("• Si desea Cerrar Sesión, ingrese 3.");
            System.out.print("> ");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1: //Comprar Entrada
                    this.comprarEntrada(usuarioLogin.getCliente());
                    break;
                case 2: // Comprar Comida,Bebida o armar un Combo
                    this.comprarComestibles(usuarioLogin.getCliente());
                    break;
                case 3: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING] La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    private void comprarEntrada(Cliente cliente) throws IOException {
        Scanner entrada = new Scanner(System.in);
        //Cinema miCinema = Cinema.getInstance();
        boolean salir = false;
        ApiMovies apiMovies = new ApiMovies();
        List<Pelicula> pelis = apiMovies.obtenerPeliculas();
        int cantidadPeliculas = pelis.size();
        List<Entrada> entradasPeliculas = new ArrayList<>();

        while(!salir) {

            System.out.println("--------CINE--------");
            System.out.println("    - Si desea consultar las Películas que están en Cartelera, ingrese 1.");
            System.out.println("    - Si desea consultar el precio de la Entrada, ingrese 2.");
            System.out.println("    - Si desea comprar una Entrada, ingrese 3.");
            System.out.println("    - Si desea volver al Menú Principal, ingrese 4.");
            System.out.print("> ");
            int opcionElegida = entrada.nextInt();

            switch(opcionElegida) {
                case 1: // Mostrar peliculas en Cartelera
                    this.mostrarPeliculas();
                    break;
                case 2: // Consultar el precio de la Entrada
                    this.consultarPrecioEntrada();
                    break;
                case 3: // Elegir una de esas peliculas para comprar la Entrada
                    System.out.println("Ingrese el ID de la Película que desea ver: ");
                    System.out.print("> ");
                    int peliculaElegida = entrada.nextInt();

                    if(0 > peliculaElegida || peliculaElegida > cantidadPeliculas-1) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Ingrese el ID de la Película que desea ver: ");
                        System.out.print("> ");
                        peliculaElegida = entrada.nextInt();
                    }

                    System.out.println("Usted ha elegido la película: " + pelis.get(peliculaElegida).getTitulo());
                    System.out.println("¿Está seguro de la elección?");
                    System.out.println("    - Ingrese 1 para continuar con la compra.");
                    System.out.println("    - Ingrese 2 para volver atrás.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();

                    if(0 > opcionElegida || opcionElegida > 2) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Usted ha elegido la película: " + pelis.get(peliculaElegida).getTitulo());
                        System.out.println("¿Está seguro de la elección?");
                        System.out.println("    - Ingrese 1 para continuar con la compra.");
                        System.out.println("    - Ingrese 2 para volver atrás.");
                        System.out.print("> ");
                        opcionElegida = entrada.nextInt();
                    }

                    if(opcionElegida == 2) {
                        break;
                    }
/*
TODO: Elegir un horario, el cual esta matcheado con una sala, elegir la cantidad de entradas y los asientos
 luego de esas elecciones, tiene la opcion de comprar algun comestible, o realizar la compra de las entradas
 */

                    System.out.print("¿Cuantas entradas desea comprar?: ");
                    int cantidadEntradas = entrada.nextInt();

                    if(cantidadEntradas == 0) {
                        System.out.println("[WARNING] No puede ingresar 0 como cantidad de entradas.");
                        System.out.print("¿Cuantas entradas desea comprar?: ");
                        cantidadEntradas = entrada.nextInt();
                    }

                    for(int c=0; c<cantidadEntradas; c++){
                        Entrada entradaPelicula = new Entrada();
                        entradaPelicula.setPelicula(pelis.get(peliculaElegida));

                        //this.elegirButacas();
                        entradaPelicula.setButaca(new Butaca(1));
                        entradaPelicula.setSala(1);

                        //this.elegirHorario();
                        entradaPelicula.setHorarioFuncion("10:00");

                        entradaPelicula.setFechaEmision(LocalDate.now());

                        this.agregarAlCarritoEntradas(cliente, entradaPelicula);
                        entradasPeliculas.add(entradaPelicula);
                    }
                    this.efectuarCompraEntrada(entradasPeliculas, cliente);

                    break;
                case 4: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }


    private void comprarComestibles(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        Producto producto = null;
        boolean salir = false;

        while(!salir) {
            System.out.println("--------COMPRAS--------");
            System.out.println("    - Si desea consultar los Precios de los Comestibles, ingrese 1.");
            System.out.println("    - Si desea comprar solo un Producto, ingrese 2.");
            System.out.println("    - Si desea preparar un Combo, ingrese 3.");
            System.out.println("    - Si desea volver al Menú Principal, ingrese 4.");
            System.out.print("> ");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1: // Mostrar Menú
                    this.consultarPreciosComestibles();
                    break;
                case 2: // Para comprar solamente un producto, sin necesidad de armar un Combo
                    producto = this.eleccionComestible();

                    System.out.println("Usted ha elegido: " + producto.getArticulo());
                    System.out.println("¿Está seguro de la elección?");
                    System.out.println("    - Ingrese 1 para continuar con la compra.");
                    System.out.println("    - Ingrese 2 para volver atrás.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();

                    if(0 > opcionElegida || opcionElegida > 2) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Usted ha elegido: " + producto.getArticulo());
                        System.out.println("¿Está seguro de la elección?");
                        System.out.println("    - Ingrese 1 para continuar con la compra.");
                        System.out.println("    - Ingrese 2 para volver atrás.");
                        System.out.print("> ");
                        opcionElegida = entrada.nextInt();
                    }

                    if(opcionElegida == 2) {
                        producto = null;
                        break;
                    }

                    if(producto != null) {
                        this.agregarAlCarritoComestible(cliente, producto);
                        salir = true;
                    }
                    break;
                case 3: // Preparar Combo
                    producto = this.prepararCombo();

                    if(producto != null) {
                        this.agregarAlCarritoComestible(cliente, producto);
                        salir = true;
                    }
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING] La opción que ha elegido es incorrecta.");
                    break;
            }
            this.efectuarCompraComestible(producto, cliente);
        }
    }

    private Comestible eleccionComestible() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        Comestible comestible = null;

        System.out.println("-----COMPRA COMIDA-----");
        System.out.println("    - Si desea comprar una Bolsita de Pochoclos, ingrese 1.");
        System.out.println("    - Si desea comprar un Carton de Pochoclos, ingrese 2.");
        System.out.println("    - Si desea comprar un Balde de Pochoclos, ingrese 3.");
        System.out.println("    - Si desea comprar una Bebida, ingrese 4.");
        System.out.println("    - Si desea comprar Nachos, ingrese 5.");
        System.out.println("    - Si no quiere comprar nada, ingrese 6.");
        System.out.print("> ");
        int opcionElegida = entrada.nextInt();
        System.out.println("");

        switch(opcionElegida) {
            case 1: // Comprar Bolsita
                comestible = new Comestible(new Bolsita(miCinema.obtenerPrecioPochoclos()));
                comestible.setArticulo("Bolsita");
                break;
            case 2: // Comprar Carton
                comestible = new Comestible(new Carton(miCinema.obtenerPrecioPochoclos()));
                comestible.setArticulo("Carton");
                break;
            case 3: // Comprar Balde
                comestible = new Comestible(new Balde(miCinema.obtenerPrecioPochoclos()));
                comestible.setArticulo("Balde");
                break;
            case 4: // Comprar Bebida
                comestible = this.eleccionBebida();
                break;
            case 5: // Comprar Nachos
                comestible = new Comestible(new Nachos(miCinema.obtenerPrecioNachos()));
                comestible.setArticulo("Nachos");
                break;
            case 6:
                break;
            default:
                System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                break;
        }
        return comestible;
    }


    private Comestible eleccionBebida() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        Comestible bebida = null;

        System.out.println("-----COMPRA BEBIDA-----");
        System.out.println("    - Si desea comprar una Sprite, ingrese 1.");
        System.out.println("    - Si desea comprar una Manaos, ingrese 2.");
        System.out.println("    - Si desea comprar una Pepsi, ingrese 3.");
        System.out.println("    - Si desea comprar un Agua Mineral, ingrese 4.");
        System.out.println("    - Si desea comprar un Jugo de Frutas, ingrese 5.");
        System.out.println("    - Si desea comprar una Mirinda, ingrese 6.");
        System.out.println("    - Si no quiere comprar nada, ingrese 7.");
        System.out.print("> ");
        int opcionElegida = entrada.nextInt();
        System.out.println("");

        switch(opcionElegida){
            case 1:
                bebida = new Comestible(new Bebida(TipoBebida.SPRITE, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Sprite");
                break;
            case 2:
                bebida = new Comestible(new Bebida(TipoBebida.MANAOS, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Manaos");
                break;
            case 3:
                bebida = new Comestible(new Bebida(TipoBebida.PEPSI, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Pepsi");
                break;
            case 4:
                bebida = new Comestible(new Bebida(TipoBebida.AGUA, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Agua Mineral");
                break;
            case 5:
                bebida = new Comestible(new Bebida(TipoBebida.JUGO, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Jugo de Frutas");
                break;
            case 6:
                bebida = new Comestible(new Bebida(TipoBebida.MIRINDA, miCinema.obtenerPrecioBebidas()));
                bebida.setArticulo("Mirinda");
                break;
            case 7:
                break;
            default:
                System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                break;
        }
        return bebida;
    }

    private Producto prepararCombo() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;
        Combo combo = new Combo();
        combo.setArticulo("#Combo: ");


        System.out.println("-------PREPARACION COMBO-------");
        while(!salir) {

            System.out.println("    - Si desea agregar Comida/Bebida en el combo, ingrese 1.");
            System.out.println("    - Si no quiere realizar un Combo, ingrese 2.");
            System.out.print("> ");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1:
                    Comestible comida = this.eleccionComestible();
                    combo.agregarProducto(comida);
                    combo.setArticulo(combo.getArticulo().concat(comida.getArticulo()) + " | ");
                    System.out.println("    - Si desea finalizar el Combo, ingrese 3.");
                    break;
                case 2:
                    combo = null;
                    salir = true;
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
        return combo;
    }

    private void agregarAlCarritoComestible(Cliente cliente, Producto producto) {
        System.out.println("Se agregó " + producto.getArticulo() + " a su Carrito de Compras.");
        System.out.println("    - Costo total: $" + producto.obtenerPrecio());
        cliente.agregarAlCarrito(producto);
    }

    private void efectuarCompraComestible(Producto producto, Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        int opcionElegida;

        if(producto != null) {
            System.out.println("$$$ Para efectuar la compra, ingrese 1 $$$");
            System.out.println("De lo contrario, se limpiara el carrito.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
            if(opcionElegida == 1) {
                this.debitarComestibles(cliente);
            }
            else {
                cliente.setCarrito(new ArrayList<>());
            }
        }
    }

    // TODO: falta meterlo en el Diagrama de clases
    private void debitarComestibles(Cliente cliente) {
        Cinema miCinema = Cinema.getInstance();
        Ticket nuevoTicket = new Ticket();
        String nombreArticulo = new String();
        System.out.println("¡Productos comprados!:");
        for(Producto producto : cliente.obtenerCarrito()){
            System.out.println(" - " + producto.getArticulo());
            System.out.println(" - Costo: $" + producto.obtenerPrecio());
            nuevoTicket.agregarProductoATicket(producto);
            nombreArticulo = nombreArticulo.concat(producto.getArticulo());
        }

        System.out.println(nombreArticulo);
        System.out.println("Costo Final: $" + nuevoTicket.obtenerPrecioFinal());
        nuevoTicket.generarTicket(nombreArticulo);
        miCinema.agregarTicket(nuevoTicket);
        cliente.setCarrito(new ArrayList<>());
        //cliente.comprar(nuevoTicket);
    }


    private void agregarAlCarritoEntradas(Cliente cliente, Entrada entrada) {
        Cinema miCinema = Cinema.getInstance();
        System.out.println("Se agregó la Entrada de " + entrada.getPelicula().getTitulo() + " a su Carrito de Compras.");
        System.out.println("    - Costo total: $" + miCinema.obtenerPrecioEntrada());
        cliente.agregarEntrada(entrada);
    }

    private void efectuarCompraEntrada(List<Entrada> entradasPeliculas, Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        int opcionElegida;

        if(entradasPeliculas != null) {
            System.out.println("$$$ Para efectuar la compra, ingrese 1 $$$");
            System.out.println("De lo contrario, se limpiara el carrito.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
            if(opcionElegida == 1) {
                this.debitarEntradas(cliente);
            }
            else {
                cliente.setEntradas(new ArrayList<>());
            }
        }
    }

    private void debitarEntradas(Cliente cliente) {
        Cinema miCinema = Cinema.getInstance();
        Ticket nuevoTicket = new Ticket();
        String nombreArticulo = new String();
        System.out.println("¡Productos comprados!:");
        Entrada entrada = cliente.obtenerEntradas().get(0);
        int cantidadEntradas = cliente.obtenerEntradas().size();

        for(int i=0; i<cantidadEntradas; i++){

            System.out.println(" - " + entrada.getPelicula().getTitulo());
            System.out.println(" - Costo: $" + entrada.obtenerPrecio());
            nuevoTicket.agregarEntradaATicket(entrada);
        }
        nombreArticulo = nombreArticulo.concat(entrada.getPelicula().getTitulo() + " x " + cantidadEntradas);

        nuevoTicket.generarTicket(nombreArticulo);
        miCinema.agregarTicket(nuevoTicket);
        cliente.setEntradas(new ArrayList<>());
        //cliente.comprar(nuevoTicket);
    }

}
