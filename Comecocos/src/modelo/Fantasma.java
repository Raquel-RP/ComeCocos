
package modelo;

/**
 * Hereda de personaje
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Fantasma extends Personaje{
    
    public enum NombreFantasma {
        INKY, PINKY, BLINKY, CLYDE
    }
    
    private NombreFantasma nombre_;
    
    public Fantasma(){
    }
    
    @Override
    public void inicializar(Modelo modelo){
        this.setColumnaFila(14,14);
        this.setDireccion(Direccion.NINGUNA);
        modelo.inicializarJuego();
    }
    
    @Override
    public void mover (Modelo modelo){ 
        Punto sigPosicion;
        
        if(modelo.getLaberinto().esInterseccion(this.getColumna(), this.getFila())){
            
            if(this.nombre_ == NombreFantasma.BLINKY || this.nombre_ == NombreFantasma.PINKY){
               
                sigPosicion = this.getCasillaMasCercana(modelo); 
            }
        }
        
        else{
            sigPosicion = siguientePosicion();
            if(modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())){
            
                this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY()); //paso 3 del guion

                /*
                iterar por modelo.fantasma y comprobar si están en la posición
                si está en la posicion se reinicia perdiendo vida 
                FANTASMA NO COMESTIBLE
                */
            }
        }
        
        notificarCambio();
    }
    
    private Punto siguientePosicion(){ //preguntar si se puede poner en la superclase Personaje para no repetir 
         
        Punto p = new Punto(this.getColumna(), this.getFila());
        Direccion d = this.getDireccion();
        
        switch(d){
            
            case IZQUIERDA:
                p.setCoordenadas(p.getX()-1, p.getY());
                break;
            case DERECHA:
                p.setCoordenadas(p.getX()+1, p.getY());
                break;
            case ARRIBA:
                p.setCoordenadas(p.getX(), p.getY()-1);
                break;
            case ABAJO:
                p.setCoordenadas(p.getX(), p.getY()+1);
                break;
        }
        
        return p;
    }
        
    private Punto getCasillaMasCercana(Modelo modelo){

        Punto posicionActual = new Punto(this.getColumna(), this.getFila());
        Punto posicionComecocos = new Punto(modelo.getComecocos().getColumna(), modelo.getComecocos().getFila());
        Punto mejorPunto = null;
        double mejorDistancia = 0;

        int columna = this.getColumna();
        int fila = this.getFila();

        Laberinto laberinto = modelo.getLaberinto();
            
        if(laberinto.estaLibre(this.getColumna(), this.getFila())){
            if (laberinto.estaLibre(columna-1, fila)){
                if(mejorPunto == null){
                    mejorPunto = new Punto(columna-1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                }
                else{
                    if(posicionActual.getDistanciaEuclidea(posicionComecocos) < mejorDistancia){

                        mejorPunto = new Punto(columna-1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);

                    }
                }
            }
            else if (laberinto.estaLibre(columna, fila-1)){
                if(mejorPunto == null){
                    mejorPunto = new Punto(columna, fila-1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                }
                else{
                    if(posicionActual.getDistanciaEuclidea(posicionComecocos) < mejorDistancia){

                        mejorPunto = new Punto(columna, fila-1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                            
                    }
                }
            }
            else if (laberinto.estaLibre(columna+1, fila)){
                if(mejorPunto == null){
                    mejorPunto = new Punto(columna+1, fila);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                }
                else{
                    if(posicionActual.getDistanciaEuclidea(posicionComecocos) < mejorDistancia){
                            
                        mejorPunto = new Punto(columna+1, fila);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                            
                    }
                }
            }
            else if (laberinto.estaLibre(columna, fila+1)){
                if(mejorPunto == null){
                    mejorPunto = new Punto(columna, fila+1);
                    mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                }
                else{
                    if(posicionActual.getDistanciaEuclidea(posicionComecocos) < mejorDistancia){
                            
                        mejorPunto = new Punto(columna, fila+1);
                        mejorDistancia = mejorPunto.getDistanciaEuclidea(posicionComecocos);
                           
                    }
                }
            }
        }
            
        return mejorPunto;
    }
    
    
}
