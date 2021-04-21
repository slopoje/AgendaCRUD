package util;

import color.Color;
import java.sql.*;
import org.h2.tools.Server;
import java.util.Scanner;

/**
 * Programa para gestionar el servidor de consola web del gestor de base de
 * datos H2.
 *
 * @author Sebastián López
 * @version abril/2021
 * @see
 * <a href="http://www.h2database.com/html/tutorial.html#tutorial_starting_h2_console">Documentación
 * H2: Starting and Using the H2 Console</a>
 * @see
 * <a href="http://www.h2database.com/javadoc/org/h2/tools/Server.html?highlight=web%2Cserver&search=web%20server/">Documentación
 * H2: clase Server</a>
 */
public class ConsolaWeb {

    public static void main(String[] args) {

        Server srv = null;
        boolean srvCreado = false;

        Scanner teclado = new Scanner(System.in);
        String opcion = "";

        try {
            System.out.println(Color.dialogo("Creando el servidor web..."));
            srv = Server.createWebServer();
            System.out.println(Color.dialogo("Servidor creado"));
            srvCreado = true;
        } catch (SQLException e) {
            System.out.println(Color.error("ERROR: No se pudo crear el servidor web."));
        }

        if (srvCreado) {
            do {
                try {
                    System.out.println(Color.menu("---------[Gestión de servidor web H2]---------"));
                    System.out.println(Color.menu("(A)rranque"));
                    System.out.println(Color.menu("(P)arada"));
                    System.out.println(Color.menu("(E)stado"));
                    System.out.println(Color.menu("(C)onsola web (se abrirá en el navegador web)"));
                    System.out.println(Color.menu("(F)in"));
                    System.out.println(Color.menu("----------------------------------------------"));
                    System.out.println(Color.menu("Opción>>"));
                    do {
                        opcion = teclado.nextLine().toUpperCase();
                    } while (!opcion.matches("A|P|E|C|F"));

                    switch (opcion) {
                        case "A":
                            if (srv.isRunning(false)) {
                                System.out.println(Color.error("El servidor ya estaba funcionando."));
                            } else {
                                System.out.println(Color.dialogo("Arrancando el servidor..."));
                                srv.start();
                                System.out.println(Color.dialogo("Servidor arrancado."));
                            }
                            break;
                        case "P":
                            if (!srv.isRunning(false)) {
                                System.out.println(Color.error("El servidor ya estaba parado."));
                            } else {
                                System.out.println(Color.dialogo("Parando el servidor..."));
                                srv.stop();
                                System.out.println(Color.dialogo("Servidor parado."));
                            }
                            break;
                        case "E":
                            System.out.println(Color.dialogo("Estado del servidor:" + srv.getStatus()));
                            break;
                        case "C":
                            System.out.println(Color.dialogo("Abriendo consola en navegador web..."));
                            srv.openBrowser("http://127.0.0.1:8082");
                            System.out.println(Color.dialogo("Consola abierta."));
                            break;
                    }
                } catch (SQLException e) {
                    System.out.println(Color.error("ERROR: No se pudo completar la operación."));
                } catch (Exception e) {
                    System.out.println(Color.error("ERROR: No se pudo completar la operación."));
                }
            } while (!opcion.equals("F"));
            System.out.println(Color.dialogo("Cerrando el servidor..."));
            srv.stop();
            System.out.println(Color.dialogo("Servidor cerrado."));
        }
    }

    //Impide que se creen objetos de esta clase
    private ConsolaWeb() {
    }
}
