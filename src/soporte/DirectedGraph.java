
package soporte;

import java.util.LinkedList;


public class DirectedGraph<T> extends Graph<T> 
{

    /**
     * Crea un grafo dirigido, con lista de vértices vacía, lista de arcos 
     * vacía y sin permitir arcos paralelos.
     */
    public DirectedGraph() 
    {
    }
    
    /**
     * Crea un grafo dirigido con lista de vértices vacía y lista de arcos 
     * vacía. El grafo permite arcos paralelos si el parámetro p es true, y no 
     * los permite si p es false.
     * @param p true: se permiten arcos paralelos.
     */
    public DirectedGraph(boolean p)
    {
        super(p);
    }

    /**
     * Crea un grafo dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>, sin permitir arcos paralelos. El método no 
     * controla si las listas de entrada contienen objetos válidos. Si alguna de 
     * las dos listas de entrada es null, la lista correspondiente se creará 
     * vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     */
    public DirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a) 
    {
        super(v, a);
    }

    /**
     * Crea un grafo dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>. El parámetro p indica si el grafo aceptará 
     * arcos paralelos (p = true) o no (p = false). El método no controla si las 
     * listas de entrada contienen objetos válidos. Si alguna de las dos listas 
     * de entrada es null, la lista correspondiente se creará vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     * @param p true: el grafo acepta arcos paralelos.
     */
    public DirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a, boolean p) 
    {
        super(v, a, p);
    }
    
    /**
     * Crea un arco dirigido con in como vértice de partida y en como vértice 
     * de llegada. El peso del arco será w. No comprueba si las referencias in y
     * en son null.
     * @param in el vértice de partida.
     * @param en el vértice de llegada. 
     * @param w el peso del arco
     * @return el arco creado.
     */
    @Override
    public Arc<T> createArc(Node <T> in, Node <T> en, int w)
    {
        return new DirectedArc(in, en, w);
    }
    
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder("[");
        for(int i = 0; i < vertices.size(); i++)
        {
            Node n = vertices.get(i);
            res.append("\n\t").append(n.getValue()).append(":\t[ ");
            LinkedList < Arc <T> > a = n.getArcs();
            for(int j = 0; j < a.size(); j++)
            {
                Arc <T> e = a.get(j);
                T vi = e.getInit().getValue();
                T ve = e.getEnd().getValue();
                int w = e.getWeight();
                if(vi.equals(n.getValue())) 
                { 
                    res.append(ve);  
                    res.append("[").append(w).append("] ");
                    //res.append(" ");
                }
            }
            res.append("]");
        }
        res.append("\n]");
        return res.toString();
    }
}
