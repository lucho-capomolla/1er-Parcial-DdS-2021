package domain.business.pelicula.apiPelicula;

import domain.business.pelicula.apiPelicula.entidades.Genero;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.entidades.Generos;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;
import domain.business.pelicula.apiPelicula.entidades.Movie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APImovies {

    private static String apikey = "be5becf6bc13fb9588e26e10ab747d2e";
    private static String urlAPI = "https://api.themoviedb.org/3/";
    private Retrofit retrofit;

    public APImovies() {
        this.retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
    }

    private ListMovie peliculasCartelera() throws IOException {
        APIservice apiPelicula = this.retrofit.create(APIservice.class);
        Call<ListMovie> pedidoPeliculas = apiPelicula.movies(apikey, "es", 1, "US");
        Response<ListMovie> respuestaPeliculas = pedidoPeliculas.execute();
        return respuestaPeliculas.body();
    }

    private ListMovie peliculasEstreno() throws IOException {
        APIservice apiPelicula = this.retrofit.create(APIservice.class);
        Call<ListMovie> pedidoEstrenos = apiPelicula.upcoming(apikey, "es", 1, "US");
        Response<ListMovie> respuestaEstrenos = pedidoEstrenos.execute();
        return respuestaEstrenos.body();
    }

    private Generos obtenerGeneros() throws IOException {
        APIservice apiPelicula = this.retrofit.create(APIservice.class);
        Call<Generos> pedidoGeneros = apiPelicula.genres(apikey, "es");
        Response<Generos> respuestaGeneros = pedidoGeneros.execute();
        return respuestaGeneros.body();
    }

    public String obtenerGenero(List<Integer> idsGenero) throws IOException {
        Generos listaGeneros = this.obtenerGeneros();
        String generoBuscado = "";
        for(int id : idsGenero) {
            for(Genero unGenero : listaGeneros.getGenres()) {
                if(unGenero.getGenero(id) != null) {
                    generoBuscado = generoBuscado.concat(unGenero.getGenero(id));
                    generoBuscado = generoBuscado.concat(" ");
                }
            }
        }
        return generoBuscado;
    }

    public List<Pelicula> damePeliculas(ListMovie listaPeliculas) throws IOException {
        List<Pelicula> listaFinal = new ArrayList<>();
        for(Movie movie : listaPeliculas.getResults()) {
            Pelicula pelicula = new Pelicula();
            pelicula.mappeoDAO(movie);
            listaFinal.add(pelicula);
        }
        return listaFinal;
    }

    public List<Pelicula> obtenerEstrenos() throws IOException {
        ListMovie listaPeliculas = this.peliculasEstreno();
        return this.damePeliculas(listaPeliculas);
    }

    public List<Pelicula> obtenerPeliculas() throws IOException{
        ListMovie listaPeliculas = this.peliculasCartelera();
        return this.damePeliculas(listaPeliculas);
    }



}
