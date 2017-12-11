package simpleUIApp;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import fr.ubordeaux.simpleUI.Application;
import fr.ubordeaux.simpleUI.TimerRunnable;
import fr.ubordeaux.simpleUI.TimerTask;

/**
 * Class used to represent Artificial intelligence
 * It is possible to launch a game between 2 AI.
 * @author Laura
 *
 */

public class AIPlayer implements Player, Serializable{

	private static final long serialVersionUID = 8656239075768690349L;
	
	private ArrayList<Planet> AIPlanets;
	private ArrayList<Planet> AITargets;
	private Color color;
	private String name;
	private boolean loser;
	
	/** 
	 * Basic constructor,
	 * @param name - the AI name
	 * @param color - the color of AI's planets
	 * */
	public AIPlayer(String name, Color color){
			loser=false;	
			AIPlanets= new ArrayList<Planet>();
			AITargets= new ArrayList<Planet>();
			this.name=name;
			this.color=color;
	}
	public boolean isNeutral(){
		return false;
	}
	public Color getColor(){
		return color;
	}
	public String getName(){
		return name;
	}
	public boolean isHuman(){
		return false;
	}
	/**
	 * init actually initializes the AIPlayers attributes AITargets and AIPlanets,
	 * AITargets = possible targets for AI (can shoot its own planet),
	 * AIPlanets= planets owned by AI,
	 * putting ennemies/neutral planets twice in AITargets, just so the AI doesn't shoot at its own planets too much
	 */
	public void init(){
				for (Planet planets : Run.LIST_PLANETS){
					AITargets.add(planets);
					if (planets.getPlayer()==this){
						AIPlanets.add(planets);
					}
					else {
					AITargets.add(planets);
					}
				}
	}
	/**
	 * loose is called when one of AI planet's is conquered by the other player,
	 * it removes this planet from the owned-planets list and adding it to the targets list
	 * @param p - the planet conquered by the other player
	 */
	public void loose(Planet p){
		AIPlanets.remove(p); 
		if (AIPlanets.isEmpty()){
			loser=true;
		}
		AITargets.add(p);
	}
	/**
	 * win is called when AI conquers a planet 
	 * @param p - the planet conquered by AI
	 */
	public void win(Planet p){
		AIPlanets.add(p);
		AITargets.remove(p);
	}
	/**
	 * action launches an attack from AI, pretty randomly
	 */
	public void action(){
		
				Collections.shuffle(AIPlanets);
				Planet shooter= AIPlanets.get(0);
				Collections.shuffle(AITargets);
				Planet target= AITargets.get(0);
				if (target==shooter){ 
					target=AITargets.get(1);
				}
				shooter.setObjective(target);
				shooter.action();
					
}
/**
 * isLoser tests whether AI has lost the game
 * @return true if AI lost, false else
 */
	public boolean isLoser() {
		return loser;
	}
	
}
