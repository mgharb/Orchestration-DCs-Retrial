package Simulator;

import DC.TopologicalLink;
import DC.TopologicalNode;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.w3c.dom.*;
import DC.*;



public class Run {
       

    public static Random universalRand;
    public static Hashtable fsmList = new Hashtable();
    public static TreeMap<String, Integer> current_users_per_segment = new TreeMap();
    public static TreeMap<String, Integer> current_users_per_pipe = new TreeMap();
    public static HashMap<String, int[]> InterDCLinksCapacities = new HashMap<>();
    public static HashMap<String, Integer> InterDCLinksSlots = new HashMap<>();
    public static String Policy = " "; //choose between Closest or Unloaded
    public final static int NumberRequests = 300000;
    public final static int NumberIterations = 3;
    public static int NumberRequestsTransitorio = 10000;
    public static boolean getStats = false;
    public static int max_VM_bulk = 15;
    public static double current_users = 0;//number of users present on the same pipe
    public static int event = 0;
    
    static double load = 720; //1 su lambda 
    
    static int kshortestpaths = 4;//number of considered k-shortest paths == value of k
    
     public static int timer_duration = 5;//in seconds
    
   //  public static double data_to_process = 250000;//in kilobit!! kb
     public static int max_data = 400000;//in kilobit!! kb
     public static int min_data = 100000;//in kilobit!! kb
    
    public static HashMap<String, Integer> weight_segments = new HashMap<>();//weight of the segments in the topology
    
    public static HashMap<Integer, Path> paths_to_release = new HashMap<>();//weight of the segments in the topology
    
    static long seed = 1;
    static double service_time = 7200;//1 su mu//almost two hours as in the case of telefonica
    static double temps[] = new double[600000];
    static double serviceTime[] = new double[600000];
    static double blocked_pipe = 0;
    static double blocked_CPU = 0;
    static double blocked_totale = 0;
    static double blocked_BW = 0;
    static double blocked_access = 0;
    static double blocked_core = 0;
    static double blocked_core_high_lat = 0;//Data centers destination is D0/H/F
    static double blocked_core_low_lat = 0;//Data centers destination is B/L/N
    static double nb_times_medium_DC = 0;
    static double nb_times_large_DC = 0;
    static double nb_times_core_DCs = 0;
    static double intraDCBlock = 0;
    static double generated = 0;
    static double established = 0;
    static double released = 0;
    static ArrayList<Double> Distance  = new ArrayList();//for delayed policy
    static ArrayList<Double> Retrial_Latency  = new ArrayList();//time for which delayed requests have waited
    public static LinkedHashMap<Integer, Document> DelayedRequests = new LinkedHashMap<>();//Requests in order of insertion
    public static LinkedHashMap<Integer, Long> DelayedRequests_latency = new LinkedHashMap<>();//Time for which they are delayed
    
    public static LinkedHashMap<Integer, Integer> DelayedRequests_ID = new LinkedHashMap<>();//Time for which they are delayed
       
    static ArrayList<Integer> DC_large = new ArrayList<>();
    static ArrayList<Integer> DC_large_high_lat = new ArrayList<>();
    static ArrayList<Integer> DC_small = new ArrayList<>();
  //  static double total_blocked = 0;
  static double total_generated = 0;
  public static int ev_id = 0;
  public static int timer_id = 0;
  public static int ev_rel_id = 0;
  public static int ev_rel_start = 1;
  public static double utilization_segments = 0;
  static ArrayList<Double> statistics = new ArrayList<Double>();
  public static Vector adresses = new Vector();
  public static HashMap allocated_freq_slots = new HashMap();//put the number of allocated frequency slots to then release them
  public static String intraPolicy = "SD-FF";
  public static int NbAcceptedVMs = 0;
  public static HashMap current_VM_traffic = new HashMap();
  public static HashMap current_VM_CPU = new HashMap();
  public static HashMap intermediate_VM_traffic = new HashMap();
  public static HashMap intermediate_VM_CPU = new HashMap();
  public static HashMap allocated_VMs = new HashMap();
  public static HashMap current_VM_traffic_baseline = new HashMap();
  public static HashMap intermediate_VM_traffic_baseline = new HashMap();
  public static HashMap storico_VM_traffic = new HashMap();
  public static HashMap storico_VM_traffic_baseline = new HashMap();
  public static HashMap storico_VM_CPU = new HashMap();
  public static int acceptedVMRequests = 0;
  public static int refusedVMRequests = 0;
  public static int refusedCpuRequests = 0;
  public static int refusedBwRequests = 0;
  public static int refusedswitchRequests = 0;
  public static int refusedaggRequests = 0;
  public static int refusedcoreRequests = 0;
  public static int acceptedGlobalVMRequest = 0;
  public static int RefusedGlobalVMRequest = 0;
  public static int GlobalVMRequest = 0;
  public static int NbReleasedVMs = 0;
  public static double Bw_Rev = 0;
  public static double CPU_Rev = 0;
  public static double Total_Rev = 0;
  public static Map<String, Map<TopologicalNode,Integer>> intraDC_topology_nodes = new ConcurrentHashMap<>();
  public static Map<String, Map<TopologicalLink,Double>> intraDC_topology_links = new ConcurrentHashMap<>();
 
