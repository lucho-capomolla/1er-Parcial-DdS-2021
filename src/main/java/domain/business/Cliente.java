package domain.business;

import domain.business.comestibles.Producto;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {
    private String nombre;
    private String apellido;
    private String email;
    private String fechaDeNacimiento;
    private int nroDocumento;
    // Queda Hardcodeada la Billetera Virtual
    private BilleteraVirtual billeteraVirtual = new BilleteraVirtual(2000);
    private ArrayList<Compra> carrito = new ArrayList<>();

    // Getters and Setters
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public ArrayList<Compra> obtenerCarrito() { return carrito; }

    public void limpiarCarrito() { this.carrito = new ArrayList<>(); }

    public void quitarDelCarrito(Compra compra) { this.carrito.remove(compra);}

    public void agregarAlCarrito(Compra nuevaCompra) { this.carrito.add(nuevaCompra); }

    public BilleteraVirtual getBilleteraVirtual() { return billeteraVirtual; }

    // Constructor
    public Cliente() {}

    public Cliente(String nombre, String apellido, String email, String fechaDeNacimiento, int nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nroDocumento = nroDocumento;
    }

    // Metodos
    public void comprar(Ticket nuevoTicket) {
        this.getBilleteraVirtual().debitar(nuevoTicket.obtenerPrecioFinal());
    }

    public void obtenerProductos() {
        int contador = 0;
        for(Compra compra : carrito) {
            System.out.println("Producto: " + contador);
            compra.mostrarCompra();
            contador++;
        }
    }

    public boolean puedoComprar() {
        double aPagar = 0;
        for(Compra compra : carrito) {
            aPagar += compra.obtenerPrecio();
        }
        return (aPagar <= this.getBilleteraVirtual().getSaldo());
    }
}
