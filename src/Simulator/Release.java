package Simulator;

import Message.ServiceMessage;
import Utility.XMLUtility;
import org.w3c.dom.*;

public class Release implements Message.Event{
    
    private int idy = 0;
    private String addresses = "";
    private int startIndex = 0;
    private int myCPU = 0;
    private int myBW = 0;
    private int myFS = 0;
    private Path myPath = null;
    private int data_to_process = 0;

    public Release(int id)
	{
		this.idy = id;
	}

     public Release(int id, String stringa, int start_index, int cpu, int bandwidth, int freq_slots, Path path)
	{
		this.idy = id;
                this.addresses = stringa;
                this.startIndex = start_index;
                this.myCPU = cpu;
                this.myBW = bandwidth;
                this.myFS = freq_slots;
                this.myPath = path;
	}
     
      public Release(int id, String stringa, int start_index, int cpu, int bandwidth, int freq_slots, Path path, int data)
	{
		this.idy = id;
                this.addresses = stringa;
                this.startIndex = start_index;
                this.myCPU = cpu;
                this.myBW = bandwidth;
                this.myFS = freq_slots;
                this.myPath = path;
                this.data_to_process = data;
	}
    
    
public static Document createRequest(String addr1, String addr2, int currentID, int index, int mycpu, int bandwidth, int freq_slots, int mydata)
 {

     Document d = XMLUtility.getInstance().createDocument();

     Element root = d.createElement("ReleaseRequest");
     Element service = d.createElement("Service");
     Element freq = d.createElement("Frequency-Slots");
     Element cpu = d.createElement("CPU");
     Element bw = d.createElement("Bandwidth");
     Element ind = d.createElement("Index");
     Element myID = d.createElement("Identificator");
     Element source = d.createElement("DCSource");
     Element destination = d.createElement("DCDestination");
     Element data = d.createElement("Data");

     d.appendChild(root);

     root.appendChild(service);
     service.appendChild(freq);
     service.appendChild(cpu);
     service.appendChild(bw);
     service.appendChild(ind);
     service.appendChild(myID);
     service.appendChild(source);
     service.appendChild(destination);
     service.appendChild(data);

     freq.setTextContent(String.valueOf(freq_slots));
     cpu.setTextContent(String.valueOf(mycpu));
     bw.setTextContent(String.valueOf(bandwidth));
     ind.setTextContent(String.valueOf(index));
     myID.setTextContent(String.valueOf(currentID));
     source.setTextContent(addr1);
     destination.setTextContent(addr2);
     data.setTextContent(String.valueOf(mydata));

    // XMLUtility.getInstance().printXML(d);

     return d;
 }

    public void entering(Message.SimEnt locale) {
        
    String session = String.valueOf(idy);  

    String[] result = this.addresses.split("-", 2);

     String source = result[0];

     String destination = result[1];

    Document doc = createRequest(source, destination, idy, startIndex, myCPU, myBW, myFS, data_to_process);
    
    Run.paths_to_release.put(idy, myPath);
    
   //XMLUtility.getInstance().printXML(doc);
    
    ServiceMessage msg = new ServiceMessage(doc, session);

    FSM f = new ReleaseInterface(session);

    Run.fsmList.put(session,f);
    f.stateSTART(msg);

    f = (FSM)Run.fsmList.get(session);
    if(f != null)
    f.execute(msg);

    }//entering

}
