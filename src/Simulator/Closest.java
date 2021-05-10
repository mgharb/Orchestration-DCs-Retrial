
package Simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Closest {
    
    
 /******************************************************************************************************************************/
               
      public int checkDest(int DC_source)
      {
      boolean found = false;
      HashMap DC_distances = new HashMap();
      int DC_destination = 0;
      
      
      if(Run.DC_small.contains(DC_source))
      {
  //    System.out.println("DC source is small: look for Dest in the large pool");
      for(int i=0; i<Run.DC_large.size(); i++)
      {
 //     System.out.println("Possible destination: "+DC_large.get(i));
      Dijkstra dij = new Dijkstra();
      int distance = dij.getDistanceShortestPath(DC_source, Run.DC_large.get(i));
      DC_distances.put(Run.DC_large.get(i), distance);//contains all the possible DC_destinations with their corresponding destinations
  //    System.out.println("distance: "+distance);
      }//for
      HashMap sorted_distances = sortByValues(DC_distances);
      DC_destination = (int)getFirstKey(sorted_distances);
      }//if DC_source is small
      
      else if (Run.DC_large.contains(DC_source))
      {
   //   System.out.println("DC source is large: look for Dest in the small pool");
      for(int i=0; i<Run.DC_small.size(); i++)
      {
  //    System.out.println("Possible destination: "+DC_small.get(i));
      Dijkstra dij = new Dijkstra();
      int distance = dij.getDistanceShortestPath(DC_source, Run.DC_small.get(i));
      DC_distances.put(Run.DC_small.get(i), distance);//contains all the possible DC_destinations with their corresponding destinations
 //     System.out.println("distance: "+distance);
      }//for
      HashMap sorted_distances = sortByValues(DC_distances);
      DC_destination = (int)getFirstKey(sorted_distances);
      }//if DC_source is large
      
      
      
  //    System.out.println("Chosen destination: "+DC_destination);

      return DC_destination;
      }
    
 /****************************************************************************************************/
      
       public ArrayList<Integer> checkPossibleDest(int DC_source)
      {

      ArrayList<Integer> DC_destination = new ArrayList();
        
      if(Run.DC_small.contains(DC_source))
      {
     // System.out.println("DC source is small: look for Dest in the large pool");
      for(int i=0; i<Run.DC_large.size(); i++)
      {
      //System.out.println("Possible destination: "+DC_large.get(i));
      DC_destination.add((int)(Run.DC_large.get(i)));
      }//for
      }//if DC_source is small
      
      else if (Run.DC_large.contains(DC_source))
      {
   //   System.out.println("DC source is large: look for Dest in the small pool");
      for(int i=0; i<Run.DC_small.size(); i++)
      {
  //   System.out.println("Possible destination: "+DC_small.get(i));
       DC_destination.add((int)(Run.DC_small.get(i)));
      }//for  
      }//if DC_source is large
      
      return DC_destination;
      }     
    
/****************************************************************************************************/
  private HashMap sortByValues(HashMap map) { 
       List list = new LinkedList(map.entrySet());
       // Defined Custom Comparator here
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
       });

       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }
  
/*************************************************************************************************/  
  
  public  static <K, V>  K getFirstKey(HashMap<K, V> map) {
    Iterator<K> i = map.keySet().iterator();
    return i.hasNext() ? i.next() : null;
}
  
/*************************************************************************************************/  
    
}
