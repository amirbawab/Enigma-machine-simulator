/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Plugboard {
	
	// Logger
	private Logger l = LogManager.getFormatterLogger(this);
		
	// Plugboard
	private int[] plugboard;
	
	// Constants
	public static final int UNPLUGGED = -1;
	public static final int INVALID = -2;
	
	/**
	 * Create plugboard
	 */
	public Plugboard() {
		
		// Create plugboard
		plugboard = new int[26];
		l.info("Plugboard created!");
		
		// Make all values unplugged
		resetPlugboard();
	}
	
	/**
	 * Reset plugboard
	 */
	public void resetPlugboard(){
		l.info("Unplugging all plugboard wires.");
		for (int wire = 0; wire < 26; wire++)
			this.plugboard[ wire ] = UNPLUGGED;
	}
	
	/**
	 * Set the plug boad
	 * @param plugboard
	 */
	public boolean insertPlugboardWire(char a, char b){
		
		// Check if a and b are between A and Z
		if(a < 'A' || a > 'Z' || b < 'A' || b > 'Z'){
			l.error("A wire should be plugged betweem two letters between A and Z.");
			return false;
		}
		
		// Check if a is equal to b
		if(a == b) {
			l.error("You should select two different letters.");
			return false;
		}
		
		// Plug wire from a to b
		this.plugboard[ a - 'A' ] = b - 'A';
		this.plugboard[ b - 'A' ] = a - 'A';
		l.info("Wire inserted between %c and %c.", a, b);
		return true;
	}
	
	/**
	 * Unset a wire from the plugboad
	 * @param wire
	 */
	public boolean removePlugboardWire(char a){
		
		// Check if a is between A and Z
		if(a < 'A' || a > 'Z') {
			l.error("You cannot unplug letters that are not between A and Z.");
			return false;
		}
		
		// If already unplugged
		if(getPlugboardOf(a) == UNPLUGGED) {
			l.error("%c is already unplugged. Nothing was done.", a);
			return false;
		}
		
		l.info("Unplugging wire from %c to %c", getPlugboardOf(a), getPlugboardOf(getPlugboardOf(a)));
		this.plugboard[ this.plugboard[ a - 'A' ] ] = UNPLUGGED;
		this.plugboard[ a - 'A' ] = UNPLUGGED;
		return true;
	}
	
	/**
	 * Get the linked char to `a`
	 * @param a
	 * @return int
	 */
	public int getPlugboardOf(int a){
		return this.plugboard[a];
	}
	
	/**
	 * Checks if a letter is occupied
	 * @param c
	 * @return boolean
	 */
	public boolean isPlugged(char c){
		return plugboard[c - 'A'] != UNPLUGGED;
	}
}
