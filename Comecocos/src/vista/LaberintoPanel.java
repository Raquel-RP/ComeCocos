package vista;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.String.valueOf;
import modelo.Comecocos;
import modelo.Laberinto;

/**
 * Extensión del JPanel que muestra los objetos del laberinto y los personajes,
 * dibujándolos con su posición y tamaño.
 *
 * @author Raquel Romero
 * @author Raquel Pulido
 */
public class LaberintoPanel extends javax.swing.JPanel {

    ComecocosFrame comecocosFrame_; //Referencia al objeto vista.
    int anchoCelda = 17; //Ancho de las celdas del panel.

    /**
     * Crea una nueva forma de LaberintoPanel inicializando la vista.
     *
     * @param comecocosFrame Objeto vista.
     */
    public LaberintoPanel(ComecocosFrame comecocosFrame) {
        this();
        comecocosFrame_ = comecocosFrame;
    }

    /**
     * Constructor sin parámetros que inicializa los componentes.
     */
    public LaberintoPanel() {
        initComponents();
    }

    /**
     * Sobreescribe el método paintComponent con el paso del parámetro gráfico g
     * pintando el fondo de la pantalla y dibujando sobre él el laberinto y los
     * personajes correspondientes.
     *
     * @param g gráfico que indica el componente que se dibuja
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        dibujarLaberinto(g);
        dibujarComecocos(g);
        dibujarFantasmas(g);
    }

    /**
     * A partir de un parámetro gráfico, dibuja el laberinto del juego según sea
     * un bloque o pared, una puerta o los cocos.
     *
     * @param g gráfico que indica el componente que se dibuja
     */
    private void dibujarLaberinto(Graphics g) {

        Laberinto laberinto = comecocosFrame_.getModelo().getLaberinto();
        int nfilas = laberinto.getAltura();
        int ncolumnas = laberinto.getAnchura();

        for (int fila = 0; fila < nfilas; fila++) {
            for (int columna = 0; columna < ncolumnas; columna++) {

                switch (laberinto.getCelda(columna, fila)) {
                    case BLOQUE:
                        g.setColor(Color.BLUE);
                        g.fillRect(columna * anchoCelda, fila * anchoCelda,
                                anchoCelda, anchoCelda);
                        break;
                    case COCOPEQUENO:
                        g.setColor(Color.white);
                        g.fillOval(columna * anchoCelda + anchoCelda / 4 + 3, fila * anchoCelda + anchoCelda / 4 + 3,
                                anchoCelda / 5, anchoCelda / 5);
                        break;
                    case COCOGRANDE:
                        g.setColor(Color.white);
                        g.fillOval(columna * anchoCelda + anchoCelda / 2 - 3, fila * anchoCelda + anchoCelda / 2 - 3,
                                anchoCelda / 2, anchoCelda / 2);
                        break;
                    case PUERTA:
                        g.setColor(Color.white);
                        g.fillRect(columna * anchoCelda, fila * anchoCelda + 4 * anchoCelda / 5,
                                anchoCelda, anchoCelda / 5);
                        break;
                }
            }
        }
    }

    /**
     * A partir del elemento gráfico, dibujará y rellenará la silueta del
     * personaje comecocos.
     *
     * @param g gráfico que indica el componente a dibujar
     */
    private void dibujarComecocos(Graphics g) { // plano vertical invertido Eje Y mayor hacia abajo

        Comecocos comecocos = comecocosFrame_.getModelo().getComecocos();

        g.setColor(Color.yellow);
        g.fillOval(comecocos.getColumna() * anchoCelda,
                comecocos.getFila() * anchoCelda, anchoCelda, anchoCelda);
        g.setColor(Color.black);
        switch (comecocos.getDireccion()) {
            case IZQUIERDA:
                g.fillOval((comecocos.getColumna() * anchoCelda),
                        (comecocos.getFila() * anchoCelda) + anchoCelda / 3, anchoCelda / 2 + 1, anchoCelda / 3+ 1);
                break;
            case DERECHA:
                g.fillOval((comecocos.getColumna() * anchoCelda) + 2 * anchoCelda / 3 - 3,
                        (comecocos.getFila() * anchoCelda) + anchoCelda / 3, anchoCelda / 2 + 1, anchoCelda / 3 + 1);
                break;
            case ARRIBA:
                g.fillOval((comecocos.getColumna() * anchoCelda) + anchoCelda/3,
                        (comecocos.getFila() * anchoCelda), anchoCelda / 3 + 1, anchoCelda / 2 + 1);
                break;
            case ABAJO:
                g.fillOval((comecocos.getColumna() * anchoCelda) + anchoCelda / 3,
                        (comecocos.getFila() * anchoCelda) + 2 * anchoCelda / 3 - 2, anchoCelda / 3 + 1, anchoCelda / 2 + 1);
                break;
        }
    }

    /**
     * A partir del elemento gráfico, dibujará y rellenará la silueta del
     * personaje fantasma diferenciando a cada uno de los 4 posibles por
     * colores.
     *
     * @param g gráfico que indica el componente a dibujar
     */
    private void dibujarFantasmas(Graphics g) {

        for (int i = 0; i < 4; i++) {

            String fantasma = valueOf(comecocosFrame_.getModelo().getFantasma(i).getNombre());

            switch (fantasma) {

                case "INKY":
                    g.setColor(Color.cyan);
                    break;

                case "PINKY":
                    g.setColor(Color.pink);
                    break;

                case "BLINKY":
                    g.setColor(Color.red);
                    break;

                case "CLYDE":
                    g.setColor(Color.orange);
                    break;
            }

            g.fillOval(comecocosFrame_.getModelo().getFantasma(i).getColumna() * anchoCelda + 3,
                    comecocosFrame_.getModelo().getFantasma(i).getFila() * anchoCelda + 3,
                    anchoCelda - 6, anchoCelda - 6);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Para una acción procedente de un evento del ratón, hará que la atención
     * se focalice en la posición del ratón para que los eventos del teclado los
     * reciba el panel siempre y cuando el ratón esté sobre el panel.
     *
     * @param evt evento procedente de la acción sobre uno de los botones de la
     * interfaz.
     */
    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        requestFocus(); // Para que los eventos del teclado los reciba el panel
    }//GEN-LAST:event_formMouseEntered

    /**
     * Para una acción procedente de una tecla pulsada, captará la tecla que
     * haya sido presionada.
     *
     * @param evt evento procedente de la acción sobre uno de los botones de la
     * interfaz.
     */
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        comecocosFrame_.getControlador().teclaPulsadaEnLaberintoPanel(evt);
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
