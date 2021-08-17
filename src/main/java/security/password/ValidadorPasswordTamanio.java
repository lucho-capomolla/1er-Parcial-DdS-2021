package security.password;

public class ValidadorPasswordTamanio implements Validador{
    @Override
    public boolean esValida(String password) {
        int tamanioPassword = password.length();
        return tamanioPassword >= 8;
    }
}
