package net.coderodde.graph.center.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.graph.center.AbstractGraphCenterFinder;
import net.coderodde.graph.DirectedGraphNode;
import net.coderodde.graph.center.DirectedGraphNodeEccentricityMap;
import net.coderodde.graph.DirectedGraphWeightFunction;
import net.coderodde.graph.center.GraphCenterData;
import net.coderodde.util.BinaryHeap;

/**
 * This class implements the most naive graph center algorithm: it runs 
 * Dijkstra's algorithm from each node with any heuristics or pruning.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Nov 27, 2015)
 */
public class GraphCenterFinderV1 extends AbstractGraphCenterFinder {

    private BinaryHeap<DirectedGraphNode> OPEN;
    private Set<DirectedGraphNode> CLOSED;
    private Map<DirectedGraphNode, Double> DISTANCES;
    private List<DirectedGraphNode> connectedComponent;
    private DirectedGraphWeightFunction weightFunction;
    
    public GraphCenterFinderV1() {
        
    }
    
    private GraphCenterFinderV1
        (DirectedGraphNode connectedComponentRepresentative,
         DirectedGraphWeightFunction weightFunction) {
        connectedComponent = 
                findConnectedComponent(connectedComponentRepresentative);
        
        int connectedComponentSize = connectedComponent.size();
        
        OPEN = new BinaryHeap<>(connectedComponentSize);
        CLOSED = new HashSet<>(connectedComponentSize);
        DISTANCES = new HashMap<>(connectedComponentSize);
        
        this.weightFunction = weightFunction;
    }
    
    @Override
    public GraphCenterData 
        findCenterNodes(DirectedGraphNode connectedComponentRepresentative,
                        DirectedGraphWeightFunction weightFunction) {
            
        GraphCenterFinderV1 finderState = 
                new GraphCenterFinderV1(connectedComponentRepresentative,
                                        weightFunction);
        
        double minimumEccentricity = Double.POSITIVE_INFINITY;
        List<DirectedGraphNode> centerNodeList = new ArrayList<>();
        DirectedGraphNodeEccentricityMap eccentricityMap =
                new DirectedGraphNodeEccentricityMap();
        
        for (DirectedGraphNode currentNode : finderState.connectedComponent) {
            double currentNodeEccentricity = 
                    finderState.findEccentricityFrom(currentNode);
            
            eccentricityMap.put(currentNode, currentNodeEccentricity);
            
            if (minimumEccentricity > currentNodeEccentricity) {
                minimumEccentricity = currentNodeEccentricity;
                centerNodeList.clear();
                centerNodeList.add(currentNode);
            } else if (currentNodeEccentricity == minimumEccentricity) {
                centerNodeList.add(currentNode);
            }
        }
        
        return new GraphCenterData(eccentricityMap, centerNodeList);
    }
        
    private double weight(DirectedGraphNode tail, DirectedGraphNode head) {
        return weightFunction.get(tail, head);
    }
        
    private double findEccentricityFrom(DirectedGraphNode node) {
        OPEN.clear();
        CLOSED.clear();
        DISTANCES.clear();
            
        OPEN.add(node, 0.0);
        DISTANCES.put(node, 0.0);
        
        double maximumDistance = 0.0;
        
        while (!OPEN.isEmpty()) {
            DirectedGraphNode current = OPEN.extractMinimum();
            
            CLOSED.add(current);
            
            for (DirectedGraphNode child : current.children()) {
                if (!CLOSED.contains(child)) {
                    double tentativeDistance = 
                            DISTANCES.get(current) + weight(current, child);
                    
                    if (!OPEN.contains(child)) {
                        OPEN.add(child, tentativeDistance);
                        DISTANCES.put(child, tentativeDistance);
                        
                        if (maximumDistance < tentativeDistance) {
                            maximumDistance = tentativeDistance;
                        }
                    } else if (DISTANCES.get(child) > tentativeDistance) {
                        DISTANCES.put(child, tentativeDistance);
                        OPEN.decreasePriority(child, tentativeDistance);
                        
                        if (maximumDistance < tentativeDistance) {
                            maximumDistance = tentativeDistance;
                        }
                    }
                }
            }
        }
        
        return maximumDistance;
    }
}
