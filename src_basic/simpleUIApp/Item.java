package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Any graphical element that will be handled by the application.
 *
 */
abstract class Item implements Serializable {

	private static final long serialVersionUID = 272302362533210822L;

	protected final Point2D center;
	private final int width;
	protected Player player;
	private Item objective;
/**
 * constructor 
 * @param x - abscissa of the center
 * @param y - ordinate of the center
 * @param w - width of the Item
 * @param p - player owning this Item
 */
	public Item(double x, double y, int w, Player p) {
		center = new Point2D.Double(x, y);
		width = w;
		player=p;
	}
/**
 * method returning the location of the Item's center
 * @return the point 2D representing the center
 */
	public Point2D getLocation() {
		return center;
	}
	
	/**
	 * setter for the Item's center
	 * @param arg0 - new abscissa 
	 * @param arg1 - new ordinate
	 */
	public void setLocation(double arg0, double arg1){
		center.setLocation(arg0, arg1);
	}
	/**
	 * getter for the Item's width
	 * @return the Item's width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * getter for the player owning the Item
	 * @return the player owning the Item
	 */
	public Player getPlayer(){ // 1,2 or 0 if the planet doesn't belong to any player
		return this.player;
	}
	/**
	 * setter to change the owner of the Item
	 * @param player - new Item's owner
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * drawing Item
	 * @param arg0 - a graphics2D
	 */
	public abstract void draw(Graphics2D arg0);

	/**
	 * setter for the Item's objective
	 * @param o - the new Item's objective
	 */
	public void setObjective(Item o){ 
	objective=o;
	}
	/**
	 * getter for the Item's objective
	 * @return the Item's objective
	 */
	public Item getObjective(){
		return objective;
	}

/**
 * checks if a point2D is inside an Item
 * @param p - point2D 
 * @return true if the point p is inside the Item
 */
	public boolean contains(Point2D p) {
		int wid=getWidth()/2;
		return ((p.getX()>= center.getX()-wid ) && (p.getX() <= center.getX()+wid)  && (p.getY() >= center.getY()-wid ) && (p.getY() <= center.getY()+wid ) );

	}
	/**
	 * checks if there's an intersection between two Items
	 * @param item 
	 * @return true if there is an intersection between those two Items
	 */
	public boolean intersection(Item item){
		return (this.contains(item.getCorner(1)) || this.contains(item.getCorner(2)) || this.contains(item.getCorner(3)) || this.contains(item.getCorner(4)));
	}
	/**
	 * checks if an Item is contained inside another
	 * @param item
	 * @return true if the parameter item is contained inside the one executing the method
	 */
	public boolean contains(Item item){
		return (this.contains(item.getCorner(1)) && this.contains(item.getCorner(2)) && this.contains(item.getCorner(3)) && this.contains(item.getCorner(4)));
	}
	/**
	 * checks if there is an intersection between this Item and a list of Planets
	 * @param planets - the list of planets
	 * @return true if there is an intersection between the Item and one of the planets in the arraylist
	 */
	public Planet intersection(ArrayList<Planet> planets){
		for (Planet pl : planets){
			if (pl.intersection(this) && !(pl.equals(this.getObjective()))){
				return pl;
			}
		}
		return null;
	}
	/**
	 * method used at the planet's creation to assure a minimum distance between planets
	 * @param planets - the list of planets
	 * @return true if the distance is OK between the item and each of the planets, false else
	 */
	public boolean minimumDistance(ArrayList<Item> planets){
		int distMin=2*getWidth(); 
		for (Item pl: planets){ 
			Planet plBig= new Planet(pl.getLocation().getX(), pl.getLocation().getY(),getWidth()+distMin,new HumanPlayer("",Color.white,true));
			if (plBig.intersection(this)){
				return false;
			}
		}
		return true;
	}
	/**
	 * method used to obtain a corner of an object (1 being the top-Left corner, 2 the top Right , 3 the Down Left, 4 Down right)
	 * @param num - the number of the corner
	 * @return a point corresponding to the corner number num 
	 */
	public Point2D getCorner(int num){
		double abs=this.getLocation().getX();
		double ord=this.getLocation().getY();
		int wid=this.getWidth()/2;
		Point2D corner = new Point2D.Double(0,0);
		switch(num){
		case 1:
			 corner = new Point2D.Double(abs-wid, ord-wid);
			 break;
		case 2:
			 corner= new Point2D.Double(abs+wid,ord-wid);
			 break;
		case 3:
			 corner = new Point2D.Double(abs-wid, ord+wid);
			 break;
		case 4:
			 corner = new Point2D.Double(abs+wid, ord+wid);
			 break;
		default :
			break;
		}
		return corner;
		
	}
}