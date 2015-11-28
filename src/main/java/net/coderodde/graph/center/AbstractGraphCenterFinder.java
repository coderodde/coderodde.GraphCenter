package net.coderodde.graph.center;

import net.coderodde.graph.DirectedGraphWeightFunction;
import net.coderodde.graph.DirectedGraphNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class defines the API and shared functionality for graph center finder
 * algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6
 */
public abstract class AbstractGraphCenterFinder {
   
    public abstract GraphCenterData 
        findCenterNodes(DirectedGraphNode connectedComponentRepresentative,
                        DirectedGraphWeightFunction weightFunction);
        
    protected List<DirectedGraphNode> 
        findConnectedComponent(
                DirectedGraphNode connectedComponentRepresentative) {
        Deque<DirectedGraphNode> queue = new ArrayDeque<>();
        Set<DirectedGraphNode> visited = new HashSet<>();
        
        queue.addLast(connectedComponentRepresentative);
        visited.add(connectedComponentRepresentative);
        
        while (!queue.isEmpty()) {
            DirectedGraphNode current = queue.removeFirst();
            
            for (DirectedGraphNode child : current.children()) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    queue.addLast(child);
                }
            }
            
            for (DirectedGraphNode parent : current.parents()) {
                if (!visited.contains(parent)) {
                    visited.add(parent);
                    queue.addLast(parent);
                }
            }
        }
        
        return new ArrayList<>(visited);
    }
}
