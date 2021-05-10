package Simulator;

import Message.ServiceMessage;
import Utility.XMLUtility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.w3c.dom.*;

public class DelayedRequest implements Message.Event{

    private String session = "";
    private Document my_document = null;
    private static int CPU = 500;
    private int event_id = 0;
    private long myTimeStamp = 0;
    
    private static int freq_slots = 4;//1FS = 12.5 Gbps

    public DelayedRequest()
	{ }


    public DelayedRequest(Document my_doc, int my_string, int ev_id, long timeStamp)
	{my_document = my_doc;
         session = String.valueOf(ev_id);
         event_id = ev_id;
         myTimeStamp = timeStamp;
        }

    protected String getText(Element el)
	{
		return el.getFirstChild().getNodeValue();
	}

  public Document createRequest(int id, int src, int cpu_dem, int slot_dem, long mystamp, int mydata)
 {

   //  System.out.println("value of stamp: "+mystamp);
     
     Document d = XMLUtility.getInstance().createDocument();

     Element root = d.createElement("ServiceRequest");
     Element service = d.createElement("Service");
     Element ID = d.createElement("ID");
     Element freq = d.createElement("Frequency-Slots");
     Element cpu = d.createElement("CPU");
     Element source = d.createElement("DC_Source");
     Element Stamp = d.createElement("Stamp");
     Element data = d.createElement("Data");
     

     d.appendChild(root);

     root.appendChild(service);
     service.appendChild(ID);
     service.appendChild(freq);
     service.appendChild(cpu);
     service.appendChild(source);
     service.appendChild(Stamp);
     service.appendChild(data);
     
     ID.setTextContent(String.valueOf(id));
     freq.setTextContent(String.valueOf(slot_dem));
     cpu.setTextContent(String.valueOf(cpu_dem));
     //int sourceID = getDC_source();
     source.setTextContent(String.valueOf(src));
     Stamp.setTextContent(String.valueOf(mystamp));
     data.setTextContent(String.valueOf(mydata));
     
   //  XMLUtility.getInstance().printXML(d);

     return d;
 }

    public void entering(Message.SimEnt locale) {
        
     
      // System.out.println("new delayed request: ");
       if(my_document == null) 
       {
      session = String.valueOf(event_id);
     
       int sourceID = getDC_source();
       int data_random = getData();
       my_document = createRequest(event_id, sourceID, CPU, freq_slots, myTimeStamp, data_random);
      // XMLUtility.getInstance().printXML(my_document);
       }
       else
       {
       
       int DC_source = Integer.parseInt(my_document.getElementsByTagName("DC_Source").item(0).getFirstChild().getNodeValue());
       int cpu_demand = Integer.parseInt(my_document.getElementsByTagName("CPU").item(0).getFirstChild().getNodeValue());
       int slots_demand = Integer.parseInt(my_document.getElementsByTagName("Frequency-Slots").item(0).getFirstChild().getNodeValue());
                      
       int id_req = Integer.parseInt(my_document.getElementsByTagName("ID").item(0).getFirstChild().getNodeValue());
       
       int data_to_process = Integer.parseInt(my_document.getElementsByTagName("Data").item(0).getFirstChild().getNodeValue());
                        
       my_document = createRequest(id_req, DC_source, cpu_demand, slots_demand, myTimeStamp, data_to_process);
                        
       }
    
    ServiceMessage msg = new ServiceMessage(my_document, session);

    FSM f = new ApplyDelayFirst(session);//ApplyPolicyLoad(session);
    
    Run.fsmList.put(session,f);
   
    f = (FSM)Run.fsmList.get(session);
    if(f != null)
    f.execute(msg);

    }//entering
    
    
    /***********************************************************************/
    
    private int randInt(int min, int max) {

    Random rand = new Random();

    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}
  
   /***********************************************************************/
   private int getData() {

    Random rand = new Random();

    int randomNum = rand.nextInt((Run.max_data - Run.min_data) + 1) + Run.min_data;

    return randomNum;
}

    /**********************************************************************/
    
    private int getDC_source()
    {
   ArrayList<Integer> DC_sources = new ArrayList<>();
   DC_sources.add(0);DC_sources.add(1);DC_sources.add(2);DC_sources.add(3);DC_sources.add(4);DC_sources.add(5);
   DC_sources.add(8);DC_sources.add(13);DC_sources.add(14);DC_sources.add(15);DC_sources.add(16);DC_sources.add(22);
   
    Collections.shuffle(DC_sources);
    Random rand = new Random();
    return DC_sources.get(rand.nextInt(DC_sources.size()));
    }
    
    /************************************************************************/

}
