package domain.business;

import domain.business.comestibles.Producto;
import domain.database.TicketDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Ticket {
    private static int contador = 0;

    //private int idTicket;
    private String horaCreacion;
    private String fechaCreacion;
    private double precioTotal;
    private ArrayList<Compra> productosListados = new ArrayList<>();

    // Getters and Setters
   /* public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }
*/
    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public ArrayList<Compra> getProductosListados() {
        return productosListados;
    }

    public void agregarProductoATicket(Producto nuevoProducto) {
        this.productosListados.add(nuevoProducto);
    }

    public void agregarEntradaATicket(Entrada nuevaEntrada) {
        this.productosListados.add(nuevaEntrada);
    }

    public void setProductosListados(ArrayList<Compra> productosListados) { this.productosListados = productosListados;}

    // Constructor
    public Ticket() {}


    public void generarTicket(String nombreArticulo) {
        //contador++;
        //this.setIdTicket(contador);
        Date date = new Date();
        DateFormat hora = new SimpleDateFormat("HH:mm:ss");
        this.setHoraCreacion(hora.format(date));
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        this.setFechaCreacion(fecha.format(date));
        this.setPrecioTotal(this.obtenerPrecioFinal());

        TicketDAO ticketDAO = new TicketDAO();
        //ticketDAO.almacenarTicket(this.getIdTicket(), this.getHoraCreacion(), this.getFechaCreacion(), this.getPrecioTotal(), nombreArticulo);
        ticketDAO.almacenarTicket(this.getHoraCreacion(), this.getFechaCreacion(), this.getPrecioTotal(), nombreArticulo);

    }

    public void obtenerProductos() {
        for(Compra compra : productosListados) {
            compra.mostrarCompra();
        }
    }

    public double obtenerPrecioFinal() {
        double precioFinal = 0;
        for(Compra compra : productosListados) {
            precioFinal += compra.obtenerPrecio();
        }
        return precioFinal;
    }
}
