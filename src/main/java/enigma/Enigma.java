/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enigma.components.Plugboard;
import enigma.components.Rotor;
import enigma.config.Loader;
import enigma.config.Tools;

public class Enigma {
	
	// Logger
	Logger l = LogManager.getFormatterLogger(this);
		
	// Create configuration
	private Loader loader = new Loader();
	
	// Tools
	private Tools tools;
	
	// Plugboard
	private Plugboard plugboard;
	
	// Name
	private String name;
	
	/**
	 * Construct the machine
	 * @param rotors Rotors names in the array (Should match with the JSON rotors names)
	 * @param ref Reflect name (Should match with a JSON reflector name)
	 */
	public Enigma(String name){
		
		// Store machine name
		this.name = name;
		
		// Load enigma tools
		tools = loader.loadTools(name);
		
		// Init plugboard
		plugboard = new Plugboard();
	}
	
	/**
	 * Type text to encode
	 * @param text
	 * @return encoded text
	 * */
	public String type(String text){
		String output = "";
		for (int i=0; i<text.length(); i++){
			
			// Allow upper case letter
			if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
				output += rotorsEncryption(text.charAt(i));
			
			// Allow white space and new line
			else if(text.charAt(i) == ' ' || text.charAt(i) == '\n')
				output += text.charAt(i);
			
			// If other characters
			else
				throw new RuntimeException("Only upper case letters allowed!");
		}
		return output;
	}
	
	/**
	 * Rotor encryption
	 * @param input
	 * @return encoded input
	 */
	private char rotorsEncryption(char inputC){
		
		// Check if machine is ready
		if(!tools.isReady()) {
			l.error("Enigma is not ready yet, please make sure all rotor and reflector are installed.");
			return '?';
		}
		
		// Store the number of installed rotors
		int numOfInstalledRotors = tools.getRotorsInstalled().length;
		
		// Rotate rotors if they should ...
		for(int p= numOfInstalledRotors; p > 1; p--) {
			
			// Store current rotor
			Rotor currentRotor = tools.getRotorAtPosition(p);
			Rotor previousRotor = tools.getRotorAtPosition(p - 1);
			
			// If its right rotor head is at a notch, or if (rotor is at a notch and it's not the most left one and its left rotor can rotate), then rotate
			if(previousRotor.isHeadAtNotch() || (currentRotor.isHeadAtNotch() && tools.doubleStepAtPosition(p))){
				l.debug("Rotate rotor %s.", currentRotor.getName());
				currentRotor.rotate();
			}
		}
		
		// Always rotate rotor at position 1
		l.debug("Rotate rotor %s.", tools.getRotorAtPosition(1).getName());
		tools.getRotorAtPosition(1).rotate();
		
		// Static wheel
		int input = inputC - 'A';
		
		// Prepare output
		int output = input;
		
		// Pass by the plugboard
		l.debug("======================");
		l.debug("Plugboard wire RTL from: %c", 'A' + output);
		if(plugboard.getPlugboardOf(output) != Plugboard.UNPLUGGED)
			output = plugboard.getPlugboardOf(output);
		l.debug("To: %c", 'A' + output);
		l.debug("======================");
		
		// Start processing from the right wheel to left wheel
		for(int p = 1; p <= numOfInstalledRotors; p++){
			l.debug("======================");
			l.debug("Rotor wire RTL from: %c", 'A' + output);
			output = tools.getRotorAtPosition(p).getOutputOf(output);
			l.debug("To: %c", 'A' + output);
			l.debug("======================");
		}
		
		// Enter and exit the reflector
		l.debug("======================");
		l.debug("Reflector wire from: %c", 'A' + output);
		output = tools.getReflectorInstalled().getOutputOf(output);
		l.debug("To: %c", 'A' + output);
		l.debug("======================");
		
		// Start processing from left wheel to right wheel
		for(int p = numOfInstalledRotors; p >= 1; p--){
			l.debug("======================");
			l.debug("Rotor wire LTR from: %c", 'A' + output);
			output = tools.getRotorAtPosition(p).getInputOf(output);
			l.debug("To: %c", 'A' + output);
			l.debug("======================");
		}
		
		// Pass by the plugboard
		l.debug("======================");
		l.debug("Plugboard wire LTR from: %c", 'A' + output);
		if(plugboard.getPlugboardOf(output) != Plugboard.UNPLUGGED)
			output = plugboard.getPlugboardOf(output);
		l.debug("To: %c", 'A' + output);
		l.debug("======================");
		
		// Static wheel
		return (char) (output + 'A');
	}
	
	/**
	 * Get rotor at specific position
	 * @param position 1 to N
	 * @return rotor or null
	 */
	public Rotor getRotorAtPosition(int position) {
		return tools.getRotorAtPosition(position);
	}
	
	/**
	 * Install a rotor from the registered ones
	 * @return true if installed, otherwise false
	 */
	public boolean installRotorAtPosition(String rotorName, int position) {
		return tools.installRotorAtPosition(rotorName, position);
	}
	
	/**
	 * Install a reflector from the registered ones
	 * @return true if installed, otherwise false
	 */
	public boolean installReflector(String reflectorName) {
		return tools.installReflector(reflectorName);
	}
	
	/**
	 * Get machine tools
	 * Access all tools
	 * @return tools
	 */
	public Tools getTools() {
		return tools;
	}
	
	/**
	 * Reset machine rotors
	 */
	public void resetRotation(){
		
		// Reset all rotors
		for(int p = 1; p <= tools.getRotorsInstalled().length; p++) {
			l.info("Resetting Rotor %s at position %d.", tools.getRotorAtPosition(p).getName(), p);
			tools.getRotorAtPosition(p).reset();
		}
	}
	
	/**
	 * Reset rotors and plugboard
	 */
	public void resetMachine() {
		l.info("Started resetting machine configurations.");
		resetRotation();
		plugboard.resetPlugboard();
	}
	
	/**
	 * Print information about the current configuration of the Enigma machine
	 * @return Information about the Enigma machine
	 */
	public String toString() {
		String output = "";
		output += "\n====================\n";
		output += "ENIGMA " + name + "\n";
		output += "====================\n";
		for(int p=1; p <= tools.getRotorsInstalled().length; p++){
			Rotor rotor = tools.getRotorAtPosition(p);
			output += "Rotors installed at position " + p + ": " + rotor.getName() + "\n";
			output += "\tHead: " + rotor.getRotorHead() + "\n";
			output += "\tRing: " + rotor.getRingHead() + "\n";
		}
		output += "Rotors available: " + tools.getAvailableRotors() + "\n";
		output += "====================\n";
		if(tools.getReflectorInstalled() != null)
			output += "Reflector installed: " + tools.getReflectorInstalled().getName() + "\n";
		else
			output += "No reflector installed!\n";
		output += "Reflector available: " + tools.getAvailableReflectors() + "\n";
		output += "====================\n";
		return output;
	}
}