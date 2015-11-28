package net.coderodde.graph;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements a directed graph node.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6
 */
public final class DirectedGraphNode {
   
    private final int id;
    
    private final Set<DirectedGraphNode> children = new LinkedHashSet<>();
    private final Set<DirectedGraphNode> childrenWrapper = 
            Collections.<DirectedGraphNode>unmodifiableSet(children);
    
    private final Set<DirectedGraphNode> parents = new LinkedHashSet<>();
    private final Set<DirectedGraphNode> parentsWrapper =
            Collections.<DirectedGraphNode>unmodifiableSet(parents);
    
    private String str;
    
    public DirectedGraphNode(int id) {
        this.id = id;
    }
    
    public void addChild(DirectedGraphNode child) {
        Objects.requireNonNull(child, "The child node is null.");
        this.children.add(child);
        child.parents.add(this);
    }
    
    public boolean hasChild(DirectedGraphNode child) {
        Objects.requireNonNull(child, "The input child node is null.");
        return children.contains(child);
    }
    
    public void removeChild(DirectedGraphNode child) {
        if (children.contains(child)) {
            children.remove(child);
            child.parents.remove(this);
        }
    }
    
    public Set<DirectedGraphNode> children() {
        return childrenWrapper;
    }
    
    public Set<DirectedGraphNode> parents() {
        return parentsWrapper;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!getClass().equals(o.getClass())) {
            return false;
        }
        
        return id == ((DirectedGraphNode) o).id;
    }
    
    @Override
    public String toString() {
        if (str == null) {
            str = "[DirectedGraphNode " + id + "]";
        }
        
        return str;
    }
}
