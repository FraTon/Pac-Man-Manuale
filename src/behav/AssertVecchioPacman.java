package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class AssertVecchioPacman extends MyOneShot {

	private String filename;

	public AssertVecchioPacman(Agent schedAgent, String filename) {
		
		super(schedAgent);
		this.filename = filename;

	}


	public void action(){
		
		String consult = "consult('" + filename + "')";
		Query q = new Query(consult);
		
		if (q.hasSolution()){
						
			String goal = "asserisci_vecchio_pacman";
			q = new Query(goal);
	
			if (q.hasSolution()){
				
				//myPrint(goal);
				
			}
		}
	}
}
