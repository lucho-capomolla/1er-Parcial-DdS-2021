package domain.business.pelicula.apiPelicula.entidades;

import domain.business.pelicula.Genero;
import java.util.List;

public class Generos {
    public List<Genero> genres;

    public List<Genero> getGenres() {
        return genres;
    }

    public void setGenres(List<Genero> genres) {
        this.genres = genres;
    }
}
