package domain.security.database;

import domain.security.TipoRol;
import domain.security.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static domain.security.database.ConexionDB.newConnection;

public class UsuariosDAO {

    private Connection conn;

    public List<Usuario> obtenerUsuarios() {

        try {

            // generacion de query
            String consulta = "SELECT * FROM usuario;";

            // Conexión
            this.conn = newConnection();

            // Ejecución
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);

            // Recorrer y usar cada línea retornada
            List<Usuario> usuarios = new ArrayList<>();

            while (rs.next()) {
                String email = rs.getString("email");
                String contrasenia = rs.getString("contrasenia");
                String rol = rs.getString("rol");

                TipoRol mapeoRol = null;
                if(rol.equals("ADMIN")) {
                    mapeoRol = TipoRol.ADMIN;
                }
                else if(rol.equals("USER")) {
                    mapeoRol = TipoRol.USER;
                }

                Usuario usuarioEncontrado = new Usuario(email, contrasenia, mapeoRol);
                usuarios.add(usuarioEncontrado);
            }
            return usuarios;

        } catch (SQLException ex) {

            // handle any errors
            System.out.println("No se encuentran usuarios en la BD.");
            return null;
        }
    }

}

