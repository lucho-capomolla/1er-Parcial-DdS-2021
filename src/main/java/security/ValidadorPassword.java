package security;

import java.util.ArrayList;

public class ValidadorPassword {
    private final ArrayList<Validador> validadoresPassword = new ArrayList<>();

    public ValidadorPassword() {
        this.validadoresPassword.add(new ValidadorPasswordTamanio());
        this.validadoresPassword.add(new ValidadorPasswordNull());
    }

    public boolean esValida(String password) {
        return validadoresPassword.stream().allMatch(validadorPassword -> validadorPassword.esValida(password));
    }
}
