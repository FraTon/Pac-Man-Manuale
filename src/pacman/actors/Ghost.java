package pacman.actors;

import jade.core.Agent;
import pacman.PacmanActor;
import pacman.PacmanGame;
import pacman.enums.Mode;
import pacman.enums.State;
import pacman.view.ShortestPathFinder;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import behav.FugaFantasmaArancione;
import behav.FugaFantasmaAzzurro;
import behav.FugaFantasmaRosa;
import behav.FugaFantasmaRosso;
import behav.MossaFantasmaArancione;
import behav.MossaFantasmaAzzurro;
import behav.MossaFantasmaRosa;
import behav.MossaFantasmaRosso;


public class Ghost extends PacmanActor {

	public static Pacman pacman;
    public int type;
    public String color;
    public int cageUpDownCount;
    public Point[] initialPositions = {new Point(18, 11), new Point(16, 14), 
    								   new Point(18, 14), new Point(20, 14)};
    public int dx;
    public int dy;
    public int col;
    public int row;
    public int direction = 0;
    public static int lastDirection;
    public static int direzioneRosso; //direzione fantasma rosso
    public static int colonnaRosso; //colonna matrice fantasma rosso
    public static int rigaRosso; //riga matrice fantasma rosso
    public static int colonnaArancione;
    public static int rigaArancione;
    
    public Mode mode = Mode.CAGE;
    public List<Integer> desiredDirections = new ArrayList<Integer>();
    public int desiredDirection;
    public static final int[] backwardDirections = {2, 3, 0, 1};
    public long vulnerableModeStartTime;
    public boolean markAsVulnerable;
    
    // Using path finder just to return the ghost to the center (cage)
    public ShortestPathFinder pathFinder; 
    
    public Ghost(PacmanGame game,Agent grafica, Pacman pacman, int type) {
        super(game,grafica);
        this.pacman = pacman;
        this.type = type;
        this.pathFinder = new ShortestPathFinder(game.maze);
        //In base al type, assegnamento della stringa colore alla variabile color
        if(type==0)
        	color="rosso";
        if(type==1)
        	color="rosa";
        if(type==2)
        	color="azzurro";
        if(type==3)
        	color="arancione";
    }

    private void setMode(Mode mode) {
        this.mode = mode;
        modeChanged();
    }
    
    ///recupera tutte le immagini di ogni fantasma
    @Override
    public void init() {
        String[] ghostFrameNames = new String[8 + 4 + 4];
        
        for (int i = 0; i < 8; i++) 
            ghostFrameNames[i] = "/res/ghost_" + type + "_" + i + ".png";
        
        for (int i = 0; i < 4; i++) 
            ghostFrameNames[8 + i] = "/res/ghost_vulnerable_" + i + ".png";
        
        for (int i = 0; i < 4; i++) 
            ghostFrameNames[12 + i] = "/res/ghost_died_" + i + ".png";
        
        loadFrames(ghostFrameNames);
        collider = new Rectangle(0, 0, 8, 8);
        setMode(Mode.CAGE); //FANTASMI NELLA GABBIA
    }
    
    private int getTargetX(int col) {
        return col * 8 - 3 - 32;
    }

    private int getTargetY(int row) {
        return (row + 3) * 8 - 2;
    }
    
    //VALORE GRAFICO DELLA POSIZOINE DEL FANTASMA
    public void updatePosition() {
        x = getTargetX(col);
        y = getTargetY(row);
    }
    
    //ASSEGNA I VALOR COL E ROW ALLA POSIIONE DEL FANTASMA VERA E PROPRIA
    private void updatePosition(int col, int row) {
        this.col = col;
        this.row = row;
        updatePosition();
    }
    
   public int getCol() {
	   return col;
   }
    
    //CALCOLA PER LO SPOSTAMENTO DEL DISEGNO DEL FANTASMA
    private boolean moveToTargetPosition(int targetX, int targetY, double velocity) {
    	double sx = (double) (targetX - x);
    	double sy = (double) (targetY - y);
        double vx = Math.abs(sx) < velocity ? Math.abs(sx) : velocity;
        double vy = Math.abs(sy) < velocity ? Math.abs(sy) : velocity;
        double idx = vx * (sx == 0 ? 0 : sx > 0 ? 1 : -1);
        double idy = vy * (sy == 0 ? 0 : sy > 0 ? 1 : -1);
        x += idx;
        y += idy;
        return sx != 0 || sy != 0;
    }

