package domain.security;

import domain.business.Cliente;
import domain.security.database.ClienteDAO;

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

    public void cambiarRol(Rol rol) {
        this.rol = rol;
    }

    // Constructor
    public Usuario() {}

    public Usuario(String email, String contrasenia, TipoRol rol, int idCliente) {
        this.email = email;
        this.contrasenia = contrasenia;
        if(rol == TipoRol.ADMIN) {
            this.rol = new Admin();
        }
        else if(rol == TipoRol.USER) {
            this.rol = new User();
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        this.cliente = clienteDAO.buscarCliente(idCliente);
    }

}
