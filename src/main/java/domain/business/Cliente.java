package domain.business;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {
    private String nombre;
    private String apellido;
    private String email;
    private String fechaDeNacimiento;
    private int nroDocumento;
    // Queda Hardcodeada la Billetera Virtual
    private BilleteraVirtual billeteraVirtual = new BilleteraVirtual(10000);
    private ArrayList<Ticket> carrito = new ArrayList<>();

    // Getters and Setters
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFechaDeNacimiento() { return fechaDeNacimiento; }

    public void setFechaDeNacimiento(String fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

    public int getNroDocumento() { return nroDocumento; }

    public void setNroDocumento(int nroDocumento) { this.nroDocumento = nroDocumento; }

    public ArrayList<Ticket> obtenerCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Ticket> carrito) {
        this.carrito = carrito;
    }

    public void agregarAlCarrito(Ticket nuevaTicket) {
        this.carrito.add(nuevaTicket);
    }

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
        if(this.getBilleteraVirtual().debitar(nuevoTicket.obtenerPrecioFinal())) {
            agregarAlCarrito(nuevoTicket);
        }
        else {
            System.out.println("ERROR 404: MONEY NOT FOUND.");
        }
    }
}
