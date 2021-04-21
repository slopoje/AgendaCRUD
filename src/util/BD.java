package util;

import color.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import org.h2.tools.Server;

/**
 * Clase para gestionar la conexión a base de datos desde Java JDBC por medio
 * del patrón singleton, también se incluyen métodos útiles para trabajar con la
 * base de datos.
 *
 * @author Sebastián López
 * @version abril/2021
 * @see
 * <a href=http://www.juntadeandalucia.es/servicios/madeja/contenido/recurso/202/>Marco
 * de Desarrollo de la Junta de Andalucía</a>
 * @see
 * <a href=https://itquetzali.com/2019/09/01/patron-de-diseno-singleton-implementacion-en-java/>itquetzali.com:
 * Singleton</a>
 * @see
 * <a href=http://www.programandoapasitos.com/2016/07/java-y-mysql-patron-singleton.html>Patrón
 * Singleton acceso a base de datos en Java</a>
 */
public class BD {

    /**
     * Ruta al directorio que contiene los ficheros script de SQL.
     */
    public static final String RUTA_SCRIPTSQL = "./src/scriptSQL/";

    /**
     * Ruta al directorio que contiene los ficheros de base de datos.
     */
    public static final String RUTA_BD = "./bd/";

    /**
     * Nombre de la base de datos.
     */
    public static final String BASE_DE_DATOS = "agenda";

    /**
     * Usuario de la base de datos.
     */
    public static final String USER = "java";

    /**
     * Password del usuario de base de datos.
     */
    public static final String PASSWORD = "java";

    /**
     * Cadena de conexión JDBC.
     */
    public static final String URL = "jdbc:h2:" + RUTA_BD + BASE_DE_DATOS + ";AUTO_SERVER=TRUE";

    /**
     * Objeto Connection Singleton.
     */
    private static Connection conexionBD = null;

    /**
     * Objeto Servidor Web para consola
     */
    private static Server servidorWebH2 = null;

    /**
     * Método privado para que no se puedan instanciar objetos de la clase desde
     * fuera.
     */
    private BD() {
        try {
            BD.conexionBD = DriverManager.getConnection(URL, USER, PASSWORD);
            try {
                servidorWebH2 = Server.createWebServer(); //Crea el servidor web para consola
                servidorWebH2.start(); //Arranca el servidor web pata consola
            } catch (SQLException e) {
                System.out.println(Color.error("ERROR: no se pudo crear el servidor web para la consola."));
            }
        } catch (SQLException e) {
            System.out.println(Color.error("ERROR: no es posible conectar con la base de datos."));
        }
    }

    /**
     * Devuelve la conexión a la base de datos según el patrón Singleton.
     *
     * @return el objeto Singleton Connection
     */
    public static Connection getConexion() {

        if (BD.conexionBD == null) {
            new BD();
        }
        return BD.conexionBD;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public static void closeConexion() {

        if (BD.conexionBD != null) {
            try {
                if (servidorWebH2 != null) { //Parar el servidor web si está funcionando
                    servidorWebH2.stop();
                }
                BD.conexionBD.close(); //Cierra la conexión con la base de datos
            } catch (SQLException e) {
                System.out.println(Color.error("ERROR: no es posible cerrar la conexión con la base de datos."));
            }
        }
    }

    /**
     * Lanza la consola de la base de datos H2 en una ventana de navegador.
     */
    public static String openWebConsola() {
        String mensaje = "";

        if (servidorWebH2 == null) {
            mensaje = Color.error("ERROR: El servidor web no está funcionando.");
        } else {
            try {
                servidorWebH2.openBrowser("http://127.0.0.1:8082");
            } catch (Exception e) {
                mensaje = Color.error("ERROR: no se pudo abrir la consola en el navegador web.");
            }
        }
        return mensaje;
    }

    /**
     * Devuelve un String con el contenido de un fichero de script SQL. El
     * fichero debe estar en la ruta
     *
     * @param ficheroScriptSQL Nombre del fichero script SQL
     * @return el contenido del fichero script como un String
     */
    private static String cargaFicheroScriptSQL(String ficheroScriptSQL) {
        String linea = "";
        StringBuilder scriptSQL = new StringBuilder();

        System.out.print(Color.dialogo("Leyendo fichero de script SQL " + ficheroScriptSQL + "... "));
        File f = new File(BD.RUTA_SCRIPTSQL + ficheroScriptSQL);
        try ( FileReader fr = new FileReader(f);  BufferedReader br = new BufferedReader(fr)) {
            while ((linea = br.readLine()) != null) {
                scriptSQL.append(linea);
            }
            System.out.println(Color.dialogo("OK"));
        } catch (FileNotFoundException e) {
            System.out.println(Color.error("ERROR al abrir el fichero script SQL."));
        } catch (IOException ex) {
            System.out.println(Color.error("ERROR leyendo el fichero de script sQL."));
        }
        return scriptSQL.toString();
    }

    /**
     * Ejecuta un script SQL contenido en el fichero que se indica como
     * parámetro. El fichero debe estar en la ruta {@value #RUTA_SCRIPTSQL}
     *
     * @param ficheroScriptSQL Nombre del fichero script SQL
     * @return true si el script se ejecutó correctamente, false en otro caso
     */
    public static boolean execScriptSQL(String ficheroScriptSQL) {
        boolean resultado = false;
        String scriptSQL = "";

        scriptSQL = cargaFicheroScriptSQL(ficheroScriptSQL);
        if (scriptSQL.length() > 0) {
            System.out.print(Color.dialogo("Ejecutando el fichero de script SQL " + ficheroScriptSQL + "... "));
            try ( Statement st = BD.getConexion().createStatement()) {
                st.execute(scriptSQL);
                resultado = true;
                System.out.println(Color.dialogo("OK"));
            } catch (SQLException ex) {
                resultado = false;
                System.out.println(Color.error("ERROR ejecutando script SQL."));
            }
        }
        return resultado;
    }
}
