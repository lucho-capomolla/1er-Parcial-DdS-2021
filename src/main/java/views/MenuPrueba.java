package views;

import business.Ticket;
import business.comestibles.Combo;
import business.comestibles.*;

import java.util.Scanner;

public class MenuPrueba {


    public void iniciarMenu() {
        Scanner entrada = new Scanner(System.in);
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


}
