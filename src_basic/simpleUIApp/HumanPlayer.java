package simpleUIApp;

import java.awt.Color;
import java.io.Serializable;

/**
 * this Class represents the human player
 * @author Laura
 *
 */
public class HumanPlayer implements Player,Serializable {
	
	private static final long serialVersionUID = -7985253929262081612L;
	private String name;
	private Color color;
	private boolean neutral;
	
	/**
	 * constructor 
	 * @param name - name that the user types when launching the game
	 * @param color - color of player's planets
	 * @param neutral - boolean returning true if player is neutral (only used to assign a player to neutral planets)
	 */
	public HumanPlayer(String name,Color color, boolean neutral){
		this.name = name;
		this.color= color;
		this.neutral=neutral;
	}
	public Color getColor(){
		return color;
		}
	public String getName(){
		return name;
	}
	
	public boolean isHuman(){
		return true;
	}
	public boolean isNeutral(){
		return neutral;
	}
}
