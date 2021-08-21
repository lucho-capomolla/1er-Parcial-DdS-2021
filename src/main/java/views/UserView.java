package views;

import domain.business.*;
import domain.business.comestibles.*;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.ApiMovies;
import domain.security.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserView {
    private static UserView instance;
    private AdminView adminView = AdminView.getInstance();

    public static UserView getInstance() {
        if(instance == null) {
            instance = new UserView();
        }
        return instance;
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

    public void inicioCliente(Usuario usuarioLogin) throws IOException {
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
        Cinema miCinema = Cinema.getInstance();
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
                    adminView.consultarPrecioEntrada();
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

                    System.out.print("¿Cuantas entradas desea comprar?: ");
                    int cantidadEntradas = entrada.nextInt();
                    int cantidadEntradasReservadas = 0;

                    if(cantidadEntradas == 0) {
                        System.out.println("[WARNING] No puede ingresar 0 como cantidad de entradas.");
                        System.out.print("¿Cuantas entradas desea comprar?: ");
                        cantidadEntradas = entrada.nextInt();
                    }

                    for(int c=0; c<cantidadEntradas; c++){
                        Entrada entradaPelicula = new Entrada();
                        entradaPelicula.setPelicula(pelis.get(peliculaElegida));

                        entradaPelicula.setSala(miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)).getNumeroSala());

                        while(cantidadEntradasReservadas < cantidadEntradas) {
                            entradaPelicula.setButaca(this.elegirButacas(miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)), pelis.get(peliculaElegida)));
                            entradaPelicula.setHorarioFuncion(entradaPelicula.getButaca().getHorario());
                            cantidadEntradasReservadas++;
                        }
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

    private Butaca elegirButacas(Sala sala, Pelicula pelicula) {
        Scanner entrada = new Scanner(System.in);
        List<Butaca> butacasDisponibles = (sala.getButacas().stream().filter(butaca -> butaca.estaLibre()).filter(butaca -> butaca.getPelicula().getTitulo().equals(pelicula.getTitulo())).collect(Collectors.toList()));

        boolean salir = false;
        int opcionElegida;
        int numButaca;
        Butaca butacaElegida = null;

        while(!salir) {
            System.out.println("Elija el Número de Butaca que quiere reservar: ");
            for(Butaca butacaBuscada : butacasDisponibles) {
                System.out.println("    - N° Butaca: " + butacaBuscada.getNumeroButaca());
            }
            System.out.print("> ");
            numButaca = entrada.nextInt();

            while(0 >= numButaca || numButaca > sala.getCantidadButacas() && !butacasDisponibles.get(numButaca).estaLibre()) {
                System.out.println("[WARNING] El número de Butaca que ha ingresado no está disponible.");
                System.out.println("Elija el Número de Butaca que quiere reservar: ");
                System.out.print("> ");
                numButaca = entrada.nextInt();
            }


            System.out.println("Usted ha elegido la Butaca: " + numButaca);
            System.out.println("¿Está seguro de la elección?");
            System.out.println("    - Ingrese 1 para continuar con la compra.");
            System.out.println("    - Ingrese 2 para volver atrás.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();

            if(0 > opcionElegida || opcionElegida > 2) {
                System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                System.out.println("Usted ha elegido la Butaca: " + numButaca);
                System.out.println("¿Está seguro de la elección?");
                System.out.println("    - Ingrese 1 para continuar con la compra.");
                System.out.println("    - Ingrese 2 para volver atrás.");
                System.out.print("> ");
                opcionElegida = entrada.nextInt();
            }

            if(opcionElegida == 2) {
                numButaca = 0;
                break;
            }

            butacaElegida = butacasDisponibles.get(numButaca);
            butacaElegida.ocuparButaca();
            salir = true;
            break;
        }
        return butacaElegida;
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
                    adminView.consultarPreciosComestibles();
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


    // Compra Comestibles
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
        cliente.comprar(nuevoTicket);
        System.out.println("Saldo restante: $" + cliente.getBilleteraVirtual().getSaldo());
    }

    // Compra Entradas
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
        cliente.comprar(nuevoTicket);
        System.out.println("Saldo restante: $" + cliente.getBilleteraVirtual().getSaldo());
    }
}
