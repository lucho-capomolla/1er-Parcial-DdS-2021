package domain.business.pelicula.apiPelicula.entidades;

import java.util.List;

public class ListMovie {

    int page;
    public List<Movie> results;
    public Dates dates;
    int total_pages;
    int total_results;

    public List<Movie> getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public Dates getDates() {
        return dates;
    }

}
