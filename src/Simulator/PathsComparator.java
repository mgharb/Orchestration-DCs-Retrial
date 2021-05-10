
package Simulator;

import java.util.Comparator;

public class PathsComparator implements Comparator<Path>{
    
    @Override
    public int compare(Path P1, Path P2) {
        
        int value = 0;
        if (P1.size() <= (P2.size()))
        {value = -1;}
        else if (P1.size() > (P2.size()))
        {value = 1;}
          return value;  
    }
    
}
