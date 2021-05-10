package Simulator;

import java.util.List;

public abstract class AbstractKShortestPathFinder {

    public abstract List<Path>
         findShortestPaths(DirectedGraphNode source,
                           DirectedGraphNode target,
                           DirectedGraphWeightFunction weightFunction,
                           int k);

    protected void checkK(int k) {
        if (k < 1) {
            throw new IllegalArgumentException(
                    "The value of k is too small: " + k + ", " +
                    "should be at least 1.");
        }
    }
}