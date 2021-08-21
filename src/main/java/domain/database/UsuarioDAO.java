package domain.database;

import domain.security.TipoRol;
import domain.security.Usuario;

import java.sql.*;
import static domain.database.ConexionDB.newConnection;

public class UsuarioDAO {

    private Connection conn;

    public void insert(String email, String contrasenia, TipoRol rol) {

        String mapeoRol = null;
        if(rol == TipoRol.USER) {
            mapeoRol = "USER";
        }
        else if(rol == TipoRol.ADMIN) {
            mapeoRol = "ADMIN";
        }

        String consulta = "INSERT INTO usuario (email, contrasenia, rol) VALUES ('" + email + "','" + contrasenia + "','" + mapeoRol + "');";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo ingresar el Usuario a la BD.");
        }
    }

    public void insertIDCliente(int idCliente, String email) {

        String consulta = "UPDATE usuario SET id_cliente = '" + idCliente + "' WHERE email = '" + email +"';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo ingresar el ID Cliente al Usuario a la BD.");
        }
    }

    public void insertRolCliente(TipoRol rol, String email) {

        String mapeoRol = null;
        if(rol == TipoRol.USER) {
            mapeoRol = "USER";
        }
        else if(rol == TipoRol.ADMIN) {
            mapeoRol = "ADMIN";
        }

        String consulta = "UPDATE usuario SET rol = '" + mapeoRol + "' WHERE email = '" + email +"';";

        try {
            this.conn = newConnection();

            // Ejecución
            PreparedStatement stmt = this.conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            // Execute the PreparedStatement
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se pudo ingresar el nuevo Rol del Usuario a la BD.");
        }
    }

    public int obtenerIDCliente(String emailBuscado) {
        String consulta = "SELECT * FROM usuario WHERE email = '" + emailBuscado + "';";

        try {
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();

            int idCliente = rs.getInt("id_cliente");

            return idCliente;

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("No se encuentra el Usuario en la BD.");
            return 0;
        }
    }

   public Usuario buscarUsuario(String emailBuscado) {
        String consulta = "SELECT * FROM usuario WHERE email = '" + emailBuscado + "';";

       try {
           this.conn = newConnection();

           // Ejecución
           Statement stmt = this.conn.createStatement();
           ResultSet rs = stmt.executeQuery(consulta);
           rs.next();

           String email = rs.getString("email");
           String contrasenia = rs.getString("contrasenia");
           String rol = rs.getString("rol");
           int idCliente = rs.getInt("id_cliente");

           TipoRol mapeoRol = null;
           if(rol.equals("ADMIN")) {
               mapeoRol = TipoRol.ADMIN;
           }
           else if(rol.equals("USER")) {
               mapeoRol = TipoRol.USER;
           }

           Usuario usuarioBuscado = new Usuario(email, contrasenia, mapeoRol, idCliente);

           return usuarioBuscado;

       } catch (SQLException ex) {
           // handle any errors
           System.out.println("No se encuentra el Usuario en la BD.");
           return null;
       }
   }
}
