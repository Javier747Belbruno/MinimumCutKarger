/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import soporte.Node;
import soporte.UndirectedArc;
import soporte.UndirectedGraph;

/**
 *
 * @author Javier
 */
public class ManagerFile {

    private static String pathFile;
    
    ManagerFile(String graphtxt) {
       pathFile = graphtxt;  
    }

    public static UndirectedGraph createGraphFromTxt() throws FileNotFoundException{
        UndirectedGraph <String> ug1 = new UndirectedGraph<>(true);
        Scanner myReader = new Scanner(new File(pathFile));
        while (myReader.hasNextLine()) {
            String[] values = myReader.nextLine().split(" ");
            Node nodeValue = new Node(values[0]);
            ug1.add(nodeValue);
            UndirectedArc undirectedArc;
            Node<String> nodeAux;
            for (int i = 1; i < values.length; i++) {
                nodeAux = new Node(values[i]);
                undirectedArc = new UndirectedArc<String>(nodeValue, nodeAux);
                ug1.add(undirectedArc);
            }
        }
        return ug1;
    }
    
    
}
