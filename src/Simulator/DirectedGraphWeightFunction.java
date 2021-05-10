
package Simulator;

import java.util.HashMap;
import java.util.Map;


public class DirectedGraphWeightFunction {

    private final Map<DirectedGraphNode, 
                      Map<DirectedGraphNode, Integer>> map = new HashMap<>();

    public void put(DirectedGraphNode tail,
                    DirectedGraphNode head, 
                    int weight) {
        checkWeight(weight);
        
        Map<DirectedGraphNode, Integer> tempo = new HashMap<>();
        
        map.putIfAbsent(tail, tempo);
        
         map.get(tail).put(head, weight);
    }

    public double get(DirectedGraphNode tail, DirectedGraphNode head) {
        return map.get(tail).get(head);
    }

    private void checkWeight(double weight) {
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("The weight is NaN.");
        }

        if (weight < 0.0) {
            throw new IllegalArgumentException("The weight is negative.");
        }
    }
}