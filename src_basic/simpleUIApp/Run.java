package simpleUIApp;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import fr.ubordeaux.simpleUI.Application;
import fr.ubordeaux.simpleUI.ApplicationRunnable;
import fr.ubordeaux.simpleUI.Arena;
import fr.ubordeaux.simpleUI.TimerRunnable;
import fr.ubordeaux.simpleUI.TimerTask;
/**
 * Run class 
 * @author Laura
 *
 */
public class Run implements ApplicationRunnable<Item> {

	private int width;
	private int height;
	private boolean end;
	
	/* I made them static so I have access to them in MouseListener & KeyListener */
	public static ArrayList<Planet> LIST_PLANETS;
	public static Player player2;
	public static Player player1;
	
	public Run(int width, int height) {
		this.width = width;
		this.height = height;
		this.end=false;
	}

	@Override
	public void run(final Arena<Item> arg0, final Collection<? extends Item> arg1) {

		MouseListener mouseHandler = new MouseListener();

		/*
		 * We build the graphical interface by adding the graphical component
		 * corresponding to the Arena - by calling createComponent - to a JFrame.
		 */
		final JFrame frame = new JFrame("Test Arena");

		/*
		 * This is our KeyHandler that will be called by the Arena in case of key events
		 */
		KeyListener keyListener = new KeyListener(frame);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(arg0.createComponent(this.width, this.height, mouseHandler, keyListener));
		frame.pack();
		frame.setVisible(true);

		/*
		 * We initially draw the component
		 */
		arg0.refresh();
		/* the List of Planets stays the same, it's the arg1 in parameter, 
		 * player 1 is the player of the first planet added into the list,
		 * player 2 the second */
		
		LIST_PLANETS= new ArrayList<Planet>((Collection<? extends Planet>) arg1); 
		final ArrayList<SpaceShip> LIST_SPACESHIPS = new ArrayList<SpaceShip>();
		
		player1 = LIST_PLANETS.get(0).getPlayer();
		player2 = LIST_PLANETS.get(1).getPlayer();
		
		final ArrayList<AIPlayer> AIplayers= new ArrayList<AIPlayer>();
		if (!player1.isHuman()){
			((AIPlayer) player1).init();
			AIplayers.add((AIPlayer) player1);
		}
		((AIPlayer) player2).init();
		AIplayers.add((AIPlayer) player2); // for now player2 is always an AI
		
		final SaveGame game= new SaveGame();
		
		/* saving the game when closing the window  */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
		public void run(){
			if (!end){
			game.saving(end, LIST_PLANETS);
			}
		}}));
		/*
		 * We ask the Application to call the following run function every seconds. This
		 * method just refresh the component.
		 */

	/* this first timer handles the human player moves and checks for a game over */
		Application.timer(150, new TimerRunnable() {

			public void run(TimerTask timerTask) {
				arg0.refresh();

				if (end==true){
					System.out.println(" GAME OVER ! THE WINNER IS " + ((ArrayList<Planet>) arg1).get(0).getPlayer().getName()+ "!");
					game.saving(end,LIST_PLANETS);
					timerTask.cancel();

				}
				
				
				for (Planet item : LIST_PLANETS){
					LIST_SPACESHIPS.addAll(item.getListSpaceShips() );
				}
				
				Iterator<SpaceShip> it = LIST_SPACESHIPS.iterator();
				while (it.hasNext()){
					SpaceShip el= it.next();
					if (el.isArrived()==false){
						el.move(LIST_PLANETS);
					}
					if (el.isArrived()){
						it.remove();
						
					}
				}
				
				arg1.clear(); 
				((ArrayList<Item>)(arg1)).addAll(LIST_PLANETS);
				((ArrayList<Item>)(arg1)).addAll(LIST_SPACESHIPS);
				
				end=true;
				Player player=((ArrayList<Planet>) arg1).get(0).getPlayer();
				for (Planet planets : LIST_PLANETS){
					if (planets.getPlayer()!= player && !planets.getPlayer().isNeutral()){
						end=false;
					}
				}
			}

		});

	// A second timer for AI moves
		Application.timer(3000, new TimerRunnable() {
			public void run(final TimerTask timerTask) {
				for (AIPlayer player : AIplayers){
					if (((AIPlayer) player).isLoser()){
						end=true;
						timerTask.cancel();
					}
					if (end!=true){	
					((AIPlayer) player).action();
					
				}
				}
				
				
				}});		

/* a third timer to increment the planets' number of ships */
	Application.timer(5000, new TimerRunnable() {
		public void run(final TimerTask timerTask) {
			
			if (end==true){ 
				timerTask.cancel();
				}
		
			for (Planet planets : LIST_PLANETS){
			if (!planets.getPlayer().isNeutral()){
				planets.addNumberSpaceShips(planets.getProductionRate());
			}
		
		}
		}
});
}
}