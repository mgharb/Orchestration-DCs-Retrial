
package DC;

public class Constants {
    
   public static final int switchLink = 31250;//up to handle 5 FSs in one request
   public static final int aggregatorLink = 31250;
   public static final int coreLink = 31250;
   public static final int serverCpuCapacity = 500;//the CPU is never blocking
 
   /****FatTreeSigComm*****/
   public static int nbHostsFatTree = 128;//128;//3600;//128; 
   /***********************/                             
   
   
   /******HyperCube******/
   public static int switches = 8; 	// # of switch
   public static int serverports = 100; // # of ports per switch that used to connect to servers
   public static int nsvrs = 0;	// # of servers we want to support (this, if non-zero, overrides the server ports arguments.)
   /************/
   
   /******BCube*****/
   public static int nbBCubeHosts = 16;
   
    /******DCell*****/
   public static int nbDCellHosts = 4;
   public static int DCellK = 2;
   
   /****************/
   public static int switchports = 8; 	// # of ports per switch
   public static int extended_switches = 0; // # switches added in expansion
  
   
   /****VL2*************/
   public static int AggSwitches = 8;//24;//4;  // di // # of Aggregation switches in VL2
   public static int AggPorts = 8;//48;//4; // da// # of Aggregation ports in VL2
   
   /***********VL2 compare********/
   public static int tors = 8; // > da * di / 4
   
   /***********/
   
   /*****Initial Placement*************/
   
   public static final int nHosts = 2;//40
   public static final int nAggregators = 4;//16
   public static final int nSwitches = 4;//8 //number of edge switches for a couple of aggregators
   //public static final int totalnSwitches = 16;//64
   public static final int nCores = 2;//4
   public static final int totalHosts = (nHosts * nSwitches);
  
   /*************************************/
   
  // public static int policy=1; //policy 0 FF
                                 //policy 1 WF
                                 //policy 2 BF
                                 //policy 3 Random
   public static int profile=3;
   
   public static final int Iteration = 4;//11;//31;//5
   
   public static final int vmDuration = 5100;//in seconds: the time a vm is alive (85 mins)
   public static final double load = 1;//in seconds
   
   public static final double load_mice = 1.3;//in seconds
   public static final double load_elephant = 126;//in seconds
   public static final double load_hybrid = 4.25;//in seconds
   
   public static final int vmDurationElephant = 151200;//42 hours
   public static final int vmDurationMice = 780;//13 minutes
   public static final int vmDurationAverage = 5100;//85 minutes
   
   public static final int Step = 180;//180;
   
   public static final int threshold = 100;

   public static final double alpha = 0.5;//0.5;//0.125;
   
   public static final double alpha_revenue = 1.0;

   public static final int totalMigrationRequests = 10;//50500;//1280;

   public static final int totalPlacementRequests = 10000;//10000;//5000;

   public static final int totalInitialRequests = 10;//1280;

   public static final int fillingPercentage = 20;//1280;

   public static final int totalTimers = 100000;//100000;
   
   public static final int totalTimersBaselines = 100000;//100000;
   
   public static final String VMsize = "Medium";//Small //Large // Medium
   public static final String typeFlow = "Mixture";//Elephant // Middle //Percentage (Mice: percentage = 0)
   public static final int percentageElephantFlows = 100;//Mice traffic: percentage equal to zero!!!!
   
   public static final int requestsInterval = 1000;//after how many requests take the statistics??

}
