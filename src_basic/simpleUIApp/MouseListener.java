package simpleUIApp;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.simpleUI.KeyPress;
import fr.ubordeaux.simpleUI.MouseHandler;
/**
 * the MouseListener handles the mouse's actions
 * @author Laura
 *
 */
public class MouseListener implements MouseHandler<Item> {

	ArrayList<Item> dragList = new ArrayList<Item>();;

	public MouseListener() {
		super();
	}

	@Override
	/**
	 * on mouseClick + ALT : prints details about the planet
	 * on mouseClick + Shift : if the planet belongs to the player, incrementing percent of ships sent of 25% 
	 */
	public void mouseClicked(List<Item> arg0, KeyPress arg1) {
		for (Item testItem : arg0) {
			if (arg1 == KeyPress.ALTGR && testItem instanceof Planet){
				System.out.println(testItem.toString());
			}
			if (arg1== KeyPress.SHIFT && testItem instanceof Planet && testItem.getPlayer()==Run.player1){
				if (((Planet)testItem).getPercentShipsAttack()==100){
					((Planet)testItem).setPercentShipsAttack(0);
				}
			((Planet) testItem).setPercentShipsAttack(((Planet) testItem).getPercentShipsAttack()+25);
			System.out.println("Playing with"+((Planet) testItem).getPercentShipsAttack()+"% of Spaceships!");
			}
		}
	}
/**
 * creating a dragList containing the items from arg0
 */
	@Override
	public void mouseDrag(List<Item> arg0, KeyPress arg1) {
		dragList = new ArrayList<Item>(arg0);
	}
/**
 * on mouse dragg : if CTRL is pressed : can add several planets from the player in the dragList
 */
	@Override
	public void mouseDragging(List<Item> arg0, KeyPress arg1) {
		if (!arg0.isEmpty() && arg1== KeyPress.CRTL){
		for(Item items : arg0){
			if ( dragList.contains(items)){
				dragList.remove(items);
			}
		}
		for(Item items : arg0){
			if (items.getPlayer()== Run.player1){
				dragList.add(items);
			}
		}
	}}

	/**
	 * on MouseDrop : if pressing ALTGR, changing the target of the planet's current squadron (spaceships) by the planet on drop
	 * else just targetting the planet on drop
	 */
	@Override
	public void mouseDrop(List<Item> arg0, KeyPress arg1) {
		if (arg0.isEmpty()){
			System.out.println("Nothing selected!");
		}else {
		if (arg1== KeyPress.ALTGR){
			for (Item item : dragList){
				for (SpaceShip sp : ((Planet) item).getListSpaceShips()){
					sp.setObjective(arg0.get(0));
				}
			}
		} else {
		for (Item item : dragList) {
			if (item instanceof Planet && arg0.get(0) instanceof Planet && item.getPlayer().isHuman() && !(item.getPlayer().isNeutral()) &&((Planet) item).getNumberSpaceShips()>0){
			item.setObjective((Planet)(arg0.get(0)));
			((Planet) item).action();
			}
		}}
	}}

	@Override
	public void mouseOver(List<Item> arg0, KeyPress arg1) {
	}
/**
 * mouseWheel moved UP : increment the percent of ships sent by 5% 
 * mouseWheel moved DOWN : decrement the percent of ships sent by 5%
 */
	@Override
	public void mouseWheelMoved(List<Item> arg0, KeyPress arg1, int arg2) {
		System.out.println(arg0 + " using " + arg1.toString() + " wheel rotate " + arg2);
		for (Item item : arg0){
			if (item instanceof Planet){ //need to precise that the planet must belong to the player
				if (arg2==1){ 
					if (((Planet)item).getPercentShipsAttack()==100){
						System.out.println("SPACESHIPS TO 100% CANNOT GO HIGHER");
					}
					else {
					((Planet)item).setPercentShipsAttack(((Planet) item).getPercentShipsAttack()+5);
				}
			}
				if (arg2==-1){
					if (((Planet)item).getPercentShipsAttack()==5){
						System.out.println("SPACESHIPS TO 5% CANNOT GO LOWER");
					}
					else {
						((Planet)item).setPercentShipsAttack(((Planet) item).getPercentShipsAttack()-5);
					}
				}
		}
	}
	}
}