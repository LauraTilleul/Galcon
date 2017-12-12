package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 * the class representing Spaceships, child of Item
 * @author Laura
 *
 */
class SpaceShip extends Item {

	private static final long serialVersionUID = 1099732395220632562L;

	private Item objective;
	private Planet p1 ;
	boolean isArrived;
	private int waitingTurn;
	private int powerAttack; 
	private Collision collision;
	/**
	 * constructor 
	 * @param x - center abscissa
	 * @param y - center ordinate
	 * @param w - width of the ship
	 * @param p1 - planet origin
	 */
	public SpaceShip(double x, double y, int w, Planet p1){
		super(x, y, w,p1.getPlayer());
		this.p1=p1;
		objective = p1.getObjective();
		isArrived=false;
		waitingTurn=0;
		powerAttack=p1.getShipsPowerAttack();
		collision=new Collision();
	
	}

/**
 * getter for the attack power of spaceShips, depends on the planet p1's size
 * @return the attack power
 */
	public int getPowerAttack() {
		return powerAttack;
	}

/**
 * method to check if the spaceship belongs to the hit planet
 * @param p - a planet
 * @return true if spaceship has same player than the planet p
 */
	protected boolean samePlayer(Planet p){
		if (this.getPlayer() == p.getPlayer()){
			return true;
		}
		return false;
	}
	/**
	 * this method represents the attack : used when a spaceship hits its target
	 */
	public void attack(){
		if (this.getObjective()instanceof Planet){
		Planet p= (Planet) this.getObjective();
		Player tmp= p.getPlayer(); 
		if ((this.samePlayer(p) == true)){
			p.setNumberSpaceShips(p.getNumberSpaceShips() +1); // when ships going to one of the player's planet, just incrementing of 1
		}
		else {
			if (tmp.isNeutral()){
				if (p.getNumberSpaceShips() < powerAttack){
					p.setNumberSpaceShips(powerAttack-p.getNumberSpaceShips());
					p.setPlayer(this.getPlayer());
				}
				else {
				p.setNumberSpaceShips(p.getNumberSpaceShips() - powerAttack);
				if(p.getNumberSpaceShips()<=0){
					p.setPlayer(this.getPlayer());
				}
				}
			}
			else {
				if (p.getNumberSpaceShips() < powerAttack){
					p.setNumberSpaceShips(powerAttack-p.getNumberSpaceShips());
					p.setPlayer(this.getPlayer());
				}
				else {
				p.setNumberSpaceShips(p.getNumberSpaceShips() -powerAttack);
				if (p.getNumberSpaceShips()<=0){
					p.setPlayer(this.getPlayer());
				}
				}
			}
		}
		if (tmp == Run.player2 && p.getPlayer()!= Run.player2){
			((AIPlayer) Run.player2).loose(p);
		}
		if (tmp != Run.player2 && p.getPlayer()== Run.player2){
			((AIPlayer) Run.player2).win(p);
		}
		if (!Run.player1.isHuman()){
		if (tmp == Run.player1 && p.getPlayer()!= Run.player1){
			((AIPlayer) Run.player1).loose(p);
		}
		if (tmp != Run.player1 && p.getPlayer()== Run.player1 ){
			((AIPlayer) Run.player1).win(p);
		}}
	}
	}

	/**
	 * setter for waitingTurn ( this attribute is used to make some ships wait when several waves are needed)
	 * @param waitingTurn - the number of turns it will be waiting (1 turn = 1 occurrence of the principal timer)
	 */
	public void setWaitingTurn(int waitingTurn) {
		this.waitingTurn = waitingTurn;
	}
	/**
	 * moving methods, taking collision in consideration 
	 * @param ListPlanets - list of planets to avoid
	 */
	public void move (ArrayList<Planet> ListPlanets){
		/* handling the waiting part */
		if (waitingTurn>0){
			waitingTurn --;
			if (waitingTurn==0){
				if (p1.getPlayer()!= this.getPlayer() || p1.getNumberSpaceShips()==0 ){
					this.setWaitingTurn(-1);
					p1.getListSpaceShips().remove(this);
				}else {
				p1.setNumberSpaceShips(p1.getNumberSpaceShips()-1);
				}
			}
		}
		
		if (!objective.intersection(this)) {
			if (waitingTurn==0){
			double newx = center.getX();
			double newy = center.getY();
			double tmp=newx; double tmp2=newy;

		/* if there is a collision, treat it in priority */
			if (!(getCollision().getType().equals(""))){
				collision.handle(ListPlanets,this);
			}
			
			else {
			if (newx > objective.getLocation().getX()) { 
				newx--;
			} else {
				if (newx < objective.getLocation().getX()){
				newx++;
			}}
			if (newy > objective.getLocation().getY()) {
				newy--;
			} else { if (newy < objective.getLocation().getY()) {
				newy++;}
			}
			
			SpaceShip sp= new SpaceShip(newx,newy,getWidth(),this.getP1());
			Planet planetObs=sp.intersection(ListPlanets);

	/* if the position obtained is actually making the ship intersect with something, defining the collision */
			
			if (planetObs!=null){
			collision.define(planetObs,sp, this);
			}
			center.setLocation(newx, newy);
		 }
		}
		}else {
			isArrived=true;
			p1.getListSpaceShips().remove(this);
			if (this.getObjective() instanceof Planet){
			this.attack();
			objective = this;
			}

		}
		
	}
/** getter for the collision
 * 
 * @return collision
 */
	public Collision getCollision() {
		return collision;
	}
	/**
	 * setter for the collision 
	 * @param collision - the collision to set
	 */
	public void setCollision(Collision collision) {
		this.collision = collision;
	}
	/**
	 * getter for P1 
	 * @return p1 - the starting Planet
	 */
	public Planet getP1() {
		return p1;
	}
	public Item getObjective(){
		return objective;
	}
	/**
	 * boolean to check if the ship has arrived to destination
	 * @return true if the ship is arrived to its objective
	 */
	public boolean isArrived(){
		return isArrived;
	}
	/**
	 * drawing method
	 */
	@Override
	public void draw(Graphics2D arg0) {
		if (waitingTurn==0 && !p1.contains(this)){
		Color col=Color.white;
		Point2D pos = this.center;
		if (!this.getPlayer().isNeutral()){
			int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
			col=this.getPlayer().getColor();
			arg0.setColor(col);
			arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}	}}
}


