/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartrobot;

import java.util.ArrayList;

/**
 *
 * @author pabloava
 */
public class BusquedaAStar {

    ArrayList<Nodo> listaNodos;
    Nodo temporal, raiz;
    private ArrayList camino;
    int heuristica;

    public BusquedaAStar(int _heuristica) {
        raiz = new Nodo(_heuristica);
        listaNodos = new ArrayList();
        heuristica = _heuristica;

    }

    void realizarBusqueda() {
        int resultado = -1;
        int[] posibilidades;
        listaNodos.add(raiz);
        int contador = 0;
        long tiempoInicio = System.currentTimeMillis();



        while (resultado == -1) {
            temporal = menorF();
            resultado = temporal.esMeta();
            posibilidades = temporal.aplicarOperador();
            System.out.println("< " + temporal.getEstado()[0] + " , " + temporal.getEstado()[1] + " > en profundidad: " + temporal.getEstado()[3] + " con costo: " + temporal.getCosto());
            //System.out.println("Con " + temporal.getItems() + " items");
            //System.out.println("Distancia al item1 : " + temporal.getDistanciaItem1());
            //System.out.println("Distancia al item2 : " + temporal.getDistanciaItem2());
            //System.out.println("Distancia a la Salida : " + temporal.getDistanciaSalida());
            System.out.println("Heuristica : " + temporal.getHeuristica());
            System.out.println("Funcion : " + temporal.getFuncion());
            //dibuja(temporal);
            crearNodos(temporal, posibilidades);
            contador++;

            if (resultado == 3) {
                System.out.println("Lo encontramos");
                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                trazarRuta(temporal);
                setCamino(trazarRuta1(temporal));
                Interfaz.jTextArea1.append("///////////////***************////////////\n");
                Interfaz.jTextArea1.append("Informe de Busqueda Informada A* : \n");
                Interfaz.jTextArea1.append("Profundidad del arbol : " + temporal.getEstado()[3] + "\n");
                Interfaz.jTextArea1.append("Costo total del recorrido : " + temporal.getCosto() + "\n");
                Interfaz.jTextArea1.append("Heuristica : " + temporal.getHeuristica() + "\n");
                Interfaz.jTextArea1.append("Funcion : " + temporal.getFuncion() + "\n");
                Interfaz.jTextArea1.append("Cantidad de Nodos expandidos : " + contador + "\n");
                Interfaz.jTextArea1.append("Tiempo de ejecucion : " + totalTiempo + "\n");
                Interfaz.jTextArea1.append("///////////////***************////////////\n");
                break;
            } else {
                // System.out.println("Lo sentimos no se encontro la solucion");
            }

        }
        if (resultado == -1) {
            System.out.println("Lo sentimos no se encontro la solucion");
        }

    }

