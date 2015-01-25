/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */
package gui.listener;

public interface RotorListener {
	public void configure(
			String[] leftRotor, String[] centerRotor, String[] rightRotor, 
			char leftStart, char centerStart, char rightStart, 
			char leftRing, char centerRing, char rightRing, 
			String reflector);
}
