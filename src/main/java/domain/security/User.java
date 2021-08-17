package domain.security;

public class User implements Rol{
    @Override
    public boolean puedoAdministrarPrecios() {
        return false;
    }

    @Override
    public boolean puedoAdministrarPeliculas() {
        return false;
    }
}
