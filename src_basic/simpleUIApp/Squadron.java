package simpleUIApp;

import java.util.ArrayList;
/**
 * A squadron is created every time there is an action from a planet,
 * this class handles the cases where several waves of ships have to be sent
 * @author Laura
 *
 */
public class Squadron {
	private Planet startPl;
	private Planet endPl;
	private int nbShips;
/**
 * constructor	
 * @param p - the starting planet
 */
public Squadron(Planet p){
	startPl=p;
	endPl=(Planet) p.getObjective();
	nbShips= p.getPercentShipsAttack()*p.getNumberSpaceShips()/100;
}
/**
 * init() is a recursive function creating the ships necessary, and put some to wait if several waves of ships has to be sent.
 * It defines in which direction the ships should be sent and from which side of the planet they should start
 * @param wait - wait get bigger each time a new wave is sent,
 * @param rest - the rest of ships to be send by new waves 
 */
public void init(int wait,int rest){
		int tmp;
		if (rest==0){
			tmp= nbShips;
			if (tmp==0){
				tmp=startPl.getNumberSpaceShips(); }
		}
		else {
			tmp=rest;
		}
		
		ArrayList<SpaceShip> listSp= new ArrayList<SpaceShip>();
		int space=startPl.getShipSize()+5;
		int count= (startPl.getWidth())/space ;
		double x=0 ; double y=0;
		int xmov=0 ; 
		int ymov=0;
		
		if (endPl.getLocation().getX()<= startPl.getCorner(2).getX() + space && endPl.getLocation().getX() >= startPl.getCorner(1).getX() - space){
			if (endPl.getLocation().getY()>= startPl.getLocation().getY()){
				y= startPl.getCorner(3).getY() +space;
			}
			else 
			{
				y= startPl.getCorner(1).getY() -space; 
			}
			x=startPl.getCorner(1).getX()- space;
			xmov=1;
		}
		else {
			if (endPl.getLocation().getY()<= startPl.getCorner(3).getY() + space && endPl.getLocation().getY() >= startPl.getCorner(1).getY() -space){
				if (endPl.getLocation().getX()>= startPl.getLocation().getX()){
					x= startPl.getCorner(2).getX() +space;
				}
				else 
				{ 	
					x= startPl.getCorner(1).getX() -space; 
				}
				y= startPl.getCorner(1).getY()-space ;
				ymov=1;
			}
			else {
				if (endPl.getLocation().getX() > startPl.getLocation().getX() ){
					x= startPl.getCorner(2).getX() + space;
					xmov=-1;
					
					if (endPl.getLocation().getY() > startPl.getLocation().getY()){
						y= startPl.getCorner(4).getY() + space;
						ymov=-1;
				}
				else { 
					if (endPl.getLocation().getY() < startPl.getLocation().getY()){
						y= startPl.getCorner(2).getY() - space;
						ymov=1;
				}
		} }
				else {
					if (endPl.getLocation().getX() < startPl.getLocation().getX()){
						  x= startPl.getCorner(1).getX() - space;
						  xmov=1;
						  if (endPl.getLocation().getY() > startPl.getLocation().getY()){
							  y= startPl.getCorner(4).getY() + space;
							  ymov=-1;
				}
					else { 
						if (endPl.getLocation().getY() < startPl.getLocation().getY()){
							  y= startPl.getCorner(1).getY() - space;
							  ymov=1;
						}
			}
		}
		}
		}
		}
		
		if (ymov==0){
			for(double j=x ; j<= startPl.getCorner(2).getX()+space ; j+=space){
				if ( tmp==0){
					break;
					}
				SpaceShip spaceship= new SpaceShip(j, y, startPl.getShipSize(), startPl);
				listSp.add(spaceship);
				tmp--;
			}
		} 
		else {
			
		if (xmov==0){
			for(double j=y ; j<= startPl.getCorner(3).getY()+space ; j+=space){
				if ( tmp==0){ 
					break;
				}
				SpaceShip spaceship= new SpaceShip(x, j, startPl.getShipSize(), startPl);
				listSp.add(spaceship);
				tmp--;
			}
		} else {
			
			for (int i=0; i<= count+1 ; i++){
				if ( tmp==0){
					break;
				}
				SpaceShip spaceship= new SpaceShip(x+xmov*i*space, y, startPl.getShipSize(), startPl);
				listSp.add(spaceship);
				tmp--;
				if (i!=0){
				if ( tmp==0){
					break; 
				}
				spaceship= new SpaceShip(x, y+ ymov*i*space, startPl.getShipSize(), startPl);
				listSp.add(spaceship);
				tmp--;
				if ( tmp==0 ){
					break;
				}
				}
			}
		}
		}
		
		for (SpaceShip sp : listSp){
		sp.setWaitingTurn(wait*50);
		
		if (wait==0){
			startPl.addNumberSpaceShips(-1);
		}
		startPl.getListSpaceShips().add(sp);
		}
		if (tmp>0){
			wait+=1;
			init(wait,tmp);
		}
	
}
}