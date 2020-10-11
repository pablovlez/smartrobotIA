package smartrobot;

import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Apolo
 */
public class Espacio {

    static final int VACIO = 0;
    static final int MURO = 1;
    static final int INICIO = 2;
    static final int SALIDA = 3;
    static final int NAVE1 = 4;
    static final int NAVE2 = 5;
    static final int ITEM = 6;
    static final int CAMPO = 7;
    private static int[][] espacio;

    /**
     * @return the espacio
     */
    public static int[][] getEspacio() {
        return espacio;
    }

    /**
     * @param aEspacio the espacio to set
     */
    public static void setEspacio(int[][] aEspacio) {
        espacio = aEspacio;
    }

    public Espacio(int _espacio[][]) {
        espacio = _espacio;
    }

    static int[] getEstadoRaiz() {
        int[] resultado = new int[8];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getEspacio()[i][j] == INICIO) {
                    resultado[0] = i;
                    resultado[1] = j;
                    resultado[2] = 0;
                    resultado[3] = 0;
                    resultado[4] = 0;
                    resultado[5] = 0;
                    resultado[6] = 0;
                    resultado[7] = 0;                    
                    return resultado;
                }
            }
        }

        System.out.println("No se encontro el punto de inicio");
        System.exit(-1);

        return resultado;
    }

    static int getCelda(int[] estado) throws ArrayIndexOutOfBoundsException {
        return getEspacio()[estado[0]][estado[1]];
    }

    static int recogerItem(int[] estado) {
        if (getEspacio()[estado[0]][estado[1]] == ITEM) {
            //getEspacio()[estado[0]][estado[1]] = VACIO;
            return 1;
        } else {
            return 0;
        }
    }

    static ArrayList<Integer> coordItem() {
        ArrayList resultado = new ArrayList();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getEspacio()[i][j] == ITEM) {
                    resultado.add(i);
                    resultado.add(j);
                }
            }
        }
        return resultado;
    }

    static ArrayList<Integer> coordSalida() {
        ArrayList resultado = new ArrayList();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getEspacio()[i][j] == SALIDA) {
                    resultado.add(i);
                    resultado.add(j);
                }
            }
        }
        return resultado;
    }

    static int montarNave(int[] estado) {
        if (getEspacio()[estado[0]][estado[1]] == NAVE1) {
            //getEspacio()[estado[0]][estado[1]] = VACIO;
            if (estado[2] == 0) {
                System.out.println("Me he montado en la nave1");
                return 10;
            }
        }

        if (getEspacio()[estado[0]][estado[1]] == NAVE2) {
            //getEspacio()[estado[0]][estado[1]] = VACIO;

            ///se puede montar en una nave mientras no vaya en otra
            if (estado[2] == 0) {
                System.out.println("Me he montado en la nave2");
                return 20;
            }
        }

        return 0;
    }

    /*
     *  posibilidades(int [] estado)
     *  Esta funcion devuelve si se puede seguir o no en cada sentido
     */
    static int[] getPosibilidades(int[] estado) {
        int[] resultados = new int[4];
        int[] direccion = new int[2];

        // revisamos si puede ir hacia arriba
        if (estado[0] > 0) {
            direccion[0] = estado[0] - 1;
        }
        direccion[1] = estado[1];

        if (estado[0] > 0 && getCelda(direccion) != MURO) {
            resultados[0] = VACIO;
        } else {
            resultados[0] = MURO;
        }

        // revisamos si puede ir hacia la derecha
        if (estado[1] < 9) {
            direccion[1] = estado[1] + 1;
        }
        direccion[0] = estado[0];

        if (estado[1] < 9 && getCelda(direccion) != MURO) {
            resultados[1] = VACIO;
        } else {
            resultados[1] = MURO;
        }

        // revisamos si puede ir hacia abajo
        if (estado[0] < 9) {
            direccion[0] = estado[0] + 1;
        }
        direccion[1] = estado[1];

        if (estado[0] < 9 && getCelda(direccion) != MURO) {
            resultados[2] = VACIO;
        } else {
            resultados[2] = MURO;
        }

        // revisamos si puede ir hacia la izquierda
        direccion[0] = estado[0];
        if (estado[1] > 0) {
            direccion[1] = estado[1] - 1;
        }

        if (estado[1] > 0 && getCelda(direccion) != MURO) {
            resultados[3] = VACIO;
        } else {
            resultados[3] = MURO;
        }

        return resultados;
    }
}
