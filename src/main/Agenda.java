package main;

import color.Color;
import util.BD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que modela la entidad-tabla Agenda. xyz
 *
 * @author Sebastián López
 * @version abril/2021
 */
public class Agenda {

    //Se crea la base de datos a partir del script SQL
    public static String crea() {
        String mensaje = "";

        if (BD.execScriptSQL("creaBD.sql")) {
            mensaje = Color.dialogo("Se ha creado la agenda");
        } else {
            mensaje = Color.error("ERROR al crear la agenda");
        }
        return mensaje;
    }

    //Se carga la base de datos a partir del script SQL 
    public static String cargaDatos() {
        String mensaje = "";

        if (BD.execScriptSQL("cargaBD.sql")) {
            mensaje = Color.dialogo("Se han cargado los datos en la agenda");
        } else {
            mensaje = Color.error("ERROR al cargar datos en la agenda");
        }
        return mensaje;
    }

    //Se añade un contacto nuevo
    public static String insert(String nombre, String telefono) {
        String mensaje = "";

        try ( PreparedStatement st = BD.getConexion().prepareStatement(
                "INSERT INTO CONTACTO (NOMBRE, TELEFONO) VALUES (?,?)")) {
            st.setString(1, nombre);
            st.setString(2, telefono);
            st.executeUpdate();
            mensaje = Color.dialogo(String.format("Se ha insertado correctamente a %s con teléfono: %s", nombre, telefono));
        } catch (SQLException ex) {
            mensaje = Color.error("ERROR al ejecutar el INSERT SQL");
        }
        return mensaje;
    }

    //Se borra, si existe, el contacto pasado como parámetro
    public static String delete(String nombre) {
        String mensaje;
        try ( PreparedStatement st = BD.getConexion().prepareStatement("DELETE FROM CONTACTO WHERE NOMBRE=?")) {
            st.setString(1, nombre);
            if (st.executeUpdate() > 0) {
                mensaje = Color.dialogo(String.format("Se ha borrado correctamente el contacto %s", nombre));
            } else {
                mensaje = Color.error(String.format("El contacto %s no existe", nombre));
            }
        } catch (SQLException ex) {
            mensaje = Color.error("ERROR al ejecutar el DELETE SQL");
        }
        return mensaje;
    }

    //Se modifica, si existe, el contacto pasado como parámetro
    public static String update(String nombre, String telefono) {
        String mensaje;
        try ( PreparedStatement st = BD.getConexion().prepareStatement("UPDATE CONTACTO SET TELEFONO=? WHERE NOMBRE=?")) {
            st.setString(1, telefono);
            st.setString(2, nombre);
            st.executeUpdate();
            if (st.executeUpdate() > 0) {
                mensaje = Color.dialogo(String.format("Se ha modificado el teléfono de %s a %s", nombre, telefono));
            } else {
                mensaje = Color.error(String.format("El contacto %s no existe", nombre));
            }
        } catch (SQLException ex) {
            mensaje = Color.error("ERROR al ejecutar el UPDATE SQL");
        }
        return mensaje;
    }

    //Se imprimen los datos del contacto pasado como parámetro
    public static String consulta(String nombre) {
        String mensaje, telefono = "";

        try ( PreparedStatement st = BD.getConexion().prepareStatement("SELECT * FROM CONTACTO WHERE NOMBRE=?")) {
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                telefono = rs.getString("TELEFONO");
                mensaje = Color.dialogo(String.format("El teléfono de %s es: %s", nombre, telefono));
            } else {
                mensaje = Color.dialogo(String.format("No existe el contacto: %s", nombre));
            }
        } catch (SQLException ex) {
            mensaje = Color.error("ERROR al ejecutar el SELECT SQL");
        }
        return mensaje;
    }

    //Se listan todos los contactos de la agenda que cumplan el patrón de búsqueda pasado como parámetro
    public static String lista(String nombre) {
        StringBuilder mensaje = new StringBuilder();
        String telefono = "";

        try ( PreparedStatement st = BD.getConexion().prepareStatement("SELECT * FROM CONTACTO WHERE UPPER(NOMBRE) LIKE UPPER(?) ORDER BY NOMBRE")) {
            st.setString(1, nombre + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                nombre = rs.getString("NOMBRE");
                telefono = rs.getString("TELEFONO");
                mensaje.append(String.format(nombre + " - " + telefono)).append("\n");
            }
        } catch (SQLException ex) {
            mensaje.append(Color.error("ERROR al ejecutar el SELECT SQL"));
        }
        return mensaje.toString();
    }

    //Se calcula la cantidad de contactos en la agenda
    public static String cantidadContactos() {
        StringBuilder mensaje = new StringBuilder();

        try ( PreparedStatement st = BD.getConexion().prepareStatement("SELECT COUNT(*) FROM CONTACTO")) {
            ResultSet rs = st.executeQuery();
            rs.first(); //Se posiciona en la primera (y única al ser un COUNT(*)) fila del ResultSet
            mensaje.append(String.format("En la agenda hay %d contactos\n", rs.getInt(1)));
        } catch (SQLException ex) {
            mensaje.append(Color.error("ERROR al ejecutar el SELECT SQL"));
        }
        return mensaje.toString();
    }
}
