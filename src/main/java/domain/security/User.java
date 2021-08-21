package domain.security;

public class User implements Rol{
    @Override
    public boolean puedoAdministrarPrecios() {
        return false;
    }

    @Override
    public boolean puedoCambiarRoles() {
        return false;
    }

    @Override
    public String mostrarRol() {
        return "USER";
    }
}
