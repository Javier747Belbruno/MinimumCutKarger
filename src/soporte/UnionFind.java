package soporte;

/**
 * Una estructura para soporte de operaciones de Union - Find. La idea es poder
 * chequear en forma rápida si dos elementos pertenecen al mismo conjunto o 
 * grupo (o clase de equivalencia) y poder hacer uniones de grupos en forma 
 * rápida también.
 * 
 * Estrategia usada: equilibrado de pesos + compresión de caminos.
 * 
 * El equilibrado de pesos (o "unión por tamaños") consiste en que al hacer una 
 * unión, se deje como raiz (o lider) del nuevo grupo al valor raiz / lider del 
 * grupo de mayor cantidad de componentes (con lo cual deben hacerse menos 
 * actualizaciones de enlaces hijo / padre en el grupo de menor cantidad de 
 * componentes).
 * 
 * La compresión de caminos implica que al hacer una búsqueda de x, todos los 
 * elementos en el camino para llegar hasta x se ajusten para apuntar al mismo 
 * lider / raiz, y por lo tanto futuras búsquedas tendrán menos recorridos de 
 * camino interno.
 * 
 * Si se utiliza esta estrategia conjunta, una cantidad de m operaciones 
 * aleatorias de uniones y búsquedas insumirá un tiempo que en el peor caso 
 * será O(m * log(m)).
 * 
 * @author Ing. Valerio Frittelli.
 * @version Diciembre de 2012.
 */

public class UnionFind
{
    private int items[];   // un bosque de arboles, un arbol por cada grupo...
    private int groups;    // la cantidad de grupos distintos que estan contenidos...

    /**
     * Crea un objeto con capacidad máxima para 100 grupos.
     */
    public UnionFind()
    {
        this(100);
    }
    
    /**
     * Crea un objeto con capacidad máxima para n grupos iniciales.
     * @param n el numero inicial de objetos a unir/buscar.
     */
    public UnionFind(int n)
    {
        // si valor n no es válido, asumimos un tamaño de 100...
        int t = n;
        if(t <= 0) { t = 100; } 
        
        // creamos el vector y asignamos -1 en cada casilla...
        // ... el valor de cada casilla que represente una raiz /lider, es el 
        // negativo del tamaño del grupo... e inicialmente todos son raices.
        items = new int[t];
        for(int i = 0; i < t; i++) { items[i] = -1; }        
        
        // la cantidad inicial de grupos es igual al tamaño del vector...
        groups = t;
    }
    
    /**
     * Determina la cantidad de grupos diferentes que contiene la estructura.
     * @return la cantidad de grupos distintos que se han formado.
     */
    public int countGroups()
    {
        return groups;
    }
    
    /**
     * Retorna el identificador/indice del grupo al que pertenece el objeto idx.
     * Si idx no es un indice válido para un objeto (idx < 0) el metodo 
     * retornará -1. Si idx es valido, el valor retornado será >=0 (el índice 
     * del objeto raiz o lider del objeto idx).
     * @param idx el índice del objeto a buscar.
     * @return el iíndice del lider o raiz del grupo de idx.
     */
    public int find(int idx)
    {
        // aquí se aplica la compresión de caminos: se busca el objeto idx, 
        // y en caso de hallarlo se cambian todos los enlaces hacia arriba
        // para que todos apunten al lider.
        
        // cada casilla que corresponde a una raiz, almacena el tamaño de ese 
        // grupo, como un número negativo. Iinicialmente, todos son raices de 
        // un grupo de tamaño 1, por lo cual todos los casilleros inicialmente
        // valen -1.
        
        // a partir de allí, cada casillero que representa a un nodo hijo, 
        // cambia su valor para contener el indice de su padre o de su lider 
        // (no negativo)
        
        // si idx no es válido, retornar -1...
        if(idx < 0) { return -1; }
        
        // comenzar en idx, y ascender hacia su lider...
        // ... el lider tiene un valor negativo...
        int i = idx;
        while(items[i] >= 0) { i = items[i]; }
        
        // ... una vez encontrado el índice "i" del lider, recorrer otra vez y 
        // ajustar los padres de todos en el camino de ascenso, para que apunten 
        // a "i"...
        int ix = idx;
        while(items[ix] >= 0)
        {
            int aux = ix;
            ix = items[ix];
            items[aux] = i;
        }
        
        // ... finalmente, retornar el índice del lider...
        return i;       
    }
    
    /**
     * Une los grupos de los objetos cuyos índices son idx1 e idx2, formando un 
     * único nuevo grupo. El método retorna true si la union es válida y pudo 
     * hacerse, y retorna false si la union no pudo realizarse (alguno de los 
     * índices idx1 o idx2 era negativo, o los dos objetos ya estaban en el 
     * mismo grupo).
     * @param idx1 el índice del primer objeto de los grupos a unir.
     * @param idx2 el índice del segundo objeto de los grupos a unir.
     * @return true: la unión se realizo con exito.
     */
    public boolean union(int idx1, int idx2)
    {
        // buscar el lider de ambos objetos...
        // ... ambas búsquedas podrían haber hecho compresión de caminos...
        int lx1 = find(idx1);
        int lx2 = find(idx2);
        
        // ... si alguno no existe, o existen y tienen el mismo lider, retornar false...
        if(lx1 == -1 || lx2 == -1 || lx1 == lx2) { return false; }
        
        // ... caso contrario, proceder a la unión...
        // ... y aquí se aplica el equilibrado de pesos...
        if(items[lx2] < items[lx1])
        {
            // el grupo de idx2 tiene más componentes que el del idx1...
            // ... recordar que los tamaños están expresados como negativos...
            
            // sumar al grupo de idx2 el tamaño del grupo de idx1...
            items[lx2] += items[lx1];
            
            // ... y cambiar el lider del grupo de idx1...
            items[lx1] = lx2;
        }
        else
        {
            // caso contrario, lo mismo pero al revés...
            items[lx1] += items[lx2];
            items[lx2] = lx1;            
        }
              
        // ... la unión tuvo exito... 
        groups--;
        return true;
    }
    
    /**
     * Retorna una representación en forma de String de la estructura completa.
     */
    @Override
    public String toString()
    {
        StringBuilder r = new StringBuilder("Cantidad de grupos: " + groups + "\n[");
        
        for(int i = 0; i < items.length; i++)
        {
            r.append("(").append(i).append(" : ").append(items[i]).append( ")");
            if(i != items.length - 1) { r.append(" "); }
        }
        
        r.append("]");
        return r.toString();
    }
}
