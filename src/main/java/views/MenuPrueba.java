package views;

import domain.business.Cinema;
import domain.business.Cliente;
import domain.business.Ticket;
import domain.business.comestibles.*;
import domain.business.pelicula.Pelicula;
import domain.security.Usuario;

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
            opcionElegida = entrada.nextInt();

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

        /*System.out.print("Ingrese un precio estandar: ");
        double precio = entrada.nextInt();

        Combo combo = new Combo();
        combo.setArticulo("Combo Pochoclos");

        Balde balde = new Balde(precio);
        Comestible comestible1 = new Comestible(balde);
        comestible1.setArticulo("Baldecito");
        System.out.println("El valor de " + comestible1.getArticulo() + " es $" + comestible1.obtenerPrecio());
        combo.agregarProducto(comestible1);

        Carton carton = new Carton(precio);
        Comestible comestible2 = new Comestible(carton);
        comestible2.setArticulo("Cartoncito");
        System.out.println("El valor de " + comestible2.getArticulo() + " es $" + comestible2.obtenerPrecio());
        combo.agregarProducto(comestible2);

        Bolsita bolsita = ;
        Comestible comestible3 = new Comestible(new Bolsita(precio));
        comestible3.setArticulo("Bolsita");
        System.out.println("El valor de " + comestible3.getArticulo() + " es $" + comestible3.obtenerPrecio());
        combo.agregarProducto(comestible3);

        Combo combo2 = new Combo();
        combo2.setArticulo("Combo chiquito");
        Bebida bebida = new Bebida(TipoBebida.AGUA, 300);
        Comestible comestible4 = new Comestible(bebida);
        comestible4.setArticulo("Agua");
        System.out.println("El valor de " + comestible4.getArticulo() + " es $" + comestible4.obtenerPrecio());
        combo2.agregarProducto(comestible4);
        //combo.agregarProducto(combo2);
        System.out.println("El valor de " + combo2.getArticulo() + " es $" + combo2.obtenerPrecio());
        System.out.println("Los productos del " + combo2.getArticulo() + " son: " + combo2.obtenerProductosDelCombo());

        System.out.println("El valor de " + combo.getArticulo() + " es $" + combo.obtenerPrecio());
        System.out.println("Los productos del " + combo.getArticulo() + " son: " + combo.obtenerProductosDelCombo());


        Ticket nuevoTicket = new Ticket();
        nuevoTicket.agregarProductoATicket(combo);
        nuevoTicket.agregarProductoATicket(combo2);
        nuevoTicket.generarTicket();

        System.out.println("ID Ticket: " + nuevoTicket.getIdTicket());
        System.out.println("Hora Creación: " + nuevoTicket.getHoraCreacion());
        System.out.println("Fecha Creación: " + nuevoTicket.getFechaCreacion());
        System.out.println("Precio Final: " + nuevoTicket.getPrecioTotal());
        System.out.println("Los productos que usted tiene en su ticket son:");
        nuevoTicket.obtenerProductos();*/

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
        while(!miCinema.existeUsuario(email, contrasenia)) {
            System.out.println("Datos incorrectos. Le quedan " + contador + " intentos restantes.");
            System.out.print("Ingrese su email: ");
            email = entrada.nextLine();
            System.out.print("Ingrese su contraseña: ");
            contrasenia = entrada.nextLine();
            contador--;
            if(contador == 0) {
                System.out.println("Se acabaron los intentos, volviendo al Menú Principal...");
                return;
            }
        }
        Usuario usuarioLogin = miCinema.obtenerUsuario(email, contrasenia);

        if(usuarioLogin.getRol().puedoAdministrarPrecios()) {
            System.out.println("----ADMIN----");
            this.administrarPrecios();
        }
        else {
            System.out.println("----USER----");
            this.inicioCliente(usuarioLogin);
        }
    }

    public void crearUsuario() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;
        String contraseniaCheck;

        System.out.println("Ingrese un email para crear el usuario: ");
        String email = entrada.nextLine();

        while(miCinema.validarUsuario(email)) {
            System.out.println("Ese email ya está en uso. Ingrese otro email: ");
            email = entrada.nextLine();
        }

        System.out.println("Ingrese una contraseña: ");
        String contrasenia = entrada.nextLine();

        while(!miCinema.validarContrasenia(contrasenia)) {
            System.out.println("La contraseña no cumple con los parámetros de seguridad. Ingrese una nueva contraseña: ");
            contrasenia = entrada.nextLine();
        }

        do{
            System.out.println("Confirme la contraseña: ");
            contraseniaCheck = entrada.nextLine();
        }while(!contrasenia.equals(contraseniaCheck));

        Usuario nuevoUsuario = new Usuario(email, contrasenia);
        System.out.println("A continuación, le pediremos los datos para crear la Cuenta en nuestro Sistema: ");
        nuevoUsuario.setCliente(this.crearCliente(email));

        miCinema.agregarUsuario(nuevoUsuario);

    }

    private Cliente crearCliente(String email) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("    - Ingrese su nombre: ");
        String nombre = entrada.nextLine();
        System.out.println("    - Ingrese su apellido: ");
        String apellido = entrada.nextLine();
        System.out.println("    - Ingrese su fecha de nacimiento: ");
        String fechaNacimiento = entrada.nextLine();
        System.out.println("    - Ingrese su documento: ");
        int documento = entrada.nextInt();

        Cliente nuevoCliente = new Cliente(nombre, apellido, email, fechaNacimiento, documento);
        return nuevoCliente;
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
            System.out.println("• Para finalizar, ingrese 3.");

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

    private void consultarPrecios() {
        Cinema miCinema = Cinema.getInstance();

        System.out.println("Los precios de los Comestibles y la Entrada estándar son los siguientes:");
        System.out.println("    - Pochoclos: $" + miCinema.getPrecioPochoclos());
        System.out.println("    - Bebidas: $" + miCinema.getPrecioBebida());
        System.out.println("    - Nachos: $" + miCinema.getPrecioNachos());
        System.out.println("    - Precio estándar: $" + miCinema.getPrecioEntrada());
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
                    miCinema.setPrecioPochoclos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Pochoclos a $" + miCinema.getPrecioPochoclos());
                    break;
                case 2: // Cambiar precio Bebidas
                    System.out.print("Ingrese el nuevo precio de las Bebidas: $");
                    miCinema.setPrecioBebida(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de las Bebidas a $" + miCinema.getPrecioBebida());
                    break;
                case 3: // Cambiar precio Nachos
                    System.out.print("Ingrese el nuevo precio de los Nachos: $");
                    miCinema.setPrecioNachos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Nachos a $" + miCinema.getPrecioNachos());
                    break;
                case 4: // Cambiar precio estándar de Entradas
                    System.out.print("Ingrese el nuevo precio estándar de las Entradas: $");
                    miCinema.setPrecioEntrada(entrada.nextDouble());
                    System.out.println("Se actualizó el precio estándar de las Entradas a $" + miCinema.getPrecioEntrada());
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
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {

            System.out.println("• Si desea comprar una Entrada para una Película, ingrese 1.");
            System.out.println("• Si desea comprar alguna Bebida, Pochoclos, Nachos o un Combo, ingrese 2.");
            System.out.println("• Si desea finalizar, ingrese 3.");

            int opcionElegida = entrada.nextInt();

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
            System.out.println("    - Si desea consultar los precios, ingrese 1.");
            System.out.println("    - Si desea comprar solo un producto, ingrese 2.");
            System.out.println("    - Si desea preparar un Combo, ingrese 3.");

            int opcionElegida = entrada.nextInt();

            switch(opcionElegida) {
                case 1: // Mostrar Menú
                    this.consultarPrecios();
                    break;
                case 2: // Para comprar solamente un producto, sin necesidad de armar un Combo
                    producto = this.eleccionComestible();
                    salir = true;
                    break;
                case 3: // Preparar Combo
                    producto = this.prepararCombo();
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }

        System.out.println("¡Producto comprado!");
        System.out.println("Costo: $" + producto.obtenerPrecio());

        Ticket nuevoTicket = new Ticket();
        nuevoTicket.agregarProductoATicket(producto);
        nuevoTicket.generarTicket();
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

        int opcionElegida = entrada.nextInt();
        switch(opcionElegida) {
            case 1: // Comprar Bolsita
                comestible = new Comestible(new Bolsita(miCinema.getPrecioPochoclos()));
                comestible.setArticulo("Bolsita");
                break;
            case 2: // Comprar Carton
                comestible = new Comestible(new Carton(miCinema.getPrecioPochoclos()));
                comestible.setArticulo("Carton");
                break;
            case 3: // Comprar Balde
                comestible = new Comestible(new Balde(miCinema.getPrecioPochoclos()));
                comestible.setArticulo("Balde");
                break;
            case 4: // Comprar Bebida
                comestible = this.eleccionBebida();
                break;
            case 5: // Comprar Nachos
                comestible = new Comestible(new Nachos(miCinema.getPrecioNachos()));
                comestible.setArticulo("Nachos");
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

        int opcionElegida = entrada.nextInt();
        switch(opcionElegida){
            case 1:
                bebida = new Comestible(new Bebida(TipoBebida.SPRITE, miCinema.getPrecioBebida()));
                bebida.setArticulo("Sprite");
                break;
            case 2:
                bebida = new Comestible(new Bebida(TipoBebida.MANAOS, miCinema.getPrecioBebida()));
                bebida.setArticulo("Manaos");
                break;
            case 3:
                bebida = new Comestible(new Bebida(TipoBebida.PEPSI, miCinema.getPrecioBebida()));
                bebida.setArticulo("Pepsi");
                break;
            case 4:
                bebida = new Comestible(new Bebida(TipoBebida.AGUA, miCinema.getPrecioBebida()));
                bebida.setArticulo("Agua Mineral");
                break;
            case 5:
                bebida = new Comestible(new Bebida(TipoBebida.JUGO, miCinema.getPrecioBebida()));
                bebida.setArticulo("Jugo de Frutas");
                break;
            case 6:
                bebida = new Comestible(new Bebida(TipoBebida.MIRINDA, miCinema.getPrecioBebida()));
                bebida.setArticulo("Mirinda");
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

        System.out.println("-----PREPARACION COMBO-----");

        while(!salir) {

            System.out.println("    - Si desea agregar comida en el combo, ingrese 1.");
            System.out.println("    - Si desea agregar una bebida en el combo, ingrese 2.");
            System.out.println("    - Si desea finalizar, ingrese 3.");

            int opcionElegida = entrada.nextInt();
            switch(opcionElegida) {
                case 1:
                    Comestible comida = this.eleccionComestible();
                    combo.agregarProducto(comida);
                    break;
                case 2:
                    Comestible bebida = this.eleccionBebida();
                    combo.agregarProducto(bebida);
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

}
