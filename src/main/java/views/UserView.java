package views;

import domain.business.*;
import domain.business.comestibles.*;
import domain.business.pelicula.Entrada;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.APImovies;
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
        APImovies apiMovies = new APImovies();
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
            System.out.println("• Si desea consultar su Billetera Virtual, ingrese 3.");
            System.out.println("• Si desea Cerrar Sesión, ingrese 4.");
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
                case 3: // Para accionar sobre la Billetera Virtual
                    this.consultarBilleteraVirtual(usuarioLogin.getCliente());
                    break;
                case 4: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING] La opción que ha elegido es incorrecta.");
                    break;
            }
        }
    }

    private void consultarBilleteraVirtual(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        int opcionElegida = 0;

        while(!salir) {
            System.out.println(" - Para consultar el saldo de su Billetera, ingrese 1.");
            System.out.println(" - Para agregar más saldo a su Billetera, ingrese 2.");
            System.out.println(" - Si desea volver al Menú Principal, ingrese 3.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1:
                    System.out.println(" - El saldo del Cliente es de: $" + cliente.getBilleteraVirtual().getSaldo());
                    break;
                case 2:
                    System.out.print(" - Indique cuanto saldo quiere recargar: $");
                    double cantidad = entrada.nextDouble();
                    cliente.getBilleteraVirtual().recargar(cantidad);
                    System.out.println(" - La recarga de $" + cantidad + " ha sido exitosa.");
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    break;
            }
        }

    }

    private void comprarEntrada(Cliente cliente) throws IOException {
        Scanner entrada = new Scanner(System.in);
        Cinema miCinema = Cinema.getInstance();
        boolean salir = false;
        APImovies apiMovies = new APImovies();
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

                    while(cantidadEntradasReservadas < cantidadEntradas) {

                        Entrada entradaPelicula = new Entrada();

                        entradaPelicula.setSala(miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)).getNumeroSala());

                        String tituloPeliculaElegida = pelis.get(peliculaElegida).getTitulo();
                        List<Butaca> butacasDisponibles = (miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)).getButacas().stream().filter(butaca -> butaca.estaLibre()).filter(butaca -> butaca.getPelicula().getTitulo().equals(tituloPeliculaElegida)).collect(Collectors.toList()));

                        if(butacasDisponibles.isEmpty()) {
                            System.out.println("Función Agotada. Intente con otra Película.");
                            break;
                        }

                        if(cantidadEntradas > butacasDisponibles.size()) {
                            System.out.println("No hay suficientes butacas disponibles para la reserva. Intente con otra Película.");
                            break;
                        }

                        entradaPelicula.setPelicula(pelis.get(peliculaElegida));
                        entradaPelicula.setSala(miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)).getNumeroSala());

                        entradaPelicula.setButaca(this.elegirButaca(miCinema.buscarSalaXPelicula(pelis.get(peliculaElegida)), pelis.get(peliculaElegida)));

                        entradaPelicula.setHorarioFuncion(entradaPelicula.getButaca().getHorario());
                        entradaPelicula.setFechaEmision(LocalDate.now());

                        this.agregarAlCarrito(cliente, entradaPelicula);
                        entradasPeliculas.add(entradaPelicula);
                        cantidadEntradasReservadas++;
                    }

                    this.efectuarCompra(cliente);
                    salir = true;
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

    private Butaca elegirButaca(Sala sala, Pelicula pelicula) {
        Scanner entrada = new Scanner(System.in);
        List<Butaca> butacasDisponibles = (sala.getButacas().stream().filter(butaca -> butaca.estaLibre()).filter(butaca -> butaca.getPelicula().getTitulo().equals(pelicula.getTitulo())).collect(Collectors.toList()));
        boolean salir = false;
        int opcionElegida;
        int numButaca = -1;
        Butaca butacaElegida = null;

        while(!salir) {
            System.out.println("Elija el Número de Butaca que quiere reservar: ");
            for(Butaca butacaBuscada : butacasDisponibles) {
                System.out.println("    - N° Butaca: " + butacaBuscada.getNumeroButaca());
            }

            System.out.print("> ");
            opcionElegida = entrada.nextInt();

            for(int i=0; i<butacasDisponibles.size(); i++){
                Butaca butaca = butacasDisponibles.get(i);
                if(butaca.getNumeroButaca() == opcionElegida) {
                    numButaca = i;
                }
            }

            while(0 > numButaca || numButaca > butacasDisponibles.size()) {
                System.out.println("[WARNING] El número de Butaca que ha ingresado no está disponible.");
                System.out.println("Elija el Número de Butaca que quiere reservar: ");
                System.out.print("> ");
                opcionElegida = entrada.nextInt();

                for(int i=0; i<butacasDisponibles.size(); i++){
                    Butaca butaca = butacasDisponibles.get(i);
                    if(butaca.getNumeroButaca() == opcionElegida) {
                        numButaca = i;
                    }
                }
            }

            System.out.println("Usted ha elegido la Butaca: " + butacasDisponibles.get(numButaca).getNumeroButaca());
            System.out.println("¿Está seguro de la elección?");
            System.out.println("    - Ingrese 1 para continuar con la compra.");
            System.out.println("    - Ingrese 2 para volver atrás.");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();

            if(0 > opcionElegida || opcionElegida > 2) {
                System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                System.out.println("Usted ha elegido la Butaca: " + butacasDisponibles.get(numButaca).getNumeroButaca());
                System.out.println("¿Está seguro de la elección?");
                System.out.println("    - Ingrese 1 para continuar con la compra.");
                System.out.println("    - Ingrese 2 para volver atrás.");
                System.out.print("> ");
                opcionElegida = entrada.nextInt();
            }

            if(opcionElegida == 1) {
                butacaElegida = butacasDisponibles.get(numButaca);
                butacaElegida.ocuparButaca();
                salir = true;
                break;
            }
        }
        return butacaElegida;
    }

    private void comprarComestibles(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        Compra compra = null;
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
                    compra = this.eleccionComestible();

                    System.out.println("Usted ha elegido: " + compra.obtenerNombre());
                    System.out.println("¿Está seguro de la elección?");
                    System.out.println("    - Ingrese 1 para continuar con la compra.");
                    System.out.println("    - Ingrese 2 para volver atrás.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();

                    if(0 > opcionElegida || opcionElegida > 2) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Usted ha elegido: " + compra.obtenerNombre());
                        System.out.println("¿Está seguro de la elección?");
                        System.out.println("    - Ingrese 1 para continuar con la compra.");
                        System.out.println("    - Ingrese 2 para volver atrás.");
                        System.out.print("> ");
                        opcionElegida = entrada.nextInt();
                    }

                    if(opcionElegida == 2) {
                        compra = null;
                        break;
                    }

                    if(compra != null) {
                        this.agregarAlCarrito(cliente, compra);
                        this.efectuarCompra(cliente);
                        salir = true;
                    }
                    break;
                case 3: // Preparar Combo
                    compra = this.prepararCombo();

                    if(compra != null) {
                        this.agregarAlCarrito(cliente, compra);
                        this.efectuarCompra(cliente);
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

    private Compra prepararCombo() {
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        Combo combo = new Combo();
        combo.setArticulo("#Combo: ");

        System.out.println("-------PREPARACION COMBO-------");
        while(!salir) {
            System.out.println("    - Si desea agregar Comida/Bebida en el combo, ingrese 1.");
            System.out.println("    - Si desea meter un Combo en el Combo, ingrese 2.");
            System.out.println("    - Si no quiere realizar un Combo, ingrese 3.");
            System.out.print("> ");
            int opcionElegida = entrada.nextInt();
            System.out.println("");

            switch(opcionElegida) {
                case 1:
                    Compra compra = this.eleccionComestible();

                    System.out.println("Usted ha elegido: " + compra.obtenerNombre());
                    System.out.println("¿Está seguro de la elección?");
                    System.out.println("    - Ingrese 1 para continuar con la compra.");
                    System.out.println("    - Ingrese 2 para volver atrás.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();

                    if(0 > opcionElegida || opcionElegida > 2) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Usted ha elegido: " + compra.obtenerNombre());
                        System.out.println("¿Está seguro de la elección?");
                        System.out.println("    - Ingrese 1 para continuar con la compra.");
                        System.out.println("    - Ingrese 2 para volver atrás.");
                        System.out.print("> ");
                        opcionElegida = entrada.nextInt();
                    }

                    if(opcionElegida == 2) {
                        compra = null;
                        break;
                    }

                    if(compra != null) {
                        combo.agregarProducto(compra);
                        combo.setArticulo(combo.getArticulo().concat(compra.obtenerNombre()) + " | ");
                        System.out.println("    - Si desea finalizar el Combo, ingrese 4.");
                        break;
                    }
                    break;
                case 2:
                    Compra comboInception = this.prepararCombo();

                    System.out.println("Usted ha elegido: " + comboInception.obtenerNombre());
                    System.out.println("¿Está seguro de la elección?");
                    System.out.println("    - Ingrese 1 para continuar con la compra.");
                    System.out.println("    - Ingrese 2 para volver atrás.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();

                    if(0 > opcionElegida || opcionElegida > 2) {
                        System.out.println("[WARNING] Usted ha elegido una opción inválida. Por favor intente nuevamente.");
                        System.out.println("Usted ha elegido: " + comboInception.obtenerNombre());
                        System.out.println("¿Está seguro de la elección?");
                        System.out.println("    - Ingrese 1 para continuar con la compra.");
                        System.out.println("    - Ingrese 2 para volver atrás.");
                        System.out.print("> ");
                        opcionElegida = entrada.nextInt();
                    }

                    if(opcionElegida == 2) {
                        comboInception = null;
                        break;
                    }

                    if(comboInception != null) {
                        combo.agregarProducto(comboInception);
                        combo.setArticulo(combo.getArticulo().concat(comboInception.obtenerNombre()) + " | ");
                        System.out.println("    - Si desea finalizar el Combo, ingrese 4.");
                        break;
                    }
                    break;
                case 3:
                    combo = null;
                    salir = true;
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("[WARNING]La opción que ha elegido es incorrecta.");
                    break;
            }
        }
        return combo;
    }

    private void agregarAlCarrito(Cliente cliente, Compra compra) {
        System.out.println("Se agrego al carrito del cliente: " + compra.obtenerNombre());
        cliente.agregarAlCarrito(compra);
    }

    private void efectuarCompra(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        int opcionElegida;
        boolean salir = false;
        List<Compra> compras = cliente.obtenerCarrito();

        while(!salir) {
            if(compras != null) {
                System.out.println("$$$ Si desea efectuar la compra, ingrese 1 $$$");
                System.out.println(" - Si desea visualizar los productos del carrito, ingrese 2.");
                System.out.println(" - De lo contrario, presione cualquier otra tecla para limpiar el carrito.");
                System.out.print("> ");
                opcionElegida = entrada.nextInt();
                if(opcionElegida == 1) {
                    this.debitarCompra(cliente);
                    salir = true;
                }
                else if(opcionElegida == 2) {
                    cliente.obtenerProductos();
                    System.out.println("Si quiere quitar un producto del carrito, ingrese 1.");
                    System.out.println("Si desea continuar con la compra, ingrese 2.");
                    System.out.print("> ");
                    opcionElegida = entrada.nextInt();
                    if(opcionElegida == 1) {
                        this.quitarProductoCarrito(cliente);
                    }
                }
                else {
                    cliente.limpiarCarrito();
                    salir = true;
                }
            }
        }

    }

    private void quitarProductoCarrito(Cliente cliente) {
        Scanner entrada = new Scanner(System.in);
        int opcionElegida;

        System.out.print("Seleccione el ID del producto que quiere quitar del Carrito: ");
        System.out.print("> ");
        opcionElegida = entrada.nextInt();

        if(opcionElegida < 0 || opcionElegida > cliente.obtenerCarrito().size()){
            System.out.println("[WARNING] La opción que ha ingresado es incorrecta.");
            System.out.print("Seleccione el ID del producto que quiere quitar del Carrito: ");
            System.out.print("> ");
            opcionElegida = entrada.nextInt();
        }

        cliente.quitarDelCarrito(cliente.obtenerCarrito().get(opcionElegida));
        System.out.println("Se ha quitado el producto de forma exitosa.");
    }

    private void debitarCompra(Cliente cliente) {
        Cinema miCinema = Cinema.getInstance();
        Ticket nuevoTicket = new Ticket();
        String nombreArticulo = new String();
        boolean esEntrada = false;

        int contador = 0;

        System.out.println("¡Productos comprados!:");
        for (Compra compra : cliente.obtenerCarrito()) {
            System.out.println(contador);
            contador++;

            if (compra.getClass().equals(Entrada.class)) {
                esEntrada = true;
                nuevoTicket.agregarCompra(compra);
                compra.mostrarCompra();
                nombreArticulo = compra.obtenerNombre();
            } else {
                nuevoTicket.agregarCompra(compra);
                compra.mostrarCompra();
                nombreArticulo = nombreArticulo.concat(compra.obtenerNombre());
            }
        }

        if(esEntrada) {
            nombreArticulo = nombreArticulo.concat(" x " + cliente.obtenerCarrito().size());
        }

        System.out.println(nombreArticulo);
        System.out.println("Costo Final: $" + nuevoTicket.obtenerPrecioFinal());
        if(cliente.puedoComprar()){
            nuevoTicket.generarTicket(nombreArticulo);
            miCinema.agregarTicket(nuevoTicket);
            cliente.limpiarCarrito();
            cliente.comprar(nuevoTicket);
            System.out.println("Saldo restante: $" + cliente.getBilleteraVirtual().getSaldo());
        }
        else {
            if(esEntrada) {
                for(Compra compra : cliente.obtenerCarrito()) {
                    ((Entrada)compra).getButaca().liberarButaca();
                }
            }
            System.out.println("No hay saldo suficiente para realizar la compra.");
            cliente.limpiarCarrito();
        }

    }
}
