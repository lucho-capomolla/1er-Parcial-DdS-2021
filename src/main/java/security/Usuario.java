package security;

public class Usuario {
    private String email;
    private String contrasenia;

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
