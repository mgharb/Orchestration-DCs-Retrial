package Simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.TreeMap;


public class KshortestPath {
    
    Map<Integer, DirectedGraphNode> nodesId = new HashMap<>();
    
       public List<Path> calculateKshortestPathUpLink(int start, int target, int Npaths)
    {
               
        DirectedGraphWeightFunction weightFunction = new DirectedGraphWeightFunction();
        
      //  System.out.println("calculate K shortest path");
        
        /**************************************uplink*************************************************************/
   DirectedGraphNode a = new DirectedGraphNode(0); DirectedGraphNode b = new DirectedGraphNode(1); DirectedGraphNode c = new DirectedGraphNode(2);
   DirectedGraphNode d = new DirectedGraphNode(3); DirectedGraphNode e = new DirectedGraphNode(4); DirectedGraphNode f = new DirectedGraphNode(5);     
   DirectedGraphNode g = new DirectedGraphNode(6); DirectedGraphNode h = new DirectedGraphNode(7); DirectedGraphNode i = new DirectedGraphNode(8);      
   DirectedGraphNode j = new DirectedGraphNode(9); DirectedGraphNode k = new DirectedGraphNode(10); DirectedGraphNode l = new DirectedGraphNode(11);    
   DirectedGraphNode m = new DirectedGraphNode(12); DirectedGraphNode n = new DirectedGraphNode(13); DirectedGraphNode o = new DirectedGraphNode(14);     
   DirectedGraphNode p = new DirectedGraphNode(15); DirectedGraphNode q = new DirectedGraphNode(16); DirectedGraphNode r = new DirectedGraphNode(17);    
   DirectedGraphNode s = new DirectedGraphNode(18); DirectedGraphNode t = new DirectedGraphNode(19); DirectedGraphNode u = new DirectedGraphNode(20);     
   DirectedGraphNode v = new DirectedGraphNode(21); DirectedGraphNode w = new DirectedGraphNode(22); DirectedGraphNode x = new DirectedGraphNode(23);   
   DirectedGraphNode y = new DirectedGraphNode(24);   
   
   //associate nodes to id      
   nodesId.put(0, a); nodesId.put(1, b); nodesId.put(2, c); nodesId.put(3, d);nodesId.put(4, e); nodesId.put(5, f); nodesId.put(6, g); 
   nodesId.put(7, h); nodesId.put(8, i); nodesId.put(9, j); nodesId.put(10, k);nodesId.put(11, l); nodesId.put(12, m); nodesId.put(13, n); 
   nodesId.put(14, o); nodesId.put(15, p); nodesId.put(16, q); nodesId.put(17, r);nodesId.put(18, s); nodesId.put(19, t); nodesId.put(20, u); 
   nodesId.put(21, v); nodesId.put(22, w);nodesId.put(23, x); nodesId.put(24, y); 
   
      //associate weights in a DIRECTED graph :UPLINK   
   a.addChild(g); weightFunction.put(a, g, 12); a.addChild(x); weightFunction.put(a, x, 12);     
   b.addChild(g); weightFunction.put(b, g, 12); b.addChild(x); weightFunction.put(b, x, 12);  
   c.addChild(g); weightFunction.put(c, g, 12); c.addChild(y); weightFunction.put(c, y, 12); 
   d.addChild(g); weightFunction.put(d, g, 12); d.addChild(y); weightFunction.put(d, y, 12); 
   e.addChild(x); weightFunction.put(e, x, 12); e.addChild(y); weightFunction.put(e, y, 12); 
   f.addChild(x); weightFunction.put(f, x, 12); f.addChild(y); weightFunction.put(f, y, 12); 
   g.addChild(h); weightFunction.put(g, h, 36); g.addChild(i); weightFunction.put(g, i, 37); 
   h.addChild(j); weightFunction.put(h, j, 41); j.addChild(i); weightFunction.put(j, i, 88);
   i.addChild(k); weightFunction.put(i, k, 278); i.addChild(n); weightFunction.put(i, n, 208);
   j.addChild(w); weightFunction.put(j, w, 182); k.addChild(l); weightFunction.put(k, l, 144);
   l.addChild(m); weightFunction.put(l, m, 114); l.addChild(n); weightFunction.put(l, n, 120);
   m.addChild(n); weightFunction.put(m, n, 157); m.addChild(o); weightFunction.put(m, o, 306);
   n.addChild(o); weightFunction.put(n, o, 298); w.addChild(n); weightFunction.put(w, n, 316);
   v.addChild(w); weightFunction.put(v, w, 85); v.addChild(u); weightFunction.put(v, u, 64);
   t.addChild(u); weightFunction.put(t, u, 74); t.addChild(s); weightFunction.put(t, s, 86);      
   s.addChild(r); weightFunction.put(s, r, 143); t.addChild(q); weightFunction.put(t, q, 187);
   w.addChild(q); weightFunction.put(w, q, 224); w.addChild(p); weightFunction.put(w, p, 353);
   n.addChild(p); weightFunction.put(n, p, 258); o.addChild(p); weightFunction.put(o, p, 174);
   p.addChild(q); weightFunction.put(p, q, 275); q.addChild(r); weightFunction.put(q, r, 179);
  // v.addChild(a); weightFunction.put(v, a, 12); v.addChild(b); weightFunction.put(v, b, 12);
  
  x.addChild(v);weightFunction.put(x, v, 0);  x.addChild(j);weightFunction.put(x, j, 267);
  y.addChild(t);weightFunction.put(y, t, 0);      
        /******************************************************************************************/
          
          List<Path> paths = new DefaultKShortestPathFinder().findShortestPaths(nodesId.get(start), nodesId.get(target), weightFunction, Npaths);
          
          List<Path> returned_paths = new ArrayList<>();
          
        //  System.out.println("number of paths: "+paths.size());
          
          TreeMap map = new TreeMap();
                 
          for(int ind=0; ind<paths.size(); ind++)
          {
          Path path = paths.get(ind);//get ième path
                 
          List<DirectedGraphNode> nodeList = path.getNodeList();
          
        //  System.out.println("path length in terms of nodes: "+nodeList.size());
          
          double distance = 0;
          for(int indexi=1; indexi<nodeList.size(); indexi++)
          {
              DirectedGraphNode tempa = nodeList.get(indexi-1);
              DirectedGraphNode tempb = nodeList.get(indexi);
              distance = distance + weightFunction.get(tempa, tempb);
          }
          
        //  System.out.println("path length in terms of distance: "+distance);
          
          map.put(distance, path);
                              
       //   System.out.println(Arrays.toString(nodeList.toArray()));
        }
          
       //   System.out.println("map.size(): "+map.size());
          
         Iterator keys = map.keySet().iterator();
         
            while (keys.hasNext())
                          {
                              double currentPath = (double)keys.next();
                              
                              Path myPath = (Path)map.get(currentPath);
                              
                              returned_paths.add(myPath);
                              
                          }
          
             for(int ind=0; ind<returned_paths.size(); ind++)
          {
          Path path = returned_paths.get(ind);//get ième path
                 
          List<DirectedGraphNode> nodeList = path.getNodeList();
          
       //  System.out.println("path length: "+nodeList.size());
          
       //   System.out.println("path length in terms of nodes: "+nodeList.size());
          
          double distance = 0;
          for(int indexi=0; (indexi+1)<nodeList.size(); indexi++)
          {
              DirectedGraphNode tempa = nodeList.get(indexi);
              DirectedGraphNode tempb = nodeList.get(indexi+1);
              distance = distance + weightFunction.get(tempa, tempb);
          }
          
        //  System.out.println("path length in terms of distance: "+distance);
          
        //  System.out.println(Arrays.toString(nodeList.toArray()));

        }
          
         //   System.out.println("number of returned paths: "+returned_paths.size());
            
          return returned_paths;
    }
       
