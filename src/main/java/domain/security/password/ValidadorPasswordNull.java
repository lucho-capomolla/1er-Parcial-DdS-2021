package domain.security.password;

public class ValidadorPasswordNull implements Validador {

    @Override
    public boolean esValida(String password) {
        return !password.isEmpty();
    }
}
