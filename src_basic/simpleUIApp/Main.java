package simpleUIApp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import fr.ubordeaux.simpleUI.*;
/**
 * Main class, starting the game by calling the run method of Application
 * @author Laura
 *
 */
public class Main {
	static int width=1000;
	static int height=500;
	
	public static void main(String[] args) {
		ArrayList<Item> testItemList = new ArrayList<Item>();
		
/* Try and restore the previous game */
		LoadGame game= new LoadGame();
		if (!game.gameOver()){
			
			/* asking the user if he/she actually wants to load the previous game or start a new one */
			System.out.println("Do you want to load previous Game ? type YES, else a new game will start");
			Scanner scanner = new Scanner(System.in);
			String rep = scanner.nextLine();
			
			/*if yes, testItemList will just be the list of planets restore from previous game */ 
			if (rep.toUpperCase().equals("YES")){
			testItemList.addAll(game.getPreviousGame());
			}
		}
	
	/* if the game was over or if user wants to play a new one, starting a new game*/
		if (testItemList.isEmpty()){
		
		Random random = new Random();
		
		/* creating an ArrayList of different sizes for the planets */ 
		ArrayList<Integer> sizeP= new ArrayList<Integer>();
		sizeP.add(50);
		sizeP.add(60);
		sizeP.add(70);
		sizeP.add(80);
		
	/* asking the user if he/she actually wants to play or just watch an AI VS AI game */ 
		Player player1 = new AIPlayer("Lory", Color.BLUE); // initializing player1 
		
		System.out.println("Do you want to play ? Type YES to be player, else it will launch a game AI VS AI");
		Scanner scanner = new Scanner(System.in);
		String resp = scanner.nextLine();
	
		/* if yes, player1 is a new human player, the user types its name */	
		if (resp.toUpperCase().equals("YES")){
		System.out.println("Type your username : ");
		String username = scanner.nextLine();
		scanner.close();
		player1 = new HumanPlayer(username,Color.BLUE, false);
		}
		
		/* player2 is an AI in any cases */
		Player player2 = new AIPlayer("Dexter", Color.GREEN);
		
		/* adding players' planets */
		Collections.shuffle(sizeP);
		int size=sizeP.get(0);
		
		Planet planet1=new Planet(random.nextInt(width-size)+size/2, random.nextInt(height-size)+size/2,size,player1);
		
		Collections.shuffle(sizeP);
		size=sizeP.get(0);
		
		Planet planet2=new Planet(random.nextInt(width-size)+size/2, random.nextInt(height-size)+size/2,size,player2);
		
		/* putting a bigger distance between those 2 planets or the game could end too quickly */
		while ((planet1.getLocation()).distance(planet2.getLocation()) < 400){
			Collections.shuffle(sizeP);
			size=sizeP.get(0);
			planet2=new Planet(random.nextInt(width-size)+size/2, random.nextInt(height-size)+size/2,size,player2);
		}
		
		testItemList.add(planet1);
		testItemList.add(planet2);
		
		/* adding 8 neutral planets, creating a human-like player, but neutral*/
		Player neutral= new HumanPlayer("Nobody",Color.WHITE, true);
		for (int i=0 ; i<7 ; i++){
			Collections.shuffle(sizeP);
			size=sizeP.get(0);
			Planet planet= new Planet(random.nextInt(width-size)+size/2, random.nextInt(height-size)+size/2,size,neutral);
			while (!(planet.minimumDistance(testItemList))){
				planet= new Planet(random.nextInt(width-size)+size/2, random.nextInt(height-size)+size/2,size,neutral);
			}
			testItemList.add(planet);
		}
		}
		
		Manager manager = new Manager();
		Run r = new Run(width, height);
		
		
		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(testItemList, manager, r);
	}
}