package Simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class DefaultKShortestPathFinder extends AbstractKShortestPathFinder {

    @Override
    public List<Path> 
        findShortestPaths(DirectedGraphNode source, 
                          DirectedGraphNode target,
                          DirectedGraphWeightFunction weightFunction, 
                          int k) {
        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(target, "The target node is null.");
        Objects.requireNonNull(weightFunction,
                               "The weight function is null.");
        checkK(k);

        List<Path> paths = new ArrayList<>(k);
        Map<DirectedGraphNode, Integer> countMap = new HashMap<>();
        Queue<Path> HEAP = new PriorityQueue<>();

        HEAP.add(new Path(weightFunction, source));

        while (!HEAP.isEmpty() && countMap.getOrDefault(target, 0) < k) {
            Path currentPath = HEAP.remove();
            DirectedGraphNode endNode = currentPath.getEndNode();

            countMap.put(endNode, countMap.getOrDefault(endNode, 0) + 1);

            if (endNode.equals(target)) {
                paths.add(currentPath);
            }

            if (countMap.get(endNode) <= k) {
                for (DirectedGraphNode child : endNode.children()) {
                    Path path = currentPath.append(child);
                    HEAP.add(path);
                }
            }
        }
        
        Collections.sort(paths, new PathsComparator());

        return paths;
    }
           
}

 