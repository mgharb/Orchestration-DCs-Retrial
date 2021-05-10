
package DC;

public class TopologicalLink {

	private String srcNodeID = "";

	private String destNodeID = "";

	private float linkDelay = 0;

	public TopologicalLink(String srcNode, String destNode, float delay) {
	
		linkDelay = delay;
		srcNodeID = srcNode;
		destNodeID = destNode;
	}

	public String getSrcNodeID() {
		return srcNodeID;
	}

	public String getDestNodeID() {
		return destNodeID;
	}

	public float getLinkDelay() {
		return linkDelay;
	}


}
