
package DC;


import java.util.HashMap;


public class VMachine extends NwMachines {

    public double lifeDuration;

    public HashMap connLsit;

    public VMachine(){
        super();
    }
    
    public VMachine(int bw,int cpu,String id,double duration)
    {
        super(bw, cpu, id);
        lifeDuration=duration;
      
    }
    public  void printVM(){
        
        //System.out.println("VM id: "+this.Id+" bw "+this.bwCapacity+" cpu "+this.cpuCapacity+ " duration "+lifeDuration+" connectionlist"+connLsit
       //         +" total reserved cpu "+totalCpuReserved+" total reserved bw "+totalBwReserved +"server is "+server);

     //  System.out.println("VM id: "+this.Id+" bw "+this.bwCapacity+" cpu "+this.cpuCapacity+ " duration "+lifeDuration+" total reserved cpu ");

     //  System.out.println("Maximum traffic generated by VM "+this.Id+": "+this.bwCapacity);

   
    }
    
}
