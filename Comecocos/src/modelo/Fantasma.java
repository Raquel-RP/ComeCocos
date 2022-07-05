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
     * Datos miembro que representan los instantes de tiempo que determinarán
     * cuándo salen los fantasmas de la cárcel.
     */
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
     * establece un nombre dado, además de contar el tiempo máximo que se
     * mantendrán cada uno dentro de la jaula.
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
     * @return El nombre enumerado del fantasma.
     */
    public NombreFantasma getNombre() {
        return nombre_;
    }

    /**
     * Cambia el valor del dato miembro FirstTime
     *
     * @param v Booleano que
     */
    public void setFirsTime(boolean v) {
        FirstTime = v;
    }

    /**
     * Inicializa los fantasmas del modelo estableciendo a cada uno de los
     * fantasmas una columna y fila específicos.
     *
     * @param modelo: Modelo en el que se representan los fantasmas que se
     * quieren iniciar
     */
    @Override
    public void inicializar(Modelo modelo) {
        time_inicio = System.currentTimeMillis();
        setFirsTime(false);

        switch (this.nombre_) {
            case INKY:
                this.setColumnaFila(14, 14);
                break;
            case PINKY:
                this.setColumnaFila(13, 14);
                break;
            case BLINKY:
                this.setColumnaFila(13, 11);
                break;
            case CLYDE:
                this.setColumnaFila(12, 14);
                break;
        }
    }

    /**
     * Método principal responsable del movimiento adecuado del fantasma
     * comprobando la disponibilidad de la celda a la que quiere acceder en su
     * siguiente movimiento y estableciendo la dirección correcta, responsable
     * de sacar los fantasmas inicializados en la jaula.
     *
     * @param modelo modelo en el que se encuentran los elementos del juego que
     * pueden interferir en el movimiento del fantasma.
     */
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
                this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());

            } else {//Clyde, Inky
                this.setDireccionAleatoriaPosible(modelo);
                sigPosicion = siguientePosicion();

                this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
            }
        } else { //si no es cruce

            if (this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY) {
                sigPosicion = siguientePosicion();
 
                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                } else {
                    this.setDireccionAleatoriaPosible(modelo);
                }

            } else { //Clyde, Inky
                Personaje.Direccion d = this.getDireccion();
                sigPosicion = siguientePosicion();

                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                } else {
                    this.setDireccionAleatoriaPosible(modelo);
                }

            }
        }
        notificarCambio();
    }

    /**
     * Calcula la posición del siguiente punto según la posición y el tamaño del
     * laberinto, es un método auxiliar de mover() que cambia las coordenadas
     * del fantasma según la dirección que vaya a tomar.
     *
     * @return El punto en sus coordenadas x e y de la siguiente posición según
     * la dirección que lleva.
     */
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
     * Coge la casilla con menor distancia euclidea posible al comecocos
     * comprobando por todas las posibles casillas libres cuál tiene la menor
     * distancia al comecocos.
     *
     * @param modelo: modelo en el que se encuentran los estados del laberinto
     * del juego y la posicion del comecocos que se quiere seguir
     * @return mejorCelda: la celda que representa la más cercana según la
     * distancia euclídea entre comecocos y fantasma.
     */
    private Punto getCeldaPerseguir(Modelo modelo) {
        double mejorDistancia = 0;
        Punto mejorCelda = null;

        Punto posActual = new Punto(this.getColumna(), this.getFila());
        Punto posComecocos = new Punto(modelo.getComecocos().getColumna(), modelo.getComecocos().getFila());
        
        Direccion d = this.getDireccion();
        Direccion dirIncial = this.getDireccion();
        
        //IZQUIERDA
        if (modelo.getLaberinto().estaLibre((this.getColumna() - 1 + 28) % 28, this.getFila()) && dirIncial != Direccion.DERECHA) {
            if (mejorCelda == null) {
                mejorCelda = new Punto((this.getColumna() - 1 + 28) % 28, this.getFila());
                mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                d = Direccion.IZQUIERDA;
            } else {
                if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                    mejorCelda = new Punto((this.getColumna() - 1 + 28) % 28, this.getFila());
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    d = Direccion.IZQUIERDA;
                }
            }
        }
        //DERECHA
        if (modelo.getLaberinto().estaLibre((this.getColumna() + 1) % 28, this.getFila()) && dirIncial != Direccion.IZQUIERDA) {
            if (mejorCelda == null) {
                mejorCelda = new Punto((this.getColumna() + 1) % 28, this.getFila());
                mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                d = Direccion.DERECHA;
            } else {
                if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                    mejorCelda = new Punto((this.getColumna() + 1) % 28, this.getFila());
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    d = Direccion.DERECHA;
                }
            }
        }
        //ABAJO
        if (modelo.getLaberinto().estaLibre(this.getColumna(), this.getFila() + 1) && dirIncial != Direccion.ARRIBA) {
            if (mejorCelda == null) {
                mejorCelda = new Punto(this.getColumna(), this.getFila() + 1);
                mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                d = Direccion.ABAJO;
            } else {
                if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                    mejorCelda = new Punto(this.getColumna(), this.getFila() + 1);
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    d = Direccion.ABAJO;

                }
            }
        }
        //ARRIBA
        if (modelo.getLaberinto().estaLibre(this.getColumna(), this.getFila() - 1) && dirIncial != Direccion.ABAJO) {
            if (mejorCelda == null) {
                mejorCelda = new Punto(this.getColumna(), this.getFila() - 1);
                mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                d = Direccion.ARRIBA;
            } else {
                if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {
                    mejorCelda = new Punto(this.getColumna(), this.getFila() - 1);
                    mejorDistancia = mejorCelda.getDistanciaEuclidea(posComecocos);
                    d = Direccion.ARRIBA;
                }
            }
        }
        
        this.setDireccion(d);

        return mejorCelda;
    }

    /**
     * Proporciona una nueva dirección aleatoria dentro de las posibles opciones
     * que haya.
     *
     * @param modelo modelo en el que se encuentran almacenados los datos del
     * juego
     */
    public void setDireccionAleatoriaPosible(Modelo modelo) {
        ArrayList<Direccion> dirPosibles;

        dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo);

        int aleatorio = new Random().nextInt(dirPosibles.size());
        this.setDireccion(dirPosibles.get(aleatorio));
    }

    /**
     * Añade las direcciones posibles que puede tomar en un cruce evitando que
     * tome la direccion por la que venía
     *
     * @param columna columna de la posicion en la que se encuentra el fantasma
     * @param fila fila de la posicion en la que se encuentra el fantasma
     * @param modelo modelo con los datos del laberinto del cual queremos
     * obtener si las posiciones contiguas de la actual están disponibles.
     * @return un ArrayList con las posibles direcciones que puede tomar el
     * fantasma según la posición en la que se encuentre.
     */
    private ArrayList<Direccion> posiblesDirecciones(int columna, int fila, Modelo modelo) {
        ArrayList<Direccion> direcciones = new ArrayList();

        boolean izq = modelo.getLaberinto().estaLibre((columna - 1 + 28) % 28, fila);
        boolean drcha = modelo.getLaberinto().estaLibre((columna + 1) % 28, fila);
        boolean arriba = modelo.getLaberinto().estaLibre(columna, fila - 1);
        boolean abajo = modelo.getLaberinto().estaLibre(columna, fila + 1);

        boolean[] vLibre = {izq, drcha, arriba, abajo};
        Direccion[] dirs = {Direccion.IZQUIERDA, Direccion.DERECHA, Direccion.ARRIBA, Direccion.ABAJO};

        Direccion d = this.getDireccion();

        if (null != d) {
            switch (d) {
                case IZQUIERDA:
                    vLibre[1] = false;
                    break;
                case DERECHA:
                    vLibre[0] = false;
                    break;
                case ARRIBA:
                    vLibre[3] = false;
                    break;
                case ABAJO:
                    vLibre[2] = false;
                    break;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (vLibre[i]) {
                direcciones.add(dirs[i]);
            }
        }
        return direcciones;
    }
}
