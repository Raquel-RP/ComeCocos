package modelo;

/**
 * Clase con la que se pone en marcha el funcionamiento del comecocos mediante
 * la interfaz Runnable.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class TareaAnimarPersonajes implements Runnable {

    private Modelo modelo_;//objeto modelo del juego que se ha iniciado.
    private int delay_;//retraso del tiempo para controlar la rapidez de movimiento de la hebras
    private boolean continuar_;//true mientras sigue iterando
    private boolean suspendFlag_;//para detener la hebra momentaneamente

    /**
     * Constructor de la clase TareaAnimarPersonajes que toma el modelo de
     * entrada y le da valor de inicio a las variables de control de la hebra.
     *
     * @param modelo Objeto modelo del juego iniciado.
     */
    public TareaAnimarPersonajes(Modelo modelo) {
        modelo_ = modelo;
        delay_ = 200; //valor deseado
        continuar_ = true;
        suspendFlag_ = false; //todo true 
    }

    /**
     * Controla la hebra del movimiento de los personajes y ejecuta el método
     * run de runnable, controlando también los casos en los que pause o se pare
     * el juego.
     */
    public void run() {
        try {
            while (continuar_) {
                synchronized (this) {
                    while (suspendFlag_) {
                        wait();
                    }
                }
                modelo_.getComecocos().mover(modelo_);

                if (modelo_.getComecocos().getDireccion() != Personaje.Direccion.NINGUNA) {
                    for (int i = 0; i < 4; i++) {
                        modelo_.getFantasma(i).mover(modelo_);
                        modelo_.getComecocos().colision(modelo_.getFantasma(i), modelo_);  // Comprueba colision por si el fantasma se ha movido donde el comecocos
                    }
                }
                Thread.sleep(delay_); //Para la velocidad del juego
            } // end while
        } catch (InterruptedException e) { //excepción comprobada que pueden lanzarla sleep y wait
        }
    }

    /**
     * Provoca una pausa en el juego mediante la suspensión temporal con el dato
     * miembro suspendFlag_.
     */
    public void pausa() {
        suspendFlag_ = true;
    }

    /**
     * Método sincronizado que despierta a la hebra.
     */
    public synchronized void resume() {
        suspendFlag_ = false;
        notify();
    }

    /**
     * Provoca la terminación de la hebra mediante el control de la variable
     * continuar_.
     */
    public void terminar() {
        continuar_ = false;
    }
}
