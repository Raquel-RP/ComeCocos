package modelo;

/**
 * Subclase de personaje
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Comecocos extends Personaje {

    public Comecocos(Modelo modelo) {

        // para que sepa donde esta el centro hay que pasarle el modelo para
        // que al llamar a inicializar podamos saber donde esta el centro del laberinto
        this.inicializar(modelo);
    }

    /**
     * Comecocos preparado para moverse cuando llama a este metodo mira en que
     * posicion esta el comecocos y calcular cual seria la siguiente celda en la
     * que se movería (celda de arriba, de abajo o de al lado) dependiendo de la
     * posicion actual y de la direccion de movimiento hacer metodo privado
     * siguiente posición
     *
     * @param modelo
     */
    @Override
    public void mover(Modelo modelo) {

        Punto sigPosicion = siguientePosicion();
        int puntos = modelo.getPuntos_();

        //si la pos actual es en la col 0 la sig posicion tiene que ser x+27 (laberinto-1)
        if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {

            this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY()); //paso 3 del guion
            Laberinto.TipoCelda celda = modelo.getLaberinto().getCelda(sigPosicion.getX(), sigPosicion.getY());

            if (celda == Laberinto.TipoCelda.COCOPEQUENO) {
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE); // Se come el coco
                puntos += 10;
            } else if (celda == Laberinto.TipoCelda.COCOGRANDE) {
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE);
                puntos += 50;
            }
            /*
            iterar por modelo.fantasma y comprobar si están en la posición
            si está en la posicion se reinicia perdiendo vida 
            FANTASMA NO COMESTIBLE
             */
        }
        //Si llega al máximo de puntos
        if (puntos == 2620) {
            modelo.setPuntos_(puntos); 
            notificarCambio();
            modelo.pausa(); //Pausa el juego, si le doy a resume (cuando funcione) sigue, habria que cambiarlo
        }
        modelo.setPuntos_(puntos); 
        notificarCambio();
    }

    @Override
    public void inicializar(Modelo modelo) {
        this.setColumnaFila(13, 17);
        this.setDireccion(Direccion.NINGUNA);
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
}