    private boolean moveToGridPosition(int col, int row, double velocity) {
        int targetX = getTargetX(col);
        int targetY = getTargetY(row);
        return moveToTargetPosition(targetX, targetY, velocity);
    }
    
    @Override
    public void updateTitle() {
        int frameIndex = 0;
        x = pacman.x + 17 + 17 * type;
        y = 200;
        
        if (pacman.direction == 0) 
            frameIndex = 8 + (int) (System.nanoTime() * 0.00000001) % 2;
        else if (pacman.direction == 2) 
            frameIndex = 2 * pacman.direction + (int) (System.nanoTime() * 0.00000001) % 2;
        
        frame = frames[frameIndex];
    }
    
    
    //AGGIONRAMENTO POSIZIONE FANTASMA A SECONDA DELLA MODALIT� DI GIOCO DEL SINGOLO FANTASMA
    @Override
    public void updatePlaying() {
        switch (mode) {
            case CAGE: 
            	updateGhostCage();
            	break;
            case NORMAL:
            	updateGhostNormal();
            	break;
            case VULNERABLE: 
            	updateGhostVulnerable();
            	break;
            case DIED: 
            	updateGhostDied();
            	break;
        }
        
        updateAnimation();
    }

    public void updateAnimation() {
        int frameIndex = 0;
        
        switch (mode) {
            case CAGE: 
            case NORMAL:
                frameIndex = 2 * direction + (int) (System.nanoTime() * 0.00000001) % 2;
                
                if (!markAsVulnerable) 
                    break;
                
            case VULNERABLE:
                if (System.currentTimeMillis() - vulnerableModeStartTime > 5000) 
                    frameIndex = 8 + (int) (System.nanoTime() * 0.00000002) % 4;
                else 
                    frameIndex = 8 + (int) (System.nanoTime() * 0.00000001) % 2;

                break;
            case DIED:
                frameIndex = 12 + direction;
                break;
        }
        
        frame = frames[frameIndex];
    }

    //AGGIORNAMENTO POSIZIONE FANTAMSMA CHE SI TROVA ANCORA NELLA GABBIA
    private void updateGhostCage() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    Point initialPosition = initialPositions[type];
                    updatePosition(initialPosition.x, initialPosition.y);
                    x -= 4;
                    cageUpDownCount = 0;
                                   
                    if (type == 0) {
                        instructionPointer = 6;
                        break;
                    }
                    else if (type == 2) {
                        instructionPointer = 2;
                        break;
                    }
                    
                    instructionPointer = 1;
                case 1:
                    if (moveToTargetPosition((int) x, 134 + 4, 0.8)) 
                        break yield;
                    
                    instructionPointer = 2;
                case 2:
                    if (moveToTargetPosition((int) x, 134 - 4, 0.8)) 
                        break yield;
                    
                    cageUpDownCount++;
                    
                    if (cageUpDownCount <= type * 2) {
                        instructionPointer = 1;
                        break yield;
                    }
                    
                    instructionPointer = 3;
                case 3:
                    if (moveToTargetPosition((int) x, 134, 0.8)) 
                        break yield;
                    
                    instructionPointer = 4;
                case 4:
                    if (moveToTargetPosition((int) 105, 134, 0.8)) 
                        break yield;
                    
                    instructionPointer = 5;
                case 5:
                    if (moveToTargetPosition((int) 105, 110, 0.8)) 
                        break yield;
                    
                    if ((int) (2 * Math.random()) == 0) {
                        instructionPointer = 7;
                        continue yield;
                    }
                    
                    instructionPointer = 6;
                case 6:
                    if (moveToTargetPosition((int) 109, 110, 0.8)) 
                        break yield;
                    
                    desiredDirection = 0;
                    lastDirection = 0;
                    updatePosition(18, 11);
                    instructionPointer = 8;
                    continue yield;
                case 7:
                    if (moveToTargetPosition((int) 101, 110, 0.8)) 
                        break yield;
                    
