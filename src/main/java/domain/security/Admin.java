package domain.security;

public class Admin implements Rol {
    @Override
    public boolean puedoAdministrarPrecios() {
        return true;
    }

    @Override
    public boolean puedoAdministrarPeliculas() {
        return true;
    }
}
