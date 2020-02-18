package agents;

import behav.QueryPosizione;
import jade.core.Agent;

public class FantasmaAgent extends Agent {

	public void setup(){
		
		String name = this.getLocalName();
		System.out.println(name + ": init...");
		//CAMBIA SWITCH
		//String colore[] = new String[4];
		//colore = ["rosso","azzurro","rosa","arancione"];
		/*if (name.equals("FantasmaRosso")) 
			colore = ("rosso");
		if (name.equals("FantasmaAzzurro")) 
			colore = ("azzurro");
		if (name.equals("FantasmaRosa")) 
			colore = ("rosa");
		if (name.equals("FantasmaArancione")) 
			colore = ("arancione");*/

		//behaviour che ottiene la posizione 
		/*this.addBehaviour(new QueryPosizione(
				this,
				"database.pl",
				"fantasma_start("+colore+",X,Y)",
				"X",
				"Y"
			));*/
	}

}
