
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
    //si incluimos los puntos añadimos dato miembro con puntuación
    
    public Modelo(){
        laberinto_ = new Laberinto();
        comecocos_ = new Comecocos(this);
        fantasmas_ = new ArrayList();
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
    
    public void inicializarJuego() {
        laberinto_.inicializar();
        comecocos_.inicializar(this); 
        //fantasmas_.inicializar(this);
        this.crearTareaLanzarHebraAnimarPersonajes();
    }
    
    /*
    public int colisionComecocosFantasma(){
    //mejora de los fantasmas 
    }*/
     
    public void crearTareaLanzarHebraAnimarPersonajes() {
        if (animadorPersonajes_ != null) {
           //TODO animadorPersonajes_.terminar();
        }
        animadorPersonajes_ = new TareaAnimarPersonajes(this);
        Thread t = new Thread(animadorPersonajes_); //Hebra creada
        t.start(); //empieza a ejecutarse run
    }
    
}
