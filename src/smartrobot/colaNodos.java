package smartrobot;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Apolo
 */
public class colaNodos extends CListaCircularSE {

    public void meterEnCola(Object obj) {
        anadirAlFinal(obj);
    }

    public Object sacarDeCola() {
        return borrar();
    }
}
