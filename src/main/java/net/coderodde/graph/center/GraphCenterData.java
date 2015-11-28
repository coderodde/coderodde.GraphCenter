package net.coderodde.graph.center;

import net.coderodde.graph.DirectedGraphNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class holds all the necessary information for describing graph centers.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 
 */
public class GraphCenterData {
   
    private final DirectedGraphNodeEccentricityMap eccentricityMap;
    private final List<DirectedGraphNode> centerNodeList;
    
    public GraphCenterData(DirectedGraphNodeEccentricityMap eccentricityMap,
                           List<DirectedGraphNode> centerNodeList) {
        Objects.requireNonNull(eccentricityMap, 
                               "The input eccentricity map is null.");
        Objects.requireNonNull(centerNodeList, "The center node list is null.");
        
        this.eccentricityMap = eccentricityMap;
        this.centerNodeList = Collections.<DirectedGraphNode>
                              unmodifiableList(
                                      new ArrayList<>(centerNodeList));
    }
    
    public DirectedGraphNodeEccentricityMap getEccentricityMap() {
        return eccentricityMap;
    }
    
    public List<DirectedGraphNode> getCenterNodeList() {
        return centerNodeList;
    }
}