                    desiredDirection = 2;
                    lastDirection = 2;
                    updatePosition(17, 11);
                    instructionPointer = 8;
                case 8:
                    setMode(Mode.NORMAL);     //FANTASMA � IN MODALIT� NORMALE
                    break yield;
            }
        }
    }
    
    private PacmanCatchedAction pacmanCatchedAction = new PacmanCatchedAction();
    
    private class PacmanCatchedAction implements Runnable {
       
    	@Override
        public void run() {
            game.setState(State.PACMAN_DIED);
        }
    }
    
    
    
    private void updateGhostNormal() {
        if (checkVulnerableModeTime() && markAsVulnerable) {  //fantasma mangiabile
            setMode(Mode.VULNERABLE);   
            markAsVulnerable = false;
        }
        
        updateGhostMovementNormal(pacman.col,pacman.row,0.8,pacmanCatchedAction);
        
    }
    
    
    
    private GhostCatchedAction ghostCatchedAction = new GhostCatchedAction();
    
    private class GhostCatchedAction implements Runnable {
        
    	@Override
        public void run() {
            game.ghostCatched(Ghost.this);
        }
    }
    
    
    //MOVIMENTO DEL FANTASMA CHE SCAPPA DA PAC-MAN
    private void updateGhostVulnerable() {

    	if (markAsVulnerable) 
            markAsVulnerable = false;

        updateGhostMovementVulnerable(pacman.col, pacman.row, 0.8, ghostCatchedAction); // run away movement
        
        // return to normal mode after 8 seconds
        if (!checkVulnerableModeTime()) 
            setMode(Mode.NORMAL);
    }
    
    private boolean checkVulnerableModeTime() {
        return System.currentTimeMillis() - vulnerableModeStartTime <= 8000;
    }
    
    
    //FANTASMA MORTO MANGIATO DA PACMAN
    private void updateGhostDied() {
    	
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:                	
                    pathFinder.find(col, row, 18, 11);
                    instructionPointer = 1;
                case 1:
                    if (!pathFinder.hasNext()) {
                        instructionPointer = 3;
                        continue yield;
                    }
                    
                    Point nextPosition = pathFinder.getNext();                
                    col = nextPosition.x;
                    row = nextPosition.y;
                    instructionPointer = 2;
                case 2:
                    if (!moveToGridPosition(col, row, 4)) {
                        if (row == 11 && (col == 17 || col == 18)) {
                            instructionPointer = 3; 
                            continue yield;
                        }
                         
                        instructionPointer = 1; 
                        continue yield;
                    }
                    
                    break yield;
                case 3:
                    if (!moveToTargetPosition(105, 110, 0.8)){
                        instructionPointer = 4;
                        continue yield;
                    }
                    break yield;
                case 4:
                    if (!moveToTargetPosition(105, 134, 0.8)){
                        instructionPointer = 5;
                        continue yield;
                    }
                    
                    break yield;
                case 5:
                    setMode(Mode.CAGE);  //tornato nel recinto
                    instructionPointer = 4;
                    break yield;
            }
        }
    }    
    
    
    
    private void updateGhostMovementNormal(int targetCol, int targetRow, double velocity,
			 Runnable collisionWithPacmanAction) {
        
		//Definisce la sincronizzazione obbligata in ordine
    	yield:
    	
        while (true) {
                	
        	switch (instructionPointer) {
            
                case 0:
                	
                	//in base al colore del fantasma
                	//aggiunta di un behaviour all'agentGrafica che permette di invocare la mossa di quel fantasma calcolata da prolog
                	if(color.equals("rosso")){
            			
            			agentGrafica.addBehaviour(new MossaFantasmaRosso(

            					agentGrafica,
            					this
            				));    
            			
            		}else if(color.equals("rosa")){
            			
            			agentGrafica.addBehaviour(new MossaFantasmaRosa(
            					agentGrafica,
            					this
            				));
            			
            		}else if(color.equals("azzurro")){

            			agentGrafica.addBehaviour(new MossaFantasmaAzzurro(
            					agentGrafica,
            					this
            				));
            				
            		}else if(color.equals("arancione")){
            			
            			agentGrafica.addBehaviour(new MossaFantasmaArancione(
            					agentGrafica,
            					this
            				));
            		}
               	
                    instructionPointer = 1;
                    
                case 1:
                
                    if (!moveToGridPosition(col, row, velocity)) {
                    	lastDirection = direction;
                        instructionPointer = 0;
                    }
                    
                    //controlla se collide
                    if (collisionWithPacmanAction != null && checkCollisionWithPacman()) 
                        collisionWithPacmanAction.run();
                    
                    break yield;
            }
        }        
    }
    
    private void updateGhostMovementVulnerable(int targetCol, int targetRow, double velocity,
			 Runnable collisionWithPacmanAction) {

		//Definisce la sincronizzazione obbligata in ordine
	   	yield:
	       while (true) {
	    	   
	           switch (instructionPointer) {
	           
	               case 0:
	            	   //in base al colore del fantasma
	                   //aggiunta di un behaviour all'agentGrafica che permette di invocare la mossa per la fuuga di quel fantasma calcolata da prolog
		               if(color.equals("rosso")){
		               		
		               		agentGrafica.addBehaviour(new FugaFantasmaRosso(
		
		           					agentGrafica,
		           					this
		           				));         
		               		
		               	}else if(color.equals("rosa")) {
		           			
		           			agentGrafica.addBehaviour(new FugaFantasmaRosa(
		           					agentGrafica,
		           					this
		           				));
		           			
		           		}else if(color.equals("azzurro")){
		
		           			agentGrafica.addBehaviour(new FugaFantasmaAzzurro(
		           					agentGrafica,
		           					this
		           				));
		           			
		           		}else if(color.equals("arancione")) {
		           			
		           			agentGrafica.addBehaviour(new FugaFantasmaArancione(
		           					agentGrafica,
		           					this
		           				));
		           		}
		               	
		                instructionPointer = 1;
	               case 1:
	  
	                   if (!moveToGridPosition(col, row, velocity)) {
	                       lastDirection = direction;
	                       instructionPointer = 0;
	                   }
	                   
	                   //controlla se collide
	                   if (collisionWithPacmanAction != null && checkCollisionWithPacman()) 
	                       collisionWithPacmanAction.run();
	                   
	                   break yield;
	           }
	       }        
   }


    //FANTASMA MANGIATO
    @Override
    public void updateGhostCatched() {
        if (mode == Mode.DIED) {
            updateGhostDied();
            updateAnimation();
        }
    }

    
    //PACMAN MANGIATO
    @Override
    public void updatePacmanDied() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 1500) 
                        break yield;
                    
                    visible = false;
                    setMode(Mode.CAGE);  //FANTASMI NEL RECINTO
                    updateAnimation();
                    break yield;
            }
        }
        updateAnimation();
    }

    @Override
    public void updateLevelCleared() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 1500) 
                        break yield;
                    
                    visible = false;
                    setMode(Mode.CAGE);
                    updateAnimation();
                    instructionPointer = 2;
                case 2:
                    break yield;
            }
        }
    }
   
    
    //VERIFICA SE FANTASMA ROSSO COLLIDE CON PAC-MAN
    private boolean checkCollisionWithPacman() {
        pacman.updateCollider(); //aggiora il rettangolo di collisione di pacman e del fantasma
        updateCollider();
        return pacman.collider.intersects(collider);
    }

    @Override
    public void updateCollider() {
        collider.setLocation((int) (x + 4), (int) (y + 4));
    }
    
    private void modeChanged() {
        instructionPointer = 0;      
    }
    
    @Override
    public void stateChanged() {
        if (game.getState() == State.TITLE) {
            updateTitle();
            visible = true;
        }
        else if (game.getState() == State.READY) 
            visible = false;
        else if (game.getState() == State.READY2) {
            setMode(Mode.CAGE);
            updateAnimation();
            Point initialPosition = initialPositions[type];
            updatePosition(initialPosition.x, initialPosition.y);
            x -= 4;
        }
        else if (game.getState() == State.PLAYING && mode != Mode.CAGE) 
            instructionPointer = 0;
        else if (game.getState() == State.PACMAN_DIED) 
            instructionPointer = 0;
        else if (game.getState() == State.LEVEL_CLEARED) 
            instructionPointer = 0;
    }
    
    public void showAll() {
        visible = true;
    }

    public void hideAll() {
        visible = false;
    }
    
    public void startGhostVulnerableMode() {
        vulnerableModeStartTime = System.currentTimeMillis();
        markAsVulnerable = true;
    }
    
    public void died() {
        setMode(Mode.DIED);
    }
}