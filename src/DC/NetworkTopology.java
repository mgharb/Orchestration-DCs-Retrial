
package DC;

import java.util.HashMap;
import java.util.Map;


public class NetworkTopology {


    protected static int nextIdx = 0;

    protected static double[][] bwMatrix = null;


	public static void addLink(String srcId, String destId, double bw, int cpu, double lat, TopologicalGraph graph, Map<String, Integer> map) {


		if (map == null) {
                        System.out.println("Map is null");
			map = new HashMap<String, Integer>();
		}

		if (!map.containsKey(srcId)) {
			
                        if(srcId.contains("Server"))
                        {graph.addNode(new TopologicalNode(srcId), cpu);}
                        else
                        {graph.addNode(new TopologicalNode(srcId), 0);}
			map.put(srcId, nextIdx);
			nextIdx++;
		}

		if (!map.containsKey(destId)) {
                        if(destId.contains("Server"))
                        {graph.addNode(new TopologicalNode(destId), cpu);}
                        else
                        {graph.addNode(new TopologicalNode(destId), 0);}
            
			map.put(destId, nextIdx);
			nextIdx++;
		}

                graph.addLink(new TopologicalLink(srcId, destId, (float) lat), bw);

		generateMatrices(graph);

	}


	private static void generateMatrices(TopologicalGraph graph) {

		bwMatrix = createBwMatrix(graph, false);
	}

	private static double[][] createBwMatrix(TopologicalGraph graph, boolean directed) {
		int nodes = graph.getNumberOfNodes();

		double[][] mtx = new double[nodes][nodes];


		return mtx;
	}

}//create the network topology
