
package DC;

import java.util.Map;
import java.util.Random;
import Simulator.*;
import static Simulator.Run.intraDC_topology_links;
import static Simulator.Run.intraDC_topology_nodes;
import java.util.Iterator;


public class DataCenter {
    
      private static Random universalRand;
    
      public DataCenter()
      {}
    
      
    public static void createRealDataCenter(String addresse)
    {
        
            
     int myTopology = 1;
     
     long randSeed = 0;
        if (randSeed == 0) universalRand = new Random();
		else { 
			System.out.println("NEWSFLASH: USING YOUR RANDOM SEED -----------------------------------------------");
			universalRand = new Random(randSeed);
		}
    
    // System.out.println("creating DC in address: "+addresse);
        
    DC.TopologicalGraph graph = DC.Graphs.createGraphs(myTopology);
    
    Map<TopologicalLink,Double> newLinkList = graph.linkList;
    Map<TopologicalNode,Integer> newNodeList = graph.nodeList;
    
   
    Run.intraDC_topology_nodes.put(addresse, newNodeList);
    Run.intraDC_topology_links.put(addresse, newLinkList);
    
    Run.intraDC_aggregate_traffic.put(Integer.parseInt(addresse), Run.aggregate_possible_traffic);
    Run.intraDC_servers_utilization.put(Integer.parseInt(addresse), Run.aggregate_servers_capacity);
    
    }
    
 /********************************************************************************************************************************/
    
    public static void printIntraDC_Topology()
    {
        
           Iterator Nodes = intraDC_topology_nodes.keySet().iterator();
           
          // System.out.println("intraDC_topology_nodes.size(): "+intraDC_topology_nodes.size());
           
                        while (Nodes.hasNext())
                          {
                              String keyNode = Nodes.next().toString();
                                  
                              Map<TopologicalNode,Integer> nodeList = (Map<TopologicalNode,Integer>)intraDC_topology_nodes.get(keyNode);
                                                            
                            //  System.out.println("nodeList.size(): "+nodeList.size());
                              
                              StringBuffer buffer = new StringBuffer();
                              
                              Iterator it1 = nodeList.keySet().iterator();


                buffer.append("\nnode-information:\n");

                Iterator it2 = nodeList.keySet().iterator();
                while (it2.hasNext())
                    {
                    TopologicalNode node = (TopologicalNode)it2.next();
                    buffer.append("at: " + node.getNodeID() + " cpu: " + nodeList.get(node) + "\n");
                   }
                
              //  System.out.println(buffer.toString());
                             // }
                          }

           
           
           
        //  System.out.println("intraDC_topology_links.size(): "+intraDC_topology_links.size());
                
                      Iterator Links = intraDC_topology_links.keySet().iterator();
                        while (Links.hasNext())
                          {   
                              String keyLink = Links.next().toString();
                            //  System.out.println("Considered DC: "+keyLink);
                              
                              Map<TopologicalLink,Double> linkList = (Map<TopologicalLink,Double>)intraDC_topology_links.get(keyLink);
                              
             //   System.out.println("linkList.size(): "+linkList.size());
                
		StringBuffer buffer = new StringBuffer();
		//buffer.append("topological-node-information: \n");

                

                buffer.append("\nnode-link-information:\n");

                Iterator it3 = linkList.keySet().iterator();
                while (it3.hasNext())
                    {
                    TopologicalLink link = (TopologicalLink)it3.next();
                    buffer.append("from: " + link.getSrcNodeID() + " to: " + link.getDestNodeID() + " bw: "
					+ linkList.get(link)+ "\n");
                   }
                
              //  System.out.println(buffer.toString());
                
                          }
               
                        
    }
    
 /********************************************************************************************************************************/   
    
}
