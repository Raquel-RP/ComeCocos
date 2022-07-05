package modelo;

import vista.ObservadorLaberinto;

/**
 * Clase que representa el laberinto propio del juego y almacena el estado del
 * panel en todo momento.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Laberinto {

    /**
     * Enumera los posibles tipos de celdas del laberinto.
     */
    public enum TipoCelda {
        BLOQUE, LIBRE, COCOPEQUENO, COCOGRANDE, PUERTA
    }

    /**
     * Vector de String que define el laberinto inicial.
     */
    private final static String LABERINTOINICIAL[] = {
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "B............BB............B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "BoBBBB.BBBBB.BB.BBBBB.BBBBoB",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "B..........................B",
        "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
        "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
        "B......BB....BB....BB......B",
        "BBBBBB.BBBBB BB BBBBB.BBBBBB",
        "     B.BBBBB BB BBBBB.B     ",
        "     B.BB          BB.B     ",
        "     B.BB BBBPPBBB BB.B     ",
        "BBBBBB.BB B      B BB.BBBBBB",
        "      .   B      B   .      ",
        "BBBBBB.BB B      B BB.BBBBBB",
        "     B.BB BBBBBBBB BB.B     ",
        "     B.BB          BB.B     ",
        "     B.BB BBBBBBBB BB.B     ",
        "BBBBBB.BB BBBBBBBB BB.BBBBBB",
        "B............BB............B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
        "Bo..BB................BB..oB",
        "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
        "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
        "B......BB....BB....BB......B",
        "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
        "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
        "B..........................B",
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBB"
    };

    private TipoCelda[][] celdas_;//almacena los tipos de celdas del laberinto
    private ObservadorLaberinto observador_;//observa continuamente el laberinto

    private static final int NUMEROFILAS = LABERINTOINICIAL.length; // número de filas que va a tener el laberinto
    private static final int NUMEROCOLUMNAS = LABERINTOINICIAL[0].length(); // número de columnas que va a tener el laberinto

    /**
     * Constructor del laberinto que inicializa la matriz bidimensional de
     * celdas con la dimensión dada por el numero de filas y de columnas.
     */
    public Laberinto() {
        celdas_ = new TipoCelda[NUMEROCOLUMNAS][NUMEROFILAS]; //x son columnas, y son filas, hay que hacerlo así 
    }

    /**
     * Método de consulta que devuelve el tipo de celda en una posición dada en
     * forma de columna y fila.
     *
     * @param columna Entero que representa la columna de busqueda de un tipo de
     * celda
     * @param fila Entero de la fila de búsqueda de un tipo de celda.
     * @return El tipo de celda de una posición específica.
     */
    public TipoCelda getCelda(int columna, int fila) {
        return celdas_[columna][fila];
    }

    /**
     * Establece el tipo de celda para una posición dada de la matriz de celdas
     * y notifica el cambio una vez se actualiza la matriz.
     *
     * @param columna El entero de la columna de la celda que se establece.
     * @param fila El entero de la fila de la celda que se establece
     * @param valor Es el tipo de celda que se establece en la posición dicha.
     */
    public void setCelda(int columna, int fila, TipoCelda valor) {
        celdas_[columna][fila] = valor;
        notificarCambio(); //avisa de que las celdas han cambiado para que se actualice el laberinto
    }

    /**
     * Método de consulta del ancho del laberinto.
     *
     * @return el entero que representa el ancho del laberinto.
     */
    public int getAnchura() {
        return celdas_.length;
    }

    /**
     * Método de consulta del alto del laberinto.
     *
     * @return el entero que representa la altura del laberinto.
     */
    public int getAltura() {
        return celdas_[0].length;
    }

    /**
     * Inicializa el laberinto al completo recorriendo las dos dimensiones del
     * laberinto y estableciendo el tipo de celda según el String de laberinto
     * inicial propuesto.
     */
    public void inicializar() {

        for (int fila = 0; fila < NUMEROFILAS; fila++) {
            for (int columna = 0; columna < NUMEROCOLUMNAS; columna++) {
                switch (LABERINTOINICIAL[fila].charAt(columna)) {
                    case 'B':
                        celdas_[columna][fila] = TipoCelda.BLOQUE;
                        break;
                    case 'P':
                        celdas_[columna][fila] = TipoCelda.PUERTA;
                        break;
                    case 'o':
                        celdas_[columna][fila] = TipoCelda.COCOGRANDE;
                        break;
                    case ' ':
                        celdas_[columna][fila] = TipoCelda.LIBRE;
                        break;
                    case '.':
                        celdas_[columna][fila] = TipoCelda.COCOPEQUENO;
                        break;
                    default:
                        celdas_[columna][fila] = TipoCelda.LIBRE;
                }
            }
        }
    }

    /**
     * Notifica el estado del laberinto al observador de laberinto.
     */
    public void notificarCambio() {
        observador_.actualizarObservadorLaberinto();
    }

    /**
     * Registra el observador de laberinto en el dato miembro observador_.
     *
     * @param o Observador de laberinto que se quiere registrar.
     */
    public void registrarObservador(ObservadorLaberinto o) {
        observador_ = o;
    }

    /**
     * Compueba si está libre o no la celda solicitada según sus componentes de
     * columna y fila, es decir, comprueba que no sea bloque o puerta.
     *
     * @param columna número de la columna que se quiere comprobar
     * @param fila número de la fila que se quiere comprobar
     * @return un booleano que indica si la posición está libre
     */
    public boolean estaLibre(int columna, int fila) {

        boolean libre = true;
        TipoCelda celda = this.getCelda(columna, fila);

        if (celda == TipoCelda.BLOQUE || celda == TipoCelda.PUERTA) {
            libre = false;
        }

        return libre;
    }

    /**
     * Comprueba si la casilla de la posición dada es un cruce de caminos en el
     * que se encuentren más de una dirección posible o no.
     *
     * @param columna número de la columna que se quiere comprobar
     * @param fila número de la fila que se quiere comprobar
     * @return un booleano que indica si la posición está libre
     */
    public boolean esCruce(int columna, int fila) {
        boolean cruce = false;
        int libres = 0;

        boolean izq = estaLibre((columna - 1 + 28) % 28, fila);
        boolean drcha = estaLibre((columna + 1) % 28, fila);
        boolean arriba = estaLibre(columna, fila - 1);
        boolean abajo = estaLibre(columna, fila + 1);

        boolean[] vLibre = {izq, drcha, arriba, abajo};

        for (int i = 0; i < vLibre.length; i++) {
            if (vLibre[i]) {
                libres++;
            }
        }
        
        if (libres > 2) {
            cruce = true;
        } 
        
        return cruce;
    }
}
