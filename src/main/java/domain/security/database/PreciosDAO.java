package domain.security.database;

import domain.security.TipoRol;
import domain.security.Usuario;

import java.sql.*;

import static domain.security.database.ConexionDB.newConnection;

public class PreciosDAO {

    private Connection conn;

    // Cambio de Precios
    public void cambiarPrecioEntrada(double precioEntrada) {

        String consulta = "UPDATE precios SET precio_entrada = '" + precioEntrada + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo actualizar el Precio de la Entrada en la BD.");
        }
    }

    public void cambiarPrecioPochoclos(double precioPochoclos) {

        String consulta = "UPDATE precios SET precio_pochoclos = '" + precioPochoclos + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo actualizar el Precio de los Pochoclos en la BD.");
        }
    }

    public void cambiarPrecioBebidas(double precioBebida) {

        String consulta = "UPDATE precios SET precio_bebidas = '" + precioBebida + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo actualizar el Precio de las Bebidas en la BD.");
        }
    }

    public void cambiarPrecioNachos(double precioNachos) {

        String consulta = "UPDATE precios SET precio_nachos = '" + precioNachos + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo actualizar el Precio de los Nachos en la BD.");
        }
    }


    // Obtener los Precios
    public double obtenerPrecioEntrada() {

        String consulta = "SELECT precio_entrada FROM precios;";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioEntrada = rs.getDouble("precio_entrada");

            return precioEntrada;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de la Entrada en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioPochoclos() {

        String consulta = "SELECT precio_pochoclos FROM precios;";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioPochoclos = rs.getDouble("precio_pochoclos");

            return precioPochoclos;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de los Pochoclos en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioBebidas() {

        String consulta = "SELECT precio_bebidas FROM precios;";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioBebidas = rs.getDouble("precio_bebidas");

            return precioBebidas;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de las Bebidas en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioNachos() {

        String consulta = "SELECT precio_nachos FROM precios;";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioNachos = rs.getDouble("precio_nachos");

            return precioNachos;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de los Nachos en la BD.");
            return 0;
        }
    }

}
