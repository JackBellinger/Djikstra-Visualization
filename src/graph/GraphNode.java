/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author mrsofty
 */
public class GraphNode {
    int positionX, positionY;
    int index;      // refers to index in adjacency matrix
    Color color;
    ArrayList<GraphNode> neighbors;
    ArrayList<Integer> edgeWeights;
    private Graph graph;

    protected GraphNode(int x, int y, int index, Color c, Graph g) {
        graph = g;
        positionX = x;
        positionY = y;
        this.index = index;
        color = c;
        neighbors = new ArrayList<GraphNode>();
        edgeWeights = new ArrayList<Integer>();
    }

    protected void visualize() {
        int distance = graph.sg.getWidth() / (graph.graphSize + 2);
        int size = distance / 3;
        graph.sg.drawFilledEllipse(positionX * distance + distance, positionY * distance + distance, size, size, color, 0.3, "");
        graph.sg.drawEllipse(positionX * distance + distance, positionY * distance + distance, distance / 3, distance / 3, color, 0.5, 2, "");
        size /= 2;
        graph.sg.drawText(index + "", positionX * distance + distance + size, positionY * distance + distance + size, color, 1.0, "");

        // draw edges to all neighbors with higher positionX
        size += distance;
        int count = 0;
        for (GraphNode n : neighbors) {
            if (n.positionX > this.positionX) {
                int pxs = positionX * distance + size;
                int pys = positionY * distance + size;
                int pxe = n.positionX * distance + size;
                int pye = n.positionY * distance + size;
                graph.sg.drawLine(pxs, pys, pxe, pye, Color.BLACK, 0.5, 1, "");
                int textX = (int) Math.round(pxs + (pxe - pxs) / 5.0);
                int textY = (int) Math.round(pys + (pye - pys) / 5.0);

                graph.sg.drawText(edgeWeights.get(count) + "", textX, textY, Color.black, 1.0, null);
            }
            count++;
        }
    }
}
