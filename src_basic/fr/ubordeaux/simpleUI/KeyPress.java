package fr.ubordeaux.simpleUI;

/**
 * Handled key pressed during a mouse event
 *
 */
public enum KeyPress {
	/**
	 * ConTRoL key
	 */
	CRTL,
	/**
	 * Alt key (the AltGrisDown() was not working for me so I use alt now..)
	 */
	ALTGR,
	/**
	 * Shift key
	 */
	SHIFT,
	/**
	 * Any other key; including no key pressed
	 */
	UNKNOWN;
}