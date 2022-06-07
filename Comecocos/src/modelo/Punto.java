
package modelo;

import java.util.Scanner;

/**
 *
 * @author Raqeul Romero
 */
public class Punto {
   
    private int coorX_; 
    private int coorY_; 
    
    public Punto(){
    }
    
    public Punto(int x, int y){
        
        this.coorX_ = x;
        this.coorY_ = y;
    }
    
    public int getX(){
        return coorX_;
    }
    
    public int getY(){
        return coorY_;
    }
    
    
    public void setCoordenadas(int x, int y){
        coorX_ = x;
        coorY_ = y;
    }
    
    public void leer(Scanner conin){
        coorX_ = conin.nextInt();
        coorY_ = conin.nextInt();
        
    }
    
    @Override
    public String toString(){ 
        return coorX_ + " " + coorY_ ;         
    } 
    
    public double getDistanciaEuclidea(Punto punto){
        double distancia;
        distancia = Math.sqrt(
                    (this.coorX_- punto.coorX_)*(this.coorX_- punto.coorX_) +
                    (this.coorY_- punto.coorY_)*(this.coorY_- punto.coorY_));
        return distancia;
    } 
    
    public Punto calcularPuntoMedio(Punto punto){
        Punto pMedio;
        pMedio = new Punto();
        pMedio.setCoordenadas((coorX_ + punto.coorX_)/2,(coorY_ + punto.coorY_)/2);
    
        return pMedio;
    }
    
}
