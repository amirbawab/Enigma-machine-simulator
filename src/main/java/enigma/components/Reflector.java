/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.components;
public class Reflector {
	
	// Reflector wires, arrays will store the jump distance to get to the end of the wire
	private int rotorOut[] = new int[26];
	
	// Reflector name
	private String name;
	
	/**
	 * Construct the reflector
	 * @param reflector
	 */
	public Reflector(String name, String reflector){
		this.name = name;
		setReflector(reflector);
	}
	
	/**
	 * Get index of the output of the input wire
	 * @param pos
	 * @return index
	 */
	public int getOutputOf(int pos){
		return (pos + rotorOut[pos]) % 26;
	}
	
	/**
	 * Get an out wires
	 * @return rotorOut
	 */
	public int getAnOutWire(int pos){
		return (rotorOut[pos] + pos ) % 26;
	}
	
	/**
	 * Get reflector name
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the reflector
	 * @param reflector
	 */
	public void setReflector(String reflector){
		for (int i = 0; i < 26; i++){
			int from = (char) ('A' + i);
			int to = reflector.charAt(i);
			rotorOut[i] = from < to ? to - from : (26 - (from - to)) % 26;
		}
	}
}
