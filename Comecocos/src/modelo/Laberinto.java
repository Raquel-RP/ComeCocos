package modelo;

import vista.ObservadorLaberinto;

/**
 *
 * @author Raquel Romero
 */
public class Laberinto {

    public enum TipoCelda {
        BLOQUE, LIBRE, COCOPEQUENO, COCOGRANDE, PUERTA
    }

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

    private TipoCelda[][] celdas_;
    private ObservadorLaberinto observador_;

    private static final int NUMEROFILAS = LABERINTOINICIAL.length; //añado num de filas y columnas que va a tener el laberinto
    private static final int NUMEROCOLUMNAS = LABERINTOINICIAL[0].length();

    public Laberinto() {
        celdas_ = new TipoCelda[NUMEROCOLUMNAS][NUMEROFILAS]; //x son columnas, y son filas, hay que hacerlo así 
    }

    public TipoCelda getCelda(int columna, int fila) {
        return celdas_[columna][fila];
    }

    public void setCelda(int columna, int fila, TipoCelda valor) {
        celdas_[columna][fila] = valor;
        notificarCambio(); //avisa de que las celdas han cambiado para que se actualice el laberinto
    }

    public int getAnchura() {
        return celdas_.length;
    }

    public int getAltura() {
        return celdas_[0].length;
    }

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

    public void notificarCambio() {
        observador_.actualizarObservadorLaberinto();
    }

    public void registrarObservador(ObservadorLaberinto o) {
        observador_ = o;
    }

    public boolean estaLibre(int columna, int fila) {

        boolean libre = true;
        TipoCelda celda = this.getCelda(columna, fila);

        if (celda == TipoCelda.BLOQUE || celda == TipoCelda.PUERTA) {
            libre = false;
        }

        return libre;
    }

    public boolean esInterseccion(int columna, int fila) {

        int caminos = 0;

        if (this.estaLibre(columna - 1, fila)) {
            caminos++;
        } else if (this.estaLibre(columna, fila - 1)) {
            caminos++;
        } else if (this.estaLibre(columna + 1, fila)) {
            caminos++;
        } else if (this.estaLibre(columna, fila + 1)) {
            caminos++;
        }
        return caminos > 2;
    }
}
