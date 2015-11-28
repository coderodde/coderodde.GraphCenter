package net.coderodde.graph.center;

import net.coderodde.graph.DirectedGraphNode;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a map mapping each 
 * {@link net.coderodde.graph.center.DirectedGraphNode} to its eccentricity. 
 * Eccentricity of a node is its cost to the farthest nodes. The intuition is
 * following: choose a node, call it {code u}. Then compute all shortest paths 
 * from {@code u} to any other node in its connected component. The eccentricity
 * will of {@code u} is the cost of the longest shortest path.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6
 */
public final class DirectedGraphNodeEccentricityMap {
   
    private final Map<DirectedGraphNode, Double> map = new HashMap<>();
    
    public double get(DirectedGraphNode node) {
        return map.get(node);
    }
    
    public void put(DirectedGraphNode node, double eccentricity) {
        map.put(node, eccentricity);
    }
}
