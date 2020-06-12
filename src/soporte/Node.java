package soporte;

import java.util.LinkedList;

/**
 * Usada para representar los vértices o nodos de un grafo implementado con 
 * listas de adyancencia. Cada vértice contiene un objeto de la clase T, y 
 * mantiene una lista con todos los arcos o aristas incidentes a ese nodo (es 
 * decir, todos los arcos que comienzan o terminan en el nodo).
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */

public class Node <T>
{
    private T value;
    private LinkedList < Arc <T> > arcs;
    
    /**
     * Crea un Node conteniendo al objeto x, con lista de arcos vacía.
     * @param x el objeto a almacenar en el vértice.
     */
    public Node(T x)
    {
        this(x, null);
    }
    
    /**
     * Crea un Node conteniendo al objeto <b>x</b>. La lista de arcos será la 
     * lista que ingresa en la referencia <b>a</b>. El método no verifica si los 
     * arcos que están contenidos en <b>a</b> son válidos. Si la referencia 
     * <b>a</b> fuese null, la lista de arcos del vértice se crea vacía. 
     * 
     * @param x el objeto a almacenar en el vértice.
     * @param a la lista de arcos a asignar al vértice.
     * @throws NullPointerException si x es null.
     */
    public Node(T x, LinkedList <Arc <T> > a)
    {
        if(x == null) { throw new NullPointerException("Error: referencia nula para crear el vértice"); }
        
        value = x;
        if(a == null) { a = new LinkedList <> (); }
        arcs = a;
    }
    
    /**
     * Retorna el objeto contenido en el vértice representado.
     * @return el objeto contenido en el vértice.
     */
    public T getValue()
    {
        return value;
    }
    
    /**
     * Retorna la lista de arcos incidentes al vértice representado.
     * @return la lista de arcos incidentes al vértice.
     */
    public LinkedList < Arc <T> > getArcs()
    {
        return arcs;
    }
    
    
    /**
     * Cambia el objeto almaccenado en el vértice representado, pero sólo si la
     * referencia <b>x</b> no es null (en este caso, queda el objeto anterior, 
     * sin cambios).
     * @param x 
     */
    public void setValue(T x)
    {
        if(x != null) { value = x; }
    }
    
    /**
     * Cambia la lista de arcos del vértice, sin verificar si los nuevos arcos
     * son válidos. Si la referencia <b>a</b> a la nueva lista es null, el 
     * cambio no se efectúa y queda la lista anterior.
     * @param a la nueva lista de arcos para el vértice.
     */
    public void setArcs(LinkedList < Arc <T> > a)
    {
        if(a != null) { arcs = a; }
    }
    
    @Override
    public boolean equals(Object x)
    {
        if(x == null) { return false; }
        if(! (x instanceof Node)) { return false; }
        return ((Node<T>) x).value.equals(this.value);
    }
    
    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
    
    @Override
    public String toString()
    {
        return value.toString();
    }
}
