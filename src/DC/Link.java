
package DC;


public class Link extends NwEntity {
    
    public NwMachines source;
    public NwMachines destination;

    public int remainingBw;
    
    public Integer linkTo;
    public int linkcapacity;
    
    public Link(){
        super();
    }
    public Link(int bw,String id, NwMachines src, NwMachines dst){
        super(bw,id);
        source= src;
        destination= dst;  
       
      //  System.out.println("created new link Id: "+Id+"with bw capacity "+this.bwCapacity+" source node "+src.Id+"destination node "+dst.Id);


    }

    public Link(int bw,String id){
        super(bw,id);

        remainingBw=Constants.switchLink;

       // System.out.println("created new link Id: "+Id+"with bw capacity "+this.bwCapacity);
    }

    	public Link(Integer _linkTo){
		linkTo = _linkTo;
		linkcapacity = 1;
	}
        
        	public Link(Integer _linkTo, int capacity){
		linkTo = _linkTo;
		linkcapacity = capacity;
	}
        
                
       public int intValue() {
		return linkTo;
	}

    public void updateAvailableBw(int bw){
        remainingBw = remainingBw+bw;
        //System.out.println("the available bw of server "+this.Id+" became "+remainingBw);
    }
    
}

