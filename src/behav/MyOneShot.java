package behav;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class MyOneShot extends OneShotBehaviour {

	public MyOneShot(Agent schedAgent){
		
		super(schedAgent); //chiama il costruttore della superclasse
	}

	public void action(){
		
		myPrint("Empty action");
	}
	
	//Definizione di un metodo MyPrint, che prende come argomento una stringa da stampare a schermo
	protected void myPrint(String toPrint){
		
		//Stampa il nome dell'agente, più la stringa da stampare
		System.out.println(this.myAgent.getLocalName() + ": " + toPrint);
	}
}
