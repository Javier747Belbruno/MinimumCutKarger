package soporte;

/**
 * Usada para representar arcos dirigidos en la implementación de grafos 
 * mediante listas de adyacencia. Esencialmente, la clase DirectedArc sólo 
 * implementa una redefinición del método equals() acorde al hecho de ser un
 * arco dirigido: los arcos A y B sólo serán considerados iguales si sus pesos 
 * son iguales y se cumple la siguiente condición:
 * 
 * (A.init == B.init && A.end == B.end)
 *
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public class DirectedArc<T> extends Arc<T>
{
    /**
     * Crea un arco dirigido con peso 0, y vértices inicial y final iguales a
     * in y en.
     * @param in el vértice de partida del arco.
     * @param en el vértice de llegada del arco.
     * @throws NullPointerException si in o en es null.
     */
    public DirectedArc(Node<T> in, Node<T> en) 
    {
        super(in, en);
    }

    /**
     * Crea un arco dirigido con peso w, y vértices inicial y final iguales a
     * in y en.
     * @param in el vértice de partida del arco.
     * @param en el vértice de llegada del arco.
     * @param w el peso del arco.
     * @throws NullPointerException si in o en es null.
     */
    public DirectedArc(Node in, Node en, int w) 
    {
        super(in, en, w);
    }  
      
    /**
     * Determina si dos arcos son iguales. Comprueba que los pesos de ambos 
     * arcos sean iguales, y que los nodos de inicio y fin también lo sean. Si 
     * los pesos son iguales, los arcos A y B será tomados como iguales sólo si 
     * se cumple que:
     * 
     *          (A.init == B.init && A.end == B.end) 
     * 
     * @param x el segundo arco para hacer la comparación.
     * #return true si los arcos son iguales.
     */
    @Override
    public boolean equals(Object x)
    {
        if(x == null) { return false; }
        if( ! (x instanceof Arc) ) { return false; }
        
        Arc <T> p = null; 
        try
        {
            p = (Arc <T>) x;
        }
        catch(ClassCastException e)
        {
            return false;
        }
        
        if(this.weight != p.weight) { return false; }
        return (p.init.equals(this.init) && p.end.equals(this.end));
    }
}
