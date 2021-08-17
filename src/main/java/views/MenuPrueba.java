package views;

import domain.business.Cinema;
import domain.business.Ticket;
import domain.business.comestibles.*;
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
        System.out.println("¡Gracias por utilizar Cinema!");

        System.out.print("Ingrese un precio estandar: ");
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

        Bolsita bolsita = new Bolsita(precio);
        Comestible comestible3 = new Comestible(bolsita);
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
        nuevoTicket.obtenerProductos();

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
            this.administrarPrecios();
        }
        else {
            this.inicioCliente();
        }


    }

    public void crearUsuario() {
        Scanner entrada = new Scanner(System.in);
    }

    public void mostrarPeliculas() {

    }

    private void administrarPrecios() {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;

        while(!salir) {
            System.out.println("• Para consultar los precios de los Comestibles y la Entrada, ingrese 1.");
            System.out.println("• Para cambiar los precios, ingrese 2.");
            System.out.println("• Para finalizar, ingrese 3.");

            switch(entrada.nextInt()) {
                case 1:
                    System.out.println("Los precios de los Comestibles y la Entrada estándar son los siguientes:");
                    System.out.println("    - Pochoclos: $" + miCinema.getPrecioPochoclos());
                    System.out.println("    - Bebidas: $" + miCinema.getPrecioBebida());
                    System.out.println("    - Nachos: $" + miCinema.getPrecioNachos());
                    System.out.println("    - Precio estándar: $" + miCinema.getPrecioEntrada());
                    break;
                case 2:
                    this.cambiarPrecios();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }

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
                case 1:
                    System.out.print("Ingrese el nuevo precio de los Pochoclos: $");
                    miCinema.setPrecioPochoclos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Pochoclos a $" + miCinema.getPrecioPochoclos());
                    break;
                case 2:
                    System.out.print("Ingrese el nuevo precio de las Bebidas: $");
                    miCinema.setPrecioBebida(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de las Bebidas a $" + miCinema.getPrecioBebida());
                    break;
                case 3:
                    System.out.print("Ingrese el nuevo precio de los Nachos: $");
                    miCinema.setPrecioNachos(entrada.nextDouble());
                    System.out.println("Se actualizó el precio de los Nachos a $" + miCinema.getPrecioNachos());
                    break;
                case 4:
                    System.out.print("Ingrese el nuevo precio estándar de las Entradas: $");
                    miCinema.setPrecioEntrada(entrada.nextDouble());
                    System.out.println("Se actualizó el precio estándar de las Entradas a $" + miCinema.getPrecioEntrada());
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    private void inicioCliente() {
        Scanner entrada = new Scanner(System.in);

    }

}
