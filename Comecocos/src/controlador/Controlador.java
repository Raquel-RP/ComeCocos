package controlador;

import java.awt.event.KeyEvent;
import modelo.Comecocos;
import modelo.Modelo;
import modelo.Personaje;
import vista.ComecocosFrame;

/**
 * Clase para inicializar, generar el juego en la interfaz gráfica y
 * traducir eventos y realizar las acciones pertinentes frente a estos.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class Controlador {

    private Modelo modelo_; //Referencia al objeto modelo
    private ComecocosFrame comecocosFrame_; //Referencia a la vista

    /**
     * Contructor de la clase Controlador encargado de establecer el modelo, 
     * crear un objeto vista, inicializar el juego del modelo para inicializar
     * el juego y mostrar la vista.
     * 
     * @param modelo Modelo a controlar
     */
    public Controlador(Modelo modelo) {

        modelo_ = modelo;
        comecocosFrame_ = new ComecocosFrame(modelo, this);
        modelo_.inicializarJuego();
        comecocosFrame_.setVisible(true);

    }

    /**
     * Capta un evento de teclado y lo traduce asignando la dirección
     * correspondiente a cada posible tecla pulsada.
     * 
     * @param evt Evento de teclado
     */
    public void teclaPulsadaEnLaberintoPanel(java.awt.event.KeyEvent evt) {
        Comecocos comecocos = modelo_.getComecocos();
        
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                comecocos.setDireccion(Personaje.Direccion.IZQUIERDA);
                break;
            case KeyEvent.VK_RIGHT:
                comecocos.setDireccion(Personaje.Direccion.DERECHA);
                break;
            case KeyEvent.VK_UP:
                comecocos.setDireccion(Personaje.Direccion.ARRIBA);
                break;
            case KeyEvent.VK_DOWN:
                comecocos.setDireccion(Personaje.Direccion.ABAJO);
                break;

        }
    }

}
