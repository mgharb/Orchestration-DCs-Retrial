package Message;

import java.io.*;

abstract public class Message implements Serializable {
	
	protected int 		type = 0;
	protected int 		srcPort;
	protected String 	srcHost;
	protected int 		dstPort;
	protected String 	dstHost;		
	protected String 	sessionID;
	

       final static public int Service_MSG = 1;


	public int getType()
	{
		return type;	
	}

	public int getSrcPort()
	{
		return srcPort;	
	}

	public void setSrcPort(int p)
	{
		srcPort = p;	
	}
		
	public String getSrcHost()
	{
		return srcHost;	
	}
	
	public void setSrcHost(String h)
	{
		srcHost = h;	
	}
	
	public int getDstPort()
	{
		return dstPort;	
	}

	public void setDstPort(int p)
	{
		dstPort = p;	
	}
		
	public String getDstHost()
	{
		return dstHost;	
	}
	
	public void setDstHost(String h)
	{
		dstHost = h;	
	}
	
	public String getSessionID()
	{
		return sessionID;	
	}
	
	public void setSessionID(String s)
	{
		sessionID = s;	
	}	
	
	abstract public String getCommand();	
}
