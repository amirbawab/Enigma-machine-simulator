/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma;

import java.util.HashMap;

import enigma.components.Reflector;
import enigma.components.Rotor;
import enigma.config.Config;

public class Enigma {
	
	// Machine configuration
	private Rotor rightRotor;
	private Rotor centerRotor;
	private Rotor leftRotor;
	private Reflector reflector;
	private int[] plugboard;
	
	// Available rotors to choose from
	public static final String[] I = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"};
	public static final String[] II = {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"};
	public static final String[] III = {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"};
	public static final String[] IV = {"ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"};
	public static final String[] V = {"VZBRGITYUPSDNHLXAWMJQOFECK", "Z"};
	
	// Available reflectors to choose from
	public static final String A = "EJMZALYXVBWFCRQUONTSPIKHGD";
	public static final String B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
	public static final String C = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
	
	
	
	// Create configuration
	Config config = new Config();
	
	// Rotors mapper
	HashMap<String, Rotor> rotors;
	
	/**
	 * Construct the machine
	 * @param left
	 * @param center
	 * @param right
	 * @param ref
	 */
	public Enigma(String[] left,String[] center,String[] right,String ref){
		
		// Load rotors
		rotors = config.loadRotors();
		
		// Check for correct rotor
		if(!correctRotor(left) || !correctRotor(center) || !correctRotor(right))
			throw new RuntimeException("Please choose a correct Rotor");
		
		// Check for correct reflector
		if(!correctReflector(ref))
			throw new RuntimeException("Please choose a correct Reflector");
		
		// Assign rotors and reflector
		this.leftRotor = new Rotor(left[0], left[1].charAt(0));
		this.centerRotor = new Rotor(center[0], center[1].charAt(0));
		this.rightRotor = new Rotor(right[0], right[1].charAt(0));
		this.reflector = new Reflector(ref);
		
		// Create plugboard
		plugboard = new int[26];
		
		// Reset plugboard
		resetPlugboard();
	}
	
	/**
	 * Check if the chosen Rotor is correct
	 * @param rotor
	 * @return boolean
	 */
	private boolean correctRotor(String[] rotor){
		return rotor == I || rotor == II || rotor == III || rotor == IV || rotor == V;
	}
	
	/**
	 * Check if the chosen Reflector is correct
	 * @param reflector
	 * @return boolean
	 */
	private boolean correctReflector(String reflector){
		return reflector == A || reflector == B || reflector == C;
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
		
		// Rotate Rotor left & center
		if (centerRotor.getNotch() == centerRotor.getRotorHead()){
			leftRotor.rotate();
			centerRotor.rotate();
		}

		// Rotate Rotor center
		if (rightRotor.getNotch() == rightRotor.getRotorHead())
			centerRotor.rotate();

		// Rotate Rotor right
		rightRotor.rotate();
		
		// Static wheel
		int input = inputC - 'A';
		
		// Pass by the plugboard
		if(plugboard[input] != -1)
			input = plugboard[input];

		// Start processing from the right wheel to left wheel
		int outOfrightRotor = rightRotor.getOutputOf(input);
		int outOfcenterRotor = centerRotor.getOutputOf(outOfrightRotor);
		int outOfleftRotor = leftRotor.getOutputOf(outOfcenterRotor);

		// Enter and exit the reflector
		int outOfReflector = reflector.getOutputOf(outOfleftRotor);

		// Start processing from left wheel to right wheel
		int inOfleftRotor = leftRotor.getInputOf(outOfReflector);
		int inOfcenterRotor = centerRotor.getInputOf(inOfleftRotor);
		int inOfrightRotor = rightRotor.getInputOf(inOfcenterRotor);
		
		// Pass by the plugboard
		if(plugboard[inOfrightRotor] != -1)
			inOfrightRotor = plugboard[inOfrightRotor];
		
		// Static wheel
		return (char) (inOfrightRotor + 'A');
	}
	
	/**
	 * Get left rotor
	 * @return left rotor
	 */
	public Rotor getLeftRotor(){
		return leftRotor;
	}
	
	/**
	 * Get center rotor
	 * @return left rotor
	 */
	public Rotor getCenterRotor(){
		return centerRotor;
	}
	
	/**
	 * Get right rotor
	 * @return right rotor
	 */
	public Rotor getRightRotor(){
		return rightRotor;
	}
	
	/**
	 * Get reflector
	 * @return reflector
	 */
	public Reflector getReflector(){
		return reflector;
	}
	
	/**
	 * Set the plug boad
	 * @param plugboard
	 */
	public void insertPlugboardWire(char a, char b){
		this.plugboard[ a - 'A' ] = b - 'A';
		this.plugboard[ b - 'A' ] = a - 'A';
	}
	
	/**
	 * Unset a wire from the plug boad
	 * @param wire
	 */
	public void removePlugboardWire(char a){
		this.plugboard[ this.plugboard[ a - 'A' ] ] = -1;
		this.plugboard[ a - 'A' ] = -1;
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
	 * Reset plugboard
	 */
	public void resetPlugboard(){
		for (int wire = 0; wire < 26; wire++)
			this.plugboard[ wire ] = -1;
	}
	
	/**
	 * Checks if a letter is occupied
	 * @param c
	 * @return boolean
	 */
	public boolean isPlugged(char c){
		return plugboard[c - 'A'] != -1;
	}
	
	/**
	 * Reset machine
	 */
	public void resetRotation(){
		leftRotor.reset();
		centerRotor.reset();
		rightRotor.reset();
	}
}
