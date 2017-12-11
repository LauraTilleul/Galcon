package simpleUIApp;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * Class used to save the game 
 * @author Laura
 *
 */
public class SaveGame {
	private FileOutputStream saveFile;
	private ObjectOutputStream save ;
	private boolean gameOver;
/** constructor : opening the file SaveObj and creating an ObjectOutPutStream from it  
 * 	
 */
	public SaveGame(){
		try{ 
		saveFile=new FileOutputStream("resources/SaveObj.sav");
		save = new ObjectOutputStream(saveFile);
		gameOver=false;
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		
	}
	/** method used to save the game whenever it is closed
	 * 
	 * @param end - a boolean indicating if the game is over
	 * @param LIST_PLANETS - the list of planets 
	 */
	public void saving(boolean end, ArrayList<Planet> LIST_PLANETS){
		try{ 
		save.reset();
		save.writeObject(end);
		save.writeObject(LIST_PLANETS);
		if (end==true){
			gameOver=true;
			save.close();
		}
		}
	
		catch(Exception exc){
		exc.printStackTrace();
		}
}
	public boolean isGameOver(){
		return gameOver;
	}
}
