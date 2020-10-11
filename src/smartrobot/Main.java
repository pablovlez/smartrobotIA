/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartrobot;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.GraphiteGlassSkin;

/**
 *
 * @author Pablo Velez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here


        java.awt.EventQueue.invokeLater(new Runnable() {

    public void run() {
    Interfaz frame = new Interfaz();
    frame.setDefaultLookAndFeelDecorated(true);
    SubstanceLookAndFeel.setSkin(new GraphiteGlassSkin());
        frame.setVisible(true);
    }
    });
    }
}
