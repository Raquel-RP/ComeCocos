package modelo;

/**
 * Hereda de personaje y representa al fantasma definiendo sus movimientos.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Fantasma extends Personaje {

    /**
     * Enumera los nombres de los fantasmas.
     */
    public enum NombreFantasma {
        INKY, PINKY, BLINKY, CLYDE
    }

    private NombreFantasma nombre_;//nombre del fantasma

    /**
     * Constructor del fantasma que lo inicializa en una posición dada y se le
     * establece un nombre dado.
     * 
     * @param col    Columna de la posición en la que se quiere inicializar el fantasma
     * @param fila   Fila de la posición en la que se quiere inicializar el fantasma
     * @param nombre Nombre del fantasma que se quiere incializar en cierta posicion
     */
    public Fantasma(int col, int fila, NombreFantasma nombre) {
        this.setColumnaFila(col, fila);
        this.nombre_ = nombre;
        this.setDireccion(Personaje.Direccion.NINGUNA);
    }

    /**
     * Método de consulta del nombre de un fantasma.
     * 
     * @return El nomnbre enumerado del fantasma.
     */
    public NombreFantasma getNombre() {
        return nombre_;
    }

    @Override
    public void inicializar(Modelo modelo) {
        //this.setColumnaFila(14,14);
        //this.setDireccion(Direccion.NINGUNA);
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
                    Personaje.Direccion d = this.getDireccion();
                    this.setDireccionAleatoriaPosible(d, modelo);
                    System.out.println("Direccion tomada: " + this.getDireccion());
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
        notificarCambio();
    }

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
    
        /**
     * Proporciona una nueva dirección aleatoria dentro de las posibles 
     * opciones que haya.
     * 
     * @param d Dirección que traía para no seguir en esa dirección
     */
    public void setDireccionAleatoriaPosible(Direccion d, Modelo modelo) {
        ArrayList<Direccion> dirPosibles = new ArrayList();

        dirPosibles = posiblesDirecciones(this.getColumna(), this.getFila(), modelo);
        for(int i=0; i<dirPosibles.size(); i++){
            System.out.println("Dir" + i + " " + dirPosibles.get(i));
        }
        if (dirPosibles.contains(d)) {
            dirPosibles.remove(d);
        }

        int aleatorio = new Random().nextInt(dirPosibles.size());
        this.setDireccion(dirPosibles.get(aleatorio));
        System.out.println(this.getDireccion());
    }

    /**
     * Añade las direcciones posibles que puede tomar en un cruce
     *
     * @param columna
     * @param fila
     * @param modelo
     * @return
     */
    public ArrayList<Direccion> posiblesDirecciones(int columna, int fila, Modelo modelo) {
        ArrayList<Direccion> direcciones = new ArrayList();

        boolean izq = modelo.getLaberinto().estaLibre(columna - 1, fila);
        boolean drcha = modelo.getLaberinto().estaLibre(columna + 1, fila);
        boolean arriba = modelo.getLaberinto().estaLibre(columna, fila - 1);
        boolean abajo = modelo.getLaberinto().estaLibre(columna, fila + 1);

        boolean[] vLibre = {izq, drcha, arriba, abajo};
        Direccion[] dirs = {Direccion.IZQUIERDA, Direccion.DERECHA, Direccion.ARRIBA, Direccion.ABAJO};
               
        for (int i = 0; i < 4; i++) {
            if (vLibre[i]) {
                direcciones.add(dirs[i]);
            }
        }
        return direcciones;
    }
}
