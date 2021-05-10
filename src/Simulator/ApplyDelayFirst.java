package Simulator;

import Message.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.w3c.dom.*;


public class ApplyDelayFirst extends FSM{

	final protected int START = 1;
	final protected int END = 2;
	final protected int WAIT_FOR_REQUEST = 3;
        

	private ServiceMessage serviceMes;

     public ApplyDelayFirst(String ses){

          super(ses);
            
        state = START;
	}//


        @Override
	public int execute(Message msg)
	{
              
		switch(state)
		{
		case START:
		{
                  stateSTART(msg);
                     
                  ServiceMessage serMsg = (ServiceMessage) msg;
                  
                  if(msg.getCommand().equals("ServiceRequest")){

                        
                        
                        Document docService = serMsg.getValue();
                        int DC_source = Integer.parseInt(docService.getElementsByTagName("DC_Source").item(0).getFirstChild().getNodeValue());
                        int cpu_demand = Integer.parseInt(docService.getElementsByTagName("CPU").item(0).getFirstChild().getNodeValue());
                        int slots_demand = Integer.parseInt(docService.getElementsByTagName("Frequency-Slots").item(0).getFirstChild().getNodeValue());
                        
                        int id_req = Integer.parseInt(docService.getElementsByTagName("ID").item(0).getFirstChild().getNodeValue());
                        
                        long req_time_stamp = Long.parseLong(docService.getElementsByTagName("Stamp").item(0).getFirstChild().getNodeValue());
                        
                        System.out.println("Received a new request: "+id_req);
                      //  System.out.println("stamp new request: "+req_time_stamp);
                        
                        boolean available_bandwidth = false;
                        boolean available_cpu = false;
                        boolean available_slots = false;
                        boolean verified = false;
                        
                     //  System.out.println("dc source: "+DC_source);
             
                       if(Run.Policy.equals("Closest"))
                        {
                            
                  if(Run.DC_large.contains(DC_source))//block_access: destination in small datacenters => Downlink
                  {           
                            
                            //choose the closest path
                            
                           // System.out.println("Apply the Closest policy!");
                            Closest policy = new Closest();
                            int chosen_dest = policy.checkDest(DC_source);
                            
                           KshortestPath ksh = new KshortestPath();
                           List<Path> paths = ksh.calculateKshortestPathDownLink(DC_source, chosen_dest, Run.kshortestpaths);
                            
                            int ind = 0;
                            boolean shortest_found = false;
                            while((ind<paths.size())&& (!shortest_found))
                            {
                      //      System.out.println("ind: "+ind);
                            Path path = paths.get(ind);//get first path => should be unique in this case
                            List<DirectedGraphNode> chosen_path = path.getNodeList();
                            
                            int distance_chosen_path = 0;
                            
                            String[] path_segments = new String[chosen_path.size()-1];
                            for(int i=0; i<chosen_path.size()-1; i++)
                            {
                       //     System.out.println(chosen_path.get(i));
                            path_segments[i] = chosen_path.get(i).toString().concat("-").concat(chosen_path.get(i+1).toString());
                            path_segments[i] = path_segments[i].replaceAll(" ", ""); //delete spaces in the string
                       //     System.out.println(path_segments[i]);
                            distance_chosen_path = distance_chosen_path + Run.weight_segments.get(path_segments[i]);
                            }

                            
                            //check the availability of the total number of frequency slots
                            
                            for(int i=0; i<path_segments.length; i++)
                            {
                            if(((int)(Run.InterDCLinksSlots.get(path_segments[i])) - slots_demand) >= 0)
                            {
                            available_slots = true;
                            }
                            }
                            
                            String adopted_start = "0"; 
                            if(available_slots)
                            {
                            
                       //    System.out.println("Total number of frequency slots is available!");
                                
                            //CHECK the availability of the continuous/contiguous frequency slots
                             
                            String combine_adresses = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                            String response = verify_spectrum_continuity(path_segments, slots_demand, combine_adresses);
                            String[] temp = response.split("-", 2);
                            
                            adopted_start = temp[0];
                            verified = Boolean.parseBoolean(temp[1]);
                            
                    //   System.out.println("verify spectrum continuity: "+verified);
                    //   System.out.println("adopted_start: "+adopted_start);
                               
                            }
                     
                       //check the availability of the resources Intra-DC in un modo aggregato
                            
                    //    System.out.println("Check the servers utilization/aggregated traffic in the DC destination");
                        
                        int bandwidth_to_allocate = slots_demand * Run.frequency_slot_capacity;//in Mbps
                        int current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        int current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        int current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        int current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                         
                  //      System.out.println("bandwidth_to_allocate: "+bandwidth_to_allocate);
                         
                         if (( (current_agg_traffic_source - bandwidth_to_allocate) > 0) && ((current_agg_traffic_dest  - bandwidth_to_allocate) > 0))
                         {
                       //     System.out.println("Enough aggregated bandwidth");
                              available_bandwidth = true;

                         }
                         
                         if (( (current_serv_utilization_source - cpu_demand) > 0) && ((current_serv_utilization_dest - cpu_demand) > 0))
                         {
                         
                         //    System.out.println("Enough CPU in the servers");
                               available_cpu = true;
                               
                         }      
                        
                            
                         /***************Allocate the VM in the DC*****************************/
                        
                     if((available_bandwidth) && (available_cpu) && (available_slots) && (verified))
                         {    
                         
                          int nb_VM_to_allocate = 1 + (int)(Math.random() * ((Run.max_VM_bulk - 1) + 1));
                          
                         //System.out.println("nb_VM_to_allocate: "+nb_VM_to_allocate);
                         
                          DC.VMachine[] VM = new DC.VMachine[nb_VM_to_allocate];
                          
                       //   System.out.println("requested_bandwidth per VM: "+((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate));
                     //     System.out.println("requested_cpu: "+cpu_demand);

                          for (int i=0; i<nb_VM_to_allocate; i++)
                          {
                          int bw = ((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate);
                          double duration = Run.serviceTime[Run.ev_id];
                          DC.VMachine tempVM = new DC.VMachine(bw, cpu_demand, String.valueOf(Run.ev_id), duration);
                          VM[i] = tempVM;
                          }
                          
                      //    System.out.println("Allocation Request accepted!!");
                     
                          //update the number of current users per segment if the request is accepted
                          
                          String combined_address = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                          int current_users = (int)Run.current_users_per_pipe.get(combined_address);
                          Run.current_users_per_pipe.put(combined_address, (current_users+1));
                          
                          //update the total number of frequency slots on all the segments of the path
                          
                            for(int i=0; i<path_segments.length; i++)
                            {
                            int current_slots = (int)Run.InterDCLinksSlots.get(path_segments[i]);
                            Run.InterDCLinksSlots.put(path_segments[i], (current_slots-slots_demand));
                            }
                            
              // update the tables of the frequency slots on each involved segment
                            
               Run.start_indexes.put(combined_address, Integer.parseInt(adopted_start));
                       
               for(int k=0; k<path_segments.length; k++)
                        {
                String segment = path_segments[k];
                int[] temp_table  = null;
                int temp_slot_capacity = 0;       
                temp_table = (int[])(Run.InterDCLinksCapacities.get(segment)); 
                temp_slot_capacity = (int)(Run.InterDCLinksSlots.get(segment));
                
                for(int i=Integer.parseInt(adopted_start); i<(Integer.parseInt(adopted_start)+slots_demand); i++)
                {
                temp_table[i] = 1;
                }
                PhysicalTopology.check_adjacencies(temp_table, slots_demand);
                               
                Run.InterDCLinksCapacities.put(segment, temp_table);
                        
                        }
                            
                        //update the intraDC resources: CPU and bandwidth
                         
                        current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                        
                        Run.intraDC_aggregate_traffic.put(DC_source, (current_agg_traffic_source-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(DC_source, (current_serv_utilization_source - cpu_demand));
                        Run.intraDC_aggregate_traffic.put(chosen_dest, (current_agg_traffic_dest-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(chosen_dest, (current_serv_utilization_dest - cpu_demand));
                        
                         //update the traversed distance       
                        Run.Distance.add((double)distance_chosen_path);
                        
                        shortest_found = true;
                        
                      //  System.out.println("accepted request");
                        
                    //    System.out.println("req_time_stamp: "+req_time_stamp);
                        
                        if(req_time_stamp > 0)
                        {
                         long current_time = (System.currentTimeMillis() / 1000);
                     //    System.out.println("current_time: "+current_time);
                         long difference = current_time - req_time_stamp;
                         Long lon = new Long(difference);
                         
                     //    System.out.println("difference: "+difference);
                        
                         Run.Retrial_Latency.add(lon.doubleValue());
                        }
                       
                        
                    //    System.out.println("accepted request!");
                        
                        //update statistics
                       if(Run.ev_id > Run.NumberRequestsTransitorio)
                         { Run.established = Run.established + 1;}
                          
                        //create and launch the release message
                         
                       int release_id = Run.ev_rel_id;
                       Run.ev_rel_id = Run.ev_rel_id + 1;
                       double service_time = Run.serviceTime[Run.ev_id];
                       Admin n2 = new Admin(0, new Release(release_id, combined_address, Integer.parseInt(adopted_start), cpu_demand, bandwidth_to_allocate, slots_demand, path), service_time);
                       n2.setAdmin(n2);

                          }
                            
                       ind++;
                            
                         }
                               
                       //  }
               
                      
         if((Run.ev_id > Run.NumberRequestsTransitorio) && ((!available_bandwidth) || (!available_cpu) || (!available_slots) || (!verified)))
                      {
                           
                    //  System.out.println("No resources are available at this moment => delay request");
                      
                      //create a new handler for each delayed request
                      int id = Run.timer_id;
                      Run.timer_id = Run.timer_id + 1;
                      Run.DelayedRequests.put(id, docService);//or put as key the value of the timer
                 //     System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      long current_time = (System.currentTimeMillis()/1000);//expressed in seconds
                      
                      if(req_time_stamp > 0)
                        {
                      Run.DelayedRequests_latency.put(id, req_time_stamp);
                        }
                      else
                       {
                      Run.DelayedRequests_latency.put(id, current_time);
                       }
                      
                //      System.out.println("ev_id of delayed request: "+id_req);
                      Run.DelayedRequests_ID.put(id, id_req);
                      
                      Admin nT = new Admin(0, new KillTimer(id), Run.timer_duration*1000);
                      nT.setAdmin(nT);
                      
                   //   System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      
                         }
                                           
                    }//DC_large contains DC_source
                  
                  else if(Run.DC_small.contains(DC_source))//block_core: destination in large datacenters => uplink
                  {
                    //choose the closest path
                            
                          //  System.out.println("Apply the Closest policy!");
                            Closest policy = new Closest();
                            int chosen_dest = policy.checkDest(DC_source);
                            
                        //    System.out.println("Source: "+DC_source+" dest: "+chosen_dest);
                            
                           KshortestPath ksh = new KshortestPath();
                           List<Path> paths = ksh.calculateKshortestPathUpLink(DC_source, chosen_dest, Run.kshortestpaths);//source, destination, number_of_paths
                          
                         //   System.out.println("Number of k-shortest paths: "+paths.size());
                       //     System.out.println("Components of the chosen path:");
                            int ind = 0;
                            boolean shortest_found = false;
                            while((ind<paths.size())&& (!shortest_found))
                            {
                        //    System.out.println("ind: "+ind);
                            Path path = paths.get(ind);//get first path => should be unique in this case
                            List<DirectedGraphNode> chosen_path = path.getNodeList();
                            
                            int distance_chosen_path = 0;
                            
                            String[] path_segments = new String[chosen_path.size()-1];
                            for(int i=0; i<chosen_path.size()-1; i++)
                            {
                      //      System.out.println(chosen_path.get(i));
                            path_segments[i] = chosen_path.get(i).toString().concat("-").concat(chosen_path.get(i+1).toString());
                            path_segments[i] = path_segments[i].replaceAll(" ", ""); //delete spaces in the string
                        //    System.out.println(path_segments[i]);
                            distance_chosen_path = distance_chosen_path + Run.weight_segments.get(path_segments[i]);
                            }
                            
                        //    System.out.println("distance_chosen_path: "+distance_chosen_path);
                           //int distance_chosen_path = dij.getDistanceShortestPath(DC_source, chosen_dest);
                            
                            //check the availability of the total number of frequency slots
                            
                            for(int i=0; i<path_segments.length; i++)
                            {
                            if(((int)(Run.InterDCLinksSlots.get(path_segments[i])) - slots_demand) >= 0)
                            {
                            available_slots = true;
                            }
                            }
                            
                            String adopted_start = "0"; 
                            if(available_slots)
                            {
                            
                      //     System.out.println("Total number of frequency slots is available!");
                                
                            //CHECK the availability of the continuous/contiguous frequency slots
                             
                            String combine_adresses = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                            String response = verify_spectrum_continuity(path_segments, slots_demand, combine_adresses);
                            String[] temp = response.split("-", 2);
                            
                            adopted_start = temp[0];
                            verified = Boolean.parseBoolean(temp[1]);
                            
                        //   System.out.println("verify spectrum continuity: "+verified);
                    //   System.out.println("adopted_start: "+adopted_start);
                             
                            }
                  
                       //check the availability of the resources Intra-DC in un modo aggregato
                            
                    //    System.out.println("Check the servers utilization/aggregated traffic in the DC destination");
                        
                        int bandwidth_to_allocate = slots_demand * Run.frequency_slot_capacity;//in Mbps
                        int current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        int current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        int current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        int current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                         
                  //      System.out.println("bandwidth_to_allocate: "+bandwidth_to_allocate);
                         
                         if (( (current_agg_traffic_source - bandwidth_to_allocate) > 0) && ((current_agg_traffic_dest  - bandwidth_to_allocate) > 0))
                         {
                         //   System.out.println("Enough aggregated bandwidth");
                              available_bandwidth = true;

                         }
                         
                         if (( (current_serv_utilization_source - cpu_demand) > 0) && ((current_serv_utilization_dest - cpu_demand) > 0))
                         {
                         
                         //    System.out.println("Enough CPU in the servers");
                               available_cpu = true;
                               
                         }
                         
                         /***************Allocate the VM in the DC*****************************/
                         
                       if((available_bandwidth) && (available_cpu) && (available_slots) && (verified))
                         {
                         
                          int nb_VM_to_allocate = 1 + (int)(Math.random() * ((Run.max_VM_bulk - 1) + 1));
                          
                         //System.out.println("nb_VM_to_allocate: "+nb_VM_to_allocate);
                         
                          DC.VMachine[] VM = new DC.VMachine[nb_VM_to_allocate];
                          
                       //   System.out.println("requested_bandwidth per VM: "+((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate));
                     //     System.out.println("requested_cpu: "+cpu_demand);

                          for (int i=0; i<nb_VM_to_allocate; i++)
                          {
                          int bw = ((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate);
                          double duration = Run.serviceTime[Run.ev_id];
                          DC.VMachine tempVM = new DC.VMachine(bw, cpu_demand, String.valueOf(Run.ev_id), duration);
                          VM[i] = tempVM;
                          }
                          
                      //    System.out.println("Allocation Request accepted!!");
                     
                          //update the number of current users per segment if the request is accepted
                          
                          String combined_address = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                          int current_users = (int)Run.current_users_per_pipe.get(combined_address);
                          Run.current_users_per_pipe.put(combined_address, (current_users+1));
                          
                          //update the total number of frequency slots on all the segments of the path
                          
                            for(int i=0; i<path_segments.length; i++)
                            {
                            int current_slots = (int)Run.InterDCLinksSlots.get(path_segments[i]);
                            Run.InterDCLinksSlots.put(path_segments[i], (current_slots-slots_demand));
                            }
                            
              //update the tables of the frequency slots on each involved segment
                            
               Run.start_indexes.put(combined_address, Integer.parseInt(adopted_start));
                       
               for(int k=0; k<path_segments.length; k++)
                        {
                String segment = path_segments[k];
                int[] temp_table  = null;
                int temp_slot_capacity = 0;       
                temp_table = (int[])(Run.InterDCLinksCapacities.get(segment)); 
                temp_slot_capacity = (int)(Run.InterDCLinksSlots.get(segment));
                
                for(int i=Integer.parseInt(adopted_start); i<(Integer.parseInt(adopted_start)+slots_demand); i++)
                {
                temp_table[i] = 1;
                }
                PhysicalTopology.check_adjacencies(temp_table, slots_demand);
                               
                Run.InterDCLinksCapacities.put(segment, temp_table);
                        }
               
                            
                        //update the intraDC resources: CPU and bandwidth
                         
                        current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                        
                        Run.intraDC_aggregate_traffic.put(DC_source, (current_agg_traffic_source-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(DC_source, (current_serv_utilization_source - cpu_demand));
                        Run.intraDC_aggregate_traffic.put(chosen_dest, (current_agg_traffic_dest-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(chosen_dest, (current_serv_utilization_dest - cpu_demand));
                        
                         //update the traversed distance       
                        Run.Distance.add((double)distance_chosen_path);
                        
                        shortest_found = true;
                        
                    //    System.out.println("accepted request");

                        
                   //     System.out.println("req_time_stamp: "+req_time_stamp);
                        
                        if(req_time_stamp > 0)
                        {
                         long current_time = (System.currentTimeMillis() / 1000);
                     //    System.out.println("current_time: "+current_time);
                         long difference = current_time - req_time_stamp;
                         Long lon = new Long(difference);
                         
                     //    System.out.println("difference: "+difference);
                        
                         Run.Retrial_Latency.add(lon.doubleValue());
                        }
                      //   System.out.println("accepted request!");
                        
                        //update statistics
                       if(Run.ev_id > Run.NumberRequestsTransitorio)
                         { Run.established = Run.established + 1;}
                          
                        //create and launch the release message
                         
                       int release_id = Run.ev_rel_id;
                       Run.ev_rel_id = Run.ev_rel_id + 1;
                       double service_time = Run.serviceTime[Run.ev_id];
                       Admin n2 = new Admin(0, new Release(release_id, combined_address, Integer.parseInt(adopted_start), cpu_demand, bandwidth_to_allocate, slots_demand, path), service_time);
                       n2.setAdmin(n2);
                       
                       if(Run.DC_large_high_lat.contains(chosen_dest))
                       {
                       Run.nb_times_core_DCs = Run.nb_times_core_DCs + 1;
                       Run.nb_times_large_DC = Run.nb_times_large_DC + 1;
                       }
                       else
                       {
                       Run.nb_times_core_DCs = Run.nb_times_core_DCs + 1;
                       Run.nb_times_medium_DC = Run.nb_times_medium_DC + 1;
                       }
                       
                         }
                       
                       ind++;
                          
                        // }
                        }
                        
                       //  }
                       
          if((Run.ev_id > Run.NumberRequestsTransitorio) && ((!available_bandwidth) || (!available_cpu) || (!available_slots) || (!verified)))
                      {
                           
                  //    System.out.println("No resources are available at this moment => delay request");
                      
                       //create a new handler for each delayed request
                      int id = Run.timer_id;
                      Run.timer_id = Run.timer_id + 1;
                      Run.DelayedRequests.put(id, docService);//or put as key the value of the timer
                   //   System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      long current_time = (System.currentTimeMillis()/1000);//expressed in seconds
                      
                      if(req_time_stamp > 0)
                        {
                      Run.DelayedRequests_latency.put(id, req_time_stamp);
                        }
                      else
                       {
                      Run.DelayedRequests_latency.put(id, current_time);
                       }
                      
                    //  System.out.println("ev_id of delayed request: "+id_req);
                      Run.DelayedRequests_ID.put(id, id_req);
                      
                      Admin nT = new Admin(0, new KillTimer(id), Run.timer_duration*1000);
                      nT.setAdmin(nT);
                      
                   //   System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      
                         }    
           
                        }    
                      
                        }//Closest policy
                        
        /*******************************************************************************************************************/                
        /********************************************Unloaded Policy********************************************************/
        /*******************************************************************************************************************/
                  else
                 {
                            
              if(Run.DC_small.contains(DC_source))//block_core: destination in large datacenters => uplink       
              {
                        
                     //       System.out.println("Apply the most unloaded policy!");
                            
                            //choose the most unloaded path
                            TreeMap<String, Integer> temp_hashmap = new TreeMap();
                            temp_hashmap.putAll(Run.current_users_per_pipe);
                            HashMap<String,Integer> sorted_map = PhysicalTopology.sortHashMapByValues(Run.current_users_per_pipe, true);
                            Run.current_users_per_pipe.putAll(temp_hashmap);
                            String unloaded_pipe = "";
                            Iterator keys = sorted_map.keySet().iterator();
                            while (keys.hasNext())
                            {
                            String current_key = keys.next().toString();
                            String[] temp = current_key.toString().split("-", 2);
                            String temp_source = temp[0];
                            if(temp_source.equals(String.valueOf(DC_source)))
                            {
                            unloaded_pipe = current_key;
                            break;
                            }
                            }
                       //     System.out.println("most unloaded pipe: "+unloaded_pipe);
                            String[] result = unloaded_pipe.toString().split("-", 2);
                            String chosen_destination = result[1];
                       //    System.out.println("chosen_dest: "+chosen_destination);
                            int chosen_dest = Integer.parseInt(chosen_destination);
                            
                       //     System.out.println("Source: "+DC_source+" dest: "+chosen_dest);
                            
                            KshortestPath ksh = new KshortestPath();
                           List<Path> paths = ksh.calculateKshortestPathUpLink(DC_source, chosen_dest, Run.kshortestpaths);//source, destination, number_of_paths
                          
                       //     System.out.println("Number of k-shortest paths: "+paths.size());
                       //     System.out.println("Components of the chosen path:");
                            int ind = 0;
                            boolean shortest_found = false;
                            
                            while((ind<paths.size()) && (!shortest_found))
                            {
                       //     System.out.println("ind: "+ind);
                            Path path = paths.get(ind);//get first path => should be unique in this case
                            List<DirectedGraphNode> chosen_path = path.getNodeList();
                            
                            int distance_chosen_path = 0;
                            
                            String[] path_segments = new String[chosen_path.size()-1];
                            for(int i=0; i<chosen_path.size()-1; i++)
                            {
                      //      System.out.println(chosen_path.get(i));
                            path_segments[i] = chosen_path.get(i).toString().concat("-").concat(chosen_path.get(i+1).toString());
                            path_segments[i] = path_segments[i].replaceAll(" ", ""); 
                      //      System.out.println(path_segments[i]);
                            distance_chosen_path = distance_chosen_path + Run.weight_segments.get(path_segments[i]);
                            }
                            
                            //check the availability of the total number of frequency slots
                            
                            for(int i=0; i<path_segments.length; i++)
                            {
                            if(((int)(Run.InterDCLinksSlots.get(path_segments[i])) - slots_demand) >= 0)
                            {
                            available_slots = true;
                      //      System.out.println("Total number of frequency slots is NOT available!");
                            }
                            }
                            
                            String adopted_start = "0"; 
                            if(available_slots)
                            {
                            
                        //    System.out.println("Total number of frequency slots is available!");
                                
                       //check the availability of the continuous/contiguous frequency slots
                             
                            String combine_adresses = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                  //          System.out.println("combine_adresses: "+combine_adresses);
                            String response = verify_spectrum_continuity(path_segments, slots_demand, combine_adresses);
                            String[] temp = response.split("-", 2);
                            
                            adopted_start = temp[0];
                            verified = Boolean.parseBoolean(temp[1]);
                            
                      //     System.out.println("verify spectrum continuity: "+verified);
                   //         System.out.println("adopted_start: "+adopted_start);
                            
                            }                
                   
                            //check the availability of the resources Intra-DC
                            
                  //      System.out.println("Check the servers utilization/aggregated traffic in the DC destination");
                        int bandwidth_to_allocate = slots_demand * Run.frequency_slot_capacity;//in Mbps
                        
                        int current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        int current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        int current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        int current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                         
                 //       System.out.println("bandwidth_to_allocate: "+bandwidth_to_allocate);
                         
                         if (( (current_agg_traffic_source - bandwidth_to_allocate) > 0) && ((current_agg_traffic_dest  - bandwidth_to_allocate) > 0))
                         {
                      //    System.out.println("Enough aggregated traffic");
                             available_bandwidth = true;
                         }
                         
                         if (( (current_serv_utilization_source - cpu_demand) > 0) && ((current_serv_utilization_dest - cpu_demand) > 0))
                         {
                         
                       //    System.out.println("Enough utilization of the servers");
                             available_cpu = true;
                             
                         }
                         
                         /***************Allocate the VM in the DC*****************************/
                         
                         if((available_bandwidth) && (available_cpu) && (available_slots) && (verified))
                         {
                          int nb_VM_to_allocate = 1 + (int)(Math.random() * ((Run.max_VM_bulk - 1) + 1));
                          
          //               System.out.println("nb_VM_to_allocate: "+nb_VM_to_allocate);
                         
                          DC.VMachine[] VM = new DC.VMachine[nb_VM_to_allocate];
                          
          //                System.out.println("requested_bandwidth per VM: "+((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate));
          //                System.out.println("requested_cpu: "+cpu_demand);

                          for (int i=0; i<nb_VM_to_allocate; i++)
                          {
                          int bw = ((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate);
                          double duration = Run.serviceTime[Run.ev_id-1];
                          DC.VMachine tempVM = new DC.VMachine(bw, cpu_demand, String.valueOf(Run.ev_id-1), duration);
                          VM[i] = tempVM;
                          }
                          
          //                System.out.println("Allocation Request accepted!!");
                          
                         //update the number of current users per segment if the request is accepted
                          
                          String combined_address = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
          //                System.out.println("combined_address: "+combined_address);
          //                System.out.println("Run.current_users_per_pipe.size(): "+Run.current_users_per_pipe.size());
                         
                          int current_users = (int)Run.current_users_per_pipe.get(combined_address);
                          Run.current_users_per_pipe.put(combined_address, (current_users+1));
                            
                        //update the total number of frequency slots on all the segments of the path
                          
                            for(int i=0; i<path_segments.length; i++)
                            {
                            int current_slots = (int)Run.InterDCLinksSlots.get(path_segments[i]);
                            Run.InterDCLinksSlots.put(path_segments[i], (current_slots-slots_demand));
                            }

                 // update the tables of the frequency slots on each involved segment
                            
                Run.start_indexes.put(combined_address, Integer.parseInt(adopted_start));
                          
                 for(int k=0; k<path_segments.length; k++)
                        {
                String segment = path_segments[k];
                int[] temp_table  = null;
                int temp_slot_capacity = 0;       
                temp_table = (int[])(Run.InterDCLinksCapacities.get(segment)); 
                temp_slot_capacity = (int)(Run.InterDCLinksSlots.get(segment));
                
                for(int i=Integer.parseInt(adopted_start); i<(Integer.parseInt(adopted_start)+slots_demand); i++)
                {
                temp_table[i] = 1;
                }
                PhysicalTopology.check_adjacencies(temp_table, slots_demand);
                               
                Run.InterDCLinksCapacities.put(segment, temp_table);
                        
                        } 
                            
                          //update the intraDC resources: CPU and bandwidth
                         
                        current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                        
                        Run.intraDC_aggregate_traffic.put(DC_source, (current_agg_traffic_source-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(DC_source, (current_serv_utilization_source - cpu_demand));
                        Run.intraDC_aggregate_traffic.put(chosen_dest, (current_agg_traffic_dest-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(chosen_dest, (current_serv_utilization_dest - cpu_demand));
                       
                         //update the traversed distance       
                        Run.Distance.add((double)distance_chosen_path);  
                        
                        shortest_found = true;
                        
                           if(req_time_stamp > 0)
                        {
                         long current_time = (System.currentTimeMillis() / 1000);
                     //    System.out.println("current_time: "+current_time);
                         long difference = current_time - req_time_stamp;
                         Long lon = new Long(difference);
                         
                     //    System.out.println("difference: "+difference);
                        
                         Run.Retrial_Latency.add(lon.doubleValue());
                        }
                        
                    //    System.out.println("accepted equest");
                        
                         //update statistics
                       if(Run.ev_id > Run.NumberRequestsTransitorio)
                             {Run.established= Run.established + 1;}

                       //create and launch the release message
                         
                       int release_id = Run.ev_rel_id;
                       Run.ev_rel_id = Run.ev_rel_id + 1;
                       double service_time = Run.serviceTime[Run.ev_id-1];
                       Admin n2 = new Admin(0, new Release(release_id, combined_address, Integer.parseInt(adopted_start), cpu_demand, bandwidth_to_allocate, slots_demand, path), service_time);
                       n2.setAdmin(n2);
                       
                       if(Run.DC_large_high_lat.contains(chosen_dest))
                       {
                       Run.nb_times_core_DCs = Run.nb_times_core_DCs + 1;
                       Run.nb_times_large_DC = Run.nb_times_large_DC + 1;
                       }
                       else
                       {
                       Run.nb_times_core_DCs = Run.nb_times_core_DCs + 1;
                       Run.nb_times_medium_DC = Run.nb_times_medium_DC + 1;
                       }

                         }
                       //  }
                     
                                                                           
                       ind++;
       
                        // }
                        }
                        
                        // }
                        
         if((Run.ev_id > Run.NumberRequestsTransitorio) && ((!available_bandwidth) || (!available_cpu) || (!available_slots) || (!verified)))
                      {
                           
                  //    System.out.println("No resources are available at this moment => delay request");
                      
                       //create a new handler for each delayed request
                      int id = Run.timer_id;
                      Run.timer_id = Run.timer_id + 1;
                      Run.DelayedRequests.put(id, docService);//or put as key the value of the timer
                  //    System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      long current_time = (System.currentTimeMillis()/1000);//expressed in seconds
                                           
                      if(req_time_stamp > 0)
                        {
                      Run.DelayedRequests_latency.put(id, req_time_stamp);
                        }
                      else
                       {
                      Run.DelayedRequests_latency.put(id, current_time);
                       }
                      
                  //    System.out.println("ev_id of delayed request: "+Integer.parseInt(sessionID));
                      Run.DelayedRequests_ID.put(id, id_req);
                      
                      Admin nT = new Admin(0, new KillTimer(id), Run.timer_duration*1000);//Release doit prendre session comme argument!!!!!
                      nT.setAdmin(nT);
                      
                   //   System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      
                         }   
          
                        }
                  
             if(Run.DC_large.contains(DC_source))//block_access: destination in small datacenters => downlink       
              {
                        
                     //       System.out.println("Apply the most unloaded policy!");
                            
                            //choose the most unloaded path
                            TreeMap<String, Integer> temp_hashmap = new TreeMap();
                            temp_hashmap.putAll(Run.current_users_per_pipe);
                            HashMap<String,Integer> sorted_map = PhysicalTopology.sortHashMapByValues(Run.current_users_per_pipe, true);
                            Run.current_users_per_pipe.putAll(temp_hashmap);
                            String unloaded_pipe = "";
                            Iterator keys = sorted_map.keySet().iterator();
                            while (keys.hasNext())
                            {
                            String current_key = keys.next().toString();
                            String[] temp = current_key.toString().split("-", 2);
                            String temp_source = temp[0];
                            if(temp_source.equals(String.valueOf(DC_source)))
                            {
                            unloaded_pipe = current_key;
                            break;
                            }
                            }
                       //     System.out.println("most unloaded pipe: "+unloaded_pipe);
                            String[] result = unloaded_pipe.toString().split("-", 2);
                            String chosen_destination = result[1];

                            int chosen_dest = Integer.parseInt(chosen_destination);
                            
                        //    System.out.println("Source: "+DC_source+" dest: "+chosen_dest);
                            
                           KshortestPath ksh = new KshortestPath();
                           List<Path> paths = ksh.calculateKshortestPathDownLink(DC_source, chosen_dest, Run.kshortestpaths);//source, destination, number_of_paths
                            
                       //     System.out.println("Number of k-shortest paths: "+paths.size());
                       //     System.out.println("Components of the chosen path:");
                            int ind = 0;
                            boolean shortest_found = false;
                            while((ind<paths.size()) && (!shortest_found))
                            {
                       //     System.out.println("ind: "+ind);
                            Path path = paths.get(ind);//get first path => should be unique in this case
                            List<DirectedGraphNode> chosen_path = path.getNodeList();
                            
                            int distance_chosen_path = 0;
                            
                            String[] path_segments = new String[chosen_path.size()-1];
                            for(int i=0; i<chosen_path.size()-1; i++)
                            {
                         //   System.out.println(chosen_path.get(i));
                            path_segments[i] = chosen_path.get(i).toString().concat("-").concat(chosen_path.get(i+1).toString());
                            path_segments[i] = path_segments[i].replaceAll(" ", ""); //delete spaces in the string
                         //   System.out.println(path_segments[i]);
                            distance_chosen_path = distance_chosen_path + Run.weight_segments.get(path_segments[i]);
                            }
                            
                            //check the availability of the total number of frequency slots
                            
                            for(int i=0; i<path_segments.length; i++)
                            {
                            if(((int)(Run.InterDCLinksSlots.get(path_segments[i])) - slots_demand) >= 0)
                            {
                            available_slots = true;
                            //System.out.println("Total number of frequency slots is available!");
                            }
                            }
                            
                            String adopted_start = "0"; 
                            if(available_slots)
                            {
                            
                         //    System.out.println("Total number of frequency slots is available!");
                                
                       //check the availability of the continuous/contiguous frequency slots
                             
                            String combine_adresses = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
                  //          System.out.println("combine_adresses: "+combine_adresses);
                            String response = verify_spectrum_continuity(path_segments, slots_demand, combine_adresses);
                            String[] temp = response.split("-", 2);
                            
                            adopted_start = temp[0];
                            verified = Boolean.parseBoolean(temp[1]);
                            
                        //    System.out.println("verify spectrum continuity: "+verified);
                   //         System.out.println("adopted_start: "+adopted_start);
                            
                            }
                            //check the availability of the resources Intra-DC
                            
                  //      System.out.println("Check the servers utilization/aggregated traffic in the DC destination");
                        int bandwidth_to_allocate = slots_demand * Run.frequency_slot_capacity;//in Mbps
                        
                        int current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        int current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        int current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        int current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                         
                 //       System.out.println("bandwidth_to_allocate: "+bandwidth_to_allocate);
                         
                         if (( (current_agg_traffic_source - bandwidth_to_allocate) > 0) && ((current_agg_traffic_dest  - bandwidth_to_allocate) > 0))
                         {
                       //   System.out.println("Enough aggregated traffic");
                             available_bandwidth = true;
                             
                         }
                         
                         if (( (current_serv_utilization_source - cpu_demand) > 0) && ((current_serv_utilization_dest - cpu_demand) > 0))
                         {
                         
                         //   System.out.println("Enough utilization of the servers");
                             available_cpu = true;
                             
                         }
                         
                         /***************Allocate the VM in the DC*****************************/
                         
                    if((available_bandwidth) && (available_cpu) && (available_slots) && (verified))
                         {
                         
                          int nb_VM_to_allocate = 1 + (int)(Math.random() * ((Run.max_VM_bulk - 1) + 1));
                          
          //               System.out.println("nb_VM_to_allocate: "+nb_VM_to_allocate);
                         
                          DC.VMachine[] VM = new DC.VMachine[nb_VM_to_allocate];
                          
          //                System.out.println("requested_bandwidth per VM: "+((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate));
          //                System.out.println("requested_cpu: "+cpu_demand);

                          for (int i=0; i<nb_VM_to_allocate; i++)
                          {
                          int bw = ((slots_demand * Run.frequency_slot_capacity)/nb_VM_to_allocate);
                          double duration = Run.serviceTime[Run.ev_id-1];
                          DC.VMachine tempVM = new DC.VMachine(bw, cpu_demand, String.valueOf(Run.ev_id-1), duration);
                          VM[i] = tempVM;
                          }
                          
          //                System.out.println("Allocation Request accepted!!");
                          
                         //update the number of current users per segment if the request is accepted
                          
                          String combined_address = String.valueOf(DC_source).concat("-").concat(String.valueOf(chosen_dest));
          //                System.out.println("combined_address: "+combined_address);
          //                System.out.println("Run.current_users_per_pipe.size(): "+Run.current_users_per_pipe.size());
                         
                          int current_users = (int)Run.current_users_per_pipe.get(combined_address);
                          Run.current_users_per_pipe.put(combined_address, (current_users+1));
                            
                        //update the total number of frequency slots on all the segments of the path
                          
                            for(int i=0; i<path_segments.length; i++)
                            {
                            int current_slots = (int)Run.InterDCLinksSlots.get(path_segments[i]);
                            Run.InterDCLinksSlots.put(path_segments[i], (current_slots-slots_demand));
                            }

            //update the tables of the frequency slots on each involved segment
                            
                Run.start_indexes.put(combined_address, Integer.parseInt(adopted_start));
                          
                 for(int k=0; k<path_segments.length; k++)
                        {
                String segment = path_segments[k];
                int[] temp_table  = null;
                int temp_slot_capacity = 0;       
                temp_table = (int[])(Run.InterDCLinksCapacities.get(segment)); 
                temp_slot_capacity = (int)(Run.InterDCLinksSlots.get(segment));
                
                for(int i=Integer.parseInt(adopted_start); i<(Integer.parseInt(adopted_start)+slots_demand); i++)
                {
                temp_table[i] = 1;
                }
                PhysicalTopology.check_adjacencies(temp_table, slots_demand);
                               
                Run.InterDCLinksCapacities.put(segment, temp_table);
                        
                        }
                            
                          //update the intraDC resources: CPU and bandwidth
                         
                        current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(chosen_dest));
                        current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(chosen_dest));
                        
                        Run.intraDC_aggregate_traffic.put(DC_source, (current_agg_traffic_source-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(DC_source, (current_serv_utilization_source - cpu_demand));
                        Run.intraDC_aggregate_traffic.put(chosen_dest, (current_agg_traffic_dest-bandwidth_to_allocate));
                        Run.intraDC_servers_utilization.put(chosen_dest, (current_serv_utilization_dest - cpu_demand));
                       
                         //update the traversed distance       
                        Run.Distance.add((double)distance_chosen_path); 
                        
                        shortest_found = true;
                        
                     //   System.out.println("accepted equest");
                     
                        if(req_time_stamp > 0)
                        {
                         long current_time = (System.currentTimeMillis() / 1000);
                     //    System.out.println("current_time: "+current_time);
                         long difference = current_time - req_time_stamp;
                         Long lon = new Long(difference);
                         
                     //    System.out.println("difference: "+difference);
                        
                         Run.Retrial_Latency.add(lon.doubleValue());
                        }
                        
                         //update statistics
                       if(Run.ev_id > Run.NumberRequestsTransitorio)
                             {Run.established= Run.established + 1;}

                       //create and launch the release message
                         
                       int release_id = Run.ev_rel_id;
                       Run.ev_rel_id = Run.ev_rel_id + 1;
                       double service_time = Run.serviceTime[Run.ev_id-1];
                       Admin n2 = new Admin(0, new Release(release_id, combined_address, Integer.parseInt(adopted_start), cpu_demand, bandwidth_to_allocate, slots_demand, path), service_time);
                       n2.setAdmin(n2);

                      //   }
                      //  }
                          }
                    ind++;
           
                         }
                            
                if((Run.ev_id > Run.NumberRequestsTransitorio) && ((!available_bandwidth) || (!available_cpu) || (!available_slots) || (!verified)))
                      {
                           
                  //    System.out.println("No resources are available at this moment => delay request");
                      
                       //create a new handler for each delayed request
                      int id = Run.timer_id;
                      Run.timer_id = Run.timer_id + 1;
                      Run.DelayedRequests.put(id, docService);//or put as key the value of the timer
                  //    System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      long current_time = (System.currentTimeMillis()/1000);//expressed in seconds
                      
                      if(req_time_stamp > 0)
                        {
                      Run.DelayedRequests_latency.put(id, req_time_stamp);
                        }
                      else
                       {
                      Run.DelayedRequests_latency.put(id, current_time);
                       }
                      
                   //   System.out.println("ev_id of delayed request: "+Integer.parseInt(sessionID));
                      Run.DelayedRequests_ID.put(id, id_req);
                      
                      Admin nT = new Admin(0, new KillTimer(id), Run.timer_duration*1000);//Release doit prendre session comme argument!!!!!
                      nT.setAdmin(nT);
                      
                   //   System.out.println("Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                      
                         }                    
         
                        }
                  }//Unloaded policy
                       
     /********************************************************************************************************/                  
     /********************************************************************************************************/
                  }
                }
		break;
		}//switch

           return 0;

	}

/*************************************************************************************************/
        @Override
        protected void  stateSTART(Message msg){

                    serviceMes = (ServiceMessage)msg;
       
                    if(serviceMes.getCommand().equals("ServiceRequest")){
                    state = WAIT_FOR_REQUEST;
                    }
}

/*************************************************************************************************/
        
   public String verify_spectrum_continuity(String[] path_segments, int requested_freq_slots, String new_address)     
   {
        
   //     System.out.println("verify spectrum continuity");
        int start_index = (Integer)Run.start_indexes.get(new_address);
        
        Map<String,HashMap> starting_indexes = new ConcurrentHashMap<>(); 
        Map<String,Integer> starting_indexes_sizes = new ConcurrentHashMap<>(); 
               
        Map<String,int[]> involved_segments = new ConcurrentHashMap<>();
        int i = 0;
        while (i < path_segments.length)
                  {
              String segment = path_segments[i];
              int[] temp_table  = null;
       //       System.out.println("segment: "+segment);
              temp_table = (int[])(Run.InterDCLinksCapacities.get(segment));    
              involved_segments.put(segment, temp_table);
              i++;
               }
        
       final Set<Map.Entry<String, int[]>> entries = involved_segments.entrySet();

               for (Map.Entry<String, int[]> entry : entries) {
               String keyValue = entry.getKey();
        //       System.out.println("key: "+keyValue);
               int[] temp_tab = (int[])entry.getValue();
               PhysicalTopology physTopo = new PhysicalTopology() ;
               HashMap hm_temp = physTopo.check_adjacencies(temp_tab, requested_freq_slots);

               starting_indexes.put(keyValue, hm_temp);
               starting_indexes_sizes.put(keyValue, hm_temp.size());
                  }
                
               
      int maxValueInMap=(Collections.max(starting_indexes_sizes.values()));  // This will return max value in the Hashmap
      String key_max_value = "";
      for (Map.Entry<String, Integer> entry : starting_indexes_sizes.entrySet()) {  // Itrate through hashmap
      if (entry.getValue()==maxValueInMap) {
       //  System.out.println("Segment with maximum starting entries: "+entry.getKey());     // Print the key with max value
         key_max_value = entry.getKey();
         }
         }

             HashMap start_results = new HashMap();
             boolean ok = false;
             int current_value = 100000;
             int number_ok = 0;
           
             HashMap first_segment = starting_indexes.get(key_max_value);
             
       //      System.out.println("Print all the boxes of the first segment "+starting_indexes.size());
             
       //     System.out.println("first_segment.size(): "+first_segment.size());
               Iterator itPrint = first_segment.keySet().iterator();
                while (itPrint.hasNext()) 
                    {
                  current_value = (Integer)first_segment.get(itPrint.next());
                  //System.out.println("current_value: "+current_value);//0
                    
                    Iterator itOthers = starting_indexes.keySet().iterator();
                    while (itOthers.hasNext()) 
                    {
                    
                    String keyMap = itOthers.next().toString();
                    HashMap<Integer, Integer> temp_map = (HashMap<Integer, Integer>)starting_indexes.get(keyMap);//second segment

                    if (temp_map.containsValue(current_value))
                    {
        //                System.out.println("current_value exist in this hashmap");
                        number_ok++;
                    
                    }
                    
                    }//iteration on all the other segments
                    
                    if(number_ok == (starting_indexes.size()))
                    {
                    start_results.put(current_value, "true");
                    ok = true;
                    break;
                    }
                    else
                    {
                    number_ok = 0;
                    start_results.put(current_value, "false");
                    }
                    
                    }//iteration on all the possible starting points of the first segment of the path
             
                if (ok)
                    {
                     //Run.start_indexes.put(new_address, current_value);
                     start_index = current_value;}
                
        //        System.out.println("ok: "+ok);
             
       //        System.out.println("Print all the results for the segments");
                int adopted_start = 88888;
                boolean found = false;
                Iterator itPrintRes = start_results.keySet().iterator();
                while (itPrintRes .hasNext()) 
                    {
                  int key_value = (Integer)itPrintRes.next();
   //               System.out.println("starting index: "+key_value+" "+start_results.get(key_value));
                  
                  if(start_results.get(key_value).equals("true"))
                  {
                  found = true;
                  adopted_start = key_value;
                  break;
                  }
                  else
                  {
         //         System.out.println("NO CONTINUOUS PATH WAS FOUND!!!!");
                  found = false;
                  }

                    }
                
           // System.out.println("The adopted key value is: "+adopted_start); 
        
           return String.valueOf(adopted_start).concat("-").concat(String.valueOf(found));
        
  }     
/**************************************************************************************************/        
    }


