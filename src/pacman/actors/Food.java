package pacman.actors;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import jade.core.Agent;
import behav.NuovaCellaVuota;
import behav.QueryDatabase;
import behav.Retract;

import pacman.PacmanActor;
import pacman.PacmanGame;
import pacman.enums.State;


public class Food extends PacmanActor {

	private int col;
    private int row;
    
    public Food(PacmanGame game, Agent grafica,int col, int row) {  //28
        super(game,grafica);
        this.col = col;
        this.row = row;        
    }

    @Override
    public void init() {  //29
        loadFrames("/res/food.png");
        x = col * 8 + 3 - 32;
        y = (row + 3) * 8 + 3;
        collider = new Rectangle(0, 0, 2, 2);
        
    }

    
    //CONTROLLA SE HA COLLISO CON PAC-MAN 
    
    @Override
    public void updatePlaying() {
//        // for debug purpose A key clear level
//        if (Keyboard.keyPressed[KeyEvent.VK_A]) 
//            game.currentFoodCount = 0;
        
        if (game.checkCollision(this, Pacman.class) != null) {
            visible = false;
            game.currentFoodCount--;
            game.addScore(10);
            
            //aggiunta di un behaviour all'agentGrafica che permette di ritrarre il puntino mangiato
            agentGrafica.addBehaviour(new Retract(
    				agentGrafica,
    				"puntino",
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
//            System.out.println("Current food count: " + game.currentFoodCount);
        }
    }

    @Override
    public void draw(Graphics2D g) {//69
    	if (!visible) 
            return;
        
        g.setColor(Color.WHITE);
        g.fillRect((int) (x), (int) (y), 2, 2);
    }
    
    @Override
    public void stateChanged() {//75
        if (game.getState() == State.TITLE) 
            visible = false;
        else if (game.getState() == State.READY) 
            visible = true;
    }

    public void hideAll() {
        visible = false;
    }
}