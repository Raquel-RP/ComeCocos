
package modelo;

/**
 * Clase punto que representa las coordenadas x e y de una posición.
 * 
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Punto {
   
    private int coorX_;//Coordenada x
    private int coorY_; //Coordenada y
    
    /**
     * Constructor por defecto sin parámetros de un punto
     */
    public Punto(){
    }
    
    /**
     * Constructor de un punto a partir de las coordenadas x e y
     * 
     * @param x: coordenada x que se quiere inicializar
     * @param y : coordenada y que se quiere inicializar
     */
    public Punto(int x, int y){
        
        this.coorX_ = x;
        this.coorY_ = y;
    }
    
    /**
     * Método de consulta del entero guardado en la coordenada x.
     * 
     * @return coorX_: la coordenada x que se quiere consultar.
     */
    public int getX(){
        return coorX_;
    }
    
    /**
     * Método de consulta del entero guardado en la coordenada y.
     * 
     * @return coorY_: la coordenada y que se quiere consultar.
     */
    public int getY(){
        return coorY_;
    }
    
    /**
     * Establece las coordenadas x e y dadas de un punto.
     * 
     * @param x: coordenada x que se quiere establecer
     * @param y : coordenada y que se quiere establecer 
     */
    public void setCoordenadas(int x, int y){
        coorX_ = x;
        coorY_ = y;
    }
    
    /**
     * Obtiene la distancia euclídea entre dos puntos.
     * 
     * @param punto Punto sobre el cual se quiere medir la distancia
     * @return distancia: distancia en linea recta entre dos puntos.
     */
    public double getDistanciaEuclidea(Punto punto){
        double distancia;
        distancia = Math.sqrt(
                    (this.coorX_- punto.coorX_)*(this.coorX_- punto.coorX_) +
                    (this.coorY_- punto.coorY_)*(this.coorY_- punto.coorY_));
        return distancia;
    }
}
