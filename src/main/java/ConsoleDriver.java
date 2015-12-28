/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

import enigma.Enigma;

public class ConsoleDriver {
	public static void main(String[] args) {
		
		// Create machine
		Enigma enigma = new Enigma("I");
		
		enigma.installRotorAtPosition("III", 1);
		enigma.installRotorAtPosition("II", 2);
		enigma.installRotorAtPosition("I", 3);

		enigma.installReflector("B");
		
		// Configure rotors
		enigma.getRotorAtPosition(1).setRotorHead('A');
		enigma.getRotorAtPosition(2).setRotorHead('B');
		enigma.getRotorAtPosition(3).setRotorHead('C');
		
		// Configure rings
		enigma.getRotorAtPosition(1).setRingHead('D');
		enigma.getRotorAtPosition(2).setRingHead('E');
		enigma.getRotorAtPosition(3).setRingHead('F');
		
		System.out.println(enigma.type("AMIR BAWAB"));
	}
}
