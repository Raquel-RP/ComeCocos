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

    long time_inicio = 0, time_end = 0, time_limit;
    boolean FirstTime = false;

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
        time_inicio = System.currentTimeMillis(); //Tiempo en el que empieza

        // Para que vayan saliendo los fantasmas de la cárcel escalonadamente
        if (nombre == NombreFantasma.CLYDE) {
            time_limit = 6000;
        } else if (nombre == NombreFantasma.INKY) {
            time_limit = 13000;
        } else if (nombre == NombreFantasma.PINKY) {
            time_limit = 20000;
        }
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
        switch (this.nombre_) {
            case INKY:
                this.setColumnaFila(14,14);
                break;
            case PINKY:
                this.setColumnaFila(13,14);
                break;
            case BLINKY:
                this.setColumnaFila(13,11);
                break;
            case CLYDE:
                this.setColumnaFila(12,14);
                break;
        }
        //this.setColumnaFila(14,14);
        //this.setDireccion(Direccion.NINGUNA);
        // modelo.inicializarJuego();
    }

    @Override
    public void mover(Modelo modelo) {
        Punto sigPosicion;
        time_end = System.currentTimeMillis();

        if (!FirstTime && time_end - time_inicio >= time_limit) {
            FirstTime = true;
            modelo.sacarFantasma();
            this.setColumnaFila(12, 11);
        }

        if (modelo.getLaberinto().esCruce(this.getColumna(), this.getFila())) {

            if (this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY) {
                sigPosicion = this.getCeldaPerseguir(modelo);

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

            if (this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY) {
                sigPosicion = this.getCeldaPerseguir(modelo);

                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                }
            } else {//Clyde, Inky
                ArrayList<Direccion> dirPosibles;
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
     * funciona bien. Comprueba por todas las posibles casillas libres cuál
     * tiene la mejor distancia al comecocos.
     *
     * @param modelo
     * @return
     */
    private Punto getCeldaPerseguir(Modelo modelo) {
        double mejorDistancia = 0;
        Punto mejorCelda = null;

        Punto posActual = new Punto(this.getColumna(), this.getFila());
        Punto posComecocos = new Punto(modelo.getComecocos().getColumna(), modelo.getComecocos().getFila());

        if (modelo.getLaberinto().estaLibre(this.getColumna(), this.getFila())) {

            //IZQUIERDA
            if (modelo.getLaberinto().estaLibre((this.getColumna() - 1 + 28) % 28, this.getFila())) {
                if (mejorCelda == null) {
                    mejorCelda = new Punto((this.getColumna() - 1 + 28) % 28, this.getFila());
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                        mejorCelda = new Punto((this.getColumna() - 1 + 28) % 28, this.getFila());
                        mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    }
                }
            }
            //DERECHA
            if (modelo.getLaberinto().estaLibre((this.getColumna() + 1) % 28, this.getFila())) {
                if (mejorCelda == null) {
                    mejorCelda = new Punto((this.getColumna() + 1) % 28, this.getFila());
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                        mejorCelda = new Punto((this.getColumna() + 1) % 28, this.getFila());
                        mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    }
                }
            }
            //ARRIBA
            if (modelo.getLaberinto().estaLibre(this.getColumna(), this.getFila() + 1)) {
                if (mejorCelda == null) {
                    mejorCelda = new Punto(this.getColumna(), this.getFila() + 1);
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                        mejorCelda = new Punto(this.getColumna(), this.getFila() + 1);
                        mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);

                    }
                }
            }
            //ABAJO
            if (modelo.getLaberinto().estaLibre(this.getColumna(), this.getFila() - 1)) {
                if (mejorCelda == null) {
                    mejorCelda = new Punto(this.getColumna(), this.getFila() - 1);
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                        mejorCelda = new Punto(this.getColumna(), this.getFila() - 1);
                        mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);

                    }
                }
            }
        }
        return mejorCelda;
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

        if (dirPosibles.contains(d)) {
            dirPosibles.remove(d);
        }

        int aleatorio = new Random().nextInt(dirPosibles.size());
        this.setDireccion(dirPosibles.get(aleatorio));
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
