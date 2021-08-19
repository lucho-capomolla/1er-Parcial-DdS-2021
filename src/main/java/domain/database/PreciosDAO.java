package domain.database;

import java.sql.*;

import static domain.database.ConexionDB.newConnection;

public class PreciosDAO {

    private Connection conn;

    // Cambio de Precios
    public void cambiarPrecioEntrada(double precioEntrada) {

        String consulta = "UPDATE venta SET precio = '" + precioEntrada + "' WHERE descripcion = 'precio_entrada';";

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

        String consulta = "UPDATE venta SET precio = '" + precioPochoclos + "' WHERE descripcion = 'precio_pochoclos';";

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

        String consulta = "UPDATE venta SET precio = '" + precioBebida + "' WHERE descripcion = 'precio_bebidas';";

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

        String consulta = "UPDATE venta SET precio = '" + precioNachos + "' WHERE descripcion = 'precio_nachos';";

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

        String consulta = "SELECT precio FROM venta WHERE descripcion = 'precio_entrada';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioEntrada = rs.getDouble("precio");

            return precioEntrada;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de la Entrada en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioPochoclos() {

        String consulta = "SELECT precio FROM venta WHERE descripcion = 'precio_pochoclos';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioPochoclos = rs.getDouble("precio");

            return precioPochoclos;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de los Pochoclos en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioBebidas() {

        String consulta = "SELECT precio FROM venta WHERE descripcion = 'precio_bebidas';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioBebidas = rs.getDouble("precio");

            return precioBebidas;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de las Bebidas en la BD.");
            return 0;
        }
    }

    public double obtenerPrecioNachos() {

        String consulta = "SELECT precio FROM venta WHERE descripcion = 'precio_nachos';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            double precioNachos = rs.getDouble("precio");

            return precioNachos;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo obtener el Precio de los Nachos en la BD.");
            return 0;
        }
    }

}
