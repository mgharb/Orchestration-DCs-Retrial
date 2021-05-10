package Simulator;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Dijkstra {

  private List<Vertex> nodes;
  private List<Edge> edges;

    public LinkedList<Vertex> getShortestPath(int source, int destination) {
      
    nodes = new ArrayList<Vertex>();
    edges = new ArrayList<Edge>();
    
    //create the set of nodes: from node 1 to node 23
    for (int i = 0; i < 23; i++) {

      Vertex location = new Vertex(String.valueOf(i), String.valueOf(i));
      nodes.add(location);
    }
    
    createTopology();
    
      // Lets check from location Loc_1 to Loc_10
    Graph graph = new Graph(nodes, edges);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    
    dijkstra.execute(nodes.get(source));//id of the node source: form 0 to 10
    
    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destination));//id of the node destination: from 0 to 10

   // System.out.println("distance: "+ dijkstra.getShortestDistance(nodes.get(9)));
  /*  System.out.println("Components of the path:");
    for (Vertex vertex : path) {
     System.out.println(vertex);
    }*/
    
    return path;
    
  }
    
    public int getDistanceShortestPath(int source, int destination) {
      
    nodes = new ArrayList<Vertex>();
    edges = new ArrayList<Edge>();
    
    //create the set of nodes: from node 1 to node 23
    for (int i = 0; i < 23; i++) {
      Vertex location = new Vertex("Node_" + i, "Node_" + i);
      nodes.add(location);
    }
    
    createTopology();
    
      // Lets check from location Loc_1 to Loc_10
    Graph graph = new Graph(nodes, edges);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    
    dijkstra.execute(nodes.get(source));//id of the node source: form 0 to 10
    
    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destination));//id of the node destination: from 0 to 10


    //System.out.println("distance: "+ dijkstra.getShortestDistance(nodes.get(destination)));
    //System.out.println("Components of the path:");
  /*  for (Vertex vertex : path) {
      System.out.println(vertex);
    }*/
    
    return dijkstra.getShortestDistance(nodes.get(destination));
    
  }

  private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
    edges.add(lane);
  }
  
  
      private void createTopology()
    { 
    addLane("Edge_1", 0, 6, 12);
    addLane("Edge_2", 0, 21, 12);
    addLane("Edge_3", 1, 6, 12);
    addLane("Edge_4", 1, 21, 12);
    addLane("Edge_5", 2, 6, 12);
    addLane("Edge_6", 2, 19, 12);
    addLane("Edge_7", 3, 6, 12);
    addLane("Edge_8", 3, 19, 12);
    addLane("Edge_9", 4, 21, 12);
    addLane("Edge_10", 4, 19, 12);
  
    addLane("Edge_11", 5, 21, 12);
    addLane("Edge_12", 5, 19, 12);
    
    addLane("Edge_13", 6, 0, 12);
    addLane("Edge_14", 6, 1, 12);
    addLane("Edge_15", 6, 2, 12);
    addLane("Edge_16", 6, 3, 12);
    addLane("Edge_17", 6, 7, 36);
    addLane("Edge_18", 6, 8, 37);
    
    addLane("Edge_19", 7, 6, 36);
    addLane("Edge_20", 7, 9, 41);
    
    addLane("Edge_21", 8, 6, 37);
    addLane("Edge_22", 8, 9, 88);
    addLane("Edge_23", 8, 13, 208);
    addLane("Edge_24", 8, 10, 278);
   
    addLane("Edge_25", 9, 7, 41);
    addLane("Edge_26", 9, 8, 88);
    addLane("Edge_27", 9, 22, 182);
    
    addLane("Edge_28", 10, 8, 278);
    addLane("Edge_29", 10, 11, 144);
    
    addLane("Edge_30", 11, 10, 144);
    addLane("Edge_31", 11, 13, 120);
    addLane("Edge_32", 11, 12, 114);
    
    addLane("Edge_33", 12, 11, 114);
    addLane("Edge_34", 12, 13, 157);
    addLane("Edge_35", 12, 14, 306);
    
    addLane("Edge_36", 13, 12, 157);
    addLane("Edge_37", 13, 11, 120);
    addLane("Edge_38", 13, 8, 208);
    addLane("Edge_39", 13, 22, 316);
    addLane("Edge_40", 13, 15, 258);
    addLane("Edge_41", 13, 14, 298);
    
    addLane("Edge_42", 14, 12, 306);
    addLane("Edge_43", 14, 13, 298);
    addLane("Edge_44", 14, 15, 174);
    
    addLane("Edge_45", 15, 14, 174);
    addLane("Edge_46", 15, 13, 258);
    addLane("Edge_47", 15, 22, 353);
    addLane("Edge_48", 15, 16, 275);
   
    addLane("Edge_49", 16, 15, 275);
    addLane("Edge_50", 16, 22, 224);
    addLane("Edge_51", 16, 19, 187);
    addLane("Edge_52", 16, 17, 179);
    
    addLane("Edge_53", 17, 16, 179);
    addLane("Edge_54", 17, 18, 143);
    
    addLane("Edge_55", 18, 17, 143);
    addLane("Edge_56", 18, 19, 86);
    
    addLane("Edge_57", 19, 18, 86); 
    addLane("Edge_58", 19, 16, 187);
    addLane("Edge_59", 19, 20, 74);
    addLane("Edge_60", 19, 2, 12);
    addLane("Edge_61", 19, 3, 12);
    addLane("Edge_62", 19, 4, 12);
    addLane("Edge_63", 19, 5, 12);
    
    addLane("Edge_64", 20, 19, 74);
    addLane("Edge_65", 20, 21, 64);
    
    addLane("Edge_66", 21, 20, 64);
    addLane("Edge_67", 21, 22, 85);
    addLane("Edge_68", 21, 0, 12);
    addLane("Edge_69", 21, 1, 12);
    addLane("Edge_70", 21, 4, 12);
    addLane("Edge_71", 21, 5, 12);
    
    addLane("Edge_72", 22, 21, 85);
    addLane("Edge_73", 22, 16, 224);
    addLane("Edge_74", 22, 15, 353);
    addLane("Edge_75", 22, 13, 316);
    addLane("Edge_76", 22, 9, 182); 
    }
} 