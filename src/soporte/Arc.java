package soporte;

import java.util.Objects;

/**
 * Usada para representar un arco o arista en un grafo implementado mediante 
 * listas de adyancencia. Un objeto Arc une dos vértices, representados con 
 * objetos de la clase Node, y cada Node contiene una referencia a un objeto de
 * la clase T. La clase Arc es abstracta, pues queda para sus derivadas el 
 * manejo de la direccionalidad del arco representado. En las derivadas de la 
 * clase Arc, si el arco es dirigido el Node <b>init</b> debería ser usado como 
 * vértice de partida del arco, y el Node <b>end</b> debería tomarse como el 
 * Node de llegada del arco. Si el grafo es no dirigido, en la derivada que 
 * debería ser indistinto el uso de los vértices <b>init</b> y <b>end</b>. 
 * 
 * Todo arco admite un peso, que por razones de sencillez se supone entero. Si 
 * el grafo fuese no ponderado, simplemente se debe ignorar el peso contenido en 
 * cada arco.
 *
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public abstract class Arc <T> implements Comparable< Arc<T> >
{
    // el vértice de partida (si el arco es dirigido).
    protected Node<T> init;
    
    // el vértice de llegada (si el arco es dirigido).    
    protected Node<T> end;
    
    // el peso, si es necesario.
    protected int weight;  
    
    /**
     * Crea un arco uniendo los vértices in y en, con peso 0.
     * @param in el primero de los vértices a unir.
     * @param en el segundo de los vértices a unir.
     * @throws NullPointerException si in o en es null.
     */
    public Arc(Node <T> in, Node <T> en)
    {
        this(in, en, 0);
    }
    
    /**
     * Crea un arco uniendo los vértices in y en, y asignando un peso w.
     * @param in el primero de los vértices a unir.
     * @param en el segundo de los vértices a unir.
     * @param w el peso del arco a crear.
     * @throws NullPointerException si in o en es null.
     */
    public Arc(Node in, Node en, int w)
    {
        if(in == null) { throw new NullPointerException( "Error: inicio de arco no definido..." ); }
        if(en == null) { throw new NullPointerException( "Error: final de arco no definido..." ); }
        init = in;
        end = en;
        weight = w;
    }

    /**
     * Usado para establecer una relación de orden entre dos arcos, en base al
     * valor del peso de esos arcos. Este método no debería ser usado para hacer
     * el test de igualdad entre dos arcos (si necesita saber si dos arcos son
     * iguales en un contexto en el que no requiera una relación de orden, use 
     * el método equals()). 
     * @param o el segundo arco a comparar
     * @return 1  si el peso de this es mayor al peso de o.
     *         0  si los pesos son iguales.
     *         -1 si el peso de this es menor que el de o.
     */
    @Override
    public int compareTo(Arc<T> o) 
    {
        return this.weight - o.weight;
    }
    
    /**
     * Retorna el vértice de partida (o el primer vértice) del arco.
     * @return el primer vértice del arco.
     */
    public Node <T> getInit()
    {
        return init;
    }
    
    /**
     * Retorna el vértice de llegada (o el segundo vértice) del arco.
     * @return el segundo vértice del arco.
     */    
    public Node <T> getEnd()
    {
        return end;
    }
    
    /**
     * Retorna el peso del arco.
     * @return el peso del arco.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Cambia el vértice de partida (o primer vértice) del arco, pero sólo si 
     * <b>in</b> no es null.
     * @param in el nuevo primer vértice.
     */
    public void setInit(Node <T> in)
    {
        if( in != null ) { init = in; }
    }
    
    /**
     * Cambia el vértice de llegada (o segundo vértice) del arco, pero sólo si 
     * <b>en</b> no es null.
     * @param en el nuevo primer vértice.
     */
    public void setEnd(Node <T> en)
    {
        if( en != null ) { end = en; }
    }
    
    /**
     * Cambia el peso del arco.
     * @param w el nuevo peso.
     */
    public void setWeight(int w)
    {
        weight = w;
    }   
    
    /**
     * Determina si el arco representa un auto ciclo (true) o no (false). 
     * @return true si el arco representa un auto ciclo.
     */
    public boolean isSelfLoop()
    {
        return (init.equals(end));
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.init);
        hash = 53 * hash + Objects.hashCode(this.end);
        hash = 53 * hash + this.weight;
        return hash;
    }
    
    /**
     * Retorna una cadena de la forma (x, y [p]) donde "x" es la conversión a
     * String del primer vértice del arco, "y" es la conversión a String del
     * segundo vértice, y p es el peso del arco.
     * @return la conversión a String del arco.
     */
    @Override
    public String toString()
    {
        return "( " + init.getValue() + ", " + end.getValue() + " [" + this.weight + "] )";
    }
}
