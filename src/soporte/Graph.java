package soporte;

import java.util.LinkedList;

/**
 * Usada para representar un grafo implementado con listas de adyacencia. Un
 * objeto Graph contiene una lista de vértices o nodos, que contendrán objetos 
 * de la clase T. Un Graph contiene también una lista con todos los arcos o 
 * aristas del grafo, de modo de poder acceder a todos los arcos en tiempo 
 * lineal. 
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public abstract class Graph <T> implements Cloneable
{   
    // la lista de vértices...
    protected LinkedList < Node <T> > vertices;
    
    // la lista de arcos...
    protected LinkedList < Arc <T> > edges;
    
    // un flag para recordar si el grafo acepta o no arcos paralelos...
    protected boolean allow_parallel_arcs;
    
    /**
     * Crea un grafo con lista de vértices vacía, lista de arcos vacía y sin 
     * permitir arcos paralelos.
     */
    public Graph()
    {
        this(null, null, false);
    }
    
    /**
     * Crea un grafo con lista de vértices vacía y lista de arcos vacía. El 
     * grafo permite arcos paralelos si el parámetro p es true, y no los permite 
     * si p es false.
     * @param p true: se permiten arcos paralelos.
     */
    public Graph(boolean p)
    {
        this(null, null, p);
    }
    
    
    /**
     * Crea un grafo cuya lista de vértices será <b>v</b> y cuya lista de arcos
     * será <b>a</b>, sin permitir arcos paralelos. El método no controla si las
     * listas de entrada contienen objetos válidos. Si alguna de las dos 
     * listas de entrada es null, la lista correspondiente se creará vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     */
    public Graph(LinkedList< Node <T> > v, LinkedList< Arc <T> > a)
    {
        this(v, a, false);
    }
    
    /**
     * Crea un grafo cuya lista de vértices será <b>v</b> y cuya lista de arcos
     * será <b>a</b>. El parámetro p indica si el grafo aceptará arcos paralelos 
     * (p = true) o no (p = false). El método no controla si las listas de 
     * entrada contienen objetos válidos. Si alguna de las dos listas de entrada 
     * es null, la lista correspondiente se creará vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     * @param p true: el grafo acepta arcos paralelos.
     */
    public Graph(LinkedList< Node <T> > v, LinkedList< Arc <T> > a, boolean p)
    {
        if(v == null) { v = new LinkedList<> (); }
        this.vertices = v;
        
        if(a == null) { a = new LinkedList<> (); }
        this.edges = a;
        
        this.allow_parallel_arcs = p;
    }
    
    /**
     * Agrega un vértice con valor igual a <b>n</b> a la lista de vértices del 
     * grafo y retorna true si la operación tuvo éxito. El vértice no será 
     * insertado (y se retornará false) si n es null o si la lista de vértices 
     * ya contenía un vértice igual a n.
     * @param n el valor del vértice a insertar.
     * @return true si la inserción tuvo éxito.
     */
    public boolean add(T n)
    {
        if(n == null) { return false; }
        
        Node<T> nt = new Node<>(n);
        return this.add(nt);
    }
    
    /**
     * Agrega un vértice v con valor igual a la lista de vértices del 
     * grafo y retorna true si la operación tuvo éxito. El vértice no será 
     * insertado (y se retornará false) si v es null o si la lista de vértices 
     * ya contenía un vértice igual a v.
     * @param n el valor del vértice a insertar.
     * @return true si la inserción tuvo éxito.
     */
    public boolean add(Node<T> v)
    {
        if(v == null) { return false; }
        
        if(vertices.contains(v)) { return false; }
        return vertices.add(v);
    }
    
    /**
     * Agrega un arco <b>a</b> en la lista de arcos del grafo y retorna true si
     * la operación tuvo éxito. El arco no será insertado (y se retornará false
     * si <b>a</b> es null, o bien si alguno de los dos vértices unidos por el
     * arco <b>a</b> es null o alguno de ellos no existe en la lista de vértices
     * del grafo. Si el grafo acepta arcos paralelos, el arco <b>a</b> será 
     * agregado aún si hubiese un arco igual ya incluido en el grafo. Si el 
     * grafo no acepta arcos paralelos, el arco <b>a</b> no será agregado si ya 
     * existía en el grafo un arco igual.
     * @param a el arco a agregar en el grafo.
     * @return true si la inserción tuvo éxito.
     */
    public boolean add(Arc <T> a)
    {
        // si el arco es null, salir con false...
        if(a == null) { return false; }
        
        // si el nodo inicial es null, salir con false...
        Node <T> in = a.getInit();
        if(in == null) { return false; }
        
        // si el nodo inicial no es null y no está en el grafo, salir con false...
        int idxin = vertices.indexOf(in);
        if(idxin == -1) { return false; }
        
        // si el nodo final es null, salir con false...
        Node <T> en = a.getEnd();
        if(en == null) { return false; }
        
        // si el nodo final no es null y no está en el grafo, salir con false...
        int idxen = vertices.indexOf(en);
        if(idxen == -1) { return false; }
        
        // acceder a las listas de arcos de los vértices inicial y final...
        Node <T> ni = vertices.get(idxin);
        LinkedList < Arc <T> > lni = ni.getArcs();

        Node <T> ne = vertices.get(idxen);
        LinkedList < Arc <T> > lne = ne.getArcs();

        // si se aceptan arcos paralelos agregar el arco en todas las listas 
        // y salir con true... lo mismo si no se aceptan arcos paralelos y la
        // lista general de arcos no contiene a ese arco...
        if(this.allow_parallel_arcs || ! this.edges.contains(a))
        {
            lni.add(a);
            lne.add(a);
            this.edges.add(a);
            return true;
        }
                
        // en otro caso, salir con false...
        return false;
    }

    /**
     * Crea un arco con valor <b>n1</b> como vértice inicial y valor <b>n2</b> 
     * como vértice final, con peso igual a 0. Retorna true si el arco pudo 
     * crearse con éxito y false en caso contrario. Si alguno de los vértices no 
     * existía ya en el grafo, no será creado (y tampoco el arco), retornando 
     * false. Si el arco ya existía, será nuevamente agregado sólo si el grafo 
     * acepta arcos paralelos (en caso contrario, se retorna false sin volver a 
     * agregarlo).
     * @param n1 el valor del vértice inicial del arco a crear.
     * @param n2 el valord del vértice final del arco a crear.
     * @return true si el arco se agregó con éxito.
     */    
    public boolean addArc(T n1, T n2)
    {
        if(n1 == null || n2 == null) { return false; }
        return addArc(new Node<>(n1), new Node<>(n2), 0, false);
    }
    
    /**
     * Crea un arco con <b>in</b> como vértice inicial y <b>en</b> como vértice 
     * final, con peso igual a 0. Retorna true si el arco pudo crearse con éxito
     * y false en caso contrario. Si alguno de los vértices no existía ya en el 
     * grafo, no será creado (y tampoco el arco), retornando false. Si el arco 
     * ya existía, será nuevamente agregado sólo si el grafo acepta arcos 
     * paralelos (en caso contrario, se retorna false sin volver a agregarlo).
     * @param in el vértice inicial del arco a crear.
     * @param en el vértice final del arco a crear.
     * @return true si el arco se agregó con éxito.
     */    
    public boolean addArc( Node <T> in, Node <T> en )
    {
        return addArc( in, en, 0, false );
    }
    
    /**
     * Crea un arco con valor <b>n1</b> como vértice inicial y valor <b>n2</b> 
     * como vértice final, con peso igual a w. Retorna true si el arco pudo 
     * crearse con éxito y false en caso contrario. Si alguno de los vértices no 
     * existía ya en el grafo, no será creado (y tampoco el arco), retornando 
     * false. Si el arco ya existía, será nuevamente agregado sólo si el grafo 
     * acepta arcos paralelos (en caso contrario, se retorna false sin volver a 
     * agregarlo).
     * @param n1 el valor del vértice inicial del arco a crear.
     * @param n2 el valord del vértice final del arco a crear.
     * @param w el peso del arco a crear.
     * @return true si el arco se agregó con éxito.
     */ 
    public boolean addArc(T n1, T n2, int w)
    {
        if(n1 == null || n2 == null) { return false; }
        return addArc(new Node<>(n1), new Node<>(n2), w, false);    
    }
    
    /**
     * Crea un arco con <b>in</b> como vértice inicial y <b>en</b> como vértice 
     * final, con peso igual a w. Retorna true si el arco pudo crearse con éxito
     * y false en caso contrario. Si alguno de los vértices no existía ya en el 
     * grafo, no será creado (y tampoco el arco), retornando false. Si el arco 
     * ya existía, será nuevamente agregado sólo si el grafo acepta arcos 
     * paralelos (en caso contrario, se retorna false sin volver a agregarlo).
     * @param in el vértice inicial del arco a crear.
     * @param en el vértice final del arco a crear.
     * @param w el peso del arco a crear.
     * @return true si el arco se agregó con éxito.
     */ 
    public boolean addArc( Node <T> in, Node <T> en, int w )
    {
        return this.addArc( in, en, w, false );    
    }
    
    /**
     * Crea un arco con valor <b>n1</b> como vértice inicial y valor <b>n2</b> 
     * como vértice final, con peso igual a 0. Retorna true si el arco pudo 
     * crearse con éxito y false en caso contrario. El parámetro <b>create</b> 
     * indica si los vértices para <b>n1</b> y <b>n2</> deben crearse y 
     * agregarse al grafo en caso de no existir previamente. Si el arco ya 
     * existía, será nuevamente agregado sólo si el grafo acepta arcos paralelos 
     * (en caso contrario, se retorna false sin volver a agregarlo).
     * @param n1 el vértice inicial del arco a crear.
     * @param n2 el vértice final del arco a crear.
     * @param create true: deben crearse los vértices si no existían.
     * @return true si el arco se agregó con éxito.
     */ 
    public boolean addArc( T n1, T n2, boolean create )
    {
        if(n1 == null || n2 == null) { return false; }
        return addArc(new Node<>(n1), new Node<>(n2), 0, create); 
    }
    
    /**
     * Crea un arco con <b>in</b> como vértice inicial y <b>en</b> como vértice 
     * final, con peso igual a 0. Retorna true si el arco pudo crearse con éxito
     * y false en caso contrario. El parámetro <b>create</b> indica si los 
     * vértices para <b>in</b> y <b>en</> deben crearse y agregarse al grafo en 
     * caso de no existir previamente. Si el arco ya existía, será nuevamente 
     * agregado sólo si el grafo acepta arcos paralelos (en caso contrario, se 
     * retorna false sin volver a agregarlo).
     * @param in el vértice inicial del arco a crear.
     * @param en el vértice final del arco a crear.
     * @param create true: deben crearse los vértices si no existían.
     * @return true si el arco se agregó con éxito.
     */ 
    public boolean addArc( Node <T> in, Node <T> en, boolean create )
    {
        return this.addArc(in, en, 0, create);
    }
    
    /**
     * Crea un arco con valor <b>n1</b> como vértice inicial y valor <b>n2</b> 
     * como vértice final, con peso igual a w. Retorna true si el arco pudo 
     * crearse con éxito y false en caso contrario. El parámetro <b>create</b> 
     * indica si los vértices para <b>n1</b> y <b>n2</> deben crearse y 
     * agregarse al grafo en caso de no existir previamente. Si el arco ya 
     * existía, será nuevamente agregado sólo si el grafo acepta arcos paralelos 
     * (en caso contrario, se retorna false sin volver a agregarlo).
     * @param n1 el valor del vértice inicial del arco a crear.
     * @param n2 el valor del vértice final del arco a crear.
     * @param w el peso del arco a crear.
     * @param create true: agregar los vértices al grafo si no existían.
     * @return
     */
    public boolean addArc(T n1, T n2, int w, boolean create)
    {
        if(n1 == null || n2 == null) { return false; }
        return addArc(new Node<>(n1), new Node<>(n2), w, create);
    }
    
    /**
     * Crea un arco con <b>in</b> como vértice inicial y <b>en</b> como vértice 
     * final, con peso igual a w. Retorna true si el arco pudo crearse con éxito
     * y false en caso contrario. El parámetro <b>create</b> indica si los 
     * vértices <b>in</b> y <b>en</> deben crearse y agregarse al grafo en caso 
     * de no existir previamente. Si el arco ya existía, será nuevamente 
     * agregado sólo si el grafo acepta arcos paralelos (en caso contrario, se
     * retorna false sin volver a agregarlo).
     * @param in el vértice inicial del arco a crear.
     * @param en el vértice final del arco a crear.
     * @param w el peso del arco a crear.
     * @param create true: agregar los vértices al grafo si no existían.
     * @return true si el arco se agregó con éxito.
     */
    public boolean addArc( Node <T> in, Node <T> en, int w, boolean create )
    {
        // si alguno de los vértices de entrada es null, salir con false...
        if( in == null || en == null ) { return false; }        
        
        // si "in" no existe y no tenemos permiso de crearlo, salir con false...
        int idxin = vertices.indexOf(in);
        if( idxin == -1 && create == false) { return false; }
        
        // si "en" no existe y no tenemos permiso de crearlo, salir con false...
        int idxen = vertices.indexOf(en);
        if( idxen == -1 && create == false ) { return false; }
              
        // si llegué acá, es una de dos: 
        // 1) algún vertice no existe, y tengo permiso de agregarlo al grafo...
        // 2) algún vértice existe y no debe volver a agregarlo al grafo...
        // los if de abajo hacen eso, de forma que las referencias in y en salen
        // apuntando al vértice correcto: el agregado o el que ya estaba.
        if(idxin == -1) {this.add(in);} else {in = this.vertices.get(idxin);}
        if(idxen == -1) {this.add(en);} else {en = this.vertices.get(idxen);}
        
        // crear el arco, e intentar agregarlo con el método add(Arc<T> a)...
        Arc <T> arc = createArc(in, en, w);
        return this.add(arc);
    }
    
    /**
     * Retorna true si el grafo admite arcos paralelos.
     * @return true el si grafo admite arcos paralelos.
     */
    public boolean allow_Parallel_Arcs()
    {
        return this.allow_parallel_arcs;
    }
    
    /**
     * Redefinicion del metodo heredado desde Object. El grafo retornado 
     * contiene exactamente la misma estructura que el original, copiada en 
     * forma profunda: la referencia retornada por clone() apunta a un objeto 
     * nuevo, cuyo contenido es igual al grafo original (esto es: grafo.clone() 
     * != grafo)
     * @return una copia clonada del grafo original.
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Graph copy = (Graph) super.clone();
        
        copy.vertices = new LinkedList <> ();
        for(Node v : this.vertices)
        {
            copy.vertices.add(new Node(v.getValue()));
        }
        
        copy.edges = new LinkedList<> ();
        for(Arc a : this.edges)
        {
            Node <T> in = new Node(a.getInit().getValue());
            Node <T> en = new Node(a.getEnd().getValue());
            int w = a.getWeight();
            copy.addArc(in, en, w, false);           
        }
        
        return copy;
    }
    
    /**
     * Retorna la cantidad de arcos que tiene el grafo.
     * @return la cantidad de arcos del grafo.
     */
    public int countEdges()
    {
        return edges.size();
    }
    
    /**
     * Retorna la cantidad de vértices que tiene el grafo.
     * @return la cantidad de vértices del grafo.
     */
    public int countNodes()
    {
        return vertices.size();
    }
    
    /**
     * Crea un arco de la clase correcta según sea el tipo de grafo que invoca
     * al método (dirigido o no dirigido).
     * @param in el vértice inicial del arco.
     * @param en el vértice final del arco.
     * @param w el peso del arco.
     * @return el arco creado.
     */
    public abstract Arc <T> createArc(Node <T> in, Node <T> en, int w);
        
    /**
     * Retorna un arco del grafo, seleccionado aleatoriamente. Si el grafo no 
     * tiene arcos, el metodo retorna null.
     * @return un arco del grafo, seleccionado en forma aleatoria.
     */
    public Arc <T> getRandomArc()
    {
        int m = this.countEdges();
        if(m == 0) { return null; }
        
        int ri = (int)(Math.random() * m);
        return edges.get(ri);
    }
    
    /**
     * Retorna el índice del vértice que contiene al objeto x entrado como
     * parámetro, o -1 si x no está en la lista de vértices.
     * @param x el objeto a buscar en la lista de vértices.
     * @return el índice del vértice que contiene a x, 0 -1 si x no existe.
     */
    public int getVertexIndex(Node<String> x)
    {
        return this.vertices.indexOf(x);
    }
       
    public int getArcIndex(Arc<String> x)
    {
        return this.edges.indexOf(x);
    }
    /**
     * Retorna la cantidad de arcos incidentes (todos arcos que tienen al 
     * k-ésimo nodo como primero o como segundo vértice) al vértice en la 
     * posición k del grafo (o sea, el <b>grado</b> del k-ésimo vértice de la 
     * lista de vértices). Si k no es válido, retorna -1.
     * @return el grado del k-ésimo nodo.
     */
    public int grade(int k)
    {
        if( k < 0 || k >= vertices.size() ) { return -1; }
        return vertices.get(k).getArcs().size();
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
                if(! ve.equals(n.getValue())) { res.append(ve); } else { res.append(vi); }
                res.append("[").append(w).append("] ");
                //res.append(" ");
            }
            res.append("]");
        }
        res.append("\n]");
        return res.toString();
    }
}
