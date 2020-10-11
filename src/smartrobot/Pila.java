/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartrobot;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class Pila {

    int tope = -1;
    ArrayList<Nodo> arg;

    public Pila(Nodo raiz) {
        arg = new ArrayList();
        arg.add(raiz);
        tope = 0;
    }

    public boolean estaVacia() {
        if (tope == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estaLlena() {
        if (tope == arg.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void push(Nodo n) {
        /*if (estaLlena())
        {
        JOptionPane.showMessageDialog(null,"Error de Overflow");
        } else
        {*/
        tope++;
        arg.add(tope, n);
        //System.out.print("Se agrego el nodo con direcicon " + n.getDireccion() + "en la posicion " + tope);
        //}
    }

    public Nodo pop() {
        Nodo n = null;
        if (estaVacia()) {
            System.out.print("Error de Underflow");
        } else {
            n = arg.remove(tope);
            //arg[tope]=0;
            tope--;
            //System.out.print("Se retiro el nodo con direccion " + n.getDireccion());
        }
        return n;

    }

    public int top() {
        System.out.print("El tope de la pila es " + tope);
        return tope;
    }

    public ArrayList verDatos() {
        ArrayList arreglo = new ArrayList();
        if (estaVacia()) {
            arreglo.add(0, "La pila esta vacia");
        } else {
            arreglo.add(0, "-----DATOS DE LA PILA-----");
            int k = 0;
            int j = tope;
            for (int i = j; i >= 0; i--) {
                arreglo.add(k + 1, "El elemento en la posicion " + i + " es " + arg.get(i).getDireccion());
                k++;
            }
        }
        return arreglo;
    }
}
