package business;

import java.util.List;

public class Cinema {
    private static Cinema instance;
    private static List<Usuario> usuarios;
    private static List salas;
    private static List<Pelicula> cartelera;

    public static Cinema getInstance() {
        if(instance == null) {
            instance = new Cinema();
        }
        return instance;
    }

    // Metodos
    public static boolean iniciarSesion(String usuario, String contraseña) {
       if(Cinema.buscarUsuario(usuario)) {
           return Cinema.verificarInicio(usuario, contraseña);
       }
       else {
           System.out.println("El usuario es incorrecto.");
           return false;
       }
    }

    public static boolean buscarUsuario(String usuarioBuscado) {
        return usuarios.stream().map(usuario -> usuario.getUsuario()).equals(usuarioBuscado);
    }

    public static boolean verificarInicio(String usuario, String contraseña) {
        // Todo: aca podria ponerse un requerimiento No funcional, como que si no ingresa nada en los proximos 10 segundos -> OUT
        return false;
    }


    public static void crearUsuario(String usuario, String contraseña, String email) {
        // TODO: validar que no exista el mismo Usuario ni Email; además validar la contraseña
    }
}
