package controlador;

import java.awt.event.KeyEvent;
import modelo.Comecocos;
import modelo.Modelo;
import modelo.Personaje;
import vista.ComecocosFrame;

/**
 * Inicializa y genera el juego.
 *
 * @author Raquel Romero
 */
public class Controlador {

    private Modelo modelo_;
    private ComecocosFrame comecocosFrame_;

    public Controlador(Modelo modelo) {

        modelo_ = modelo;
        comecocosFrame_ = new ComecocosFrame(modelo, this);  // Crear objeto vista (ComecocosFrame)
        modelo_.inicializarJuego();// Usar el metodo inicializarJuego() del Modelo para inicilizar juego
        comecocosFrame_.setVisible(true);// Mostrar la vista (comecocosFrame_)

    }

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