 public static Map<Integer, Integer> intraDC_aggregate_traffic = new ConcurrentHashMap<>();//Sum of the traffic on the core links
 public static Map<Integer, Integer> intraDC_servers_utilization = new ConcurrentHashMap<>();//Sum of the CPU on all the servers
 
 public static int aggregate_possible_traffic = 5000000;//((128*DC.Constants.coreLink) / 2);//10THz available over 10THz
 public static int aggregate_servers_capacity = (128*DC.Constants.serverCpuCapacity);
 
 public static int total_spectrum_width = 5000000;//4THz expressed in mega bit per second
 public static int total_frequency_slots = 320;//640;    bcvvc  
 public static int frequency_slot_capacity = 12500;//12.5GHz
 
 public static Map<String, Integer> start_indexes = new ConcurrentHashMap<>();
 public static Map<Integer, Integer> release_indexes = new ConcurrentHashMap<>();
 public static int number_core_links = 128;
 public static int number_servers = 128;
 
    public static void main(String[] args)

    {
       
     Run.Policy = ReadInput();  
        
     DataCenter.createRealDataCenter("0"); DataCenter.createRealDataCenter("1"); DataCenter.createRealDataCenter("2");
     DataCenter.createRealDataCenter("3"); DataCenter.createRealDataCenter("4"); DataCenter.createRealDataCenter("5");
     DataCenter.createRealDataCenter("8"); DataCenter.createRealDataCenter("13"); DataCenter.createRealDataCenter("14");
     DataCenter.createRealDataCenter("15"); DataCenter.createRealDataCenter("16"); DataCenter.createRealDataCenter("22");

 
   int k_iter = 0;
   
   while (k_iter < Run.NumberIterations)
   
   {

  System.out.println("Iteration n.: "+k_iter);
  PhysicalTopology.identifyDCs();//put DC small and DCs large in the specific ArrayLists
  PhysicalTopology.topology_linksCapacities();//associate a table with different slots to each link: bidirectional
  PhysicalTopology.topology_linksSlots();

  RequestGenerator.GenerateRequests();
  
  PhysicalTopology.update_Pipes_Segments_database();
  
  PhysicalTopology.generate_start_indexes_pipes();
  
  PhysicalTopology.assign_weight_segments();
  
  //System.out.println("Run.current_users_per_pipe.size(): "+Run.current_users_per_pipe.size());

       for (int j = 0; j<Run.NumberRequests; j++)
       {
        Message.Admin n1 = new Message.Admin(0, new Request(), temps[j]);
        n1.setAdmin(n1);
       }

      Thread t = new Thread(Message.Scheduler.instance());
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
     System.out.println("THE CURRENT ITERATION FINISHED");
     
     Statistics.Print();
 
     ev_id = 0;
     current_users = 0;
     ev_rel_id = 0;
     ev_rel_start = 0;
     allocated_freq_slots.clear();
     InterDCLinksCapacities.clear();
     InterDCLinksSlots.clear();
     start_indexes.clear();
     release_indexes.clear();
     current_users_per_segment.clear();
     current_users_per_pipe.clear();
     getStats = false;

     
     k_iter++;
     
       
    }//while iteration
   
   
   Statistics.Print_final_results();
 
    }//main
    
/*******************************************************************************************************************************/    
         public static String ReadInput()
     {
  
        String input = "";
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Applied policy: Closest or Unloaded");
        input = scanner.nextLine();
        Scanner scan = new Scanner(new InputStreamReader(System.in));
           
        return input;
      
    }   
/*******************************************************************************************************************************/    
}
