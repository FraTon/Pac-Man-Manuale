package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertCancello extends Behaviour {
	
	private String predName;
	private Object[] argument;
	private Integer ncancelli = 0;

	public AssertCancello(Agent schedAgent, String predName, Object[] argument) {
		super(schedAgent);
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){

		for(int h = 0; h < argument.length; h++) if(argument[ncancelli] != null) {
			
			String argument1 = argument[ncancelli].toString();			

			String goal = "assert(" + predName + "(" + argument1 + "))";
			Query q = new Query(goal);
	
			if (q.hasSolution()){
				
				//System.out.println(goal);
				//myPrint(goal);
				ncancelli++;
			}
		}
	}
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (ncancelli < 3) return false;
	 		else return true;
	}
}
