
package Simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class PhysicalTopology {
    
         
    public PhysicalTopology()
    {}
  
/*******************************************************************************************************************/
      
    public static void assign_weight_segments()
    {
    
    Run.weight_segments.put("0-6", 12);
    Run.weight_segments.put("0-23", 12);
    Run.weight_segments.put("1-6", 12);
    Run.weight_segments.put("1-23", 12);
    Run.weight_segments.put("2-6", 12);
    Run.weight_segments.put("2-24", 12);
    Run.weight_segments.put("3-6", 12);
    Run.weight_segments.put("3-24", 12);
    Run.weight_segments.put("4-23", 12);
    Run.weight_segments.put("4-24", 12);
  
    Run.weight_segments.put("5-23", 12);
    Run.weight_segments.put("5-24", 12);
    
    Run.weight_segments.put("23-21", 0);
    Run.weight_segments.put("24-19", 0);
    
    Run.weight_segments.put("23-9", 267);
    Run.weight_segments.put("9-23", 267);
    
    Run.weight_segments.put("6-0", 12);
    Run.weight_segments.put("6-1", 12);
    Run.weight_segments.put("6-2", 12);
    Run.weight_segments.put("6-3", 12);
    Run.weight_segments.put("6-7", 36);
    Run.weight_segments.put("6-8", 37);
    
    Run.weight_segments.put("7-6", 36);
    Run.weight_segments.put("7-9", 41);
    
    Run.weight_segments.put("8-6", 37);
    Run.weight_segments.put("8-9", 88);
    Run.weight_segments.put("8-13", 208);
    Run.weight_segments.put("8-10", 278);
   
    Run.weight_segments.put("9-7", 41);
    Run.weight_segments.put("9-8", 88);//8.5
    Run.weight_segments.put("9-22", 182);
    
    Run.weight_segments.put("10-8", 278);
    Run.weight_segments.put("10-11", 144);
    
    Run.weight_segments.put("11-10", 144);
    Run.weight_segments.put("11-13", 120);
    Run.weight_segments.put("11-12", 114);
    
    Run.weight_segments.put("12-11", 114);
    Run.weight_segments.put("12-13", 157);
    Run.weight_segments.put("12-14", 306);
    
    Run.weight_segments.put("13-12", 157);
    Run.weight_segments.put("13-11", 120);
    Run.weight_segments.put("13-8", 208);
    Run.weight_segments.put("13-22", 316);
    Run.weight_segments.put("13-15", 258);
    Run.weight_segments.put("13-14", 298);
    
    Run.weight_segments.put("14-12", 306);
    Run.weight_segments.put("14-13", 298);
    Run.weight_segments.put("14-15", 174);
    
    Run.weight_segments.put("15-14", 174);
    Run.weight_segments.put("15-13", 258);
    Run.weight_segments.put("15-22", 353);
    Run.weight_segments.put("15-16", 275);
   
    Run.weight_segments.put("16-15", 275);
    Run.weight_segments.put("16-22", 224);
    Run.weight_segments.put("16-19", 187);
    Run.weight_segments.put("16-17", 179);
    
    Run.weight_segments.put("17-16", 179);
    Run.weight_segments.put("17-18", 143);
    
    Run.weight_segments.put("18-17", 143);
    Run.weight_segments.put("18-19", 86);
    
    Run.weight_segments.put("19-18", 86); 
    Run.weight_segments.put("19-16", 187);
    Run.weight_segments.put("19-20", 74);
    Run.weight_segments.put("24-2", 12);
    Run.weight_segments.put("24-3", 12);
    Run.weight_segments.put("24-4", 12);
    Run.weight_segments.put("24-5", 12);
    
    Run.weight_segments.put("20-19", 74);
    Run.weight_segments.put("20-21", 64);
    
    Run.weight_segments.put("21-20", 64);
    Run.weight_segments.put("21-22", 85);
    Run.weight_segments.put("23-0", 12);
    Run.weight_segments.put("23-1", 12);
    Run.weight_segments.put("23-4", 12);
    Run.weight_segments.put("23-5", 12);
    
    Run.weight_segments.put("21-23", 0);
    Run.weight_segments.put("19-24", 0);
    
    Run.weight_segments.put("22-21", 85);
    Run.weight_segments.put("22-16", 224);
    Run.weight_segments.put("22-15", 353);
    Run.weight_segments.put("22-13", 316);
    Run.weight_segments.put("22-9", 182); 
    }
           
    
 /*******************************************************************************************/
    static public void identifyDCs()
   {
    Run.DC_small.add(0);Run.DC_small.add(1);Run.DC_small.add(2);Run.DC_small.add(3);Run.DC_small.add(4);Run.DC_small.add(5);
    Run.DC_large.add(8);Run.DC_large.add(22);Run.DC_large.add(13);Run.DC_large.add(14);Run.DC_large.add(15);Run.DC_large.add(16);
    
    Run.DC_large_high_lat.add(14);Run.DC_large_high_lat.add(15);Run.DC_large_high_lat.add(16);
   }
 /******************************************************************************************/
   static public void topology_linksCapacities()
   {
   
   int[] temp_tab = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab.length; i++){temp_tab[i]=0;}
   Run.InterDCLinksCapacities.put("0-6", temp_tab);
            
   int[] temp_tabb = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb.length; i++){temp_tabb[i]=0;}
   Run.InterDCLinksCapacities.put("6-0", temp_tabb);
   
   int[] temp_tab1 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab1.length; i++){temp_tab1[i]=0;}
   Run.InterDCLinksCapacities.put("0-23", temp_tab1);
   
   int[] temp_tabb1 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb1.length; i++){temp_tabb1[i]=0;}
   Run.InterDCLinksCapacities.put("23-0", temp_tabb1);
   
   int[] temp_tab2 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab2.length; i++){temp_tab2[i]=0;}
   Run.InterDCLinksCapacities.put("1-6", temp_tab2);
   
   int[] temp_tabb2 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb2.length; i++){temp_tabb2[i]=0;}
   Run.InterDCLinksCapacities.put("6-1", temp_tabb2);
   
   int[] temp_tab3 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab3.length; i++){temp_tab3[i]=0;}
   Run.InterDCLinksCapacities.put("1-23", temp_tab3);
   
   int[] temp_tabb3 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb3.length; i++){temp_tabb3[i]=0;}
   Run.InterDCLinksCapacities.put("23-1", temp_tabb3);
   
   int[] temp_tab4 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab4.length; i++){temp_tab4[i]=0;}
   Run.InterDCLinksCapacities.put("2-6", temp_tab4);
   
   int[] temp_tabb4 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb4.length; i++){temp_tabb4[i]=0;}
   Run.InterDCLinksCapacities.put("6-2", temp_tabb4);
   
   int[] temp_tab5 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab5.length; i++){temp_tab5[i]=0;}
   Run.InterDCLinksCapacities.put("2-24", temp_tab5);
  
   int[] temp_tabb5 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb5.length; i++){temp_tabb5[i]=0;}
   Run.InterDCLinksCapacities.put("24-2", temp_tabb5);
   
   int[] temp_tab6 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab6.length; i++){temp_tab6[i]=0;}
   Run.InterDCLinksCapacities.put("3-6", temp_tab6);
   
   int[] temp_tabb6 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb6.length; i++){temp_tabb6[i]=0;}
   Run.InterDCLinksCapacities.put("6-3", temp_tabb6);
   
   int[] temp_tab7 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab7.length; i++){temp_tab7[i]=0;}
   Run.InterDCLinksCapacities.put("3-24", temp_tab7);
   
   int[] temp_tabb7 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb7.length; i++){temp_tabb7[i]=0;}
   Run.InterDCLinksCapacities.put("24-3", temp_tabb7);
   
   int[] temp_tab8 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab8.length; i++){temp_tab8[i]=0;}
   Run.InterDCLinksCapacities.put("4-23", temp_tab8);
   
   int[] temp_tabb8 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb8.length; i++){temp_tabb8[i]=0;}
   Run.InterDCLinksCapacities.put("23-4", temp_tabb8);
   
   int[] temp_tab9 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab9.length; i++){temp_tab9[i]=0;}
   Run.InterDCLinksCapacities.put("4-24", temp_tab9);
  
   int[] temp_tabb9 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb9.length; i++){temp_tabb9[i]=0;}
   Run.InterDCLinksCapacities.put("24-4", temp_tabb9);
   
   int[] temp_tab10 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab10.length; i++){temp_tab10[i]=0;}
   Run.InterDCLinksCapacities.put("5-23", temp_tab10);
   
   int[] temp_tabb10 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb10.length; i++){temp_tabb10[i]=0;}
   Run.InterDCLinksCapacities.put("23-5", temp_tabb10);
   
   int[] temp_tab11 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab11.length; i++){temp_tab11[i]=0;}
   Run.InterDCLinksCapacities.put("5-24", temp_tab11);
   
   int[] temp_tabb11 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb11.length; i++){temp_tabb11[i]=0;}
   Run.InterDCLinksCapacities.put("24-5", temp_tabb11);
   
   int[] temp_tab13 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab13.length; i++){temp_tab13[i]=0;}
   Run.InterDCLinksCapacities.put("6-7", temp_tab13);
   
   int[] temp_tabb13 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb13.length; i++){temp_tabb13[i]=0;}
   Run.InterDCLinksCapacities.put("6-8", temp_tabb13);
   
   int[] temp_tab14 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab14.length; i++){temp_tab14[i]=0;}
   Run.InterDCLinksCapacities.put("7-6", temp_tab14);
   
   int[] temp_tabb14 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb14.length; i++){temp_tabb14[i]=0;}
   Run.InterDCLinksCapacities.put("7-9", temp_tabb14);
   
   int[] temp_tab15 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab15.length; i++){temp_tab15[i]=0;}
   Run.InterDCLinksCapacities.put("8-6", temp_tab15);
   
   int[] temp_tabb15 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb15.length; i++){temp_tabb15[i]=0;}
   Run.InterDCLinksCapacities.put("8-9", temp_tabb15);
   
   int[] temp_tab16 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab16.length; i++){temp_tab16[i]=0;}
   Run.InterDCLinksCapacities.put("8-13", temp_tab16);
   
   int[] temp_tabb16 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb16.length; i++){temp_tabb16[i]=0;}
   Run.InterDCLinksCapacities.put("8-10", temp_tabb16);
   
   int[] temp_tab17 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab17.length; i++){temp_tab17[i]=0;}
   Run.InterDCLinksCapacities.put("9-7", temp_tab17);
   
   int[] temp_tabb17 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb17.length; i++){temp_tabb17[i]=0;}
   Run.InterDCLinksCapacities.put("9-8", temp_tabb17);
   
   int[] temp_tab18 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab18.length; i++){temp_tab18[i]=0;}
   Run.InterDCLinksCapacities.put("9-22", temp_tab18);
   
   int[] temp_tabb18 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb18.length; i++){temp_tabb18[i]=0;}
   Run.InterDCLinksCapacities.put("10-8", temp_tabb18);
   
   int[] temp_tab19 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab19.length; i++){temp_tab19[i]=0;}
   Run.InterDCLinksCapacities.put("10-11", temp_tab19);
   
   int[] temp_tabb19 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb19.length; i++){temp_tabb19[i]=0;}
   Run.InterDCLinksCapacities.put("11-10", temp_tabb19);
   
   int[] temp_tab20 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab20.length; i++){temp_tab20[i]=0;}
   Run.InterDCLinksCapacities.put("11-13", temp_tab20);
   
   int[] temp_tabb20 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb20.length; i++){temp_tabb20[i]=0;}
   Run.InterDCLinksCapacities.put("11-12", temp_tabb20);

   int[] temp_tab21 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab21.length; i++){temp_tab21[i]=0;}
   Run.InterDCLinksCapacities.put("12-11", temp_tab21);
   
   int[] temp_tabb21 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb21.length; i++){temp_tabb21[i]=0;}
   Run.InterDCLinksCapacities.put("12-13", temp_tabb21);
  
   int[] temp_tab22 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab22.length; i++){temp_tab22[i]=0;}
   Run.InterDCLinksCapacities.put("12-14", temp_tab22);
   
   int[] temp_tabb22 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb22.length; i++){temp_tabb22[i]=0;}
   Run.InterDCLinksCapacities.put("13-12", temp_tabb22);

   int[] temp_tab23 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab23.length; i++){temp_tab23[i]=0;}
   Run.InterDCLinksCapacities.put("13-11", temp_tab23);
   
   int[] temp_tabb23 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb23.length; i++){temp_tabb23[i]=0;}
   Run.InterDCLinksCapacities.put("13-8", temp_tabb23);
   
   int[] temp_tab24 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab24.length; i++){temp_tab24[i]=0;}
   Run.InterDCLinksCapacities.put("13-22", temp_tab24);
   
   int[] temp_tabb24 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb24.length; i++){temp_tabb24[i]=0;}
   Run.InterDCLinksCapacities.put("13-15", temp_tabb24);

   int[] temp_tab25 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab25.length; i++){temp_tab25[i]=0;}
   Run.InterDCLinksCapacities.put("13-14", temp_tab25);

   int[] temp_tabb25 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb25.length; i++){temp_tabb25[i]=0;}
   Run.InterDCLinksCapacities.put("14-12", temp_tabb25);
   
   int[] temp_tab26 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab26.length; i++){temp_tab26[i]=0;}
   Run.InterDCLinksCapacities.put("14-13", temp_tab26);
   
   int[] temp_tabb26 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb26.length; i++){temp_tabb26[i]=0;}
   Run.InterDCLinksCapacities.put("14-15", temp_tabb26);
   
   int[] temp_tab27 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab27.length; i++){temp_tab27[i]=0;}
   Run.InterDCLinksCapacities.put("15-14", temp_tab27);
   
   int[] temp_tabb27 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb27.length; i++){temp_tabb27[i]=0;}
   Run.InterDCLinksCapacities.put("15-13", temp_tabb27);

   int[] temp_tab28 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab28.length; i++){temp_tab28[i]=0;}
   Run.InterDCLinksCapacities.put("15-22", temp_tab28);

   int[] temp_tabb28 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb28.length; i++){temp_tabb28[i]=0;}
   Run.InterDCLinksCapacities.put("15-16", temp_tabb28);
 
   int[] temp_tab29 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab29.length; i++){temp_tab29[i]=0;}
   Run.InterDCLinksCapacities.put("16-15", temp_tab29);

   int[] temp_tabb29 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb29.length; i++){temp_tabb29[i]=0;}
   Run.InterDCLinksCapacities.put("16-22", temp_tabb29);
 
   int[] temp_tab30 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab30.length; i++){temp_tab30[i]=0;}
   Run.InterDCLinksCapacities.put("16-19", temp_tab30);

   int[] temp_tabb30 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb30.length; i++){temp_tabb30[i]=0;}
   Run.InterDCLinksCapacities.put("16-17", temp_tabb30);
   
   int[] temp_tab31 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab31.length; i++){temp_tab31[i]=0;}
   Run.InterDCLinksCapacities.put("17-16", temp_tab31);
   
   int[] temp_tabb31 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb31.length; i++){temp_tabb31[i]=0;}
   Run.InterDCLinksCapacities.put("17-18", temp_tabb31);
   
   int[] temp_tab32 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab32.length; i++){temp_tab32[i]=0;}
   Run.InterDCLinksCapacities.put("18-17", temp_tab32);
   
   int[] temp_tabb32 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb32.length; i++){temp_tabb32[i]=0;}
   Run.InterDCLinksCapacities.put("18-19", temp_tabb32);
  
   int[] temp_tab33 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab33.length; i++){temp_tab33[i]=0;}
   Run.InterDCLinksCapacities.put("19-18", temp_tab33);
 
   int[] temp_tabb33 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb33.length; i++){temp_tabb33[i]=0;}
   Run.InterDCLinksCapacities.put("19-16", temp_tabb33);
   
   int[] temp_tab34 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab34.length; i++){temp_tab34[i]=0;}
   Run.InterDCLinksCapacities.put("19-20", temp_tab34);
   
   int[] temp_tabb34 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb34.length; i++){temp_tabb34[i]=0;}
   Run.InterDCLinksCapacities.put("20-19", temp_tabb34);

   int[] temp_tab35 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab35.length; i++){temp_tab35[i]=0;}
   Run.InterDCLinksCapacities.put("20-21", temp_tab35);
   
   int[] temp_tabb35 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb35.length; i++){temp_tabb35[i]=0;}
   Run.InterDCLinksCapacities.put("21-20", temp_tabb35);
   
   int[] temp_tab36 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab36.length; i++){temp_tab36[i]=0;}
   Run.InterDCLinksCapacities.put("21-22", temp_tab36);
   
   int[] temp_tabb36 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb36.length; i++){temp_tabb36[i]=0;}
   Run.InterDCLinksCapacities.put("22-21", temp_tabb36);
   
   int[] temp_tab37 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tab37.length; i++){temp_tab37[i]=0;}
   Run.InterDCLinksCapacities.put("22-16", temp_tab37);

   int[] temp_tabb37 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb37.length; i++){temp_tabb37[i]=0;}
   Run.InterDCLinksCapacities.put("22-15", temp_tabb37);
   
   int[] temp_tabb38 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb38.length; i++){temp_tabb38[i]=0;}
   Run.InterDCLinksCapacities.put("22-13", temp_tabb38);
   
   int[] temp_tabb39 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb39.length; i++){temp_tabb39[i]=0;}
   Run.InterDCLinksCapacities.put("22-9", temp_tabb39);
   
    int[] temp_tabb40 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb40.length; i++){temp_tabb40[i]=0;}
   Run.InterDCLinksCapacities.put("23-21", temp_tabb40);
   
   int[] temp_tabb41 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb41.length; i++){temp_tabb41[i]=0;}
   Run.InterDCLinksCapacities.put("21-23", temp_tabb41);

    int[] temp_tabb42 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb42.length; i++){temp_tabb42[i]=0;}
   Run.InterDCLinksCapacities.put("24-19", temp_tabb42);
   
    int[] temp_tabb43 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb43.length; i++){temp_tabb43[i]=0;}
   Run.InterDCLinksCapacities.put("19-24", temp_tabb43);
   
   int[] temp_tabb44 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb44.length; i++){temp_tabb44[i]=0;}
   Run.InterDCLinksCapacities.put("23-9", temp_tabb44);
   
   int[] temp_tabb45 = new int[Run.total_frequency_slots];
   for(int i=0; i<temp_tabb45.length; i++){temp_tabb45[i]=0;}
   Run.InterDCLinksCapacities.put("9-23", temp_tabb45);
   }
