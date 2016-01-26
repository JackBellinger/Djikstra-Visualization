package graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import pathfinders.PathFinder;
import simplegui.DrwImage;
import simplegui.GUIListener;
import simplegui.SimpleGUI;

public class Graph implements GUIListener {

    protected int graphSize;
    ArrayList<GraphNode> nodes; // node list
    GraphNode start, end;       // two special nodes in the graph
    DrwImage background;
    protected SimpleGUI sg = new SimpleGUI(800, 800, true);
    private PathFinder pathFinder;

    public void reactToButton1() {
        init();
    }

    public void reactToButton2() {
        int []path = pathFinder.findPath(this);
        showPath(path);

        int[][]nw = getNeighborsOfNode(4);
        for (int i=0; i<nw.length; i++){
            System.out.println(nw[i][0]+" "+nw[i][1]);
        }


    }

    public void reactToSwitch(boolean bln) {
    }

    public void reactToSlider(int i) {
    }

    // =========================================================================
    public Graph() {
        init();
        sg.labelButton1("New Graph");
        sg.labelButton2("Find Shortest Path");
        sg.registerToGUI(this);

    }

    private void init() {
        int size = sg.getSliderValue() / 10;
        background = new DrwImage("background.png");
        nodes = new ArrayList<GraphNode>(size * size);
        createRandomGraph(size);

        visualize();
    }

    private void createRandomGraph(int size) {
        graphSize = size;
        int index = 0;
        start = new GraphNode(0, 0, index++, Color.green, this);
        end = new GraphNode(size, size, index++, Color.green, this);
        nodes.add(start);
        nodes.add(end);

        // create random nodes
        for (int row = 1; row < size; row++) {
            for (int col = 1; col < size; col++) {
                if (Math.random() > 0.3) {
                    GraphNode g = new GraphNode(col, row, index++, Color.blue, this);
                    nodes.add(g);
                }
            }
        }

        // create edges
        // first connect all leftmost nodes to start
        LinkedList<GraphNode> c = getNodesOfColumn(1);
        for (GraphNode n : c) {
            int weight = (int) (Math.random() * 100 + 1);
            start.neighbors.add(n);
            start.edgeWeights.add(weight);
        }
        // connect all rightmost nodes to end
        c = getNodesOfColumn(size - 1);
        for (GraphNode n : c) {
            int weight = (int) (Math.random() * 100 + 1);
            n.neighbors.add(end);
            n.edgeWeights.add(weight);
        }
        // create random connections between neighboring columns
        for (int column = 1; column < size - 1; column++) {
            LinkedList<GraphNode> from = getNodesOfColumn(column);
            LinkedList<GraphNode> to = getNodesOfColumn(column + 1);

            for (GraphNode nF : from) {
                for (GraphNode nT : to) {
                    if (Math.random() > 0.5) {
                        int weight = (int) (Math.random() * 100 + 1);
                        nF.neighbors.add(nT);
                        nF.edgeWeights.add(weight);
                        nT.neighbors.add(nF);
                        nT.edgeWeights.add(weight);

                    }
                }
            }
        }
    }

    private LinkedList<GraphNode> getNodesOfColumn(int c) {
        LinkedList<GraphNode> l = new LinkedList<GraphNode>();
        for (GraphNode n : nodes) {
            if (n.positionX == c) {
                l.add(n);
            }
        }
        return (l);
    }

    private void visualize() {
        sg.eraseAllDrawables();
        sg.drawImage(background, 0, 0, -1, -1, "");
        sg.drawFilledBox(0, 0, sg.getWidth(), sg.getHeight(), Color.white, 0.5, "");

        for (GraphNode n : nodes) {
            n.visualize();
        }
    }

        // marks a path (sequence of nodes, given by index) in red
    // prints "invalid path" on SimpleGUI if path is, yes, invalid.
    private void showPath(int[] path) {
        boolean isValid = true;
        for (int gIndex = 0; gIndex < path.length - 1; gIndex++) {
            int startN = path[gIndex];
            int endN = path[gIndex + 1];
            // check if edge is part of the graph
            GraphNode gns = nodes.get(startN);
            isValid = false;
            for (GraphNode n : gns.neighbors) {
                if (n.index == endN) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                break;
            } else {
                GraphNode gne = nodes.get(endN);
                int distance = sg.getWidth() / (graphSize + 2);
                int size = distance / 6;
                size += distance;
                        int pxs = gns.positionX * distance + size;
                        int pys = gns.positionY * distance + size;
                        int pxe = gne.positionX * distance + size;
                        int pye = gne.positionY * distance + size;
                        sg.drawLine(pxs, pys, pxe, pye, Color.RED, 0.5, 3, "");
            }
        }
        if (!isValid) {
            System.out.println("INVALID PATH");
        }
    }

    // public methods ==========================================================
    public int[][] getNeighborsOfNode(int i) {
        GraphNode n = nodes.get(i);
        int[][] nw = new int[n.neighbors.size()][2];
        int nbi = 0;
        for (GraphNode nb : n.neighbors) {
            nw[nbi][0] = nb.index;
            nw[nbi][1] = n.edgeWeights.get(nbi);
            nbi++;
        }
        return (nw);
    }

    public int getNumberOfNodes(){
        return(nodes.size());
    }

    public void registerPathFinder(PathFinder p){
        pathFinder = p;
    }
}
