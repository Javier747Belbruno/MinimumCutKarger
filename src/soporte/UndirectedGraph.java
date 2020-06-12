package soporte;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


public class UndirectedGraph<T> extends Graph<T> 
{
    /**
     * Crea un grafo no dirigido, con lista de vértices vacía, lista de arcos 
     * vacía y sin permitir arcos paralelos.
     */
    public UndirectedGraph() 
    {
    }
    
    /**
     * Crea un grafo no dirigido con lista de vértices vacía y lista de arcos 
     * vacía. El grafo permite arcos paralelos si el parámetro p es true, y no 
     * los permite si p es false.
     * @param p true: se permiten arcos paralelos.
     */
    public UndirectedGraph(boolean p)
    {
        super(p);
    }
            

    /**
     * Crea un grafo no dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>, sin permitir arcos paralelos. El método no 
     * controla si las listas de entrada contienen objetos válidos. Si alguna de 
     * las dos listas de entrada es null, la lista correspondiente se creará 
     * vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     */
    public UndirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a) 
    {
        super(v, a);
    }

    /**
     * Crea un grafo no dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>. El parámetro p indica si el grafo aceptará 
     * arcos paralelos (p = true) o no (p = false). El método no controla si las 
     * listas de entrada contienen objetos válidos. Si alguna de las dos listas 
     * de entrada es null, la lista correspondiente se creará vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     * @param p true: el grafo acepta arcos paralelos.
     */
    public UndirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a, boolean p) 
    {
        super(v, a, p);
    }
   
    /**
     * Crea un arco no dirigido con in como primer vértice y en como segundo 
     * vértice. El peso del arco será w. No comprueba si las referencias in y en
     * son null.
     * @param in el vértice inicial.
     * @param en el vértice final. 
     * @param w el peso del arco
     * @return el arco creado.
     */
    @Override
    public Arc<T> createArc(Node <T> in, Node <T> en, int w)
    {
        return new UndirectedArc(in, en, w);
    }
    
    /**
     * Busca un Arbol de Expansión Mínimo para el grafo, aplicando el algoritmo 
     * de Prim, y retorna el valor de la suma de los pesos de sus arcos. Se 
     * asume que los arcos pueden tener pesos negativos, cero o positivos 
     * indistintamente. El algoritmo utiliza un Heap para la extracción del arco 
     * de mínimo peso. Se asume que el grafo es conexo.
     * @return la suma de pesos del Arbol de Expansión Mínimo.
     */
    public long getMSTValue_Prim()
    {
        long suma = 0;
        
        // un subconjunto de vertices, con un solo vertice cualquiera... 
        LinkedList<Node<T>> x = new LinkedList<>();
        Node<T> s = vertices.getFirst();
        x.add(s);
        
        // un heap ascendente, con todos los arcos incidentes a ese primer unico nodo...
        Heap<Arc<T>> h = new Heap<>();
        LinkedList<Arc<T>> se = s.getArcs();
        for(Arc<T> e : se) { h.add(e); }
        
        // la lista de arcos que formaran el AEM, inicialmente vacía...
        LinkedList<Arc<T>> t = new LinkedList<>();
        
        // seguir hasta que x contenga todos los vértices del grafo original...
        // while( ! x.containsAll(vertices) )
        while(x.size() != vertices.size())
        {      
            // tomar del heap el arco con menor costo...
            // ... pero controlar que x no contenga a ambos vértices... (el grafo puede tener arcos paralelos...)
            Arc mce;
            boolean ok;
            do
            {
                mce = (Arc<T>) h.remove();
                Node n1 = mce.getInit();
                Node n2 = mce.getEnd();
                ok = (x.contains(n1) && !x.contains(n2)) || (x.contains(n2) && !x.contains(n1));
            }
            while( ! h.isEmpty() && ! ok );
 
            // si el heap se vació sin darme un arco bueno, corto el proceso y retorno la suma como estaba...
            if( ! ok ) { break; }
            
            // añadir el arco al AEM...
            t.add(mce);
            
            // añadir el otro nodo incidente de ese arco al conjunto x...
            Node<T> y = mce.getInit();
            if(x.contains(y)) { y = mce.getEnd(); }
            x.add(y);
            
            // actualizar el heap, agregando los arcos que conecten al nodo "y" con {vertices - x}...
            LinkedList<Arc<T>> ye = y.getArcs();
            for(Arc<T> e : ye)
            {
                // para el arco "e", tomar el extremo que no es "y" ("y" ya está en x)... 
                Node<T> ny = e.getInit();
                if(ny.equals(y)) { ny = e.getEnd(); }
                
                // si ese extremo "ny" no está en x, entonces "e" es un arco de cruce y debe agregarse al heap "h"...
                if(! x.contains(ny)) { h.add(e); }
            }
            
            // finalmente, actualizar el valor de la suma de pesos y regresar al ciclo...
            suma += mce.getWeight();
        }
        
        // ... por fin, devolver la suma de pesos del AEM
        return suma;
    }

    /**
     * Busca un Arbol de Expansión Mínimo para el grafo, aplicando el algoritmo 
     * de Kruskal, y retorna el valor de la suma de los pesos de sus arcos. Se 
     * asume que los arcos pueden tener pesos negativos, cero o positivos 
     * indistintamente. El algoritmo utiliza un Heap para la extracción del arco 
     * de mínimo peso. El grafo puede ser conexo o no.
     * @return la suma de pesos del Arbol de Expansión Mínimo.
     /*
    public long getMSTValue_Kruskal()
    {
        long suma = 0;
        
        // una copia ordenada de la lista de arcos... 
        LinkedList<Arc<T>> a0 = new LinkedList<>(this.edges);
        Collections.sort(a0);
        
        // la lista de arcos que formaran el AEM, inicialmente vacía...
        LinkedList<Arc<T>> t = new LinkedList<>();
        
        // una estructura UnionFind para todos los vértices, numerados en la 
        // misma forma en que vienen en la lista de vértices del grafo: el 
        // vértice 0 de la llista de vértices, será el elemento 0 en la 
        // UnionFind...
        int n = this.countNodes();
        UnionFind h = new UnionFind(n);        
        
        // seguir hasta que x contenga todos los vértices del grafo original...
        int m = this.countEdges();

        for(int k = 0; k < m && h.countGroups() != 1; k++ )
        {      
            // tomar (y remover) un arco ak desde la lista ordenada a0...
            // ... pero controlar que t no contenga ya a ese arco... (el 
            // grafo puede tener arcos paralelos...)
            Arc<T> ak;
            while(!a0.isEmpty() && t.contains(a0.getFirst()))
            {
                ak = a0.removeFirst();
            }
            
            // si la lista se vació sin darme un arco bueno, corto el proceso y 
            // retorno la suma como estaba...
            if( a0.isEmpty() ) { break; }
            
            // si no quedo vacia, su primer arco es el candidato...
            ak = a0.removeFirst();
            
            // controlar si ak NO produce un ciclo en t...
            Node<T> in = ak.getInit();
            Node<T> en = ak.getEnd();
            int idx1 = this.getVertexIndex(in);
            int idx2 = this.getVertexIndex(en);
            if(h.union(idx1, idx2)) 
            {
                // si la unión tuvo éxito (no se produce un ciclo), añadir el 
                // arco ak al AEM...
                t.add(ak);
                
                // actualizar el valor de la suma de pesos y regresar al ciclo...          
                suma += ak.getWeight();
            }
        }
        
        // ... por fin, devolver la suma de pesos del AEM
        return suma;
    }*/
    
    public long getMinimumCutValue_Karger() throws CloneNotSupportedException {
        long minimumCut = 0;
        double T = ( Math.pow(vertices.size(),2) * Math.log(vertices.size())) ;
        int i = 0;
        while(i<T){
            UndirectedGraph<String> g = (UndirectedGraph<String>)this.clone();
            while(g.vertices.size()>2){
                Arc<String> randomArc = (Arc<String>) g.getRandomArc();
                
                //Obtener Nodo Final y ser el que es contraido. Obtener Los arcos y pasarlos al .init
                Node<String> init = randomArc.init;
                Node<String> end = randomArc.end;
                
                for (Arc<String> arc : end.getArcs()) {
                    if(arc.end == end ){
                        arc.setEnd(randomArc.init);
                        if(!arc.isSelfLoop()){
                        randomArc.init.getArcs().add(arc);
                        }
                    }
                    if(arc.init == end ){
                        arc.setInit(randomArc.init);
                        if(!arc.isSelfLoop()){
                        randomArc.init.getArcs().add(arc);
                        }
                    }
                    if(arc.isSelfLoop()){
                        init.getArcs().remove(arc);
                        g.edges.remove(arc);
                    }   
                }
                init.setValue(randomArc.init.getValue()+","+end.getValue());
                g.vertices.remove(end); 
            }
            if(minimumCut==0 || g.edges.size() < minimumCut ){
                minimumCut = g.edges.size();
                System.out.println(g.edges.get(0));
            }
            i++;
        }
        return minimumCut;
    }
}

