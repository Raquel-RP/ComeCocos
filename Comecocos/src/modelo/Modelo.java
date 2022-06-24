package modelo;

import java.util.ArrayList;

/**
 * Clase que reúne todos los elementos que forman el juego del comecocos
 * y albergando el estado de los datos de la aplicación.
 * 
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Modelo {

    private Laberinto laberinto_;
    private Comecocos comecocos_;
    private ArrayList<Fantasma> fantasmas_;
    private TareaAnimarPersonajes animadorPersonajes_;
    private int puntos_;
    
    /**
     * Método constructor sin parámetros que crea los objetos Laberinto,
     * Comecocos y fantasmas. Inicializa los componentes además del número de
     * puntos.
     */
    public Modelo(){
        laberinto_ = new Laberinto();
        comecocos_ = new Comecocos(this);
        fantasmas_ = new ArrayList();
        
        //fantasmas_.add(new Fantasma(14, 14, Fantasma.NombreFantasma.INKY));
        fantasmas_.add(new Fantasma(17, 17, Fantasma.NombreFantasma.INKY));
        fantasmas_.add(new Fantasma(13, 14, Fantasma.NombreFantasma.PINKY));
        fantasmas_.add(new Fantasma(13, 11, Fantasma.NombreFantasma.BLINKY));
        //fantasmas_.add(new Fantasma(12, 14, Fantasma.NombreFantasma.CLYDE));
        fantasmas_.add(new Fantasma(14, 11, Fantasma.NombreFantasma.CLYDE));
        
        fantasmas_.get(0).setDireccion(Personaje.Direccion.IZQUIERDA);
        fantasmas_.get(3).setDireccion(Personaje.Direccion.DERECHA);        
        
        puntos_ = 0;
    }
    
    /**
     * Método de consulta que devuelve el laberinto del juego actual.
     * 
     * @return  laberinto_ el objeto laberinto actual.
     */
    public Laberinto getLaberinto(){
        return laberinto_;
    }
    
    /**
     * Método de consulta que devuelve el comecocos del juego actual.
     * 
     * @return  comecocos_ el objeto comecocos actual.
     */
    public Comecocos getComecocos(){
        return comecocos_;
    }
    
    /**
     * A partir de un entero introducido como parámetro, devuelve el fantasma
     * que se encuentra en dicha posición del ArrayList.
     * 
     * @param index es el número entero que se usa como posición para obtener
     *              el fantasma
     * @return      el fantasma que se encontraba en la posición index.
     */
    public Fantasma getFantasma(int index){
        return fantasmas_.get(index);
    }

    /**
     * Método de consulta que devuelve el entero numero de puntos
     * del juego actual.
     * 
     * @return  puntos_ el entero numero de puntos del juego actual.
     */
    public int getPuntos() {
        return puntos_;
    }

    /**
     * Establece el número entero de puntos que tiene como parámetro como
     * número de puntos totales almacenados en el juego.
     * 
     * @param puntos    número entero de puntos que serán los almacenados totales.
     */
    public void setPuntos(int puntos) {
        this.puntos_ = puntos;
    }
    
    /**
     * Inicializa todas las componentes del juego a su estado inicial, tanto
     * el laberinto y el comecocos como el número de puntos y cada uno de los
     * fantasmas.
     */
    public void inicializarJuego() {
        laberinto_.inicializar();
        comecocos_.inicializar(this);
        this.setPuntos(0);
        
        for (int i = 0; i < fantasmas_.size(); i++){
            this.getFantasma(i).inicializar(this);
        }
        
        this.crearTareaLanzarHebraAnimarPersonajes();
    }

    /**
     * Crea la tarea que lanza una hebra para animar los personajes del juego.
     */
    public void crearTareaLanzarHebraAnimarPersonajes() {
        if (animadorPersonajes_ != null) {
            animadorPersonajes_.terminar(); 
        }
        animadorPersonajes_ = new TareaAnimarPersonajes(this);
        Thread t = new Thread(animadorPersonajes_); //Hebra creada
        t.start(); //empieza a ejecutarse run
    }

    /**
     * Inicializa el juego con los valores iniciales y establece el número
     * de vidas que tendrá el comecocos cada vez que empiece el juego.
     */
    public void start() {
        this.inicializarJuego();//inicializa el juego con los valores iniciales
        comecocos_.setVidas(3);
    }

    /**
     * Paralizará la animación de los personajes a través del objeto de
     * animación de los personajes.
     */
    public void pausa() {
        this.animadorPersonajes_.pausa();
    }

    /**
     * Reanudará la animación o movimiento de los personajes del juego
     * mediante el objeto de animación de los personajes.
     */
    public void resume() {
        this.animadorPersonajes_.resume();
    }
    
    /**
     * Terminará el juego finalizando el movimiento de los personajes del
     * juego con el objeto de animación de los personajes y forzará la salida
     * del sistema.
     */
    public void salir(){
        this.animadorPersonajes_.terminar();
        System.exit(0);
    }
}
