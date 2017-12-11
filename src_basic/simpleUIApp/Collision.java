package simpleUIApp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class handles the collisions occurring during spaceships moves
 * @author Laura
 *
 */

public class Collision implements Serializable{

	private static final long serialVersionUID = -9090867203078607548L;
	
	private String type; 
	private String direction;
/**
 * Create a "empty" collision, collision will be modify whenever a collision occurs
 */
	public Collision(){
		type="";
		direction="";
	}

	/**
	 * define is called when there is a collision, setting type and direction of the collision
	 * @param obs - the planet obstacle ( the one in which the spaceship sp collides)
	 * @param sp - the spaceship which is colliding the planet obstacle
	 * @param origin - the spaceship right before the collision
	 */
	public void define(Planet obs, SpaceShip sp,  SpaceShip origin){
		if (obs!=null && obs!=origin.getObjective()){
			//horizontal
			if (sp.getLocation().getX()==origin.getLocation().getX()){
				if (origin.getLocation().getX()> origin.getObjective().getLocation().getX() || ( origin.getLocation().getX()==origin.getObjective().getLocation().getX() &&  origin.getLocation().getX()>= obs.getLocation().getX())){
					type="Horizontal";
					direction="Right";
				}
				if (origin.getLocation().getX()<origin.getObjective().getLocation().getX() || (origin.getLocation().getX()==origin.getObjective().getLocation().getX() &&  origin.getLocation().getX()< obs.getLocation().getX())){
					type="Horizontal";
					direction="Left";
				}
			}
			if (( obs.getCorner(1).getX()< sp.getCorner(1).getX() && sp.getCorner(1).getX()< obs.getCorner(2).getX()) || ( obs.getCorner(1).getX()< sp.getCorner(2).getX() && sp.getCorner(2).getX()< obs.getCorner(2).getX())){
				if (origin.getObjective().getLocation().getX() > origin.getLocation().getX() ||(origin.getObjective().getLocation().getX()== origin.getLocation().getX() && origin.getLocation().getX()>= obs.getLocation().getX())){
					type="Horizontal";
					direction="Right";
				}
				if (origin.getObjective().getLocation().getX()<origin.getLocation().getX()|| (origin.getObjective().getLocation().getX()== origin.getLocation().getX() && origin.getLocation().getX()< obs.getLocation().getX())){
					type="Horizontal";
					direction="Left";
				}
			}
			if (sp.getCorner(1).getY() == obs.getCorner(3).getY() || sp.getCorner(3).getY()== obs.getCorner(1).getY()){
				if (origin.getObjective().getLocation().getX() > origin.getLocation().getX() || (origin.getObjective().getLocation().getX() == origin.getLocation().getX())&& origin.getLocation().getX()>= obs.getLocation().getX()){
					type="Horizontal";
					direction="Right";
				}
				if (origin.getObjective().getLocation().getX()<origin.getLocation().getX() || (origin.getObjective().getLocation().getX() == origin.getLocation().getX())&& origin.getLocation().getX()< obs.getLocation().getX()){
					type="Horizontal";
					direction="Left";
				}
			}
			// vertical
			if (sp.getLocation().getY()==origin.getLocation().getY()){
				if (origin.getLocation().getY()> origin.getObjective().getLocation().getY() || (origin.getLocation().getY()==origin.getObjective().getLocation().getY() &&origin.getLocation().getY()>= obs.getLocation().getY())){
					type="Vertical";
					direction="Up";
				}
				if (origin.getLocation().getY()<origin.getObjective().getLocation().getY()|| (origin.getLocation().getY()==origin.getObjective().getLocation().getY() &&origin.getLocation().getY()< obs.getLocation().getY())){
					type="Vertical";
					direction="Down";
				}
			}
			if ( ( obs.getCorner(1).getY() < sp.getCorner(1).getY() && sp.getCorner(1).getY() < obs.getCorner(3).getY() )|| (obs.getCorner(1).getY() < sp.getCorner(3).getY() && sp.getCorner(3).getY() < obs.getCorner(3).getY() ) ){ 
				if (origin.getObjective().getLocation().getY() > origin.getLocation().getY() || ( origin.getObjective().getLocation().getY()== origin.getLocation().getY() && origin.getLocation().getY()>= obs.getLocation().getY())){
					type="Vertical";
					direction="Down";
				}
				if (origin.getObjective().getLocation().getY()<origin.getLocation().getY() || (origin.getObjective().getLocation().getY()== origin.getLocation().getY() && origin.getLocation().getY()<obs.getLocation().getY())){
					type="Vertical";
					direction="Up";
				}
			}
			if (sp.getCorner(2).getX() == obs.getCorner(1).getX() || sp.getCorner(3).getX() == obs.getCorner(2).getX() ){
				if (origin.getObjective().getLocation().getY() > origin.getLocation().getY() || (origin.getObjective().getLocation().getY() == origin.getLocation().getY() &&  origin.getLocation().getY()<obs.getLocation().getY())){
					type="Vertical";
					direction="Down";
				}
				if (origin.getObjective().getLocation().getY()<origin.getLocation().getY()|| (origin.getObjective().getLocation().getY() == origin.getLocation().getY() &&  origin.getLocation().getY()>=obs.getLocation().getY())){
					type="Vertical";
					direction="Up";
				}
					}
				}
	}
	
	/**
	 * handles collision, depending on its type and direction, and finally sets the spaceship origin to the new appropriated location
	 * it also "resets" the collision if there's no collision anymore
	 * @param ListPlanets - List of Planets on the Arena, need it to check for intersections
	 * @param origin - SpaceShip to move
	 */
	
		public void handle(ArrayList<Planet> ListPlanets, SpaceShip origin){	
			double newx=origin.getLocation().getX(); 
			double newy=origin.getLocation().getY();
			
			if (type=="Horizontal"){
				if (direction=="Left"){
					newx--;
				}
				else {
					newx++;
				}
				if (origin.getObjective().getLocation().getY() <= origin.getLocation().getY() && ((new SpaceShip(origin.getLocation().getX(),origin.getLocation().getY()-2,origin.getWidth(),origin.getP1())).intersection(ListPlanets))==null || origin.getObjective().getLocation().getY() >= origin.getLocation().getY() && ((new SpaceShip(origin.getLocation().getX(),origin.getLocation().getY()+2,origin.getWidth(),origin.getP1())).intersection(ListPlanets))==null){
					type="";
					direction="";
				}
			}
			if (type=="Vertical"){
				if (direction=="Up"){
					newy--;
				}
				else {
					newy++;
				}
				if (origin.getObjective().getLocation().getX() <= origin.getLocation().getX() && ((new SpaceShip(origin.getLocation().getX()-2,origin.getLocation().getY(),origin.getWidth(),origin.getP1())).intersection(ListPlanets))==null ||( origin.getObjective().getLocation().getX() >= origin.getLocation().getX() && ((new SpaceShip(origin.getLocation().getX()+2,origin.getLocation().getY(),origin.getWidth(),origin.getP1())).intersection(ListPlanets))==null)){
					type="";
					direction="";
				
			}
			
		}
			origin.setLocation(newx, newy);
}

/**
 * getType() is actually only used to check if the collision is "empty"
 * @return the type of collision - "Horizontal" or "Vertical" , or "" if there is no collision
 */
public String getType() {
	return type;
}

}