/*****************************************************************************************/
/******************************************************************************************/
   static public void topology_linksSlots()//Capacities expressed in Mbps
   {
   
   Run.InterDCLinksSlots.put("0-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("6-0", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("0-23", Run.total_frequency_slots);Run.InterDCLinksSlots.put("23-0", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("1-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("6-1", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("1-23", Run.total_frequency_slots);Run.InterDCLinksSlots.put("23-1", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("2-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("6-2", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("2-24", Run.total_frequency_slots);Run.InterDCLinksSlots.put("24-2", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("3-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("6-3", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("3-24", Run.total_frequency_slots);Run.InterDCLinksSlots.put("24-3", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("4-23", Run.total_frequency_slots);Run.InterDCLinksSlots.put("23-4", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("4-24", Run.total_frequency_slots);Run.InterDCLinksSlots.put("24-4", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("5-23", Run.total_frequency_slots);Run.InterDCLinksSlots.put("23-5", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("5-24", Run.total_frequency_slots);Run.InterDCLinksSlots.put("24-5", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("6-7", Run.total_frequency_slots);Run.InterDCLinksSlots.put("6-8", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("7-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("7-9", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("8-6", Run.total_frequency_slots);Run.InterDCLinksSlots.put("8-9", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("8-13", Run.total_frequency_slots);Run.InterDCLinksSlots.put("8-10", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("9-7", Run.total_frequency_slots);Run.InterDCLinksSlots.put("9-8", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("9-22", Run.total_frequency_slots);Run.InterDCLinksSlots.put("10-8", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("10-11", Run.total_frequency_slots);Run.InterDCLinksSlots.put("11-10", Run.total_frequency_slots);
 
   Run.InterDCLinksSlots.put("23-21", Run.total_frequency_slots);  Run.InterDCLinksSlots.put("21-23", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("24-19", Run.total_frequency_slots);  Run.InterDCLinksSlots.put("19-24", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("23-9", Run.total_frequency_slots); Run.InterDCLinksSlots.put("9-23", Run.total_frequency_slots);
     
   Run.InterDCLinksSlots.put("11-13", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("11-12", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("12-11", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("12-13", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("12-14", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("13-12", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("13-11", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("13-8", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("13-22", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("13-15", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("13-14", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("14-12", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("14-13", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("14-15", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("15-13", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("15-14", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("15-22", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("15-16", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("16-15", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("16-22", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("16-19", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("16-17", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("17-16", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("17-18", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("18-19", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("19-18", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("19-16", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("19-20", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("20-19", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("20-21", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("21-20", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("21-22", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("22-21", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("22-16", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("22-15", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("22-13", Run.total_frequency_slots);
   
   Run.InterDCLinksSlots.put("22-9", Run.total_frequency_slots);
   Run.InterDCLinksSlots.put("18-17", Run.total_frequency_slots);
   
   
   }
   
/******************************************************************************************/
   
    public static void generate_start_indexes_pipes()
   {
   
   Run.start_indexes.put("0-8", 0);Run.start_indexes.put("1-8", 0);Run.start_indexes.put("2-8", 0);    
   Run.start_indexes.put("3-8", 0);Run.start_indexes.put("4-8", 0);Run.start_indexes.put("5-8", 0);            
   Run.start_indexes.put("0-22", 0);Run.start_indexes.put("1-22", 0);Run.start_indexes.put("2-22", 0); 
   Run.start_indexes.put("3-22", 0);Run.start_indexes.put("4-22", 0);Run.start_indexes.put("5-22", 0); 
   Run.start_indexes.put("0-16", 0);Run.start_indexes.put("1-16", 0);Run.start_indexes.put("2-16", 0); 
   Run.start_indexes.put("3-16", 0);Run.start_indexes.put("4-16", 0);Run.start_indexes.put("5-16", 0);
   Run.start_indexes.put("0-15", 0);Run.start_indexes.put("1-15", 0);Run.start_indexes.put("2-15", 0); 
   Run.start_indexes.put("3-15", 0);Run.start_indexes.put("4-15", 0);Run.start_indexes.put("5-15", 0); 
   Run.start_indexes.put("0-14", 0);Run.start_indexes.put("1-14", 0);Run.start_indexes.put("2-14", 0); 
   Run.start_indexes.put("3-14", 0);Run.start_indexes.put("4-14", 0);Run.start_indexes.put("5-14", 0);
   Run.start_indexes.put("0-13", 0);Run.start_indexes.put("1-13", 0);Run.start_indexes.put("2-13", 0); 
   Run.start_indexes.put("3-13", 0);Run.start_indexes.put("4-13", 0);Run.start_indexes.put("5-13", 0); 
   
   Run.start_indexes.put("8-0", 0);Run.start_indexes.put("8-1", 0);Run.start_indexes.put("8-2", 0); 
   Run.start_indexes.put("8-3", 0);Run.start_indexes.put("8-4", 0);Run.start_indexes.put("8-5", 0); 
   Run.start_indexes.put("22-0", 0);Run.start_indexes.put("22-1", 0);Run.start_indexes.put("22-2", 0); 
   Run.start_indexes.put("22-3", 0);Run.start_indexes.put("22-4", 0);Run.start_indexes.put("22-5", 0); 
   Run.start_indexes.put("13-0", 0);Run.start_indexes.put("13-1", 0);Run.start_indexes.put("13-2", 0); 
   Run.start_indexes.put("13-3", 0);Run.start_indexes.put("13-4", 0);Run.start_indexes.put("13-5", 0); 
   Run.start_indexes.put("14-0", 0);Run.start_indexes.put("14-1", 0);Run.start_indexes.put("14-2", 0); 
   Run.start_indexes.put("14-3", 0);Run.start_indexes.put("14-4", 0);Run.start_indexes.put("14-5", 0); 
   Run.start_indexes.put("15-0", 0);Run.start_indexes.put("15-1", 0);Run.start_indexes.put("15-2", 0); 
   Run.start_indexes.put("15-3", 0);Run.start_indexes.put("15-4", 0);Run.start_indexes.put("15-5", 0); 
   Run.start_indexes.put("16-0", 0);Run.start_indexes.put("16-1", 0);Run.start_indexes.put("16-2", 0); 
   Run.start_indexes.put("16-3", 0);Run.start_indexes.put("16-4", 0);Run.start_indexes.put("16-5", 0); 
   
   }
/******************************************************************************************/
   
   public static void update_Pipes_Segments_database()
   {
       
   Run.current_users_per_pipe.put("0-8", 0);Run.current_users_per_pipe.put("1-8", 0);Run.current_users_per_pipe.put("2-8", 0);    
   Run.current_users_per_pipe.put("3-8", 0);Run.current_users_per_pipe.put("4-8", 0);Run.current_users_per_pipe.put("5-8", 0);            
   Run.current_users_per_pipe.put("0-22", 0);Run.current_users_per_pipe.put("1-22", 0);Run.current_users_per_pipe.put("2-22", 0); 
   Run.current_users_per_pipe.put("3-22", 0);Run.current_users_per_pipe.put("4-22", 0);Run.current_users_per_pipe.put("5-22", 0); 
   Run.current_users_per_pipe.put("0-16", 0);Run.current_users_per_pipe.put("1-16", 0);Run.current_users_per_pipe.put("2-16", 0); 
   Run.current_users_per_pipe.put("3-16", 0);Run.current_users_per_pipe.put("4-16", 0);Run.current_users_per_pipe.put("5-16", 0);
   Run.current_users_per_pipe.put("0-15", 0);Run.current_users_per_pipe.put("1-15", 0);Run.current_users_per_pipe.put("2-15", 0); 
   Run.current_users_per_pipe.put("3-15", 0);Run.current_users_per_pipe.put("4-15", 0);Run.current_users_per_pipe.put("5-15", 0); 
   Run.current_users_per_pipe.put("0-14", 0);Run.current_users_per_pipe.put("1-14", 0);Run.current_users_per_pipe.put("2-14", 0); 
   Run.current_users_per_pipe.put("3-14", 0);Run.current_users_per_pipe.put("4-14", 0);Run.current_users_per_pipe.put("5-14", 0);
   Run.current_users_per_pipe.put("0-13", 0);Run.current_users_per_pipe.put("1-13", 0);Run.current_users_per_pipe.put("2-13", 0); 
   Run.current_users_per_pipe.put("3-13", 0);Run.current_users_per_pipe.put("4-13", 0);Run.current_users_per_pipe.put("5-13", 0); 
   
   Run.current_users_per_pipe.put("8-0", 0);Run.current_users_per_pipe.put("8-1", 0);Run.current_users_per_pipe.put("8-2", 0); 
   Run.current_users_per_pipe.put("8-3", 0);Run.current_users_per_pipe.put("8-4", 0);Run.current_users_per_pipe.put("8-5", 0); 
   Run.current_users_per_pipe.put("22-0", 0);Run.current_users_per_pipe.put("22-1", 0);Run.current_users_per_pipe.put("22-2", 0); 
   Run.current_users_per_pipe.put("22-3", 0);Run.current_users_per_pipe.put("22-4", 0);Run.current_users_per_pipe.put("22-5", 0); 
   Run.current_users_per_pipe.put("13-0", 0);Run.current_users_per_pipe.put("13-1", 0);Run.current_users_per_pipe.put("13-2", 0); 
   Run.current_users_per_pipe.put("13-3", 0);Run.current_users_per_pipe.put("13-4", 0);Run.current_users_per_pipe.put("13-5", 0); 
   Run.current_users_per_pipe.put("14-0", 0);Run.current_users_per_pipe.put("14-1", 0);Run.current_users_per_pipe.put("14-2", 0); 
   Run.current_users_per_pipe.put("14-3", 0);Run.current_users_per_pipe.put("14-4", 0);Run.current_users_per_pipe.put("14-5", 0); 
   Run.current_users_per_pipe.put("15-0", 0);Run.current_users_per_pipe.put("15-1", 0);Run.current_users_per_pipe.put("15-2", 0); 
   Run.current_users_per_pipe.put("15-3", 0);Run.current_users_per_pipe.put("15-4", 0);Run.current_users_per_pipe.put("15-5", 0); 
   Run.current_users_per_pipe.put("16-0", 0);Run.current_users_per_pipe.put("16-1", 0);Run.current_users_per_pipe.put("16-2", 0); 
   Run.current_users_per_pipe.put("16-3", 0);Run.current_users_per_pipe.put("16-4", 0);Run.current_users_per_pipe.put("16-5", 0); 
   

   Run.current_users_per_segment.put("0-6", 0);Run.current_users_per_segment.put("6-0", 0);
   Run.current_users_per_segment.put("0-23", 0);Run.current_users_per_segment.put("23-0", 0);
   Run.current_users_per_segment.put("1-6", 0);Run.current_users_per_segment.put("6-1", 0);
   Run.current_users_per_segment.put("1-23", 0);Run.current_users_per_segment.put("23-1", 0);
   Run.current_users_per_segment.put("2-6", 0);Run.current_users_per_segment.put("6-2", 0);
   Run.current_users_per_segment.put("2-24", 0);Run.current_users_per_segment.put("24-2", 0);
   Run.current_users_per_segment.put("3-6", 0);Run.current_users_per_segment.put("6-3", 0);
   Run.current_users_per_segment.put("3-24", 0);Run.current_users_per_segment.put("24-3", 0);
   Run.current_users_per_segment.put("4-23", 0);Run.current_users_per_segment.put("23-4", 0);
   Run.current_users_per_segment.put("4-24", 0);Run.current_users_per_segment.put("24-4", 0);
   Run.current_users_per_segment.put("5-23", 0);Run.current_users_per_segment.put("23-5", 0);
   Run.current_users_per_segment.put("5-24", 0);Run.current_users_per_segment.put("24-5", 0);
   Run.current_users_per_segment.put("6-7", 0);Run.current_users_per_segment.put("6-8", 0);
   Run.current_users_per_segment.put("7-6", 0);Run.current_users_per_segment.put("7-9", 0);
   Run.current_users_per_segment.put("8-6", 0);Run.current_users_per_segment.put("8-9", 0);
   Run.current_users_per_segment.put("8-13", 0);Run.current_users_per_segment.put("8-10", 0);
   Run.current_users_per_segment.put("9-7", 0);Run.current_users_per_segment.put("9-8", 0);
   Run.current_users_per_segment.put("9-22", 0);Run.current_users_per_segment.put("10-8", 0);
   Run.current_users_per_segment.put("10-11", 0);Run.current_users_per_segment.put("11-10", 0);
   
   Run.current_users_per_segment.put("23-21", 0); Run.current_users_per_segment.put("21-23", 0);
   Run.current_users_per_segment.put("24-19", 0); Run.current_users_per_segment.put("19-24", 0);
   Run.current_users_per_segment.put("23-9", 0); Run.current_users_per_segment.put("9-23", 0);
   
   Run.current_users_per_segment.put("11-13", 0);
   Run.current_users_per_segment.put("11-12", 0);
   
   Run.current_users_per_segment.put("12-11", 0);
   Run.current_users_per_segment.put("12-13", 0);
   
   Run.current_users_per_segment.put("12-14", 0);
   Run.current_users_per_segment.put("13-12", 0);
   
   Run.current_users_per_segment.put("13-11", 0);
   Run.current_users_per_segment.put("13-8", 0);
   
   Run.current_users_per_segment.put("13-22", 0);
   Run.current_users_per_segment.put("13-15", 0);
   
   Run.current_users_per_segment.put("13-14", 0);
   Run.current_users_per_segment.put("14-12", 0);
   
   Run.current_users_per_segment.put("14-13", 0);
   Run.current_users_per_segment.put("14-15", 0);
   Run.current_users_per_segment.put("15-13", 0);
   Run.current_users_per_segment.put("15-14", 0);
   
   Run.current_users_per_segment.put("15-22", 0);
   
   Run.current_users_per_segment.put("15-16", 0);
   
   Run.current_users_per_segment.put("16-15", 0);
   
   Run.current_users_per_segment.put("16-22", 0);
   Run.current_users_per_segment.put("16-19", 0);
   Run.current_users_per_segment.put("16-17", 0);
   Run.current_users_per_segment.put("17-16", 0);
   
   Run.current_users_per_segment.put("17-18", 0);
   
   Run.current_users_per_segment.put("18-19", 0);
   Run.current_users_per_segment.put("19-18", 0);
   Run.current_users_per_segment.put("19-16", 0);
   
   Run.current_users_per_segment.put("19-20", 0);
   
   Run.current_users_per_segment.put("20-19", 0);
   
   Run.current_users_per_segment.put("20-21", 0);
   
   Run.current_users_per_segment.put("21-20", 0);
   Run.current_users_per_segment.put("21-22", 0);
   
   Run.current_users_per_segment.put("22-21", 0);
   Run.current_users_per_segment.put("22-16", 0);
   
   Run.current_users_per_segment.put("22-15", 0);
   Run.current_users_per_segment.put("22-13", 0);
   
   Run.current_users_per_segment.put("22-9", 0);
   Run.current_users_per_segment.put("18-17", 0);
   
      }
/*****************************************************************************************/
  public static void computePaths(Vertex source)
      {
      //  System.out.println("we are in compute paths");
        source.minDistance = 0;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
  	vertexQueue.add(source);

  	while (!vertexQueue.isEmpty()) {
  	    Vertex u = vertexQueue.poll();

              // Visit each edge exiting u
             for (Edge e : u.adjacencies)
             {
                 Vertex v = e.target;
                 double weight = e.weight;
                 double distanceThroughU = u.minDistance + weight;
 		if (distanceThroughU < v.minDistance) {
 		    vertexQueue.remove(v);

 		    v.minDistance = distanceThroughU ;
		    v.previous = u;
 		    vertexQueue.add(v);

 		}

             }
         }
     }


     public static List<Vertex> getShortestPathTo(Vertex target)
     {
         List<Vertex> path = new ArrayList<Vertex>();
         for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
             path.add(vertex);

         Collections.reverse(path);
         return path;
     }


      class Vertex implements Comparable<Vertex>
 {
     public final String name;
     public Edge[] adjacencies;
     public double minDistance = Double.POSITIVE_INFINITY;
     public Vertex previous;
     public Vertex(String argName) { name = argName; }
     @Override
     public String toString() { return name; }
     @Override
     public int compareTo(Vertex other)
     {
         return Double.compare(minDistance, other.minDistance);
     }

 }


 class Edge
 {
     public final Vertex target;
	  public final double weight;
      public Edge(Vertex argTarget, double argWeight)
      { target = argTarget; weight = argWeight; }
  }
 

public double calculate_latency(double distance_km)
{

    double distance_m = distance_km * 1000.0;
    double network_latency = distance_m / ((2.0/3.0) * 299792458.0);//latency en secondes

    return network_latency;
}




 public static LinkedHashMap sortHashMapByValues(TreeMap passedMap, boolean ascending) {

List mapKeys = new ArrayList(passedMap.keySet());
List mapValues = new ArrayList(passedMap.values());
Collections.sort(mapValues);
Collections.sort(mapKeys);

if (!ascending)
Collections.reverse(mapValues);

LinkedHashMap someMap = new LinkedHashMap();
Iterator valueIt = mapValues.iterator();
while (valueIt.hasNext()) {
Object val = valueIt.next();
Iterator keyIt = mapKeys.iterator();
while (keyIt.hasNext()) {
Object key = keyIt.next();
if (passedMap.get(key).toString().equals(val.toString())) {
passedMap.remove(key);
mapKeys.remove(key);
someMap.put(key, val);
break;
}
}
}
return someMap;
}
 
/*********************************************************************************/
 
 public boolean arrayContainsDuplicate(String[] arrayToTest)
 {
 
 // System.out.println("arrayToTest.length: "+arrayToTest.length);
  
 for (int i = 0; i < arrayToTest.length; i++) {
            for (int j = 0; j < arrayToTest.length; j++) {
                if (arrayToTest[i].equals(arrayToTest[j]) && i != j) {
                    return true;
                }
            }
        }
        return false;

 }
     
/***********************************************************************/
 public static HashMap check_adjacencies (int[] temp_table, int requested_freq_slots)
{

                      int count; 
                      int j = 0;
                      HashMap hm=new HashMap(); 
                      for(int i=0;i<temp_table.length;i++){ 
                           
                          count=1; 

                          while(i<(temp_table.length-1) && temp_table[i]==temp_table[i+1] ){ 
                            count++; 
                            i++; 
                          } 

                         // System.out.println(count+"("+temp_table[i]+") "+"start: "+(i-count+1));

                          if(temp_table[i] == 0)
                          {
                            //  System.out.println("temp_table[i]: "+temp_table[i]);
                              
                          if(count >= requested_freq_slots)
                          {hm.put(j, (i-count+1));
                          j = j+1;}
                         
                          for(int k=1; k<temp_table.length; k++)
                          {
                          if ((count-k) >= requested_freq_slots)
                          {
                          hm.put(j, (i-(count-k)+1));
                          //System.out.println((count-k)+"("+temp_table[i]+") "+"start: "+(i-(count-k)+1));
                          j = j+1;
                          }
                          }
                          
                          
                          }//if it is equal 0
                          
                         
                        
}//for
                      
                      

return hm;
}
 
 /************************************************************************************************/
    
}//PhysicalTopology
