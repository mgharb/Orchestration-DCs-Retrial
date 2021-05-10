package DC;

public class NwEntity {
    
   protected int bwCapacity;
  
   protected String Id; 
   
   public NwEntity(){
       
       //System.out.println("created new NwEntity");
   }
   public NwEntity(int bw, String id){
       bwCapacity = bw;
       Id = id; 
       //System.out.println("created new NwEntity bw "+bwCapacity +"id "+Id);
   }


 


}
