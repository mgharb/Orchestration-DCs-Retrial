
package Simulator;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


public class DirectedGraphNode {

    public final int id;
    private final Set<DirectedGraphNode> children = new LinkedHashSet<>();
    private final Set<DirectedGraphNode> parents  = new LinkedHashSet<>();
    private final Set<DirectedGraphNode> childrenWrapper = 
            Collections.<DirectedGraphNode>unmodifiableSet(children);
    private final Set<DirectedGraphNode> parentsWrapper  = 
            Collections.<DirectedGraphNode>unmodifiableSet(parents);

    public DirectedGraphNode(int id) {
        this.id = id;
    }

    public void addChild(DirectedGraphNode child) {
        Objects.requireNonNull(child, "The child node is null.");
        children.add(child);
        child.parents.add(this);

}

    public Set<DirectedGraphNode> children() {
        return childrenWrapper;
    }

    public Set<DirectedGraphNode> parents() {
        return parentsWrapper;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DirectedGraphNode)) {
            return false;
        }

        return id == ((DirectedGraphNode) o).id;
    }

    @Override
    public String toString() {

        return " "+id;
    }
}