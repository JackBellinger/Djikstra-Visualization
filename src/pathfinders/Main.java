package pathfinders;

import graph.Graph;
import simplegui.SimpleGUI;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        Graph g = new Graph();
        Dijkstra d = new Dijkstra();
        g.registerPathFinder(d);
    }
}
