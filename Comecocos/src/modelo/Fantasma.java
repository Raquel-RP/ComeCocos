package modelo;

/**
 * Hereda de personaje
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Fantasma extends Personaje {

    public enum NombreFantasma {
        INKY, PINKY, BLINKY, CLYDE
    }

    private NombreFantasma nombre_;

    public Fantasma(int col, int fila, NombreFantasma nombre) {
        this.setColumnaFila(col, fila);
        this.nombre_ = nombre;
        this.setDireccion(Personaje.Direccion.NINGUNA);
    }

    public NombreFantasma getNombre() {
        return nombre_;
    }

    @Override
    public void inicializar(Modelo modelo) {
        //this.setColumnaFila(14,14);
        this.setDireccion(Direccion.NINGUNA);
        // modelo.inicializarJuego();
    }

    @Override
    public void mover(Modelo modelo) {
        Punto sigPosicion;

        for (int i = 0; i < 4; i++) {
            if (modelo.getLaberinto().esCruce(this.getColumna(), this.getFila())) {

                if (this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY) {
                    sigPosicion = this.getCasillaPerseguir(modelo);
                    //sigPosicion = siguientePosicion();

                    if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                        this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                    }
                } else {//Clyde, Inky

                    //sigPosicion = siguientePosicionAleatoria();
                    sigPosicion = siguientePosicion();
                    if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                        this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                    }
                }
            } else { //si no es cruce
                sigPosicion = siguientePosicion();
                if (modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())) {
                    this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY());
                }
            }
        }
        //notificarCambio();
    }
/*  //No funciona
    private Punto siguientePosicionAleatoria() {
        Punto p = new Punto(this.getColumna(), this.getFila());
        Personaje.Direccion d = this.getDireccion();
        this.setDireccionAleatoria(d);
        switch (d) {

            case IZQUIERDA:
                p.setCoordenadas(p.getX() - 1, p.getY());
                break;
            case DERECHA:
                p.setCoordenadas(p.getX() + 1, p.getY());
                break;
            case ARRIBA:
                p.setCoordenadas(p.getX(), p.getY() - 1);
                break;
            case ABAJO:
                p.setCoordenadas(p.getX(), p.getY() + 1);
                break;
        }
        return p;
    }
    */
    private Punto siguientePosicion() { //preguntar si se puede poner en la superclase Personaje para no repetir 

        Punto p = new Punto(this.getColumna(), this.getFila());
        Personaje.Direccion d = this.getDireccion();

        switch (d) {

            case IZQUIERDA:
                p.setCoordenadas(p.getX() - 1, p.getY());
                break;
            case DERECHA:
                p.setCoordenadas(p.getX() + 1, p.getY());
                break;
            case ARRIBA:
                p.setCoordenadas(p.getX(), p.getY() - 1);
                break;
            case ABAJO:
                p.setCoordenadas(p.getX(), p.getY() + 1);
                break;
        }
        return p;
    }

    /**
     * Coge la casilla con menor distancia euclidea posible al comecocos 
     * No funciona bien
     *
     * @param modelo
     * @return
     */
    private Punto getCasillaPerseguir(Modelo modelo) {

        Punto posActual = new Punto(this.getColumna(), this.getFila());
        Punto posComecocos = new Punto(modelo.getComecocos().getColumna(), modelo.getComecocos().getFila());
        double mejorDistancia = 0;
        Punto mejorPunto = null;

        int columna = this.getColumna();
        int fila = this.getFila();

        Laberinto laberinto = modelo.getLaberinto();

        if (laberinto.estaLibre(this.getColumna(), this.getFila())) {
            if (laberinto.estaLibre(columna - 1, fila)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna - 1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna - 1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                    }
                }
            } else if (laberinto.estaLibre(columna + 1, fila)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna + 1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna + 1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            } else if (laberinto.estaLibre(columna, fila + 1)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna, fila + 1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna, fila + 1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            } else if (laberinto.estaLibre(columna, fila - 1)) {
                if (mejorPunto == null) {
                    mejorPunto = new Punto(columna, fila - 1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);
                } else {
                    if (posActual.getDistanciaEuclidea(posComecocos) < mejorDistancia) {

                        mejorPunto = new Punto(columna, fila - 1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posComecocos);

                    }
                }
            }
        }
        return mejorPunto;
    }
}
