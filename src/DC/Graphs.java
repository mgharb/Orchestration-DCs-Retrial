
package DC;

public class Graphs {
    
    
                static double fail_rate = 0;//if 0 no failures are created in the links
                static int createLP = 1; // If 1, lp is created; otherwise, only the path lengths are analyzed
                static int trafficMode = 0;	// 0: Server-level random permutation. 1: All-to-all

                
                public Graphs()
                      {System.out.println("Starting to create graphs!");}
    
                public static TopologicalGraph createGraphs(int graphtype){
    
                    TopologicalGraph graph = new TopologicalGraph();
                    
                    
		int runs = 0;	

		if(graphtype==0) // Vanilla Random Regular Graph
		{	
			//System.out.println("sw:" + Constants.switches);
			//System.out.println("switchports:" + Constants.switchports);
			//System.out.println("svr port:" + Constants.serverports);
			//System.out.println("nsvrs" + Constants.nsvrs);
			//Graph mynet = new RandomRegularGraph(Constants.switches,Constants.switchports, Constants.switchports - Constants.serverports, 1, Constants.extended_switches, Constants.nsvrs, fail_rate);

			//if (createLP == 1) mynet.PrintGraphforMCFFairCondensed("my." + runs + ".lp", trafficMode);
			//mynet.printPathLengths("pl." + runs);
		}
		else if(graphtype==1)//Fat tree
		{
			// System.out.println("FAT-SIZE = " + Constants.switchports);
			//Graph mynet = new FatTreeSigcomm(Constants.switchports, fail_rate); 
                        Graph mynet = FatTreeSigcomm.createFatTreeByHosts(Constants.nbHostsFatTree, graph);
			//if (createLP == 1) mynet.PrintGraphforMCFFairCondensed("my." + runs + ".lp", trafficMode);
			mynet.printPathLengths("pl." + runs);
                }

		else if(graphtype==2)//VL2
		{
			int aggports = Constants.AggPorts;
			int aggsw = Constants.AggSwitches;

                        System.out.println("We are creating VL2 topology");
			//Graph mynet = new VL2(aggsw, aggports); 
			
		}
                else if(graphtype==3)//BCube
		{
	             
                   //  Graph mynet = BCube.createBCubeByHands(); 
		}
		
		else if(graphtype==4)//Hypercube
		{
			//Graph mynet = new Hypercube((int)(Math.log(Constants.switches)/Math.log(2)), Constants.serverports);
	        }
                
                else if(graphtype==5)//DCell
		{
	              //Graph mynet = new DCell(Constants.DCellK, Constants.nbDCellHosts);
		}
                else if (graphtype == 8){ // VL2 Compare
                    
			int aggsw = Constants.AggSwitches;// di
			int aggports = Constants.AggPorts;// da
			int tors = Constants.tors;// > da * di / 4

			//Graph mynet = new RandVL2Compare(aggsw, aggports, tors);
			
		}

		//System.out.println("Done Constructing Graph");
    
                return graph;
    
                }
    
    
}
