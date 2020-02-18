package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class RetractPacman extends MyOneShot {

	private String filename;

	public RetractPacman(Agent schedAgent, String filename) {
		
		super(schedAgent);
		this.filename = filename;

	}


	public void action(){
		
		String consult = "consult('" + filename + "')";
		Query q = new Query(consult);
		
		if (q.hasSolution()){
						
			String goal = "retract(pacman(_,_))";
			q = new Query(goal);
	
			if (q.hasSolution()){
				
				//myPrint(goal);
				
			}
		}
	}
}
