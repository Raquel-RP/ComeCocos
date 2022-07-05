package modelo;

/**
 * Subclase de personaje que representa al comecocos definiendo su movimiento y
 * estado.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Comecocos extends Personaje {

    public boolean vivo_; //booleano que indica si tiene vidas restantes.
    public int vidas_; //numero de vidas restantes del comecocos.
    private int tiempo_; //tiempo que lleva de juego

    /**
     * Constructor del comecocos que inicializa el comecocos del modelo en el
     * que está representado.
     *
     * @param modelo Modelo en el que está representado el comecocos que se
     * quiere inicializar.
     */
    public Comecocos(Modelo modelo) {

        // para que sepa donde esta el centro hay que pasarle el modelo para
        // que al llamar a inicializar podamos saber donde esta el centro del laberinto
        this.vidas_ = 3;
        this.inicializar(modelo);
        this.tiempo_ = 0;
    }

    /**
     * Devuelve un booleano para indicar si el comecocos está vivo.
     *
     * @return vivo_ es el booleano que indica si al comecocos le quedan vidas.
     */
    public boolean isVivo() {
        return vivo_;
    }

    /**
     * Método de consulta del número de vidas restantes que le quedan al
     * comecocos.
     *
     * @return vidas_: el numero de vidas restantes del comecocos.
     */
    public int getVidas() {
        return vidas_;
    }

    /**
     * Establece el número entero que se le introduce como el número de vidas
     * actual que le quedan al comecocos.
     *
     * @param vidas: el numero de vidas que se quiere que le queden al
     * comecocos.
     */
    public void setVidas(int vidas) {
        vidas_ = vidas;
    }
    
    /**
     * Devuelve el tiempo de juego que lleva el comecocos
     * 
     * @return Tiempo de juego que lleva el comecocos
     */
    public int getTiempo() {
        return tiempo_;
    }

    /**
     * Asigna un valor de tiempo al tiempo de juego que lleva
     * de juego.
     * 
     * @param tiempo Tiempo de juego consumido
     */
    public void setTiempo(int tiempo) {
        this.tiempo_ = tiempo;
    }

    /**
     * Método principal responsable del movimiento adecuado del comecocos
     * comprobando la disponibilidad de la celda a la que quiere acceder en su
     * siguiente movimiento.
     *
     * @param modelo modelo en el que se encuentran los elementos del juego que
     * pueden interferir en el movimiento del comecocos.
     */
    @Override
    public void mover(Modelo modelo) {

        Punto sigPosicion = siguientePosicion();
        int puntos = modelo.getPuntos();

        //si la pos actual es en la col 0 la sig posicion tiene que ser x+27 (laberinto-1)
        if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {

            this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
            Laberinto.TipoCelda celda = modelo.getLaberinto().getCelda(sigPosicion.getX(), sigPosicion.getY());

            if (celda == Laberinto.TipoCelda.COCOPEQUENO) {
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE); // Se come el coco
                puntos += 10;
            } else if (celda == Laberinto.TipoCelda.COCOGRANDE) {
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE);
                puntos += 50;
            }
        }

        //Si llega al máximo de puntos tras comer todos los cocos.(no cuentan fantasmas) o llega al fin del tiempo
        if (puntos == 2620 || this.getTiempo() >= 90000) {
            modelo.setPuntos(puntos);
            notificarCambio();
            modelo.pausa();
        } else {
            modelo.setPuntos(puntos);
            notificarCambio();
        }
    }

    /**
     * Inicializa el comecocos en una posición específica del laberinto.
     *
     * @param modelo modelo en el que se encuentran los datos del laberinto
     * registrados y en el que se establecerá la posición del comecocos.
     */
    @Override
    public void inicializar(Modelo modelo) {
        this.setColumnaFila(13, 17);
        this.setDireccion(Personaje.Direccion.NINGUNA);
        this.vivo_ = true;
    }

    /**
     * Calcula la posición del siguiente punto según la posición y el tamaño del
     * laberinto, es un método auxiliar de mover() que cambia las coordenadas
     * del comecocos según la dirección que vaya a tomar.
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
     * Comprueba si hay colisión entre un fantasma y el comecocos comprobando
     * que los dos acceden a la misma casilla del panel.
     *
     * @param fantasma Fantasma que se encuentra en una posición dada
     * @param modelo Modelo que contiene los datos para comprobar la colision
     */
    public void colision(Fantasma fantasma, Modelo modelo) {

        if (fantasma.getFila() == this.getFila() && fantasma.getColumna() == this.getColumna()) {
            vidas_--;

            if (vidas_ == 0) {
                vivo_ = false;
                notificarCambio();
                modelo.pausa();

            } else {
                modelo.setPuntos(0);
                notificarCambio();
                modelo.getComecocos().inicializar(modelo);

            }
        }
    }
}
