package Message;

import org.w3c.dom.*;

public class ServiceMessage extends Message {
	
	private Document doc;
	protected String name;
	
	public ServiceMessage()
	{
		type = Message.Service_MSG;
	}	
	
	public ServiceMessage(Document d, String s)
	{
		type = Message.Service_MSG;
		doc = d;
		sessionID = s;
	}
	
	public Document getValue()
	{
		return doc;	
	}
	
	public void setValue(Document d)
	{
		doc = d;	
	}

	public String getCommand()
	{
		return doc.getDocumentElement().getNodeName();
	}	
}