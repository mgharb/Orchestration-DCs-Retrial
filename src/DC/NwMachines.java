
package DC;


public class NwMachines extends NwEntity{
    
    protected int cpuCapacity;
    protected int bwCapacity;
    
    public NwMachines(){
        super();
    }
    public NwMachines(int bw, int cpu, String id){

        super(bw, id);
        cpuCapacity=cpu;
        bwCapacity=bw;
        //System.out.println("created new NwMachine with bw "+this.bwCapacity +"id "+this.Id + "cpu capacity"+cpuCapacity);
    }


    public NwMachines(int bw, String id){
        super(bw, id);
        //cpuCapacity=cpu;
        //System.out.println("created new NwMachine with bw "+this.bwCapacity +"id "+this.Id + "cpu capacity"+cpuCapacity);
    }

}
