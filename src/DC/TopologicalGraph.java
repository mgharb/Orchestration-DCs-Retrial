
package DC;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TopologicalGraph {

    public  static Map<TopologicalLink,Double> linkList = null;
    public  static Map<TopologicalNode,Integer> nodeList = null;

    
	public TopologicalGraph() {

            
            this.linkList = new ConcurrentHashMap<TopologicalLink,Double>();
        //    System.out.println("a new linkList is created");
            this.nodeList = new ConcurrentHashMap<TopologicalNode,Integer>();
        //    System.out.println("a new nodeList is created");
            
	}

	public void addLink(TopologicalLink edge, double bw) {

            this.linkList.put(edge, bw);
	}
        
        public void deleteLink(TopologicalLink edge) {
		
               this.linkList.remove(edge);
	}

	public void addNode(TopologicalNode node, int cpu) {

                this.nodeList.put(node, cpu);
	}
        
        public void deleteNode(TopologicalNode node) {

                this.nodeList.remove(node);
	}

	public int getNumberOfNodes() {
		return this.nodeList.size();
	}

	
	public int getNumberOfLinks() {
		return this.linkList.size();
	}

	//@Override
	public String Print() {

            //    System.out.println("nodeList.size(): "+this.nodeList.size());
             //   System.out.println("linkList.size(): "+this.linkList.size());
                
		StringBuffer buffer = new StringBuffer();

                Iterator it1 = this.nodeList.keySet().iterator();
                while (it1.hasNext())
                    {
                    TopologicalNode node = (TopologicalNode)it1.next();
                   }

                buffer.append("\nnode-information:\n");

                Iterator it2 = this.nodeList.keySet().iterator();
                while (it2.hasNext())
                    {
                    TopologicalNode node = (TopologicalNode)it2.next();
                    buffer.append("at: " + node.getNodeID() + " cpu: " + this.nodeList.get(node) + "\n");
                   }

                buffer.append("\nnode-link-information:\n");

                Iterator it3 = this.linkList.keySet().iterator();
                while (it3.hasNext())
                    {
                    TopologicalLink link = (TopologicalLink)it3.next();
                    buffer.append("from: " + link.getSrcNodeID() + " to: " + link.getDestNodeID() + " bw: "
					+ this.linkList.get(link)+ "\n");
                   }

                return buffer.toString();
                   
		}//print


}
