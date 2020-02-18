package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertModalita extends Behaviour {
	
	private String filename;
	private String goal;
	private int c = 0;
	
	public AssertModalita(Agent schedAgent, String filename, String goal){
		
		super(schedAgent);
		this.filename = filename;
		this.goal = goal;
	}
	
	public void action(){
		
		String consult = "consult('" + filename + "')";
		Query q = new Query(consult);
		
		if (q.hasSolution()){
						
			q = new Query(goal);
			
			//System.out.println(q);
			
			q.hasSolution();
				
			c++;

		}
	}
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}


}
