package domain.business.pelicula.apiPelicula.entidades;

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
}
