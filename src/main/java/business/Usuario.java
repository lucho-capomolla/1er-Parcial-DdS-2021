package business;

import java.util.ArrayList;

public class Usuario {
    private String usuario;
    private String contraseña;
    private String email;
    private ArrayList<Compra> carrito = new ArrayList<>();

    // Getters and Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Compra> obtenerCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Compra> carrito) {
        this.carrito = carrito;
    }

    public void agregarAlCarrito(Compra nuevaCompra) {
        this.carrito.add(nuevaCompra);
    }

    // Constructor
    private Usuario(String usuario, String contraseña, String email) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.email = email;
    }

    // Metodos
    public void iniciarSesion(String usuario, String contraseña) {

    }

    public void crearUsuario(String usuario, String contraseña, String email) {
        Usuario nuevoUsuario = new Usuario(usuario, contraseña, email);
    }

    public void comprar(Compra nuevaCompra) {
        agregarAlCarrito(nuevaCompra);
    }
}
