package net.coderodde.graph.center.support;

import net.coderodde.graph.DirectedGraphNode;
import net.coderodde.graph.DirectedGraphWeightFunction;
import net.coderodde.graph.center.AbstractGraphCenterFinder;
import net.coderodde.graph.center.GraphCenterData;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphCenterFinderV1Test {

    private final DirectedGraphNode n1 = new DirectedGraphNode(1);
    private final DirectedGraphNode n2 = new DirectedGraphNode(2);
    private final DirectedGraphNode n3 = new DirectedGraphNode(3);
    private final DirectedGraphNode n4 = new DirectedGraphNode(4);
    private final DirectedGraphNode n5 = new DirectedGraphNode(5);
    private final DirectedGraphNode n6 = new DirectedGraphNode(6);
    private final DirectedGraphNode n7 = new DirectedGraphNode(7);
    
    private final DirectedGraphWeightFunction wf =
              new DirectedGraphWeightFunction();
    
    private final AbstractGraphCenterFinder finder = new GraphCenterFinderV1();
    
    private GraphCenterData data;
    
    @Test
    public void testFindCenterNodes() {
        data = finder.findCenterNodes(n1, wf);
        
        assertEquals(1, data.getCenterNodeList().size());
        assertEquals(n1, data.getCenterNodeList().get(0));
        assertEquals(0.0, data.getEccentricityMap().get(n1), 0.001);
        
        // n1 -- 2 --> n2
        n1.addChild(n2);
        wf.put(n1, n2, 2.0);
        
        data = finder.findCenterNodes(n1, wf);
        
        assertEquals(1, data.getCenterNodeList().size());
        assertEquals(n2, data.getCenterNodeList().get(0));
        assertEquals(2.0, data.getEccentricityMap().get(n1), 0.001);
        
        n2.addChild(n1);
        wf.put(n2, n1, 1.5);
        
        data = finder.findCenterNodes(n1, wf);
        
        assertEquals(1, data.getCenterNodeList().size());
        assertEquals(n2, data.getCenterNodeList().get(0));
        assertEquals(1.5, data.getEccentricityMap().get(n2), 0.001);
        
        data = finder.findCenterNodes(n2, wf);
        
        assertEquals(1, data.getCenterNodeList().size());
        assertEquals(n2, data.getCenterNodeList().get(0));
        assertEquals(1.5, data.getEccentricityMap().get(n2), 0.001);
        
        wf.put(n1, n2, 1.0);
        wf.put(n2, n1, 1.0);
        
        n2.addChild(n3); wf.put(n2, n3, 3.0);
        n3.addChild(n2); wf.put(n3, n2, 3.0);
        
        n2.addChild(n4); wf.put(n2, n4, 2.0);
        n4.addChild(n2); wf.put(n4, n2, 2.0);
        
        n3.addChild(n4); wf.put(n3, n4, 5.0);
        n4.addChild(n3); wf.put(n4, n3, 5.0);
        
        n3.addChild(n5); wf.put(n3, n5, 2.0);
        n5.addChild(n3); wf.put(n5, n3, 2.0);
        
        n4.addChild(n6); wf.put(n4, n6, 3.0);
        n6.addChild(n4); wf.put(n6, n4, 3.0);
        
        data = finder.findCenterNodes(n4, wf);
        
        assertEquals(1, data.getCenterNodeList().size());
        assertEquals(n2, data.getCenterNodeList().get(0));
        assertEquals(5.0, data.getEccentricityMap().get(n2), 0.001);
        
        wf.put(n3, n4, 3.0);
        wf.put(n4, n3, 3.0);
        
        data = finder.findCenterNodes(n5, wf);
        
        assertEquals(2, data.getCenterNodeList().size());
        assertTrue(data.getCenterNodeList().contains(n2));
        assertTrue(data.getCenterNodeList().contains(n4));
        assertEquals(5.0, data.getEccentricityMap().get(n2), 0.001);
        assertEquals(5.0, data.getEccentricityMap().get(n4), 0.001);
    }
}
