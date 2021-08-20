package domain.business.pelicula;

public class Genero {
    public int id;
    public String name;

    public String getGenero(int idGenero) {
        if(id == idGenero) {
            return name;
        }
        else {
            return null;
        }
    }

    public void mostrarGenero() {
        System.out.println("ID:" + id);
        System.out.println("Nombre: " + name);
    }
}
