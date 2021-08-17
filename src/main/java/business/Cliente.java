package business;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {
    private String nombre;
    private String apellido;
    private String email;
    private Date fechaDeNacimiento;
    private int nroDocumento;
    private ArrayList<Ticket> carrito = new ArrayList<>();

    // Getters and Setters
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Date getFechaDeNacimiento() { return fechaDeNacimiento; }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

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

    // Constructor
    private Cliente() {}

    // Metodos
    public void comprar(Ticket nuevaTicket) {
        agregarAlCarrito(nuevaTicket);
    }
}
