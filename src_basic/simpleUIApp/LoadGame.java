package simpleUIApp;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
/**
 * This class is used to try and load the previous game saved if a game has been exit before game over
 * @author Laura
 *
 */
public class LoadGame {
	
	private FileInputStream saveFile;
	private ObjectInputStream save ;
	private boolean end;
	ArrayList<Item>previousGame;
	
	/**
	 * the constructor opens the file 'SaveObj', tries to read from it and to restore the previous game attributes : 
	 * the list of planets and a boolean telling if the game was over
	 */
	public LoadGame(){
		try{	
			 saveFile = new FileInputStream("resources/SaveObj.sav");
			 save = new ObjectInputStream(saveFile);
			 end= (boolean)save.readObject();
			 previousGame= (ArrayList<Item>) save.readObject();	
			 save.close();
			}
			catch(Exception exc){
			exc.printStackTrace();
			}
	}
	/**
	 * checking if the restored game was over
	 * @return true if the game was over
	 */
	public boolean gameOver(){
		return end;
	}
	/**
	 * getting the list of planets of the previous game
	 * @return the arrayList of planets saved when closing the game
	 */
	public ArrayList<Item> getPreviousGame(){	
		return previousGame;
	}
}
