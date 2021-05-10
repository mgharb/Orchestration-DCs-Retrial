
package DC;


import java.util.*;
import java.util.ArrayList;

public class FatTreeSigcomm extends Graph{
	
	public int K;
        
        public static TopologicalGraph mygraph = new TopologicalGraph();
        
        protected static Map<String, Integer> map = new HashMap<String, Integer>();

	public static FatTreeSigcomm createFatTreeByHosts(int hosts, TopologicalGraph graph ){
            
                map = new HashMap<String, Integer>();
                mygraph = graph;
		double K_ = Math.pow(hosts*4, 1.0/3.0);
		int K_int = (int)K_;
		if(K_int % 2 != 0){
			K_int++;
		}
		//System.out.println("K would be "+K_+" rounding to "+K_int+ " leading to "+(K_int*K_int*K_int/4)+" hosts");
 
		return new FatTreeSigcomm(K_int);
	}

	public FatTreeSigcomm(int K_){
		super(K_*K_*5/4);
		this.K = K_;
		populateAdjacencyList();
		name = "fat";
             //   System.out.println("number of hosts: "+getNoHosts());
             //   System.out.println("number of switches: "+getNoSwitches());
            //    System.out.println("number of edge switches: "+this.K*this.K/2);
           //     System.out.println("number of servers per edge switch: "+(getNoHosts()/(this.K*this.K/2)));
                
	}
	public FatTreeSigcomm(int K_, double fail_rate){
		super(K_*K_*5/4);
		this.K = K_;
		populateAdjacencyList();
		name = "fat";
		failLinks(fail_rate);
	}

	public ArrayList TrafficGenAllAll()
	{

		int noNodeswithTerminals = K*K/2;
		int numPerms = noNodeswithTerminals - 1;

		ArrayList<Integer> ls = new ArrayList<Integer>();
		for (int i = 0; i < noNodeswithTerminals; i++) {
			int target = 0;
			for (int svr = 0; svr < numPerms; svr++) {
				if (target == i) target ++;
				ls.add(numPerms * target  + svr);
				target ++;
			}
		}

		//System.out.println("NUM FLOWS = " + ls.size());

		return ls;
	}
	
	public ArrayList TrafficGenPermutations()
	{
	
		int noNodeswithTerminals = K*K/2;
		int numPerms = totalWeight / noNodeswithTerminals;
		int[][] allPerms = new int[numPerms][noNodeswithTerminals];
		Random rand = new Random(Simulator.Run.universalRand.nextInt(10));

		for (int currPerm = 0; currPerm < numPerms; currPerm ++)
		{
			allPerms[currPerm] = new int[noNodeswithTerminals];
			for (int i = 0; i < noNodeswithTerminals; i++)
			{
				int temprand = rand.nextInt(i + 1);
				allPerms[currPerm][i] = allPerms[currPerm][temprand];
				allPerms[currPerm][temprand] = i;
			}

			Vector<Integer> badCases = new Vector<Integer>();
			for (int i = 0; i < noNodeswithTerminals; i++)
			{
				if (allPerms[currPerm][i] == i) badCases.add(new Integer(i));
			}
			for (Integer badOne : badCases)
			{
				int temprand= -1;
				do
				{
					temprand = rand.nextInt(noNodeswithTerminals);
				}
				while (allPerms[currPerm][temprand] == temprand);

				allPerms[currPerm][badOne.intValue()] = allPerms[currPerm][temprand];
				allPerms[currPerm][temprand] = badOne.intValue();
			}
		}

		ArrayList<Integer> ls = new ArrayList<Integer>();
		for (int i = 0; i < noNodeswithTerminals; i++)
		{
			for (int currPerm = 0; currPerm < numPerms; currPerm ++)
			{
				ls.add(numPerms * allPerms[currPerm][i] + currPerm);
			}
		}
		return ls;
	}

	private void populateAdjacencyList(){

            //System.out.println("Connect lower to middle");
		for(int pod = 0; pod < K; pod++){
			for(int i = 0; i < K/2; i++){
				for(int l = 0; l < K/2; l++){
					addBidirNeighbor(pod*K/2+i, K*K/2+pod*K/2+l);
                                        NetworkTopology.addLink("Switch_".concat(String.valueOf(pod*K/2+i)), "Agg_".concat(String.valueOf(K*K/2+pod*K/2+l)), Constants.aggregatorLink, 0,0, mygraph, map); 
				}
			}
		}

            //System.out.println("Connect middle to core");
		for(int core_type = 0; core_type < K/2; core_type++){
			for(int incore = 0; incore < K/2; incore++){
				for(int l = 0; l < K; l++){
					addBidirNeighbor(K*K+core_type*K/2+incore, K*K/2+l*K/2+core_type);
                                        NetworkTopology.addLink("Agg_".concat(String.valueOf(K*K/2+l*K/2+core_type)), "Core_".concat(String.valueOf(K*K+core_type*K/2+incore)), Constants.coreLink, 0,0, mygraph, map); 
				}
			}
		}

                //set weights
		setUpFixWeight(0);
		int total = 0;
		for(int pod = 0; pod <K*K/2; pod++){
			for(int i = 0; i < K/2; i++){
                            NetworkTopology.addLink("Server_".concat(String.valueOf(total)), "Switch_".concat(String.valueOf(pod)), Constants.switchLink, Constants.serverCpuCapacity, 0, mygraph, map);
                            total = total + 1;
                        }}
	}

	
	public int getK(){
		return K;
	}
	
        @Override
	public int getNoHosts(){
		return K*K*K/4;
	}
	
        @Override
	public int getNoSwitches(){
		return K*K*5/4;
	}
	
	public int svrToSwitch(int i)	//i is the server index. return the switch index.
	{
		return i / weightEachNode[0];
	}
}

