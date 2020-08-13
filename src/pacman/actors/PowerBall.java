package pacman.actors;

import java.awt.Rectangle;

import behav.NuovaCellaVuota;
import behav.QueryDatabase;
import behav.RetractPuntino;
import jade.core.Agent;
import pacman.PacmanActor;
import pacman.PacmanGame;
import pacman.enums.State;

/**
 * @author dzimiks
 */
public class PowerBall extends PacmanActor {

	private int col;
    private int row;
    private boolean eated;
    
    public PowerBall(PacmanGame game,Agent grafica, int col, int row) {  
        super(game,grafica);
        this.col = col;
        this.row = row;
    }

    //INIZIALIZZAZIONE VITAMINA
    @Override
    public void init() {  
        loadFrames("/res/powerBall.png");
        x = col * 8 + 1 - 32;
        y = (row + 3) * 8 + 1;
        collider = new Rectangle(0, 0, 4, 4);
        eated = true;  //MANGIATA
    }

    //AGGIORNAMENTO VITAMINA 
    //controlla il suo "stato"
    @Override
    public void update() {//62
        visible = !eated && (int) (System.nanoTime() * 0.0000000075) % 2 == 0;
        
        if (eated || game.getState() == State.PACMAN_DIED) //non sono necessari aggiornamenti
            return;
        
        //se NON è VERO CHE NON HA COLLISO CON Pacman-->COLLISIONE AVVENUTA--> FANTASMI MANGIABILI
     
        if (game.checkCollision(this, Pacman.class) != null) {   
        	eated = true;
            visible = false;
            game.addScore(50);
            
            //aggiunta di un behaviour all'agentGrafica che permette di ritrarre la vitamina mangiata
            agentGrafica.addBehaviour(new RetractPuntino(
    				agentGrafica,
    				"vitamina",
    				"("+col+",-"+row+")"
    			));
             //aggiunta di un behaviour all'agentGrafica che permette di asserire quella cella come vuota
             agentGrafica.addBehaviour(new NuovaCellaVuota(
    				agentGrafica,
    				"vuota",
    				"("+col+",-"+row+")"
    			));
            //aggiunta di un behaviour all'agentGrafica che permette di aggiornare il database
            agentGrafica.addBehaviour(new QueryDatabase(
    				agentGrafica,
    				"crea_database.pl",
    				"esporta()"
    			));
            
            game.startGhostVulnerableMode();
        }
    }
    
    @Override
    public void stateChanged() {
        if (game.getState() == State.TITLE ||
            game.getState() == State.LEVEL_CLEARED ||
            game.getState() == State.GAME_OVER) {
            eated = true;
        }
        else if (game.getState() == State.READY) {
            eated = false;
            visible = true;
        }
    }

    public void hideAll() {
        visible = false;
    }
}