
package Simulator;

import java.util.ArrayList;

public class Statistics {
    
    static ArrayList<Double> pipe_block  = new ArrayList();
    static ArrayList<Double> cpu_block  = new ArrayList();
    static ArrayList<Double> bw_block  = new ArrayList();
    static ArrayList<Double> total_block  = new ArrayList();
    static ArrayList<Double> intra_block  = new ArrayList();
    static ArrayList<Double> access_block  = new ArrayList();
    static ArrayList<Double> core_block  = new ArrayList();
    
    static ArrayList<Double> core_block_high_lat  = new ArrayList();
    static ArrayList<Double> core_block_low_lat  = new ArrayList();
    
    static ArrayList<Double> number_blocked_pipe  = new ArrayList();
    static ArrayList<Double> number_requests  = new ArrayList();
    static ArrayList<Double> number_established  = new ArrayList();
    static ArrayList<Double> number_blocked_intra  = new ArrayList();
    static ArrayList<Double> number_blocked_totale  = new ArrayList();
    static ArrayList<Double> number_blocked_access  = new ArrayList();
    static ArrayList<Double> number_blocked_core  = new ArrayList();

    static ArrayList<Double> number_blocked_core_high_lat  = new ArrayList();
    static ArrayList<Double> number_blocked_core_low_lat  = new ArrayList();
    
    static ArrayList<Double> utilization_segments  = new ArrayList();
    
     static ArrayList<Double> number_times_medium  = new ArrayList();
     static ArrayList<Double> number_times_large  = new ArrayList();
     static ArrayList<Double> number_times_core  = new ArrayList();
    
    public static void Print()
            
    {
    
    
    double block_pipe = (double)((Run.blocked_pipe/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_intra = (double)((Run.intraDCBlock/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_total = (double)((Run.blocked_totale/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_access = (double)((Run.blocked_access/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_core = (double)((Run.blocked_core/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_core_high_lat = (double)((Run.blocked_core_high_lat/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
    
    double block_core_low_lat = (double)((Run.blocked_core_low_lat/(Run.NumberRequests - Run.NumberRequestsTransitorio)) * 100);
          
       pipe_block.add(block_pipe);
       intra_block.add(block_intra);
       total_block.add(block_total);
       access_block.add(block_access);
       core_block.add(block_core);
       
       core_block_high_lat.add(block_core_high_lat);
       core_block_low_lat.add(block_core_low_lat);
       
       number_blocked_pipe.add(Run.blocked_pipe);
       number_requests.add((double)(Run.NumberRequests - Run.NumberRequestsTransitorio));
       number_blocked_intra.add(Run.intraDCBlock);
       number_blocked_totale.add(Run.blocked_totale);
       number_blocked_access.add(Run.blocked_access);
       number_blocked_core.add(Run.blocked_core);
       number_established.add(Run.established);
       
       number_blocked_core_high_lat.add(Run.blocked_core_high_lat);
       number_blocked_core_low_lat.add(Run.blocked_core_low_lat);
       
       number_times_medium.add(Run.nb_times_medium_DC);
       number_times_large.add(Run.nb_times_large_DC);
       number_times_core.add(Run.nb_times_core_DCs);

       Run.established = 0;
       Run.blocked_totale = 0;
       Run.blocked_pipe = 0;
       Run.blocked_CPU = 0;
       Run.blocked_BW = 0;
       Run.intraDCBlock = 0;
       Run.blocked_access = 0;
       Run.blocked_core = 0;
       Run.blocked_core_high_lat = 0;
       Run.blocked_core_low_lat = 0;
          
       Run.nb_times_medium_DC = 0;
       Run.nb_times_large_DC = 0;
       Run.nb_times_core_DCs = 0;
    
    }
    
    
    public static void Print_final_results()
    {
    
   System.out.println("Results: probabilities");
        
   System.out.println("Total blocking: ");
   confidence_interval(95, total_block);
   
   System.out.println("Access blocking: ");
   confidence_interval(95, access_block);
   
   System.out.println("Core blocking: ");
   confidence_interval(95, core_block);
   
    System.out.println("High latency Core blocking: ");
   confidence_interval(95, core_block_high_lat);
   
    System.out.println("Low latency Core blocking: ");
   confidence_interval(95, core_block_low_lat);
   
   System.out.println("Average traversed distance: ");
   confidence_interval(95, Run.Distance);
   
   System.out.println("Additional delay of retrial in seconds: ");
   confidence_interval(95, Run.Retrial_Latency);
    
   System.out.println("IntraDC Blocking: ");
   confidence_interval(95, intra_block);
   
   System.out.println("Total pipes blocking: ");
   confidence_interval(95, pipe_block);
   
    System.out.println("Results: number of elements");
    
    System.out.println("Number blocked pipes: ");
   confidence_interval(95, number_blocked_pipe);
   
   System.out.println("Number generated requests:: ");
   confidence_interval(95, number_requests);
   
   System.out.println("Number blocked Intra: ");
   confidence_interval(95, number_blocked_intra);
   
   System.out.println("Number blocked totale: ");
   confidence_interval(95, number_blocked_totale);
   
   System.out.println("Number blocked access: ");
   confidence_interval(95, number_blocked_access);
   
   System.out.println("Number blocked core: ");
   confidence_interval(95, number_blocked_core);
   
    System.out.println("Number blocked core high latency: ");
   confidence_interval(95, number_blocked_core_high_lat);
   
    System.out.println("Number blocked core low latency: ");
   confidence_interval(95, number_blocked_core_low_lat);
   
   System.out.println("Number established requests: ");
   confidence_interval(95, number_established);
    
   System.out.println("Number times_medium: ");
   confidence_interval(95, number_times_medium);
   
   System.out.println("Number times_large: ");
   confidence_interval(95, number_times_large);
   
   System.out.println("Number times_core: ");
   confidence_interval(95, number_times_core);
   
    }
    
    
    public static double getSegmentsUtilization()
    {

    double current_used_slots = 0.0;
    
    return (current_used_slots);

    }
    
    
    
    
     public static double confidence_interval(int cl, ArrayList<Double> data) {

        double zeta = 0;
        double Relative_CI = 0;
        double mu = 0;
        double sigma2 = 0;
        double CI = 0;
        
        switch (cl) {
            case 80:
                zeta = 1.28;
                break;
            case 85:
                zeta = 1.44;
                break;
            case 90:
                zeta = 1.645;
                break;
            case 95:
                zeta = 1.96;
                break;
            case 99:
                zeta = 2.575;
                break;  

            default:
                System.out.println("Error Message: Confidence Level not supported");
                break;
        }

     //   System.out.println("data.size(): "+data.size());
        
        for (int i = 0; i < data.size(); i++) {
          //  System.out.println(data.get(i));
            mu += data.get(i);
        }

        mu = mu / (data.size());

        sigma2 = 0;

        for (int i = 1; i < data.size(); i++) {
            sigma2 += Math.pow(data.get(i) - mu, 2.0);
        }

        sigma2 = sigma2 / (data.size() - 2);


        CI = zeta * Math.sqrt(sigma2 / (data.size() - 1));


        if (mu != 0) {
            Relative_CI = (2 * (CI)) / (mu);
        } else {
            Relative_CI = 0;
        }


        System.out.println("The value of mu is equal to " + mu);

        System.out.println("Confidence Interval is equal to " + CI);

        mu *= 100.0;
        mu = Math.floor(mu + 0.5);
        mu /= 100.0; // 1.67

        CI *= 100.0;
        CI = Math.floor(CI);
        CI /= 100.0; // 1.67


        return Relative_CI;


    }

    
    
    
}
