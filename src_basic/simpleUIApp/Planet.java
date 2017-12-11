package simpleUIApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
/**
 * the class representing planets, child of Item
 * @author Laura
 *
 */
class Planet extends Item{

	private static final long serialVersionUID = -7088583650046587970L;
	
	private int productionRate;
	private int numberSpaceShips;
	private Item objective;
	private ArrayList<SpaceShip> listSpaceShips;
	private int percentShipsAttack;
	private int shipsPowerAttack;
	private int shipSize;

	public Planet(double x, double y, int w, Player state) {
		super(x, y, w, state);
		Random random = new Random();
		this.productionRate=random.nextInt(9)+2; //random production rate between 2 & 10
		this.numberSpaceShips=productionRate*10;
		this.objective=this;
		listSpaceShips=new ArrayList<SpaceShip>();
		percentShipsAttack=100;
		shipsPowerAttack=w/10 -4; // bigger planets produces more powerful ships ! (power from 1 to 4)
		shipSize= (w/10-1)+5;
		
	}
	
	/**
	 * Getter for the percent of spaceships to send for an attack
	 * @return the percent of spaceships to send for an attack
	 */
	public int getPercentShipsAttack() {
		return percentShipsAttack;
	}
/**
 * Setter for the percent of ships sent by an attack
 * @param num - the new percent of ships
 */
	public void setPercentShipsAttack(int num) {
		percentShipsAttack = num;
	}
/**
 * Getter for the attack power of the ships produced by this planet. 
 * This value depends on the size of the planet
 * @return the attack power of ships
 */
	public int getShipsPowerAttack() {
		return shipsPowerAttack;
	}
/**
 * this is the method called to create a new squadron of ships
 */
	public void action(){
		if (this.objective != this){
			Squadron squad= new Squadron(this);
			squad.init(0,0);
	}
	}
	/**
	 * getter for the ships' size : depends on the planet's size
	 * @return the size of the ships produced by this planet
	 */
	public int getShipSize() {
		return shipSize;
	}
/**
 * setter for the number of space ships of the planet
 * @param numberSpaceShips - the new number of space ships of the planet
 */
	public void setNumberSpaceShips(int numberSpaceShips) {
		this.numberSpaceShips = numberSpaceShips;
	}
/**
 * drawing a planet, color depends on the player owning it 
 */
	public void draw(Graphics2D arg0) {
		Color col= Color.white;
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		arg0.setStroke(new BasicStroke(1.7f));
		if (this.getPlayer().isNeutral()){
			arg0.setColor(Color.red);
			arg0.drawRect(x - w / 2, y - w / 2, w, w);
		}
		else {
			col= this.getPlayer().getColor();
		arg0.drawRect(x - w / 2, y - w / 2, w, w);	
		arg0.setColor(col);
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}
		arg0.setColor(Color.BLACK);
		arg0.setFont(new Font("Arial", 1, 17));
		arg0.drawString(String.valueOf(this.getNumberSpaceShips()),x-w/8,y+w/8);
	}
	/**
	 * adding number to the planet's number of spaceships
	 * @param number - the number to add
	 */
	public void addNumberSpaceShips(int number){
		this.numberSpaceShips+=number;
	}
	/**
	 * getter for number of space Ships the planet has
	 * @return the number of ships of the planet
	 */
	public int getNumberSpaceShips() {
		return numberSpaceShips;
	}
	/**
	 * getter for the planet's list of spaceships
	 * @return the list of spaceships
	 */
	public ArrayList<SpaceShip> getListSpaceShips(){
		return listSpaceShips;
	}
/**
 * getter for the planet's productionRate
 * @return the planet's productionRate
 */
	public int getProductionRate() {
		return productionRate;
	}
	/**
	 * method tostring, printing details : name of the player, percent of ships, power attack
	 */
	public String toString(){
		return "This Planet belongs to "+ this.getPlayer().getName() + "\n" + "Current percent of ships: "+ percentShipsAttack + "\n Power attack : " + shipsPowerAttack; 
	}
	/**
	 * setter for the item's objective
	 * @param o - the new item's objective
	 */
	public void setObjective(Item o){ 
	objective=o;
	}
	/**
	 * getter for the item's objective
	 * @return the item's objective
	 */
	public Item getObjective(){
		return objective;
	}
	}
