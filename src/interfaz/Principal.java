package interfaz;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import soporte.*;

/**
 * Una clase para contener un main de prueba para la implementación de grafos
 * por listas de adyancencia.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public class Principal 
{
    public static void main(String args[]){   
        ManagerFile mf = new ManagerFile("graph.txt");
        UndirectedGraph <String> ug1 = null;
        try {
            ug1 = ManagerFile.createGraphFromTxt();
        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        System.out.println("Representacion del Grafo");
        System.out.println(ug1);
        System.out.println();
        try {
            System.out.println("Grafo 1: Valor del MCV (Karger): " + ug1.getMinimumCutValue_Karger());
        } catch (CloneNotSupportedException ex) {
            System.out.println("Error " + ex.getMessage());
        }      
    }
}
