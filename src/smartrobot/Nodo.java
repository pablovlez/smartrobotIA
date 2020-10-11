package smartrobot;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hernandcb
 */
public class Nodo {

    final int SALIDA = 3;
    final int NAVE1 = 4;
    final int NAVE2 = 5;
    final int ITEM = 6;
    private int[] estado;
    private Nodo padre;
    private int items;
    private double costo;
    private String direccion;
    private double distanciaItem1;
    private double distanciaItem2;
    private double distanciaSalida;
    private double heuristica;
    private double funcion;


    /*
     *   El arreglo estado alamacena el estado actual del nodo:
     *      estado[0]: Almacena la posicion x en el tablero
     *      estado[1]: Almacena la posicion y en el tablero
     *      estado[2]: Almacena la cantidad de pasos disponibles en la nave
     *      estado[3]: Almacena la profundidad del arbol
     *                  si es cero es porque no va en la nave.
     *      estado[4]: indica si va en nave 1
     *      estado[5]: indica si coge el item1
     *      estado[6]: indica si coge el item2
     *      estado[7]: indica si va en la nave 2
     *
     */
    /*
     *  El constructor sin parametros es para el nodo raiz.
     *  toma los valores iniciales del tablero
     */

    /////constructor para el nodo raiz de las busquedas no informadas
    public Nodo() {
        estado = Espacio.getEstadoRaiz();
        padre = null;
        items = 0;
        direccion = "Inicio";
        ///////La heuristica es la suma de las distancias manhattan del item1 item2 y la salida
        ////////heuristica = distanciaItem1 + distanciaItem2 + distanciaSalida;
        
    }
/////constructor para el nodo raiz de las busquedas  informadas
    public Nodo(int h) {
        estado = Espacio.getEstadoRaiz();
        padre = null;
        items = 0;
        direccion = "Inicio";
        ///////La heuristica es la suma de las distancias manhattan del item1 item2 y la salida
        ////////heuristica = distanciaItem1 + distanciaItem2 + distanciaSalida;
        if(h==0){
        heuristica();}
        else{
        heuristica1();
        }
        costoMasHeuristica();
    }
/////constructor para los nodos hijos

    public Nodo(Nodo papa, int[] operador) {

        estado = operador;
        padre = papa;
        items = padre.items;


    }

    public int esMeta() {

        if (getItems() == 2 && Espacio.getCelda(getEstado()) == Espacio.SALIDA) {
            return Espacio.SALIDA;
        }
        return -1;
    }

    public int[] aplicarOperador() {
        int[] posibilidades = new int[4];

        if (Espacio.getCelda(getEstado()) == ITEM) {
            if (this.estado[5] != 1) {
                if (getEstado()[0] == Espacio.coordItem().get(0) && getEstado()[1] == Espacio.coordItem().get(1)) {
                    ///si encontro el primer item activa la bandera en 1
                    this.estado[5] = 1;
                    if (getItems() < 2) {
                        setItems(getItems() + Espacio.recogerItem(getEstado()));
                        System.out.println("Encontre un item");
                    }
                }
            }

            if (this.estado[6] != 1) {
                if (getEstado()[0] == Espacio.coordItem().get(2) && getEstado()[1] == Espacio.coordItem().get(3)) {
                    ///si encontro el segundo item activa la bandera en 1
                    this.estado[6] = 1;
                    if (getItems() < 2) {
                        setItems(getItems() + Espacio.recogerItem(getEstado()));
                        System.out.println("Encontre un item");
                    }
                }
            }
        } else {
            if (Espacio.getCelda(getEstado()) == NAVE1) {
                if (getEstado()[4] == 0) {
                    this.estado[2] = Espacio.montarNave(estado);
                    this.estado[4] = 1;

                }
            }
            if (Espacio.getCelda(getEstado()) == NAVE2) {
                if (getEstado()[7] == 0) {
                    this.estado[2] = Espacio.montarNave(estado);
                }

                this.estado[7] = 1;
            }
        }

        posibilidades = Espacio.getPosibilidades(getEstado());
        return posibilidades;
    }

    public void costo(double c) {

        /////asignar el costo del padre
        setCosto(c);
        ///si va a pie le cuesta 1
        if (estado[2] == 0) {
            this.costo += 1;
        } else {
            //si va en nave le cuesta la mitad
            this.costo += 0.5;
        }

        ////si pasa por un campo electrico le vale 6

        if (Espacio.getCelda(estado) == Espacio.CAMPO) {
            setCosto(getCosto() + 6);
        }

    }

