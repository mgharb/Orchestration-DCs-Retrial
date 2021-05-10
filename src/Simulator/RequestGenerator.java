package Simulator;

import static Simulator.RequestGenerator.LASTXN;
import static Simulator.RequestGenerator.negexp;
import static Simulator.RequestGenerator.rnd32;
import static Simulator.Run.seed;
import java.util.Random;

//import Scheduler.*;

public class RequestGenerator {
    
public static Random universalRand;

static long MODULE = 2147483647;
static long A = 16807;
static long LASTXN = 127773;
static long UPTOMOD = -2836;
static double RATIO = 0.46566128e-9; 
static double ALPHA = 1.9;
    
    
 public static void GenerateRequests()
 {
     
Run.temps[0] = 0;
for (int k=1; k<=Run.NumberRequests; k++)
{
Run.temps[k] = Run.temps[k-1] + negexp(Run.load, Run.seed);
Run.seed = rnd32(Run.seed);
}

for (int k=0; k<=Run.NumberRequests; k++) {
  Run.serviceTime[k] = negexp(Run.service_time, seed);// + 5;
  seed =rnd32(seed);
}
 }
 
 /*********************************************************************/

public static long rnd32(long seed) 
{
long times, rest, prod1, prod2;
times = seed / LASTXN;
rest  = seed - times * LASTXN;
prod1 = times * UPTOMOD;
prod2 = rest * A;
seed  = prod1 + prod2;
if (seed < 0) seed = seed + MODULE;
return (seed);
}


 public static double negexp(double mean, long seed) 
{
double u;

seed=rnd32(seed);

u=seed*RATIO;

double var = (-mean*Math.log(u));
return var;
}
    
    
 /************************************************************************/   
    
}
