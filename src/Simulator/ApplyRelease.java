
package Simulator;


import Message.*;
import java.util.Map;
import org.w3c.dom.Document;
import java.util.List;

abstract public class ApplyRelease extends FSM {

    final protected int START = 1;
    final protected int END = 2;
    final protected int WAIT_FOR_REL_REQUEST = 3;

    protected Message message;
    protected static ServiceMessage releaseMessage;
    protected String currentStateName;

    
    public ApplyRelease(String ses) {

        super(ses);
        
         currentStateName = "UNDEF";
         state = START;
    }

    @Override
    public int execute(Message msg) {

        switch (state) {
            case START:
                 {
                    stateSTART(msg);
            }
                break;

            case WAIT_FOR_REL_REQUEST:
                 {
                     
                     if(msg.getType() == Message.Service_MSG && msg.getCommand().equals("ReleaseRequest")){
                         
                                            
                        releaseMessage = (ServiceMessage) msg;
                        Document docRelease = releaseMessage.getValue();
                        
                        int DC_source = Integer.parseInt(docRelease.getElementsByTagName("DCSource").item(0).getFirstChild().getNodeValue());
                        int DC_dest = Integer.parseInt(docRelease.getElementsByTagName("DCDestination").item(0).getFirstChild().getNodeValue());
                        int cpu_to_release = Integer.parseInt(docRelease.getElementsByTagName("CPU").item(0).getFirstChild().getNodeValue());
                        int slots_to_release = Integer.parseInt(docRelease.getElementsByTagName("Frequency-Slots").item(0).getFirstChild().getNodeValue());
                        int bw_to_release = Integer.parseInt(docRelease.getElementsByTagName("Bandwidth").item(0).getFirstChild().getNodeValue());
                        int start_index = Integer.parseInt(docRelease.getElementsByTagName("Index").item(0).getFirstChild().getNodeValue());
                        
                        int id_rel = Integer.parseInt(docRelease.getElementsByTagName("Identificator").item(0).getFirstChild().getNodeValue());
                          
                          String combined_address = String.valueOf(DC_source).concat("-").concat(String.valueOf(DC_dest));
                          int current_users = (int)Run.current_users_per_pipe.get(combined_address);
                          Run.current_users_per_pipe.put(combined_address, (current_users-1));
                                                  
                          Path path = (Path)Run.paths_to_release.get(id_rel);
                          
                          List<DirectedGraphNode> chosen_path = path.getNodeList();
                            
                          String[] path_segments = new String[chosen_path.size()-1];

                            for(int i=0; i<chosen_path.size()-1; i++)
                            {
                        //    System.out.println(chosen_path.get(i));
                            path_segments[i] = chosen_path.get(i).toString().concat("-").concat(chosen_path.get(i+1).toString());
                            path_segments[i] = path_segments[i].replaceAll(" ", ""); //delete spaces in the string
                       //     System.out.println(path_segments[i]);
                            }
                          
                            for(int i=0; i<path_segments.length; i++)
                            {
                          //  System.out.println(path_segments[i]);
                            int current_slots = (int)Run.InterDCLinksSlots.get(path_segments[i]);
                            Run.InterDCLinksSlots.put(path_segments[i], (current_slots+slots_to_release));
                            }

                for(int j=0; j<path_segments.length; j++)
                            {
                String segment = path_segments[j];
                int[] temp_table  = null;
                int temp_slot_capacity = 0;            
              //  System.out.println("segment to release: "+segment);
                temp_table = (int[])(Run.InterDCLinksCapacities.get(segment)); 
                temp_slot_capacity = (int)(Run.InterDCLinksSlots.get(segment));
                
                for(int i=start_index; i<(start_index+slots_to_release); i++)
                {
                temp_table[i] = 0;
                }

                PhysicalTopology.check_adjacencies(temp_table, slots_to_release);
                Run.InterDCLinksSlots.put(segment, (temp_slot_capacity + slots_to_release));
                Run.InterDCLinksCapacities.put(segment, temp_table);
                            }  
                         
                        int current_agg_traffic_source = (Integer)(Run.intraDC_aggregate_traffic.get(DC_source));
                        int current_serv_utilization_source = (Integer)(Run.intraDC_servers_utilization.get(DC_source));
                        
                        int current_agg_traffic_dest = (Integer)(Run.intraDC_aggregate_traffic.get(DC_dest));
                        int current_serv_utilization_dest = (Integer)(Run.intraDC_servers_utilization.get(DC_dest));
                        
                        Run.intraDC_aggregate_traffic.put(DC_source, (current_agg_traffic_source + bw_to_release));
                        Run.intraDC_servers_utilization.put(DC_source, (current_serv_utilization_source + cpu_to_release));
                        Run.intraDC_aggregate_traffic.put(DC_dest, (current_agg_traffic_dest + bw_to_release));
                        Run.intraDC_servers_utilization.put(DC_dest, (current_serv_utilization_dest + cpu_to_release));

                        if(Run.DelayedRequests.size() > 0)
                        {
                       
                        Map.Entry<Integer,Document> entry = Run.DelayedRequests.entrySet().iterator().next();
                        Integer key= entry.getKey();
                        Document Doc_value=entry.getValue();

                        long delayed_time = Run.DelayedRequests_latency.get(key);

                        int ev_id_rescheduled = (int)Run.DelayedRequests_ID.get(key);

                        Admin n1 = new Admin(0, new DelayedRequest(Doc_value, key, ev_id_rescheduled, delayed_time), 0);
                        n1.setAdmin(n1);
                        
                        Run.DelayedRequests.remove(key);
                        Run.DelayedRequests_latency.remove(key);
                        Run.DelayedRequests_ID.remove(key);
                     //   System.out.println("After: Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
                        }
             
             state = END;
             stateEND();
             
                     }
                
                }
                break;

          
            case END:
                 {
                    currentStateName = "END";
                  //  System.out.println("state end");
                    stateEND();
            }
                break;
        }

        return 0;

    }

    @Override
    protected void  stateSTART(Message msg){

                    message = msg;
                     if(message.getCommand().equals("ReleaseRequest")){
                    
                    state = WAIT_FOR_REL_REQUEST;
               }
}


}//class





