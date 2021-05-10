package Simulator;



public class KillTimer implements Message.Event{

       private int ID = 0;


    public KillTimer(int id)
	{
        ID = id;
        }


    public void entering(Message.SimEnt locale) {

                                           
       if(Run.DelayedRequests.get(ID) != null)
         {
         Run.DelayedRequests.remove(ID);
          
       //  System.out.println("End of timer: Run.DelayedRequests.size(): "+Run.DelayedRequests.size());
         
         int ev_id = (int)Run.DelayedRequests_ID.get(ID);
         
         Run.DelayedRequests_latency.remove(ID);
         Run.DelayedRequests_ID.remove(ID);

         Run.blocked_totale = Run.blocked_totale + 1;

       }
       

    }//entering
    


}
