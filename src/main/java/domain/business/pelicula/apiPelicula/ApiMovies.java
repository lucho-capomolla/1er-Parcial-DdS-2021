package domain.business.pelicula.apiPelicula;


import domain.business.pelicula.Genero;
import domain.business.pelicula.Pelicula;
import domain.business.pelicula.apiPelicula.entidades.Generos;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;
import domain.business.pelicula.apiPelicula.entidades.Movie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;


public class ApiMovies {

    private static ApiMovies instancia = null;
    private static String apikey = "be5becf6bc13fb9588e26e10ab747d2e";
    private static String urlAPI = "https://api.themoviedb.org/3/";
    private Retrofit retrofit;

    public ApiMovies() {
        this.retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiMovies getInstancia() {
        if(instancia == null) {
            instancia = new ApiMovies();
        }
        return instancia;
    }


    public ListMovie peliculasEstreno() throws IOException {
        AdapterAPIpelicula apiPelicula = this.retrofit.create(AdapterAPIpelicula.class);
        Call<ListMovie> pedidoPeliculas = apiPelicula.movies(apikey, "es", 1, "US");
        Response<ListMovie> respuestaPeliculas = pedidoPeliculas.execute();
        return respuestaPeliculas.body();
    }

    public Generos obtenerGeneros() throws IOException {
        AdapterAPIpelicula apiPelicula = this.retrofit.create(AdapterAPIpelicula.class);
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

   /* public List<Pelicula> estrenos(List<Movie> peliculasApi) {

    }*/
}
