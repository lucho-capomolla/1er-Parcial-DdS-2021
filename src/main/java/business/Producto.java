package business;

public abstract class Producto extends Compra{
    private String articulo;

    // Getters and Setters
    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
