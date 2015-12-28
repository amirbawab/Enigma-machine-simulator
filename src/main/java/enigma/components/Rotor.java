/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.components;

import java.util.List;

public class Rotor {
	
	// Rotor wires, arrays will store the jump distance to get to the end of the wire
	private	int rotorOut[] = new int[26];
	private int rotorIn[] = new int[26];
	private int rotorHead;
	private int ringHead;
	private int rotate;
	private List<Character> notch;
	private boolean canRotate;
	
	// Rotor name
	private String name;
	
	/**
	 * Construct the rotor
	 * @param name
	 * @param rotor
	 * @param notch
	 */
	public Rotor(String name, String rotor, List<Character> notch, boolean canRotate){
		this.name = name;
		this.canRotate = canRotate;
		setRotor(rotor, notch);
	}
	
	/**
	 * Get rotor name
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Checks if the rotor can rotate
	 * @return true if rotor can rotate, otherwise false
	 */
	public boolean canRotate() {
		return this.canRotate;
	}
	
	/**
	 * Checks if the rotor head is at a notch
	 * @return true if rotor head is at a notch, otherwise false
	 */
	public boolean isHeadAtNotch() {
		return notch.contains(getRotorHead());
	}

	/**
	 * Get the index of the output of the input wire
	 * @param pos
	 * @return index
	 */
	public int getOutputOf(int pos){
		int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
		return (pos + rotorOut[ (pos + rotate + rotorRingDiff) % 26]) % 26; // Pos + Jump to mirror with the next rotor
	}
	
	/**
	 * Get the index of the input of the output wire
	 * @param pos
	 * @return index
	 */
	public int getInputOf(int pos){
		int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - ringHead + rotorHead;
		int posJump = pos - rotorIn[ (pos + rotate + rotorRingDiff) % 26];
		return posJump > 0 ? (posJump % 26) : ( 26 + posJump) % 26; // Pos - Jump to mirror with the next rotor
	}
	
	/**
	 * Get rotor notch
	 * @return notch array
	 */
	public char[] getNotch(){
		char[] notchChars = new char[notch.size()];
		for(int i = 0; i < notch.size(); i++)
			notchChars[i] = notch.get(i);
		return notchChars;
	}
	
	/**
	 * Get rotor head
	 * @return rotor head
	 */
	public char getRotorHead(){
		return (char) ('A' + (rotorHead + rotate) % 26);
	}
	
	/**
	 * Get ring head
	 * @return ring head
	 */
	public char getRingHead(){
		return (char) ('A' + (ringHead + rotate) % 26);
	}
	
	/**
	 * Rotate the rotor
	 */
	public boolean rotate(){
		
		// If can't rotate
		if(!canRotate())
			return false;
		
		rotate = (rotate + 1) % 26;
		return true;
	}
	
	/**
	 * Set the rotor head position and reset rotations
	 * @param c
	 */
	public void setRotorHead(char c){
		if(c < 'A' || c > 'Z')
			throw new RuntimeException("Only upper case letters allowed!");
		rotorHead = c - 'A';
		rotate = 0;
	}
	
	/**
	 * Set the rotor head position and don't reset the rotation
	 * @param c
	 */
	public void setRingHead(char c){
		if(c < 'A' || c > 'Z')
			throw new RuntimeException("Only upper case letters allowed!");
		ringHead = c - 'A';
	}
	
	/**
	 * Set rotor
	 * @param rotor
	 */
	public void setRotor(String map, List<Character> notch){
		this.notch = notch;
		for (int i = 0; i < 26; i++){
			int from = (char) ('A' + i);
			int to = map.charAt(i);
			rotorOut[i] = from < to ? to - from : (26 - (from - to)) % 26;
			rotorIn[ (i + rotorOut[i]) % 26] = rotorOut[i];
		}
	}
	
	/**
	 * Get an out wire connection (Used in GUI)
	 * @return rotorOut
	 */
	public int getAnOutWire(int pos){
		return getOutputOf(pos);
	}
	
	/**
	 * Get an in wire connection (Used in GUI)
	 * @return rotorOut
	 */
	public int getAnInWire(int pos){
		return getInputOf(pos);
	}
	
	/**
	 * Reset rotor
	 */
	public void reset(){
		rotate = 0;
	}
}
