package Simulator;

import Message.*;

public abstract class FSM 
{	
	final protected int UNDEF = 0;
	final protected int START = 1;
	final protected int END = 2;

	protected int state;
	protected String sessionID;
	
	
        public FSM(){
            
        }
        
        public FSM(String s)
	{
		sessionID = s;
		state = START;
	}

        public abstract int execute(Message msg);
			
	
	protected void stateSTART(Message msg)
	{
		state = START;
	}
	
	protected void stateEND()
	{
		state = END;
	}
}
