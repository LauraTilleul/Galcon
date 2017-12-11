package simpleUIApp;

import java.awt.Color;
/**
 * interface Player, implemented by HumanPlayer and AIPlayer
 * @author Laura
 *
 */
public interface Player {
/**
 * getter for the color
 * @return the color associated to this player
 */
	public Color getColor();
/**
 * getter for the name	
 * @return the name associated to this player
 */
	public String getName();
/**
 * checks whether a player is a HumanPlayer 	
 * @return true if Human, false if AI
 */
	public boolean isHuman();
/**
 * checks whether a player is neutral (only used for the neutral planets at the beginning)	
 * @return true if it is neutral
 */
	public boolean isNeutral();
}
