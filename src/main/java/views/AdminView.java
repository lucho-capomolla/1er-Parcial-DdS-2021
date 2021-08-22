package views;

import domain.business.Cinema;
import domain.database.UsuarioDAO;
import domain.database.UsuariosDAO;
import domain.security.Admin;
import domain.security.TipoRol;
import domain.security.User;
import domain.security.Usuario;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminView {
    private static AdminView instance;

    private AdminView() {}

    public static AdminView getInstance() {
        if(instance == null) {
            instance = new AdminView();
        }
        return instance;
    }

    public void inicioAdmin() {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;

        while(!salir) {
            System.out.println("---------------------------MENU PRINCIPAL--------------------------");
            System.out.println("• Para consultar los precios de los Comestibles, ingrese 1.");
            System.out.println("• Para consultar el precio de la Entrada, ingrese 2.");
            System.out.println("• Para cambiar los precios, ingrese 3.");
            System.out.println("• Para consultar todos los Usuarios, ingrese 4.");
            System.out.println("• Para hacer Admin a otro Usuario, ingrese 5.");
            System.out.println("• Para sacar los permisos de Admin a un Usuario, ingrese 6.");
            System.out.println("• Para volver al Menú Principal, ingrese 7.");
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
                case 4: // Consultar todos los Usuarios
                    this.consultarUsuarios();
                    break;
                case 5: // Hacer nuevo Admin
                    this.nuevoAdmin();
                    break;
                case 6: // Sacar Admin
                    this.quitarAdmin();
                    break;
                case 7: // Salir
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

    private void consultarUsuarios() {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        List<Usuario> usuarios = usuariosDAO.obtenerUsuarios();
        int contador = 0;

        for(Usuario usuario : usuarios) {

            System.out.println("Usuario: " + contador);
            System.out.println("    - Email Usuario: " + usuario.getEmail());
            System.out.println("    - Rol:" + usuario.getRol().mostrarRol());
            contador++;
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

    private void nuevoAdmin() {
        Scanner entrada = new Scanner(System.in);
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> users = usuariosDAO.obtenerUsuarios().stream().filter(usuario -> !usuario.getRol().puedoCambiarRoles()).collect(Collectors.toList());
        int adminElegido;
        int opcionElegida;

        System.out.println("Los Usuarios que pueden ser nuevos Admin son los siguientes: ");
        for(int i=0; i<users.size(); i++) {
            System.out.println("   " + i + " - " + users.get(i).getEmail());
        }
        System.out.println();

        System.out.println("Ingrese el número de ID de Usuario que desea agregar como nuevo Admin: ");
        System.out.print("> ");
        adminElegido = entrada.nextInt();

        if(0 > adminElegido || adminElegido > users.size()) {
            System.out.println("[WARNING] La opción que ha ingresado es incorrecta.");
            System.out.println("Ingrese el número de ID de Usuario que desea agregar como nuevo Admin: ");
            System.out.print("> ");
            adminElegido = entrada.nextInt();
        }


        System.out.println("Usted quiere hacer Admin a: " + users.get(adminElegido).getEmail());
        System.out.println("¿Está seguro de la elección?");
        System.out.println("    - Ingrese 1 para continuar.");
        System.out.println("    - Ingrese 2 para volver atrás.");
        System.out.print("> ");
        opcionElegida = entrada.nextInt();

        if(0 > opcionElegida || opcionElegida > 2) {
            System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
            System.out.println("Usted quiere hacer Admin a: " + users.get(adminElegido).getEmail());
            System.out.println("¿Está seguro de la elección?");
            System.out.println("    - Ingrese 1 para continuar con la compra.");
            System.out.println("    - Ingrese 2 para volver atrás.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
        }

        if(opcionElegida == 2) {
            return;
        }

        users.get(adminElegido).cambiarRol(new Admin());
        usuarioDAO.insertRolCliente(TipoRol.ADMIN, users.get(adminElegido).getEmail());
        return;
    }

    private void quitarAdmin() {
        Scanner entrada = new Scanner(System.in);
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> admins = usuariosDAO.obtenerUsuarios().stream().filter(usuario -> usuario.getRol().puedoCambiarRoles()).collect(Collectors.toList());
        int adminElegido;
        int opcionElegida;

        System.out.println("Los Admins del Sistema son los siguientes: ");
        for(int i=0; i<admins.size(); i++) {
            System.out.println("   " + i + " - " + admins.get(i).getEmail());
        }
        System.out.println();

        System.out.println("Ingrese el número de ID de Admin que desea quitarle los permisos: ");
        System.out.print("> ");
        adminElegido = entrada.nextInt();

        if(0 > adminElegido || adminElegido > admins.size()) {
            System.out.println("[WARNING] La opción que ha ingresado es incorrecta.");
            System.out.println("Ingrese el número de ID de Admin que desea quitarle los permisos: ");
            System.out.print("> ");
            adminElegido = entrada.nextInt();
        }

        System.out.println("Usted quiere quitarle los permisos de Admin a: " + admins.get(adminElegido).getEmail());
        System.out.println("¿Está seguro de la elección?");
        System.out.println("    - Ingrese 1 para continuar.");
        System.out.println("    - Ingrese 2 para volver atrás.");
        System.out.print("> ");
        opcionElegida = entrada.nextInt();

        if(0 > opcionElegida || opcionElegida > 2) {
            System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
            System.out.println("Usted quiere quitarle los permisos de Admin a: " + admins.get(adminElegido).getEmail());
            System.out.println("¿Está seguro de la elección?");
            System.out.println("    - Ingrese 1 para continuar con la compra.");
            System.out.println("    - Ingrese 2 para volver atrás.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
        }

        if(opcionElegida == 2) {
            return;
        }

        admins.get(adminElegido).cambiarRol(new User());
        usuarioDAO.insertRolCliente(TipoRol.USER, admins.get(adminElegido).getEmail());
        return;

    }
}