      public List<Path> calculateKshortestPathDownLink(int start, int target, int Npaths)
    {
        
        DirectedGraphWeightFunction weightFunction = new DirectedGraphWeightFunction();
        
      //  System.out.println("calculate K shortest path");
        
        /**************************************uplink*************************************************************/
   DirectedGraphNode a = new DirectedGraphNode(0); DirectedGraphNode b = new DirectedGraphNode(1); DirectedGraphNode c = new DirectedGraphNode(2);
   DirectedGraphNode d = new DirectedGraphNode(3); DirectedGraphNode e = new DirectedGraphNode(4); DirectedGraphNode f = new DirectedGraphNode(5);     
   DirectedGraphNode g = new DirectedGraphNode(6); DirectedGraphNode h = new DirectedGraphNode(7); DirectedGraphNode i = new DirectedGraphNode(8);      
   DirectedGraphNode j = new DirectedGraphNode(9); DirectedGraphNode k = new DirectedGraphNode(10); DirectedGraphNode l = new DirectedGraphNode(11);    
   DirectedGraphNode m = new DirectedGraphNode(12); DirectedGraphNode n = new DirectedGraphNode(13); DirectedGraphNode o = new DirectedGraphNode(14);     
   DirectedGraphNode p = new DirectedGraphNode(15); DirectedGraphNode q = new DirectedGraphNode(16); DirectedGraphNode r = new DirectedGraphNode(17);    
   DirectedGraphNode s = new DirectedGraphNode(18); DirectedGraphNode t = new DirectedGraphNode(19); DirectedGraphNode u = new DirectedGraphNode(20);     
   DirectedGraphNode v = new DirectedGraphNode(21); DirectedGraphNode w = new DirectedGraphNode(22);    
   DirectedGraphNode x = new DirectedGraphNode(23); DirectedGraphNode y = new DirectedGraphNode(24);    
   
   //associate nodes to id      
   nodesId.put(0, a); nodesId.put(1, b); nodesId.put(2, c); nodesId.put(3, d);nodesId.put(4, e); nodesId.put(5, f); nodesId.put(6, g); 
   nodesId.put(7, h); nodesId.put(8, i); nodesId.put(9, j); nodesId.put(10, k);nodesId.put(11, l); nodesId.put(12, m); nodesId.put(13, n); 
   nodesId.put(14, o); nodesId.put(15, p); nodesId.put(16, q); nodesId.put(17, r);nodesId.put(18, s); nodesId.put(19, t); nodesId.put(20, u); 
   nodesId.put(21, v); nodesId.put(22, w); nodesId.put(23, x); nodesId.put(24, y); 
   
   //associate weights in a DIRECTED graph :DOWNLINK 
   q.addChild(p); weightFunction.put(q, p, 275); r.addChild(q); weightFunction.put(r, q, 179);
   p.addChild(o); weightFunction.put(p, o, 174); o.addChild(m); weightFunction.put(o, m, 306);
   o.addChild(n); weightFunction.put(o, n, 298); p.addChild(n); weightFunction.put(p, n, 258);
   p.addChild(w); weightFunction.put(p, w, 353); q.addChild(w); weightFunction.put(q, w, 224);
   r.addChild(s); weightFunction.put(r, s, 143); q.addChild(t); weightFunction.put(q, t, 187);
   s.addChild(t); weightFunction.put(s, t, 86); u.addChild(t); weightFunction.put(u, t, 74);
   u.addChild(v); weightFunction.put(u, v, 64); w.addChild(v); weightFunction.put(w, v, 85);
   n.addChild(w); weightFunction.put(n, w, 316); n.addChild(m); weightFunction.put(n, m, 157);
   m.addChild(l); weightFunction.put(m, l, 114); n.addChild(l); weightFunction.put(n, l, 120);
   n.addChild(i); weightFunction.put(n, i, 208); l.addChild(k); weightFunction.put(l, k, 144);
   k.addChild(i); weightFunction.put(k, i, 278); w.addChild(j); weightFunction.put(w, j, 182);
   i.addChild(j); weightFunction.put(i, j, 88); i.addChild(g); weightFunction.put(i, g, 37); 
   h.addChild(g); weightFunction.put(h, g, 36); j.addChild(h); weightFunction.put(j, h, 41);
   g.addChild(a); weightFunction.put(g, a, 12); x.addChild(a); weightFunction.put(x, a, 12);
   g.addChild(b); weightFunction.put(g, b, 12); x.addChild(b); weightFunction.put(x, b, 12);
   g.addChild(c); weightFunction.put(g, c, 12); y.addChild(c); weightFunction.put(y, c, 12);
   g.addChild(d); weightFunction.put(g, d, 12); y.addChild(d); weightFunction.put(y, d, 12);
   x.addChild(e); weightFunction.put(x, e, 12); y.addChild(e); weightFunction.put(y, e, 12); 
   x.addChild(f); weightFunction.put(x, f, 12); y.addChild(f); weightFunction.put(y, f, 12); 
  // d.addChild(t); weightFunction.put(d, t, 12); c.addChild(t); weightFunction.put(c, t, 12); 
        
   v.addChild(x);weightFunction.put(v, x, 0);  j.addChild(x);weightFunction.put(j, x, 267);
   t.addChild(y);weightFunction.put(t, y, 0);   
        /******************************************************************************************/
          
          List<Path> paths = new DefaultKShortestPathFinder().findShortestPaths(nodesId.get(start), nodesId.get(target), weightFunction, Npaths);
          
          List<Path> returned_paths = new ArrayList<>();
          
        //  System.out.println("number of paths: "+paths.size());
          
          TreeMap map = new TreeMap();
                 
          for(int ind=0; ind<paths.size(); ind++)
          {
          Path path = paths.get(ind);//get ième path
                 
          List<DirectedGraphNode> nodeList = path.getNodeList();
          
        //  System.out.println("path length in terms of nodes: "+nodeList.size());
          
          double distance = 0;
          for(int indexi=1; indexi<nodeList.size(); indexi++)
          {
              DirectedGraphNode tempa = nodeList.get(indexi-1);
              DirectedGraphNode tempb = nodeList.get(indexi);
              distance = distance + weightFunction.get(tempa, tempb);
          }
          
         // System.out.println("path length in terms of distance: "+distance);
          
          map.put(distance, path);
                              
        //  System.out.println(Arrays.toString(nodeList.toArray()));
        }
          
       //   System.out.println("map.size(): "+map.size());
          
         Iterator keys = map.keySet().iterator();
         
            while (keys.hasNext())
                          {
                              double currentPath = (double)keys.next();
                              
                           //   System.out.println("current key: "+currentPath);
                              
                              Path myPath = (Path)map.get(currentPath);
                              
                              returned_paths.add(myPath);
                              
                          }
          
        for(int ind=0; ind<returned_paths.size(); ind++)
          {
          Path path = returned_paths.get(ind);//get ième path
                 
          List<DirectedGraphNode> nodeList = path.getNodeList();
          
        //  System.out.println("path length: "+nodeList.size());
          
       //   System.out.println("path length in terms of nodes: "+nodeList.size());
          
          double distance = 0;
          for(int indexi=0; (indexi+1)<nodeList.size(); indexi++)
          {
              DirectedGraphNode tempa = nodeList.get(indexi);
              DirectedGraphNode tempb = nodeList.get(indexi+1);
              distance = distance + weightFunction.get(tempa, tempb);
          }
          
        //  System.out.println("path length in terms of distance: "+distance);
          
       //   System.out.println(Arrays.toString(nodeList.toArray()));

        }
     
      //  System.out.println("number of returned paths: "+returned_paths.size());
     
          return returned_paths;
    }
              
}

    
     
        
     
 