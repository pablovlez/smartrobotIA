package smartrobot;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Apolo
 */
public class DibujaEspacio {

    static void dibuja(Nodo n) {
        System.out.println(" _ _ _ _ _ _ _ _ _ _ ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (n.getEstado()[0] == i && n.getEstado()[1] == j) {
                    System.out.print("*");
                } else {
                    System.out.print(Espacio.getEspacio()[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println(" _ _ _ _ _ _ _ _ _  ");
    }
}
