package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertVitamina extends Behaviour {
	
	private String predName;
	private Object[] argument;
	private Integer nvitamine = 0;

	public AssertVitamina(Agent schedAgent, String predName, Object[] argument) {
		super(schedAgent);
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){

		for(int h = 0; h < argument.length; h++) if(argument[nvitamine] != null) {
			
			String argument1 = argument[nvitamine].toString();			

			String goal = "assert(" + predName + "(" + argument1 + "))";
			Query q = new Query(goal);
	
			if (q.hasSolution()){
				
				//System.out.println(goal);
				//myPrint(goal);
				nvitamine++;
			}
		}
	}
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (nvitamine < 5) return false;
	 		else return true;
	}

}
