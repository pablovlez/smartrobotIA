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
public class BusquedaAvara {

    ArrayList<Nodo> listaNodos;
    Nodo temporal, raiz;
    private ArrayList camino;
    int heuristica;

    public BusquedaAvara(int _heuristica) {
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
            //System.out.print("el tamanio de la cola antes de es :"+colaNodos.tamano());
            temporal = menorH();

            resultado = temporal.esMeta();
            posibilidades = temporal.aplicarOperador();
            /*System.out.println("< " + temporal.getEstado()[0] + " , " + temporal.getEstado()[1] + " > en profundidad: " + temporal.getEstado()[3] + " con costo: " + temporal.getCosto());
            System.out.println("Con " + temporal.getItems() + " items");
            System.out.println("Distancia al item1 : "+temporal.getDistanciaItem1());
            System.out.println("Distancia al item2 : "+temporal.getDistanciaItem2());
            System.out.println("Distancia a la Salida : "+temporal.getDistanciaSalida());
            System.out.println("H : "+temporal.getHeuristica());

            dibuja(temporal);*/
            crearNodos(temporal, posibilidades);
            contador++;
            //System.out.print("el tamanio de la cola despuesde es :"+colaNodos.tamano());

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
                //System.out.println("Lo sentimos no se encontro la solucion");
            }
            if (temporal.getEstado()[3] > 100) {
                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                trazarRuta(temporal);
                setCamino(trazarRuta1(temporal));
                Interfaz.jTextArea1.append("///////////////***************////////////\n");
                Interfaz.jTextArea1.append("Informe de Busqueda Informada Avara : \n");
                Interfaz.jTextArea1.append("No se encontro la solucion \n");
                Interfaz.jTextArea1.append("Profundidad del arbol : " + temporal.getEstado()[3] + "\n");
                Interfaz.jTextArea1.append("Costo total del recorrido : " + temporal.getCosto() + "\n");
                Interfaz.jTextArea1.append("Heuristica : " + temporal.getHeuristica() + "\n");
                Interfaz.jTextArea1.append("Funcion : " + temporal.getFuncion() + "\n");
                Interfaz.jTextArea1.append("Cantidad de Nodos expandidos : " + contador + "\n");
                Interfaz.jTextArea1.append("Tiempo de ejecucion : " + totalTiempo + "\n");
                Interfaz.jTextArea1.append("///////////////***************////////////\n");

                break;
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
        //posibilidades = evitaDevolverse(sucesor, posibilidades);

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
            } else {
                nodoH.heuristica1();
            }
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
            } else {
                nodoH.heuristica1();
            }
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
            } else {
                nodoH.heuristica1();
            }
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
            } else {
                nodoH.heuristica1();
            }
            listaNodos.add(nodoH);
        }


    }

    /*public int[] evitaDevolverse(Nodo nod, int[] posibilidades) {

    if (nod.getPadre() != null) {

    if (posibilidades[0] == 0) {
    // El paso anterior fue hacia arriba
    if ((nod.getPadre().getEstado()[0] == nod.getEstado()[0] - 1) && (nod.getPadre().getEstado()[1] == nod.getEstado()[1]) && nod.getPadre().getItems() == nod.getItems() && nod.getPadre().getEstado()[4] == nod.getEstado()[4]) {
    posibilidades[0] = 1;
    } else {
    }
    }

    if (posibilidades[1] == 0) {
    // El paso anterior fue hacia derecha&& nod.getPadre().getEstado()[4]==nod.getEstado()[4]
    if ((nod.getPadre().getEstado()[0] == nod.getEstado()[0]) && (nod.getPadre().getEstado()[1] == nod.getEstado()[1] + 1) && nod.getPadre().getItems() == nod.getItems() && nod.getPadre().getEstado()[4] == nod.getEstado()[4]) {
    posibilidades[1] = 1;
    } else {
    }
    }

    if (posibilidades[2] == 0) {
    // El paso anterior fue hacia abajo
    if ((nod.getPadre().getEstado()[0] == nod.getEstado()[0] + 1) && (nod.getPadre().getEstado()[1] == nod.getEstado()[1]) && nod.getPadre().getItems() == nod.getItems() && nod.getPadre().getEstado()[4] == nod.getEstado()[4]) {
    posibilidades[2] = 1;
    } else {
    }
    }

    if (posibilidades[3] == 0) {
    // El paso anterior fue hacia izquierda
    if ((nod.getPadre().getEstado()[0] == nod.getEstado()[0]) && (nod.getPadre().getEstado()[1] == nod.getEstado()[1] - 1) && nod.getPadre().getItems() == nod.getItems() && nod.getPadre().getEstado()[4] == nod.getEstado()[4]) {
    posibilidades[3] = 1;
    } else {
    }
    }
    }

    return posibilidades;
    }*/
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

    public Nodo menorH() {

        /*for (int i = 0; i < listaNodos.size(); i++) {
        Nodo aux = (Nodo) listaNodos.get(i);
        System.out.print(" <<< " + aux.getHeuristica() + " <<");

        }*/

        double valormenor = 100000;
        int posMenorItem1 = 0;
        for (int i = 0; i < listaNodos.size(); i++) {
            Nodo aux = (Nodo) listaNodos.get(i);
            if (aux.getHeuristica() <= valormenor) {
                valormenor = aux.getHeuristica();
                posMenorItem1 = i;

            }

        }

        Nodo n = listaNodos.remove(posMenorItem1);

        /*System.out.println("Posicion del nodo con h menor fue " + posMenorItem1);
        System.out.println("Heuristica del nodo devuelto " + n.getHeuristica());
        //System.out.println("La direccion del nodo devuelto es " + n.getDireccion());*/
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
