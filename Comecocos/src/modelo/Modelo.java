package modelo;

import java.util.ArrayList;

/**
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
        
    public Modelo(){
        laberinto_ = new Laberinto();
        comecocos_ = new Comecocos(this);
        fantasmas_ = new ArrayList();
        
        fantasmas_.add(new Fantasma(14, 14, Fantasma.NombreFantasma.INKY));
        fantasmas_.add(new Fantasma(13, 14, Fantasma.NombreFantasma.PINKY));
        fantasmas_.add(new Fantasma(13, 11, Fantasma.NombreFantasma.BLINKY));
        fantasmas_.add(new Fantasma(12, 14, Fantasma.NombreFantasma.CLYDE));
        
        puntos_ = 0;
    }
    
    public Laberinto getLaberinto(){
        return laberinto_;
    }
    
    public Comecocos getComecocos(){
        return comecocos_;
    }
    
    public Fantasma getFantasma(int index){
        return fantasmas_.get(index);
    }

    public int getPuntos() {
        return puntos_;
    }

    public void setPuntos(int puntos) {
        this.puntos_ = puntos_;
    }
    
    public void inicializarJuego() {
        laberinto_.inicializar();
        comecocos_.inicializar(this);
        this.setPuntos(0);//para que los puntos empiecen en 0 siempre que se inicialice
        
        for (int i = 0; i < fantasmas_.size(); i++){
            this.getFantasma(i).inicializar(this);
        }
        
        this.crearTareaLanzarHebraAnimarPersonajes();
    }
    
    /*
    public int colisionComecocosFantasma(){
    //mejora de los fantasmas 
    }*/
    public void crearTareaLanzarHebraAnimarPersonajes() {
        if (animadorPersonajes_ != null) {
            animadorPersonajes_.terminar(); //TODO
        }
        animadorPersonajes_ = new TareaAnimarPersonajes(this);
        Thread t = new Thread(animadorPersonajes_); //Hebra creada
        t.start(); //empieza a ejecutarse run
    }

    public void start() {
        this.inicializarJuego();//inicializa el juego con los valores iniciales
    }

    public void pausa() {
        this.animadorPersonajes_.pausa();
    }

    public void resume() {
        this.animadorPersonajes_.resume();
    }
    
    public void salir(){
        System.exit(0);
    }
}
