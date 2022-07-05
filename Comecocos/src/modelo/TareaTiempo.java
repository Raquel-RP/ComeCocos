package modelo;

/**
 * Clase con la que se pone en marcha el reloj de tiempo de juego.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class TareaTiempo implements Runnable {

    private Modelo modelo_; // Objeto modelo del juego que se ha iniciado.
    private boolean activo_; // True mientras sigue iterando
    private int delay_; // Valor que duerme la hebra entre cada iteración
    private boolean waitFlag_; // Para suspender la hebra momentaneamente
    private long time_start; // Tiempo de comienzo
    private long time_wait; // Tiempo de espera o pausa
    private long time_wait_init; // Instante en el que comienza la pausa

    /**
     * Constructor de TareaTiempo que inicializará las flags que controlan la
     * hebra y los instantes de tiempo que serán usados más tarde.
     *
     * @param modelo Objeto modelo del juego iniciado.
     */
    public TareaTiempo(Modelo modelo) {
        modelo_ = modelo;
        activo_ = true;
        waitFlag_ = false;
        time_start = System.currentTimeMillis();
        time_wait = 0;
        time_wait_init = 0;
        delay_ = 1000;
    }

    /**
     * Método sobreescrito run() de la interfaz Runnable que controla la hebra
     * TareaTiempo, esta se encarga de controlar los cambios de tiempo y actuar
     * como si de un cronómetro se tratase.
     */
    public void run() {
        long current_time;
        int time;

        try {
            while (activo_) {
                synchronized (this) {
                    while (waitFlag_) {
                        wait();
                    }
                }

                current_time = System.currentTimeMillis();

                // Ajuste del tiempo en caso de haber habido alguna pausa
                if (time_wait != 0) {
                    time = (int) (current_time - time_wait - time_start);
                } else {
                    time = (int) (current_time - time_start);
                }

                if (time != modelo_.getComecocos().getTiempo()) {
                    modelo_.getComecocos().setTiempo(time);
                }

                Thread.sleep(delay_);
            }
        } catch (InterruptedException e) {
        }
    }

    /**
     * Provoca una pausa en el reloj mediante la suspensión temporal a través
     * del dato miembro controlador del bucle waitFlag_. Además marca el
     * instante de tiempo en el que se empieza la pausa.
     */
    public synchronized void pausa() {
        waitFlag_ = true;
        time_wait_init = System.currentTimeMillis();
        notify();
    }

    /**
     * Método sincronizado que despierta a la hebra. Calcula el tiempo de espera
     * que ha habido entre medias para ajustar el cronómetro.
     */
    public synchronized void resume() {
        time_wait = time_wait + (System.currentTimeMillis() - time_wait_init);
        waitFlag_ = false;
        notify();
    }

    /**
     * Provoca la terminación de la hebra mediante el control de la variable
     * activo_.
     */
    public void terminar() {
        activo_ = false;
    }
}
