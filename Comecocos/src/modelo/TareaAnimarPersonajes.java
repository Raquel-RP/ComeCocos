package modelo;

/**
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class TareaAnimarPersonajes implements Runnable {

    private Modelo modelo_;
    private int delay_;     //para controlar la rapidez de movimiento de la hebras
    private boolean continuar_; //true mientras sigue iterando
    private boolean suspendFlag_; //para detener la hebra momentaneamente

    public TareaAnimarPersonajes(Modelo modelo) {
        modelo_ = modelo;
        delay_ = 200; //valor deseado
        continuar_ = true;
        suspendFlag_ = false; //todo true 
    }

    public void run() {
        try {
            while (continuar_) {
                synchronized (this) {
                    while (suspendFlag_) {
                        wait();
                    }
                }
                modelo_.getComecocos().mover(modelo_);
                // Mover también los fantasmas
                for (int i = 0; i < 4; i++) {
                    //modelo_.getComecocos().colision(modelo_.getFantasma(i), modelo_);  // Comprueba colision antes de moverse por si el comecocos se ha movido donde esta el fantasma
                    //TODO implementar el mover fantasmas
                    //modelo_.getFantasma(i).mover(modelo_);
                    modelo_.getComecocos().colision(modelo_.getFantasma(i), modelo_);  // Comprueba colision por si el fantasma se ha movido donde el comecocos
                }
                Thread.sleep(delay_); //Para la velocidad del juego
            } // end while
        } catch (InterruptedException e) { //excepción comprobada que pueden lanzarla sleep y wait
        }
    }

    public void pausa() {
        suspendFlag_ = true;
    }

    public synchronized void resume() {
        suspendFlag_ = false;
        notify();
    }
    
    public void terminar() {
        continuar_ = false;
    }
}
