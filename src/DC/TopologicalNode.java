
package DC;

public class TopologicalNode {

	private String nodeID = "";

	private String nodeName = null;

	private int worldX = 0;

	private int worldY = 0;


	public TopologicalNode(String nodeID) {
		
		this.nodeID = nodeID;
		nodeName = String.valueOf(nodeID);
	}
	

	public String getNodeID() {
		return nodeID;
	}

	public int getCoordinateX() {
		return worldX;
	}

	public int getCoordinateY() {
		return worldY;
	}

}
