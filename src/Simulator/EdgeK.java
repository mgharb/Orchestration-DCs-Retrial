
package Simulator;

public class EdgeK<Vertex> {
    
    
  public String id = "0"; 
  public final Vertex source;
  public final Vertex destination;
  public final int weight; 
  
    /*****************K shortest path algorithm*****************/
  
   public EdgeK(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("The weight is NaN.");
        }
        if (weight < 0.0) {
            throw new IllegalArgumentException("The weight is negative.");
        }
    }

  
  /*********************************************************/
  
  public EdgeK(String id, Vertex source, Vertex destination, int weight) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.weight = weight;
  }
  
  public String getId() {
    return id;
  }
  public Vertex getDestination() {
    return destination;
  }

  public Vertex getSource() {
    return source;
  }
  public int getWeight() {
    return weight;
  }
  
  @Override
  public String toString() {
    return source + " " + destination;
  }
  
  
} 