package domain.security.database;

import domain.business.Cliente;
import domain.security.TipoRol;
import domain.security.Usuario;

import java.sql.*;

import static domain.security.database.ConexionDB.newConnection;

public class ClienteDAO {

    private Connection conn;

    public int crearCliente(String email, String nombre, String apellido, String fechaNacimiento, int nroDocumento) {

        String consulta = "INSERT INTO cliente (email, nombre, apellido, fecha_nacimiento, nro_documento) VALUES ('" + email + "','" + nombre + "','" + apellido + "','" + fechaNacimiento + "','" + nroDocumento + "');";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next())
                return generatedKeys.getInt(1);
            else
                return 0;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo ingresar el Cliente a la BD.");
            return 0;
        }
    }


    public Cliente buscarCliente(int idCliente) {
        String consulta = "SELECT * FROM cliente WHERE id_cliente = '" + idCliente + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            String email = rs.getString("email");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String fechaDeNacimiento = rs.getString("fecha_nacimiento");
            int nroDocumento = rs.getInt("nro_documento");

            Cliente clienteBuscado = new Cliente(nombre, apellido, email, fechaDeNacimiento, nroDocumento);

            return clienteBuscado;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se encuentra el Cliente en la BD.");
            return null;
        }
    }
}
