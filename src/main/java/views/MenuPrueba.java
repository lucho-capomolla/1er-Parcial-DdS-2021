package views;

import domain.business.Cinema;
import domain.business.Cliente;
import domain.business.Ticket;
import domain.business.comestibles.*;
import domain.business.pelicula.Pelicula;
import domain.security.Usuario;
import domain.security.database.ClienteDAO;
import domain.security.database.UsuarioDAO;

import java.util.Scanner;

public class MenuPrueba {

    public void iniciarMenu() {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        int opcionElegida;
        Cinema miCinema = Cinema.getInstance();

        while(!salir) {
            System.out.println("---------------¡Bienvenido a Cinema!---------------");
            System.out.println("- Si desea iniciar sesión, ingrese 1.");
            System.out.println("- Si no tiene un usuario, puede crearlo ingresando 2.");
            System.out.println("- Si quiere ver las películas que estan en cartelera, ingrese 3.");
            System.out.println("- Si desea salir, ingrese 4.");
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
                case 4: // Salir del Sistema
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
        System.out.println("¡Gracias por utilizar Cinema! Lo esperamos nuevamente.");
    }

    public void iniciarSesion() {
        // Una vez que inicio sesion, voy a tener diferentes opciones de acuerdo al Rol que tenga
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        int contador = 3;

        System.out.print("Ingrese su email: ");
        String email = entrada.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasenia = entrada.nextLine();
        contador--;
        while(!miCinema.validarUsuario(email, contrasenia)) {
            System.out.println("Datos incorrectos. Le quedan " + contador + " intentos restantes.");
            System.out.print("Ingrese su email: ");
            email = entrada.nextLine();
            System.out.print("Ingrese su contraseña: ");
            contrasenia = entrada.nextLine();

            if(contador == 0) {
                System.out.println("Se acabaron los intentos, volviendo al Menú Principal...");
                return;
            }
            contador--;
        }
        Usuario usuarioLogin = miCinema.buscarUsuario(email);
        ClienteDAO clienteDAO = new ClienteDAO();
        //usuarioLogin.setCliente(clienteDAO.buscarCliente(email));

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
        String email = entrada.nextLine();

        while(miCinema.buscarUsuario(email) != null) {
            System.out.println("Ese email ya está en uso. Ingrese otro email: ");
            email = entrada.nextLine();
        }

        System.out.println("Ingrese una contraseña: ");
        String contrasenia = entrada.nextLine();

        while(!miCinema.validarContrasenia(contrasenia)) {
            System.out.println("La contraseña no cumple con los parámetros de seguridad. Ingrese una nueva contraseña: ");
            contrasenia = entrada.nextLine();
        }

        System.out.println("Confirme la contraseña: ");
        String contraseniaCheck = entrada.nextLine();

        while(!contrasenia.equals(contraseniaCheck)){
            System.out.println("La contraseña no coincide. Confirme la contraseña nuevamente: ");
            contraseniaCheck = entrada.nextLine();
        };

        System.out.println("A continuación, le pediremos los datos para crear la Cuenta en nuestro Sistema: ");
        Usuario nuevoUsuario = miCinema.crearUsuario(email, contrasenia);

        ClienteDAO clienteDAO = new ClienteDAO();
        int idCliente = this.crearCliente(email);
        nuevoUsuario.setCliente(clienteDAO.buscarCliente(idCliente));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.insertIDCliente(idCliente);
        miCinema.agregarUsuario(nuevoUsuario);

    }

    private int crearCliente(String email) {
        Scanner entrada = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();

        System.out.println("    - Ingrese su nombre: ");
        String nombre = entrada.nextLine();
        System.out.println("    - Ingrese su apellido: ");
        String apellido = entrada.nextLine();
        System.out.println("    - Ingrese su fecha de nacimiento: ");
        String fechaNacimiento = entrada.nextLine();
        System.out.println("    - Ingrese su documento: ");
        int documento = entrada.nextInt();

        return clienteDAO.crearCliente(email, nombre, apellido, fechaNacimiento, documento);
       // Cliente nuevoCliente = new Cliente(nombre, apellido, email, fechaNacimiento, documento);
       // return nuevoCliente;
    }


    public void mostrarPeliculas() {
        int contador = 1;
        Cinema miCinema = Cinema.getInstance();
        for(Pelicula pelicula : miCinema.getCartelera()) {
            System.out.println("Película " + contador + ":");
            System.out.println("    - Titulo: " + pelicula.getTitulo());
            System.out.println("    - Genero: " + pelicula.getGenero());
            System.out.println("    - Duración en minutos: " + pelicula.getDuracionEnMin());
            System.out.println("    - Fecha de Estreno: " + pelicula.getFechaDeEstreno());
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
            System.out.println("• Para consultar los precios de los Comestibles y la Entrada, ingrese 1.");
            System.out.println("• Para cambiar los precios, ingrese 2.");
            System.out.println("• Para volver al Menú Principal, ingrese 3.");

            switch(entrada.nextInt()) {
                case 1: // Consultar precios
                    this.consultarPrecios();
                    break;
                case 2: // Cambiar precios
                    this.cambiarPrecios();
                    break;
                case 3: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    public void consultarPrecios() {
        Cinema miCinema = Cinema.getInstance();

        System.out.println("Los precios de los Comestibles y la Entrada estándar son los siguientes:");
        System.out.println("    - Pochoclos: $" + miCinema.obtenerPrecioPochoclos());
        System.out.println("    - Bebidas: $" + miCinema.obtenerPrecioBebidas());
        System.out.println("    - Nachos: $" + miCinema.obtenerPrecioNachos());
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
            System.out.println("• Para cambiar el precio estándar de las entradas, ingrese 4.");
            System.out.println("• Para finalizar, ingrese 5.");

            switch(entrada.nextInt()) {
                case 1: // Cambiar precio Pochoclos
                    System.out.print("Ingrese el nuevo precio de los Pochoclos: $");
                    miCinema.cambiarPrecioPochoclos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Pochoclos a $" + miCinema.obtenerPrecioPochoclos());
                    break;
                case 2: // Cambiar precio Bebidas
                    System.out.print("Ingrese el nuevo precio de las Bebidas: $");
                    miCinema.cambiarPrecioBebidas(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de las Bebidas a $" + miCinema.obtenerPrecioBebidas());
                    break;
                case 3: // Cambiar precio Nachos
                    System.out.print("Ingrese el nuevo precio de los Nachos: $");
                    miCinema.cambiarPrecioNachos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Nachos a $" + miCinema.obtenerPrecioNachos());
                    break;
                case 4: // Cambiar precio estándar de Entradas
                    System.out.print("Ingrese el nuevo precio estándar de las Entradas: $");
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
    private void inicioCliente(Usuario usuarioLogin) {
        Scanner entrada = new Scanner(System.in);
        //Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {

            System.out.println("-----------------------MENÚ PRINCIPAL------------------------");
            System.out.println("• Si desea comprar una Entrada para una Película, ingrese 1.");
            System.out.println("• Si desea comprar alguna Bebida, Pochoclos, Nachos o un Combo, ingrese 2.");
            System.out.println("• Si desea cerrar sesión, ingrese 3.");
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
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    private void comprarEntrada(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        //Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {



/*
 ToDo: para elegir una pelicula, tendria que listar todas las peliculas, y si la quiero elegir:
    A) Escribo el Titulo de la Pelicula (lo malo es si uno lo escribe mal)
    B) Listar las peliculas, y medio que "hardcodear", de forma que elija mediante un numero la pelicula deseada
 */
            int opcionElegida = entrada.nextInt();
            switch(opcionElegida) {
                case 1: // Mostrar peliculas en Cartelera
                    this.mostrarPeliculas();
                    break;
                case 2: // Elegir una de esas peliculas para comprar la Entrada
                    break;
                case 3: // Salir
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
            System.out.println("    - Si desea consultar los Precios, ingrese 1.");
            System.out.println("    - Si desea comprar solo un Producto, ingrese 2.");
            System.out.println("    - Si desea preparar un Combo, ingrese 3.");
            System.out.println("    - Si desea volver al Menú Principal, ingrese 4.");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1: // Mostrar Menú
                    this.consultarPrecios();
                    break;
                case 2: // Para comprar solamente un producto, sin necesidad de armar un Combo
                    producto = this.eleccionComestible();
                    if(producto != null) {
                        salir = true;
                    }
                    break;
                case 3: // Preparar Combo
                    producto = this.prepararCombo();
                    //salir = true;
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }

/*
 TODO:   - A) acumular todo en un carrito de compras del cliente, y pagar al final
         - B) pagar cada producto a medida que lo va "comprando"
 */



        //this.debitar(producto);
        System.out.println("¡Producto comprado!");
        System.out.println("Costo: $" + producto.obtenerPrecio());

        Cinema miCinema = Cinema.getInstance();

        Ticket nuevoTicket = new Ticket();
        nuevoTicket.agregarProductoATicket(producto);
        nuevoTicket.generarTicket();
        miCinema.agregarTicket(nuevoTicket);
        cliente.comprar(nuevoTicket);

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
        combo.setArticulo("Combo Bestia Cinema");

        while(!salir) {

            System.out.println("-------PREPARACION COMBO-------");
            System.out.println("    - Si desea agregar Comida/Bebida en el combo, ingrese 1.");
            System.out.println("    - Si no quiere realizar un Combo, ingrese 2.");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1:
                    Comestible comida = this.eleccionComestible();
                    combo.agregarProducto(comida);
                    break;
                case 2:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }

        return combo;
    }

}
