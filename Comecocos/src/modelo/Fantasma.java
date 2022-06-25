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

    long time_end = 0, time_limit;
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
        //time_inicio = System.currentTimeMillis(); //Tiempo en el que empieza

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

    /**
     * Inicializa los fantasmas del modelo estableciendo a cada uno de los
     * fantasmas una columna y fila específicos.
     * 
     * @param modelo:   Modelo en el que se representan los fantasmas que se
     *                  quieren iniciar
     */
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

    /**
     * Método principal responsable del movimiento adecuado del fantasma
     * comprobando la disponibilidad de la celda a la que quiere acceder en
     * su siguiente movimiento y estableciendo la dirección correcta, responsable
     * de sacar los fantasmas inicializados en la jaula.
     *
     * @param modelo    modelo en el que se encuentran los elementos del juego que
     *                  pueden interferir en el movimiento del fantasma.
     */
    @Override
    public void mover(Modelo modelo) {
        Punto sigPosicion;
        time_end = System.currentTimeMillis();

        if (!FirstTime && time_end - modelo.time_inicio >= time_limit) {
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
                dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo, this.getDireccion());
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

    /**
     * Calcula la posición del siguiente punto según la posición y el tamaño
     * del laberinto, es un método auxiliar de mover() que cambia las coordenadas
     * del fantasma según la dirección que vaya a tomar.
     * 
     * @return El punto en sus coordenadas x e y de la siguiente posición según
     *         la dirección que lleva.
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
     *              del juego y la posicion del comecocos que se quiere seguir
     * @return mejorCelda: la celda que representa la más cercana según la
     *                  distancia euclídea entre comecocos y fantasma.
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
     * @param d         direccion inicial que lleva el fantasma.
     * @param modelo    modelo en el que se encuentran almacenados los datos del juego
     */
    public void setDireccionAleatoriaPosible(Direccion d, Modelo modelo) {
        ArrayList<Direccion> dirPosibles;

        dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo, d);

        
        if (dirPosibles.contains(d)) {
            dirPosibles.remove(d);
        }
        //System.out.println(dirPosibles);
        int aleatorio = new Random().nextInt(dirPosibles.size());
        this.setDireccion(dirPosibles.get(aleatorio));
    }

    /**
     * Añade las direcciones posibles que puede tomar en un cruce evitando que
     * tome la direccion por la que venía
     *
     * @param columna   columna de la posicion en la que se encuentra el fantasma
     * @param fila      fila de la posicion en la que se encuentra el fantasma
     * @param modelo    modelo con los datos del laberinto del cual queremos obtener
     *                  si las posiciones contiguas de la actual están disponibles.
     * @param d         direccion que trae el fantasma hasta llegar a la posicion actual.
     * @return          un ArrayList con las posibles direcciones que puede tomar
     *                  el fantasma según la posición en la que se encuentre.
     */
    public ArrayList<Direccion> posiblesDirecciones(int columna, int fila, Modelo modelo, Direccion d) {
        ArrayList<Direccion> direcciones = new ArrayList();

        boolean izq = modelo.getLaberinto().estaLibre((columna - 1 + 28) % 28, fila);
        boolean drcha = modelo.getLaberinto().estaLibre((columna + 1) % 28, fila);
        boolean arriba = modelo.getLaberinto().estaLibre(columna, fila - 1);
        boolean abajo = modelo.getLaberinto().estaLibre(columna, fila + 1);

        boolean[] vLibre = {izq, drcha, arriba, abajo};
        Direccion[] dirs = {Direccion.IZQUIERDA, Direccion.DERECHA, Direccion.ARRIBA, Direccion.ABAJO};

        
        if(d.equals(Direccion.IZQUIERDA)){
            drcha=false;
        }
        if(d.equals(Direccion.DERECHA)){
           izq=false;
        }
        if(d.equals(Direccion.ARRIBA)){
           abajo=false;
        }
        if(d.equals(Direccion.ABAJO)){
           arriba=false;
        }
        
        for (int i = 0; i < 4; i++) {
            if (vLibre[i]) {
                direcciones.add(dirs[i]);
            }
        }
        return direcciones;
    }
}
