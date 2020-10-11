package smartrobot;

import java.util.ArrayList;

/**
 *
 * @author Pablo Velez
 */
public class BusquedaPP {

    static Pila colaNodos;
    Nodo temporal, raiz;
    private ArrayList camino;

    public BusquedaPP() {
        raiz = new Nodo();
    }

    void realizarBusqueda() {
        int resultado = -1;
        int[] posibilidades;
        colaNodos = new Pila(raiz);
        int contador = 0;
        long tiempoInicio = System.currentTimeMillis();


        while (resultado == -1) {
            temporal = colaNodos.pop();
            resultado = temporal.esMeta();
            posibilidades = temporal.aplicarOperador();
            /*System.out.println("< " + temporal.getEstado()[0] + " , " + temporal.getEstado()[1] + " > en profundidad: " + temporal.getEstado()[3] + " con costo: " + temporal.getCosto());
            System.out.println("Con " + temporal.getItems() + " items");
            System.out.println("Con gasolina" + temporal.getEstado()[2]);
            dibuja(temporal);*/
            crearNodos(temporal, posibilidades);
            contador++;

            if (resultado == 3) {
                System.out.println("Lo encontramos");
                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                trazarRuta(temporal);
                setCamino(trazarRuta1(temporal));
                Interfaz.jTextArea1.append("///////////////***************////////////\n");
                Interfaz.jTextArea1.append("Informe de Busqueda no Informada Preferente por Profundidad : \n");
                Interfaz.jTextArea1.append("Profundidad del arbol : " + temporal.getEstado()[3] + "\n");
                Interfaz.jTextArea1.append("Costo total del recorrido : " + temporal.getCosto() + "\n");
                Interfaz.jTextArea1.append("Cantidad de Nodos expandidos : " + contador + "\n");
                Interfaz.jTextArea1.append("Tiempo de ejecucion : " + totalTiempo + "\n");
                Interfaz.jTextArea1.append("///////////////***************////////////\n");
                break;
            } else {
            }
            
        }
        if(resultado==-1){
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
        ArrayList arg = new ArrayList();
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
            int[] estadoH = {estado[0] - 1, estado[1], estado[2], estado[3] + 1, estado[4], estado[5], estado[6],estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Arriba");
            nodoH.costo(c);
            arg.add(nodoH);
        }

        // Se crea el nodo hacia la derecha?
        if (posibilidades[1] == 0) {
            int[] estadoH = {estado[0], estado[1] + 1, estado[2], estado[3] + 1, estado[4], estado[5], estado[6],estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Derecha");
            nodoH.costo(c);
            arg.add(nodoH);
        }

        // Se crea el nodo hacia abajo?
        if (posibilidades[2] == 0) {
            int[] estadoH = {estado[0] + 1, estado[1], estado[2], estado[3] + 1, estado[4], estado[5], estado[6],estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Abajo");
            nodoH.costo(c);            
            arg.add(nodoH);
        }

        // Se crea el nodo hacia la izquierda?
        if (posibilidades[3] == 0) {
            int[] estadoH = {estado[0], estado[1] - 1, estado[2], estado[3] + 1, estado[4], estado[5], estado[6],estado[7]};
            nodoH = new Nodo(sucesor, estadoH);
            nodoH.setDireccion("Izquierda");
            nodoH.costo(c);            
            arg.add(nodoH);
        }

        for (int i = arg.size() - 1; i >= 0; i--) {
            colaNodos.push((Nodo) arg.get(i));
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

////ciclo para meter la ruta desde el nodo meta al nodo padre en el arreglo rutaReves
        for (int i = 0; i <= n; i++) {
            rutaReves.add(i, meta.getDireccion());
            //System.out.println(rutaReves.get(i));
            meta = meta.getPadre();
        }
        int aux = 0;
        System.out.println(rutaReves.size());
        ///////ciclo para acomodar la ruta desde la raiz hasta el nodo meta en el arreglo ruta
        for (int j = rutaReves.size() - 1; j >= 0; j--) {
            ruta.add(aux, rutaReves.get(j));
            System.out.println(ruta.get(aux));
            aux++;

        }
    }

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

    /**
     * @return the camino
     */
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