    public void heuristica() {
        ////distancia en L al item 1
        if (getEstado()[5] == 0) {/////////si no ha encontrado el item 1 calcula la distancia en L desde el robot hasta el item
            setDistanciaItem1(Math.abs(getEstado()[0] - Espacio.coordItem().get(0)) + Math.abs(getEstado()[1] - Espacio.coordItem().get(1)));
        } else {///si ya cogio el item la distancia es cero
            setDistanciaItem1(0);
        }


        ////distancia en L al item 2
        if (getEstado()[5] == 0) {//////////si no ha encontrado el item 1, se calcula la distancia en L desde el item1 hasta el item2
            if (getEstado()[6] == 0) {
                setDistanciaItem2(Math.abs(Espacio.coordItem().get(0) - Espacio.coordItem().get(2)) + Math.abs(Espacio.coordItem().get(1) - Espacio.coordItem().get(3)));
            } else {
                if (getEstado()[5] == 1) {/////si ya cogio el item1, calcula la distancia en L desde el robot hasta el item2
                    setDistanciaItem2(Math.abs(getEstado()[0] - Espacio.coordItem().get(2)) + Math.abs(getEstado()[1] - Espacio.coordItem().get(3)));
                } else {//////si ya cogio el item2 distancia es cero
                    setDistanciaItem2(0);
                }
            }

        }

        //////distancia a la salida
        if (getEstado()[6] == 1) {
            setDistanciaSalida(Math.abs(getEstado()[0] - Espacio.coordSalida().get(0)) + Math.abs(getEstado()[1] - Espacio.coordSalida().get(1)));
        } else {
            setDistanciaSalida(Math.abs(Espacio.coordItem().get(2) - Espacio.coordSalida().get(0)) + Math.abs(Espacio.coordItem().get(3) - Espacio.coordSalida().get(1)));
        }

        setHeuristica((getDistanciaItem1() + getDistanciaItem2() + getDistanciaSalida()) / 2);
        //setHeuristica(2);

    }

    public void heuristica1() {
        double Fitems = 0;
        setDistanciaSalida(Math.abs(getEstado()[0] - Espacio.coordSalida().get(0)) + Math.abs(getEstado()[1] - Espacio.coordSalida().get(1)));
        if ((getEstado()[5] == 1 && getEstado()[6] == 0) || (getEstado()[5] == 0 && getEstado()[6] == 1)) {
            Fitems = 1;
        } else {
            if (getEstado()[5] == 0 && getEstado()[6] == 0) {
                Fitems = 2;
            }
        }

        double _h=(distanciaSalida-Fitems)/3;

        if(_h>=0){
        setHeuristica(_h);
        }else
        {
        setHeuristica(_h);
        }

        


    }

    public void costoMasHeuristica() {
        setFuncion(costo + getHeuristica());
    }

    /**
     * @return the estado
     */
    public int[] getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int[] estado) {
        this.estado = estado;
    }

    /**
     * @return the padre
     */
    public Nodo getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    /**
     * @return the items
     */
    public int getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(int items) {
        this.items = items;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the distanciaItem1
     */
    public double getDistanciaItem1() {
        return distanciaItem1;
    }

    /**
     * @param distanciaItem1 the distanciaItem1 to set
     */
    public void setDistanciaItem1(double distanciaItem1) {
        this.distanciaItem1 = distanciaItem1;
    }

    /**
     * @return the distanciaItem2
     */
    public double getDistanciaItem2() {
        return distanciaItem2;
    }

    /**
     * @param distanciaItem2 the distanciaItem2 to set
     */
    public void setDistanciaItem2(double distanciaItem2) {
        this.distanciaItem2 = distanciaItem2;
    }

    /**
     * @return the distanciaSalida
     */
    public double getDistanciaSalida() {
        return distanciaSalida;
    }

    /**
     * @param distanciaSalida the distanciaSalida to set
     */
    public void setDistanciaSalida(double distanciaSalida) {
        this.distanciaSalida = distanciaSalida;
    }

    /**
     * @return the heuristica
     */
    public double getHeuristica() {
        return heuristica;
    }

    /**
     * @param heuristica the heuristica to set
     */
    public void setHeuristica(double heuristica) {
        this.heuristica = heuristica;
    }

    /**
     * @return the funcion
     */
    public double getFuncion() {
        return funcion;
    }

    /**
     * @param funcion the funcion to set
     */
    public void setFuncion(double funcion) {
        this.funcion = funcion;
    }
}
