package smartrobot;

//////////////////////////////////////////////////////////////////
// Lista lineal circular simplemente enlazada
//
public class CListaCircularSE
{
  // ultimo: referencia el ultimo elemento.
  // ultimo.siguiente referencia al primer elemento de la lista.
  private CElemento ultimo = null;

  // Elemento de una lista lineal circular simplemente enlazada
  private class CElemento
  {
    // Atributos
    private Object datos;        // referencia a los datos
    private CElemento siguiente; // siguiente elemento
    
    // M�todos
    private CElemento() {} // constructor
    
    private CElemento(Object d, CElemento s) // constructor
    {
      datos = d;
      siguiente = s;
    }
  }

  public CListaCircularSE() {} // constructor
  
  public int tamano()
  {
    // Devuelve el n�mero de elementos de la lista
    if (ultimo == null) return 0;
    CElemento q = ultimo.siguiente; // primer elemento
    int n = 1;                      // n�mero de elementos
    while (q != ultimo)
    {
      n++;
      q = q.siguiente;
    }
    return n;
  }
  
  public void anadirAlPrincipio(Object obj)
  {
    // A�ade un elemento al principio de la lista.
    // Crear el nuevo elemento.
    CElemento q = new CElemento(obj, null);

    if( ultimo != null ) // existe una lista
    {         
      q.siguiente = ultimo.siguiente;
      ultimo.siguiente =  q;
    }    
    else  // inserci�n del primer elemento
    {
      ultimo = q;
      ultimo.siguiente = q;
    }
  }
  
  public void anadirAlFinal(Object obj)
  {
    // A�ade un elemento al final de la lista.
    // Por lo tanto, ultimo referenciar� este nuevo elemento.
    // Crear el nuevo elemento.
    CElemento q = new CElemento(obj, null);
      
    if( ultimo != null ) // existe una lista
    {       
      q.siguiente = ultimo.siguiente;
      ultimo = ultimo.siguiente =  q;
    }      
    else  // inserci�n del primer elemento
    {
      ultimo = q;
      ultimo.siguiente = q;
    }
  }
  
  public Object borrar()
  {
    // Devuelve una referencia a los datos del primer elemento de
    // la lista y borra este elemento.
    if( ultimo == null )
    {
      System.err.println( "Lista vac�a\n" );
      return null;
    }

    CElemento q = ultimo.siguiente;
    Object obj = q.datos;

    if( q == ultimo )
      ultimo = null;
    else
      ultimo.siguiente = q.siguiente;
    
    return obj;
    // El elemento referenciado por q es enviado a la basura, al
    // quedar desreferenciado cuando finaliza este m�todo por ser
    // q una variable local.
  }
  
  public Object obtener(int i)
  {
    // Obtener el elemento de la posici�n i
    int numeroDeElementos = tamano();
    if (i >= numeroDeElementos || i < 0)
      return null;
    
    CElemento q = ultimo.siguiente; // primer elemento
    // Posicionarse en el elemento i
    for (int n = 0; n < i; n++)
      q = q.siguiente;
    
    // Retornar los datos
    return q.datos;
  }

  public Object eliminar(int i){
    // Obtener el elemento de la posici�n i
    int numeroDeElementos = tamano();
    if (i >= numeroDeElementos || i < 0){
        return null;
    }

    CElemento q = ultimo.siguiente; // primer elemento
    //CElemento p = q; //

    // Posicionarse en el elemento i
    for (int n = 0; n < i-1; n++){
       q= q.siguiente;       
    }
    
    if(q.siguiente==ultimo){
    ultimo=q;
    }
    //p=q.siguiente;
    q.siguiente=q.siguiente.siguiente;
    //q.siguiente = p.siguiente;
    return q.datos;

    // Retornar los datos
    //return q.datos;
  }

}
//////////////////////////////////////////////////////////////////
