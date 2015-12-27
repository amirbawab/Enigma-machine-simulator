/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

import enigma.Enigma;

public class ConsoleDriver {
	public static void main(String[] args) {
		
		// Create machine
		Enigma enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.B);
		
		// Configure rotors
		enigma.getLeftRotor().setRotorHead('A');
		enigma.getCenterRotor().setRotorHead('B');
		enigma.getRightRotor().setRotorHead('C');
		
		// Configure rings
		enigma.getLeftRotor().setRingHead('D');
		enigma.getCenterRotor().setRingHead('E');
		enigma.getRightRotor().setRingHead('F');
		
		System.out.println(enigma.type("HELLO WORLD"));
		
	}
}
