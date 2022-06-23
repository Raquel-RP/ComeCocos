package modelo;

import java.util.Random;
import vista.ObservadorPersonaje;

/**
 * Clase abstracta que representa cualquier personaje que tenga alguna
 * acción sobre el tablero de juego.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public abstract class Personaje {

    /**
     * Enumeración de los tipos de direcciones posibles que tiene un personaje.
     */
    public enum Direccion {
        ARRIBA, ABAJO, DERECHA, IZQUIERDA, NINGUNA;
    }
    private int columna_;//columna en la que se encuentra el personaje
    private int fila_;//fila en la que se encuentra el personaje
    private Direccion direccion_;//dirección que tiene el personaje
    private ObservadorPersonaje observador_;//objeto observador del personaje que notifica sus acciones

    /**
     * Método de consulta de la columna en la que se encuentra el personaje.
     * 
     * @return el entero que representa la columna en la que se encuentra el personaje.
     */
    public int getColumna() {
        return columna_;
    }

    /**
     * Método de consulta de la fila en la que se encuentra el personaje.
     * 
     * @return el entero que representa la fila en la que se encuentra el personaje.
     */
    public int getFila() {
        return fila_;
    }

    /**
     * Método de consulta de la dirección en la que se mueve el personaje.
     * 
     * @return la dirección que tiene el personaje.
     */
    public Direccion getDireccion() {
        return direccion_;
    }

    /**
     * Método de consulta del observador del personaje.
     * 
     * @return el objeto observador del personaje.
     */
    public ObservadorPersonaje getObservador() {
        return observador_;
    }

    /**
     * Método que establece las coordenadas en las que se encuentra el personaje.
     * 
     * @param columna   entero que representa la columna en la que se encuentra el personaje.
     * @param fila      entero que representa la fila en la que se encuentra el personaje.
     */
    public void setColumnaFila(int columna, int fila) {
        this.columna_ = columna;
        this.fila_ = fila;
    }

    /**
     * Método de establecimiento de la dirección que llevará el personaje.
     * 
     * @param d     Dirección que se quiere actualizar la dirección del personaje.
     */
    public void setDireccion(Direccion d) {
        this.direccion_ = d;
    }
    
    /**
     * Establece una nueva dirección aleatoria de entre las posibles evitando
     * la dirección opuesta a la que traía para que no se de la vuelta.
     * 
     * @param d Dirección que traía para evitar que se de la vuelta.
     */
    public void setDireccionAleatoria(Direccion d){
        
        while(direccion_ == d){
            int aleatorio = new Random().nextInt();
            direccion_ = Direccion.values()[aleatorio];
            if((direccion_ == direccion_.DERECHA && d == direccion_.IZQUIERDA)||
                    (direccion_ == direccion_.ARRIBA && d == direccion_.ABAJO)){
                aleatorio = new Random().nextInt();
                direccion_ = Direccion.values()[aleatorio];
            }
            else if ((direccion_ == direccion_.IZQUIERDA && d == direccion_.DERECHA)||
                    (direccion_ == direccion_.ABAJO && d == direccion_.ARRIBA)){
                aleatorio = new Random().nextInt();
                direccion_ = Direccion.values()[aleatorio];
            }
        }
    }

    /**
     * Registra el observador del personaje.
     * 
     * @param o Observador del personaje que se quiere registrar.
     */
    public void registrarObservador(ObservadorPersonaje o) {
        observador_ = o;
    }

    /**
     * Notifica un cambio en el personaje al observador del personaje.
     */
    public void notificarCambio() {
        observador_.actualizarObservadorPersonaje();
    }

    /**
     * Método abstracto que inicializa cada personaje del modelo dado.
     * 
     * @param modelo Modelo del cual se quieren inicializar los personajes.
     */
    public abstract void inicializar(Modelo modelo);

    /**
     * Método abstracto que mueve cada personaje del modelo dado.
     * 
     * @param modelo Modelo del cual se quieren mover los personajes.
     */
    public abstract void mover(Modelo modelo);
}
