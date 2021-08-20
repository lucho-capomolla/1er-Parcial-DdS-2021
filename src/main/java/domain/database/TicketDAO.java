package domain.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static domain.database.ConexionDB.newConnection;

public class TicketDAO {

    private Connection conn;

    public void almacenarTicket(String horaCreacion, String fechaCreacion, double precioTotal, String nombreArticulo) {

        String consulta = "INSERT INTO ticket (hora_creacion, fecha_creacion, precio_total, nombre_articulo) VALUES ('" + horaCreacion + "','" + fechaCreacion + "','" + precioTotal + "','" + nombreArticulo +"');";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo almacenar el Ticket en la BD.");
        }
    }

}
