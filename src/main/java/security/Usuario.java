package security;

import business.Cliente;

public class Usuario{
    private String email;
    private String contrasenia;
    private Cliente cliente;
    private Rol rol;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Constructor
    public Usuario() {}


    // Metodos
    public void iniciarSesion(String email, String contrasenia) {

    }

    public Usuario crearUsuario(String email, String contrasenia) {
        Usuario nuevoUsuario = new Usuario();
        this.setEmail(email);
        this.setContrasenia(contrasenia);
        return nuevoUsuario;
    }
}
