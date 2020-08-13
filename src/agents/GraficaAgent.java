package agents;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import jade.core.Agent;
import pacman.PacmanGame;
import pacman.view.Display;
import pacman.view.Game;

public class GraficaAgent extends Agent {
	
	
	
	public void setup(){

		//Stampa nome agente inizializzato
		System.out.println(this.getLocalName());
		
		//Inizializzazione della grafica, con operazioni di preparazione della finestra
        Game game = new PacmanGame();
        Display view = new Display(game);
        JFrame frame = new JFrame();
    	URL iconURL = getClass().getResource("/res/pacman_0_1.png");
    	ImageIcon icon = new ImageIcon(iconURL);

    	frame.setIconImage(icon.getImage());
        frame.setTitle("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        view.requestFocus();

        
        //Avvio della grafica con il passaggio dell'agente GraficaAgent
        view.start(this);
	}
}
