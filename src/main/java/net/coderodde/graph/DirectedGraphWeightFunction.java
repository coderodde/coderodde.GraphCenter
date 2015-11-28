package net.coderodde.graph;

import net.coderodde.graph.DirectedGraphNode;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a directed graph weight function.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 
 */
public class DirectedGraphWeightFunction {
   
    private final Map<DirectedGraphNode, Map<DirectedGraphNode, Double>> map =
            new HashMap<>();
    
    public void put(DirectedGraphNode tail,
                    DirectedGraphNode head,
                    double weight) {
        map.putIfAbsent(tail, new HashMap<DirectedGraphNode, Double>());
        map.get(tail).put(head, weight);
    }
    
    public double get(DirectedGraphNode tail, DirectedGraphNode head) {
        return map.get(tail).get(head);
    }
}
