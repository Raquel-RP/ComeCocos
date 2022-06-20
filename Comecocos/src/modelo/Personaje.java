package modelo;

import java.util.Random;
import vista.ObservadorPersonaje;

/**
 * Clase abstracta
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public abstract class Personaje {

    public enum Direccion {
        ARRIBA, ABAJO, DERECHA, IZQUIERDA, NINGUNA;
    }
    private int columna_;
    private int fila_;
    private Direccion direccion_;
    private ObservadorPersonaje observador_;

    public int getColumna() {
        return columna_;
    }

    public int getFila() {
        return fila_;
    }

    public Direccion getDireccion() {
        return direccion_;
    }

    public ObservadorPersonaje getObservador() {
        return observador_;
    }

    public void setColumnaFila(int columna, int fila) {
        this.columna_ = columna;
        this.fila_ = fila;
    }

    public void setDireccion(Direccion d) {
        this.direccion_ = d;
    }
    
    /**
     * 
     * @param d Dirección que traía para no seguir en esa dirección
     */
    public void setDireccionAleatoria(Direccion d){
        while(direccion_ == d){
            int aleatorio = new Random().nextInt();
            direccion_ = Direccion.values()[aleatorio];
        }
    }

    public void registrarObservador(ObservadorPersonaje o) {
        observador_ = o;
    }

    public void notificarCambio() {
        observador_.actualizarObservadorPersonaje();
    }

    public abstract void inicializar(Modelo modelo);

    public abstract void mover(Modelo modelo);
}
