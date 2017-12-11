package simpleUIApp;

import javax.swing.JFrame;

import fr.ubordeaux.simpleUI.KeyHandler;

public class KeyListener implements KeyHandler {

	private JFrame mFrame;

	public KeyListener(JFrame frame) {
		mFrame = frame;
	}

	@Override
	public JFrame getParentFrame() {
		return mFrame;
	}

	@Override
	public void keyPressed(char arg0) {

	}

	@Override
	public void keyReleased(char arg0) {

	}
/**
 * using '+' and '-' to put at maximum/ minimum the percent of ships sent by planets, for each planet of the arena.
 * the max being 100%  and the min 25%
 */
	@Override
	public void keyTyped(char arg0) {
		switch (arg0) {
		case '+':
			System.out.println("+ has been typed : every planet is playing 100% of its Ships!");
			for (Planet pl : Run.LIST_PLANETS){
				pl.setPercentShipsAttack(100);
			}
			break;
		case '-':
			System.out.println("- has been typed: every planet is playing 25% of its Ships");
			for (Planet pl : Run.LIST_PLANETS){
				pl.setPercentShipsAttack(25);
			}
			break;
		default:
			break;
		}
	}

}