package color;

/**
 * Clase que contiene la declaración de las <strong>constantes con los códigos
 * de color ANSI</strong> utilizados en la aplicación.
 * <p>
 * Esta clase no contiene ningún método, solo atributos de clase, estáticos
 * finales y públicos.</p>
 * <p>
 * Más información sobre códigos ANSI en:<br>
 * <a href="https://es.wikipedia.org/wiki/Instituto_Nacional_Estadounidense_de_Est%C3%A1ndares">Entrada
 * en wikipedia de ANSI</a><br>
 * <a href="https://www.campusmvp.es/recursos/post/como-cambiar-los-colores-de-la-consola-con-java-y-system-out-println.aspx">Cómo
 * cambiar los colores de la consola con Java y System.out.println - Entrada en
 * el blog de José Manuel Alarcón</a>
 * </p>
 *
 * @author Sebastián López
 * @version febrero/2021
 */
public class ANSI {

    //Códigos de color de fondo
    /**
     * Constante que contiene el código de color ANSI fondo dialogo {@value}
     */
    public static final String FONDO_VERDE = "\u001b[42m";
    /**
     * Constante que contiene el código de color ANSI fondo error {@value}
     */
    public static final String FONDO_ROJO = "\u001b[41m";
    /**
     * Constante que contiene el código de color ANSI fondo amarillo {@value}
     */
    public static final String FONDO_AMARILLO = "\u001b[43m";
    /**
     * Constante que contiene el código de color ANSI fondo cian {@value}
     */
    public static final String FONDO_CIAN = "\u001b[46m";
    /**
     * Constante que contiene el código de color ANSI fondo menu {@value}
     */
    public static final String FONDO_AZUL = "\u001b[44m";
    /**
     * Constante que contiene el código de color ANSI fondo blanco {@value}
     */
    public static final String FONDO_BLANCO = "\u001b[47m";

    //Códigos de color de carácter
    /**
     * Constante que contiene el código de color ANSI carácter blanco {@value}
     */
    public static final String LETRA_BLANCA = "\u001B[37m";
    /**
     * Constante que contiene el código de color ANSI carácter menu {@value}
     */
    public static final String LETRA_AZUL = "\u001B[34m";
    /**
     * Constante que contiene el código de color ANSI carácter error {@value}
     */
    public static final String LETRA_ROJA = "\u001B[31m";
    /**
     * Constante que contiene el código de color ANSI carácter amaillo {@value}
     */
    public static final String LETRA_AMARILLA = "\u001B[33m";
    /**
     * Constante que contiene el código de color ANSI carácter dialogo {@value}
     */
    public static final String LETRA_VERDE = "\u001B[32m";

    /**
     * Constante que contiene el código ANSI para volver a los colores normales
     * {@value}
     */
    public static final String RESET = "\u001B[0m";

    //Constructor privado para evitar instanciar objetos de esta clase.
    private ANSI() {
    }
}
