package main;

import color.Color;
import java.util.Scanner;
import util.BD;

/**
 * Programa principal que usa el acceso a base de datos configurada en BD.java
 *
 * @author Sebastián López
 * @version abril/2021
 */
public class Main {

    public static void main(String[] args) {
        boolean error = false;
        String opcion; //opción de menú
        String seguir;

        Scanner teclado = new Scanner(System.in);
        String nombre, telefono; //variables para leer datos de teclado

        if (BD.getConexion() != null) { //Se obtiene la conexión a la base de datos configurada en BD.java o null si no hay conexión

            do {
                System.out.println(Color.menu("----------------[Agenda]----------------"));
                System.out.println(Color.menu("(CREAR) la agenda de contactos vacía"));
                System.out.println(Color.menu("(CARGAR) con datos la agenda"));
                System.out.println(Color.menu("(CONSOLA) web en navegador"));
                System.out.println(Color.menu("(A)ñadir contacto a la agenda"));
                System.out.println(Color.menu("(B)orrar contacto"));
                System.out.println(Color.menu("(M)odificar contacto"));
                System.out.println(Color.menu("(C)onsultar contacto"));
                System.out.println(Color.menu("(L)istar todos los contactos"));
                System.out.println(Color.menu("(N)úmero de contactos en la agenda"));
                System.out.println(Color.menu("(F)in"));
                System.out.println(Color.menu("----------------------------------------"));

                //Se lee opción hasta que coincide con el patrón de la expresión regular
                do {
                    error = false;
                    System.out.print(Color.menu("Opción>>"));
                    opcion = teclado.nextLine().toUpperCase();
                    if (!opcion.matches("CREAR|CARGAR|CONSOLA|A|B|M|C|L|N|F")) {
                        error = true;
                        System.out.println(Color.error("No existe esa opción"));
                    }
                } while (error);

                // En función de la opción se lanza la operación CRUD correspondiente
                switch (opcion) {

                    case "CREAR": //Se crea la base de datos a partir del script SQL 
                        System.out.println(Color.dialogo("<<<Crear la agenda>>>"));
                        System.out.println("¡Atención! si la agenda ya existe se perderán los contactos que tenga.");
                        do {
                            System.out.println("(S)eguir o (C)ancelar");
                            seguir = teclado.nextLine().toUpperCase();
                        } while (!seguir.matches("S|C"));
                        if (seguir.equals("S")) {
                            System.out.println(Agenda.crea());
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;
                    case "CARGAR": //Se carga la base de datos a partir del script SQL 
                        System.out.println(Color.dialogo("<<<Cargar con datos la agenda>>>"));
                        System.out.println(Agenda.cargaDatos());
                        break;
                    case "CONSOLA":
                        System.out.println(BD.openWebConsola());
                        break;
                    case "A": //Se añade un contacto nuevo
                        System.out.println(Color.dialogo("<<<Añadir contacto>>>"));
                        System.out.print("Nombre:");
                        nombre = teclado.nextLine();
                        System.out.print("Teléfono:");
                        telefono = teclado.nextLine();
                        System.out.println(Agenda.insert(nombre, telefono));
                        break;
                    case "B": //Se borra, si existe, el contacto introducido
                        System.out.println(Color.dialogo("<<<Borrar contacto>>>"));
                        System.out.print("Nombre:");
                        nombre = teclado.nextLine();
                        System.out.println(Agenda.delete(nombre));
                        break;
                    case "M": //Se modifica, si existe, el contacto introducido
                        System.out.println(Color.dialogo("<<<Modificar contacto>>>"));
                        System.out.print("Nombre:");
                        nombre = teclado.nextLine();
                        System.out.print("Teléfono:");
                        telefono = teclado.nextLine();
                        System.out.println(Agenda.update(nombre, telefono));
                        break;
                    case "C": //Se imprimen los datos del contacto introducido
                        System.out.println(Color.dialogo("<<<Consultar contacto>>>"));
                        System.out.print("Nombre:");
                        nombre = teclado.nextLine();
                        System.out.println(Agenda.consulta(nombre));
                        break;
                    case "L": //Se listan todos los contactos de la agenda que cumplan el patrón de búsqueda
                        System.out.println(Color.dialogo("<<<Listar los contactos>>>"));
                        System.out.print("Nombre (admite iniciales):");
                        nombre = teclado.nextLine();
                        System.out.println("");
                        System.out.println(Agenda.lista(nombre));
                        break;
                    case "N":
                        System.out.println(Color.dialogo("<<<Cantidad de contactos en la agenda>>>"));
                        System.out.println(Agenda.cantidadContactos());
                        break;
                }
            } while (!opcion.equals("F"));

        } else {
            System.out.println(Color.error("Recuerda que debes ajustar los parámetros de conexión a la base de datos en la clase BD.java"));
        }

        BD.closeConexion(); //Se cierra la conexión con la base de datos

    }
}
