package domain.business.pelicula.apiPelicula;

import domain.business.pelicula.apiPelicula.entidades.Generos;
import domain.business.pelicula.apiPelicula.entidades.ListMovie;


import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface AdapterAPIpelicula {

    @GET("movie/now_playing")
    Call<ListMovie> movies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page, @Query("region") String region);

    @GET("genre/movie/list")
    Call<Generos> genres(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/upcoming")
    Call<ListMovie> upcoming(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page, @Query("region") String region);
}
