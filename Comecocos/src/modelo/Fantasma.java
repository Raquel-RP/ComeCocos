package modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hereda de personaje y representa al fantasma definiendo sus movimientos.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Fantasma extends Personaje {

    /**
     * Enumera los nombres de los fantasmas.
     */
    public enum NombreFantasma {
        INKY, PINKY, BLINKY, CLYDE
    }

    private NombreFantasma nombre_;//nombre del fantasma

    /**
     * Constructor del fantasma que lo inicializa en una posición dada y se le
     * establece un nombre dado.
     *
     * @param col Columna de la posición en la que se quiere inicializar el
     * fantasma
     * @param fila Fila de la posición en la que se quiere inicializar el
     * fantasma
     * @param nombre Nombre del fantasma que se quiere incializar en cierta
     * posicion
     */
    public Fantasma(int col, int fila, NombreFantasma nombre) {
        this.setColumnaFila(col, fila);
        this.nombre_ = nombre;
        this.setDireccion(Personaje.Direccion.NINGUNA);
    }

    /**
     * Método de consulta del nombre de un fantasma.
     *
     * @return El nomnbre enumerado del fantasma.
     */
    public NombreFantasma getNombre() {
        return nombre_;
    }

    @Override
    public void inicializar(Modelo modelo) {
        //this.setColumnaFila(14,14);
        //this.setDireccion(Direccion.NINGUNA);
        // modelo.inicializarJuego();
    }

    @Override
    public void mover(Modelo modelo) {
        Punto sigPosicion;
        ArrayList<Direccion> dirPosibles;

        if (modelo.getLaberinto().esCruce(this.getColumna(), this.getFila())) {
            if (this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY) {
                sigPosicion = this.getCasillaPerseguir(modelo);

                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                }
            } else {//Clyde, Inky
                Personaje.Direccion d = this.getDireccion();
                this.setDireccionAleatoriaPosible(d, modelo);
                sigPosicion = siguientePosicion();

                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                }
            }
        } else { //si no es cruce
            dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo);
            for (Direccion dir : dirPosibles) {
                
                if (dir != this.getDireccion()) {
                    this.setDireccion(dir);
                }
            }
            sigPosicion = siguientePosicion();
            if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
            }
        }
        notificarCambio();
    }

    private Punto siguientePosicion() {

        Punto punto;
        punto = new Punto();

        int x, y;
        x = this.getColumna();
        y = this.getFila();

        switch (this.getDireccion()) {

            case IZQUIERDA:
                if (x == 0) {
                    x = 27;
                } else {
                    x--;
                }
                break;
            case DERECHA:
                x = (x + 1) % 28; // Longitud del laberinto
                break;
            case ARRIBA:
                y--;
                break;
            case ABAJO:
                y++;
                break;
        }
        punto.setCoordenadas(x, y);
        return punto;
    }

    /**
     * Coge la casilla con menor distancia euclidea posible al comecocos No
     * funciona bien
     *
     * @param modelo
     * @return
     */
    private Punto getCasillaPerseguir(Modelo modelo) {

        Punto posActual = new Punto(this.getColumna(), this.getFila());
        Punto posComecocos = new Punto(modelo.getComecocos().getColumna(), modelo.getComecocos().getFila());
        double mejorDistancia = 0;
        Punto mejorPunto = null;

        int columna = this.getColumna();
        int fila = this.getFila();

        Laberinto laberinto = modelo.getLaberinto();

        if (laberinto.estaLibre(this.getColumna(), this.getFila())) {
            if (laberinto.estaLibre(columna - 1, fila)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna - 1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna - 1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                    }
                }
            } else if (laberinto.estaLibre(columna + 1, fila)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna + 1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna + 1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            } else if (laberinto.estaLibre(columna, fila + 1)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna, fila + 1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna, fila + 1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            } else if (laberinto.estaLibre(columna, fila - 1)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna, fila - 1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna, fila - 1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            }
        }
        return mejorPunto;
    }

    /**
     * Proporciona una nueva dirección aleatoria dentro de las posibles opciones
     * que haya.
     *
     * @param d
     * @param modelo
     */
    public void setDireccionAleatoriaPosible(Direccion d, Modelo modelo) {
        ArrayList<Direccion> dirPosibles;

        dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo);
        for (int i = 0; i < dirPosibles.size(); i++) {
            System.out.println("Dir" + i + " " + dirPosibles.get(i));
        }
        if (dirPosibles.contains(d)) {
            dirPosibles.remove(d);
        }

        int aleatorio = new Random().nextInt(dirPosibles.size());
        this.setDireccion(dirPosibles.get(aleatorio));
        System.out.println(this.getDireccion());
    }

    /**
     * Añade las direcciones posibles que puede tomar en un cruce
     *
     * @param columna
     * @param fila
     * @param modelo
     * @return
     */
    public ArrayList<Direccion> posiblesDirecciones(int columna, int fila, Modelo modelo) {
        ArrayList<Direccion> direcciones = new ArrayList();

        boolean izq = modelo.getLaberinto().estaLibre((columna - 1 + 28) % 28, fila);
        boolean drcha = modelo.getLaberinto().estaLibre((columna + 1) % 28, fila);
        boolean arriba = modelo.getLaberinto().estaLibre(columna, fila - 1);
        boolean abajo = modelo.getLaberinto().estaLibre(columna, fila + 1);

        boolean[] vLibre = {izq, drcha, arriba, abajo};
        Direccion[] dirs = {Direccion.IZQUIERDA, Direccion.DERECHA, Direccion.ARRIBA, Direccion.ABAJO};

        for (int i = 0; i < 4; i++) {
            if (vLibre[i]) {
                direcciones.add(dirs[i]);
            }
        }
        return direcciones;
    }
}
