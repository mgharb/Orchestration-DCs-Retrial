package Simulator;

import Message.ServiceMessage;
import Utility.XMLUtility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.w3c.dom.*;

public class Request implements Message.Event{

    private String session = "";
    private Document my_document = null;
    private static int CPU = 500;
    private static int freq_slots = 4;
    private long myTimeStamp = 0;
    

    public Request()
	{ }

    protected String getText(Element el)
	{
		return el.getFirstChild().getNodeValue();
	}

  public Document createRequest(int id, long stamp)
 {

     Document d = XMLUtility.getInstance().createDocument();

     Element root = d.createElement("ServiceRequest");
     Element service = d.createElement("Service");
     Element ID = d.createElement("ID");
     Element freq = d.createElement("Frequency-Slots");
     Element cpu = d.createElement("CPU");
     Element source = d.createElement("DC_Source");
     Element data = d.createElement("Data");
     Element Stamp = d.createElement("Stamp");

     d.appendChild(root);

     root.appendChild(service);
     service.appendChild(ID);
     service.appendChild(freq);
     service.appendChild(cpu);
     service.appendChild(source);
     service.appendChild(Stamp);
     service.appendChild(data);
     
     ID.setTextContent(String.valueOf(id));
     freq.setTextContent(String.valueOf(freq_slots));
     cpu.setTextContent(String.valueOf(CPU));
     int sourceID = getDC_source();
     source.setTextContent(String.valueOf(sourceID));
     Stamp.setTextContent(String.valueOf(stamp));
     int data_to_process = getData();
     data.setTextContent(String.valueOf(data_to_process));
     
   //  XMLUtility.getInstance().printXML(d);

     return d;
 }

    public void entering(Message.SimEnt locale) {

       if(my_document == null) 
       {
    
       session = String.valueOf(Run.ev_id);
       System.out.println("new request: "+Run.ev_id);
       Run.generated = Run.generated + 1; 
       my_document = createRequest(Run.ev_id, myTimeStamp);
       
       Run.ev_id = Run.ev_id + 1;
      // XMLUtility.getInstance().printXML(my_document);
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