    public void dibuja(Nodo n) {
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

    public void crearNodos(Nodo sucesor, int[] posibilidades) {
        int[] estado = sucesor.getEstado();
        double c = sucesor.getCosto();
        Nodo nodoH;
        posibilidades = evitaDevolverse(sucesor, posibilidades);

        /////si va montado en la nave y se mueve se le quita una unidad de gasolina
        if (estado[2] > 0) {
            estado[2] -= 1;
        }
        if (estado[2] == 0) {
            estado[4] = 0;
        }


        // Se crea el nodo hacia arriba?
        if (posibilidades[0] == 0) {
            int[] estadoH = {estado[0] - 1, estado[1], estado[2], estado[3] + 1, estado[4], estado[5], estado[6], estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Arriba");
            nodoH.costo(c);
            if (this.heuristica == 0) {
                nodoH.heuristica();
            }else{
                nodoH.heuristica1();
            }
            nodoH.costoMasHeuristica();
            listaNodos.add(nodoH);
        }

        // Se crea el nodo hacia la derecha?
        if (posibilidades[1] == 0) {
            int[] estadoH = {estado[0], estado[1] + 1, estado[2], estado[3] + 1, estado[4], estado[5], estado[6], estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Derecha");
            nodoH.costo(c);
            if (this.heuristica == 0) {
                nodoH.heuristica();
            }else{
                nodoH.heuristica1();
            }
            nodoH.costoMasHeuristica();
            listaNodos.add(nodoH);
        }

        // Se crea el nodo hacia abajo?
        if (posibilidades[2] == 0) {
            int[] estadoH = {estado[0] + 1, estado[1], estado[2], estado[3] + 1, estado[4], estado[5], estado[6], estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Abajo");
            nodoH.costo(c);
            if (this.heuristica == 0) {
                nodoH.heuristica();
            }else{
                nodoH.heuristica1();
            }
            nodoH.costoMasHeuristica();
            listaNodos.add(nodoH);
        }

        // Se crea el nodo hacia la izquierda?
        if (posibilidades[3] == 0) {
            int[] estadoH = {estado[0], estado[1] - 1, estado[2], estado[3] + 1, estado[4], estado[5], estado[6], estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Izquierda");
            nodoH.costo(c);
            if (this.heuristica == 0) {
                nodoH.heuristica();
            }else{
                nodoH.heuristica1();
            }
            nodoH.costoMasHeuristica();
            listaNodos.add(nodoH);
        }


    }

    public int[] evitaDevolverse(Nodo nod, int[] pos) {
        int[] posibilidades = pos;

        Nodo padre = nod.getPadre();

        while (padre != null) {

            if (posibilidades[0] == 0) {
                // El paso anterior fue hacia arriba
                if ((padre.getEstado()[0] == nod.getEstado()[0] - 1) && (padre.getEstado()[1] == nod.getEstado()[1]) && padre.getItems() == nod.getItems() && (padre.getEstado()[4] == nod.getEstado()[4]) && (padre.getEstado()[5] == nod.getEstado()[5]) && (padre.getEstado()[6] == nod.getEstado()[6])) {
                    posibilidades[0] = 1;
                } else {
                }
            }

            if (posibilidades[1] == 0) {
                // El paso anterior fue hacia derecha&& nodgetPadre().getEstado()[2]==nod.getEstado()[2]
                if ((padre.getEstado()[0] == nod.getEstado()[0]) && (padre.getEstado()[1] == nod.getEstado()[1] + 1) && padre.getItems() == nod.getItems() && (padre.getEstado()[4] == nod.getEstado()[4]) && (padre.getEstado()[5] == nod.getEstado()[5]) && (padre.getEstado()[6] == nod.getEstado()[6])) {
                    posibilidades[1] = 1;
                } else {
                }
            }

            if (posibilidades[2] == 0) {
                // El paso anterior fue hacia abajo
                if ((padre.getEstado()[0] == nod.getEstado()[0] + 1) && (padre.getEstado()[1] == nod.getEstado()[1]) && padre.getItems() == nod.getItems() && (padre.getEstado()[4] == nod.getEstado()[4]) && (padre.getEstado()[5] == nod.getEstado()[5]) && (padre.getEstado()[6] == nod.getEstado()[6])) {
                    posibilidades[2] = 1;
                } else {
                }
            }

            if (posibilidades[3] == 0) {
                // El paso anterior fue hacia izquierda
                if ((padre.getEstado()[0] == nod.getEstado()[0]) && (padre.getEstado()[1] == nod.getEstado()[1] - 1) && padre.getItems() == nod.getItems() && (padre.getEstado()[4] == nod.getEstado()[4]) && (padre.getEstado()[5] == nod.getEstado()[5]) && (padre.getEstado()[6] == nod.getEstado()[6])) {
                    posibilidades[3] = 1;
                } else {
                }
            }

            padre = padre.getPadre();
        }

        return posibilidades;
    }

    void trazarRuta(Nodo meta) {
        int n = meta.getEstado()[3];

        ArrayList rutaReves = new ArrayList();
        ArrayList ruta = new ArrayList();

        for (int i = 0; i <= n; i++) {
            rutaReves.add(i, meta.getDireccion());

            meta = meta.getPadre();
        }

        int aux = 0;
        System.out.println(rutaReves.size());
        for (int j = rutaReves.size() - 1; j >= 0; j--) {
            ruta.add(aux, rutaReves.get(j));
            System.out.println(ruta.get(aux));
            aux++;

        }
    }

    public Nodo menorF() {

        /* for (int i = 0; i < listaNodos.size(); i++) {
        Nodo aux = (Nodo) listaNodos.get(i);
        System.out.print(" <<< " + aux.getFuncion() + " <<");

        }*/

        double valormenor = 100000;
        int posMenorItem1 = 0;
        for (int i = 0; i < listaNodos.size(); i++) {
            Nodo aux = (Nodo) listaNodos.get(i);
            if (aux.getFuncion() <= valormenor) {
                valormenor = aux.getFuncion();
                posMenorItem1 = i;

            }

        }

        Nodo n = listaNodos.remove(posMenorItem1);


        /*System.out.println("Posicion del nodo con h menor fue " + posMenorItem1);
        System.out.println("Funcion del nodo devuelto " + n.getFuncion());*/
        return n;





    }////izquierda, derecha, arriba, abajo

    public ArrayList trazarRuta1(Nodo meta) {

        int n = meta.getEstado()[3];

        ArrayList<int[]> rutaReves = new ArrayList(n);
        ArrayList<int[]> ruta = new ArrayList(n);

////ciclo para meter la ruta desde el nodo meta al nodo padre en el arreglo rutaReves
        for (int i = 0; i <= n; i++) {
            int[] coordenada = {meta.getEstado()[0], meta.getEstado()[1]};
            rutaReves.add(i, coordenada);
            meta = meta.getPadre();
        }
        int aux = 0;
        ///////ciclo para acomodar la ruta desde la raiz hasta el nodo meta en el arreglo ruta
        for (int j = rutaReves.size() - 1; j >= 0; j--) {
            ruta.add(aux, rutaReves.get(j));
            System.out.println(ruta.get(aux)[0] + "," + ruta.get(aux)[1]);
            aux++;

        }
        return ruta;
    }

    public ArrayList getCamino() {
        return camino;
    }

    /**
     * @param camino the camino to set
     */
    public void setCamino(ArrayList camino) {
        this.camino = camino;
    }
}
