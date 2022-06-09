
package modelo;

/**
 * Subclase de personaje
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Comecocos extends Personaje{
        
    public Comecocos(Modelo modelo) { 
        
        // para que sepa donde esta el centro hay que pasarle el modelo para
        // que al llamar a inicializar podamos saber donde esta el centro del laberinto
        this.inicializar(modelo);
    }
    
    /**
     * Comecocos preparado para moverse cuando llama a este metodo
     * mira en que posicion esta el comecocos y calcular cual seria
     * la siguiente celda en la que se movería (celda de arriba, de abajo 
     * o de al lado)
     * dependiendo de la posicion actual y de la direccion de movimiento
     * hacer metodo privado siguiente posición
     * 
     * @param modelo 
     */
    @Override
    public void mover(Modelo modelo) {

        Punto sigPosicion = siguientePosicion(); 
        
        if(modelo.getLaberinto().estaLibre(sigPosicion.getX(), sigPosicion.getY())){
            
            this.setColumnaFila(sigPosicion.getX(), sigPosicion.getY()); //paso 3 del guion
            Laberinto.TipoCelda c = modelo.getLaberinto().getCelda(sigPosicion.getX(), sigPosicion.getY());
            
            if(c == Laberinto.TipoCelda.COCOPEQUENO){
                //System.out.println("Coco pequeño es nueva celda"); 
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE);
                
                //puntuacion += 10;
            }
            else if(c == Laberinto.TipoCelda.COCOGRANDE){
                //System.out.println("Coco grande es nueva celda"); 
                modelo.getLaberinto().setCelda(sigPosicion.getX(), sigPosicion.getY(), Laberinto.TipoCelda.LIBRE);
                //puntuacion += 50;
            }
            /*
            iterar por modelo.fantasma y comprobar si están en la posición
            si está en la posicion se reinicia perdiendo vida 
            FANTASMA NO COMESTIBLE
            */
        }
        notificarCambio();
    }
    
    @Override
    public void inicializar(Modelo modelo) {
        this.setColumnaFila(13, 17);
        this.setDireccion(Direccion.NINGUNA);  
    }
    
    private Punto siguientePosicion(){
         
        Punto punto;
        punto = new Punto();
        
        int x, y;
        x = this.getColumna();
        y = this.getFila();
        
        switch(this.getDireccion()){
            
            case IZQUIERDA:
                x--;
                break;
            case DERECHA:
                x++;
                break;
            case ARRIBA:
                y--;
                break;
            case ABAJO:
                y++;
                break;
        }  
        punto.setCoordenadas(x,y);
        return punto;
    }
}
