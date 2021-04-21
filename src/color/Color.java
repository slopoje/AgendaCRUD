package color;

import color.ANSI;

/**
 * Clase para añadir color ANSI a texto en forma de String.
 *
 * @author Sebastián López
 * @version febrero/2021
 */
public class Color {

    /**
     * Colorea de menu ANSI un String
     *
     * @param texto a colorear de menu
     * @return texto coloreado
     */
    public static String menu(String texto) {
        return (ANSI.LETRA_AZUL + texto + ANSI.RESET);
    }

    /**
     * Colorea de error ANSI un String
     *
     * @param texto a colorear de error
     * @return texto coloreado
     */
    public static String error(String texto) {
        return (ANSI.LETRA_ROJA + texto + ANSI.RESET);
    }

    /**
     * Colorea de dialogo ANSI un String
     *
     * @param texto a colorear de dialogo
     * @return texto coloreado
     */
    public static String dialogo(String texto) {
        return (ANSI.LETRA_VERDE + texto + ANSI.RESET);
    }

}