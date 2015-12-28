/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enigma.components.Reflector;
import enigma.components.Rotor;

public class Tools {
	
	// Logger
	private Logger l = LogManager.getFormatterLogger(this);
		
	// All Rotors
	private Map<String, Rotor> allRotors;
	
	// All Reflectors
	private Map<String, Reflector> allReflectors;
	
	// Installed rotors
	private Rotor[] rotorsInstalled;
	
	// Double step
	private boolean[] rotorsDoubleStep;
	
	// Installed Reflector
	private Reflector reflectorInstalled;
	
	// Registered rotors
	private Map<Integer, List<String>> registeredRotorsAtPosition;
		
	/**
	 * Construct empty tool
	 * @param numberOfRotors
	 */
	public Tools(int numOfInstalledRotors) {
		
		// Initialize variables
		allRotors = new HashMap<>();
		allReflectors = new HashMap<>();
		rotorsInstalled = new Rotor[numOfInstalledRotors];
		rotorsDoubleStep = new boolean[numOfInstalledRotors];
		reflectorInstalled = null;
		registeredRotorsAtPosition = new HashMap<>();
	}
	
	/**
	 * Register rotor at a position.
	 * Only registered rotors can be installed at a given position.
	 * @param rotor
	 * @param position
	 * @return true if registered, false if position is not valid or rotor registered more than once
	 */
	public boolean registerRotorAtPosition(Rotor rotor, int position) {
		
		// Convert position to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d is not valid", position);
			return false;
		}
		
		// If rotor is null
		if(rotor == null) {
			l.error("Rotor is null.");
			return false;
		}
		
		// If not already in the list of all rotors, add it
		if(!allRotors.containsKey(rotor.getName())) {
			allRotors.put(rotor.getName(), rotor);
			l.info("Added Rotor %s to the list of all rotors", rotor.getName());
		}
		
		// If position not initialized yet
		if(registeredRotorsAtPosition.get(index) == null)
			registeredRotorsAtPosition.put(index, new ArrayList<String>());
		
		// If already registered
		if(registeredRotorsAtPosition.get(index).contains(rotor.getName())) {
			l.error("Cannot register Rotor %s more than once.", rotor.getName());
			return false;
		}
		
		// Register rotor in the given position
		l.info("Rotor %s registered at position %d.", rotor.getName(), position);
		registeredRotorsAtPosition.get(index).add(rotor.getName());
		return true;
	}
	
	/**
	 * Register reflector.
	 * Only registered reflectors can be installed.
	 * @param reflector
	 * @return true if registered, false if reflector registered more than once
	 */
	public boolean registerReflector(Reflector reflector) {
		
		// If reflector is null
		if(reflector == null) {
			l.error("Reflector is null.");
			return false;
		}
		
		// If not already in the list of all reflectors, add it
		if(allReflectors.containsKey(reflector.getName())) {
			l.error("Cannot register Reflector %s more than once.", reflector.getName());
			return false;
		}
		
		// If not found, add it
		allReflectors.put(reflector.getName(), reflector);
		l.info("Reflector %s registered.", reflector.getName());
		return true;
	}
	
	/**
	 * Check if a rotor is available and not installed
	 * @param rotorName
	 * @return true if rotor is available and not installed, otherwise false
	 */
	public boolean isRotorAvailable(String rotorName) {
		return getAvailableRotors().contains(rotorName);
	}
	
	/**
	 * Check if a reflector is available and not installed
	 * @param reflectorName
	 * @return true if reflector is available and not installed, otherwise false
	 */
	public boolean isReflectorAvailable(String reflectorName) {
		return getAvailableReflectors().contains(reflectorName);
	}
	
	/**
	 * Get all available rotors
	 */
	public List<String> getAvailableRotors() {
		
		// Prepare result
		List<String> tmpRotors = new ArrayList<>();
		
		// Loop on all rotors
		for(Rotor rotor : allRotors.values())
			
			// If rotor is not installed, add it as available
			if(!Arrays.asList(rotorsInstalled).contains(rotor))
				tmpRotors.add(rotor.getName());
		
		return tmpRotors;
	}
	
	/**
	 * Get all available reflectors
	 */
	public List<String> getAvailableReflectors() {
		List<String> tmpReflectors = new ArrayList<>();
		
		// Loop on all reflectors
		for(Reflector reflector : allReflectors.values())
			
			// If reflector is not installed, add it as available
			if(reflectorInstalled != reflector)
				tmpReflectors.add(reflector.getName());
		
		return tmpReflectors;
	}
	
	/**
	 * Convert position to index
	 * @param pos 1 to N <i>(N=number of rotors that can be installed)</i>
	 * @return -1 or 0 to N-1
	 */
	private int positionToIndex(int pos) {
		
		// If position is not valid
		if(pos < 1 || pos > rotorsInstalled.length)
			return -1;
		
		return rotorsInstalled.length - pos;
	}
	
	/**
	 * Remove rotor at specific position
	 * @param position 1 to N-1
	 * @return true if removed, false if nothing installed at that position or if position is not valid
	 */
	public boolean removeRotorAtPosition(int position) {
		
		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.");
			return false;
		} 

		// If a rotor is installed at this position
		if(rotorsInstalled[index] != null){
			l.info("Removing rotor %s installed at position %d.", rotorsInstalled[index].getName(), position);
			rotorsInstalled[index] = null;
			return true;
		}
		
		// Nothing to remove
		l.info("Nothing removed. No rotor is installed at position %d.", position);
		return false;
	}
	
	/**
	 * Get rotor name at a specific position
	 * @param position
	 * @return rotor name or null if not valid or not installed
	 */
	public String getRotorNameAtPosition(int position) {
		
		// Get rotor
		Rotor rotor = getRotorAtPosition(position);
		
		// If found
		if(rotor != null)
			return rotor.getName();
		
		return null;
	}
	
	/**
	 * Get registered rotors at a given position
	 * @param position 1 to N
	 * @return array of rotors name or null if position is not valid
	 */
	public List<String> getRegisteredRotorsAtPosition(int position) {
		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.", position);
			return null;
		}
		
		// If at least one rotor was registered at the given position
		if(registeredRotorsAtPosition.get(index) != null) {
			return registeredRotorsAtPosition.get(index);
		}
		
		// If no rotors are registered at the given position
		l.warn("No registered rotors found at position %d. Returning an empty result.", position);
		return new ArrayList<>(0);
	}
	
	/**
	 * Install rotor at position
	 * @param name
	 * @param position
	 * @return true if installed, otherwise false
	 */
	public boolean installRotorAtPosition(String name, int position) {
		
		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.", position);
			return false;
		}
		
		// Check if the rotor is already in use. If so, remove it
		for(int p=1; p <= rotorsInstalled.length; p++) {
			
			// If installed already
			if(getRotorNameAtPosition(p) != null && getRotorNameAtPosition(p).equals(name)) {
				
				// Remove it
				removeRotorAtPosition(p);
				break;
			}
		}
		
		// Check if rotor is available
		if(isRotorAvailable(name)) {
			l.info("Rotor %s is available", name);
		} else {
			l.error("Rotor %s does not exist", name);
			return false;
		}
			
		// If a rotor was already installed at that position
		if(getRotorNameAtPosition(position) != null)
			removeRotorAtPosition(position);
		
		l.info("Installing Rotor %s at position %d.", name, position);
		rotorsInstalled[index] = allRotors.get(name);
		return true;
	}
	
	/**
	 * Install a reflector
	 * @param reflectorName
	 * @return true if installed, otherwise false
	 */
	public boolean installReflector(String reflectorName) {
		
		// If reflector does not exist
		if(!isReflectorAvailable(reflectorName)) {
			l.error("Reflector %s does not exist.", reflectorName);
			return false;
		}
		
		l.info("Reflector %s is available.", reflectorName);
		
		// If there was a reflector installed before
		if(reflectorInstalled != null) {
			l.info("Removing reflector %s.", reflectorInstalled.getName());
		} 
		
		l.info("Installing reflector %s.", reflectorName);
		reflectorInstalled = allReflectors.get(reflectorName);
		return true;
	}
	
	/**
	 * Get rotor
	 * @param position 1 to N
	 * @return rotor or null
	 */
	public Rotor getRotorAtPosition(int position) {

		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.", position);
			return null;
		}
		
		// Get rotor
		return rotorsInstalled[index];
	}
	
	/**
	 * Check if should double step at a given position
	 * If double step is true, then the rotor should rotate when its head is at a notch.
	 * Note: double step value does not affect the rotation of the left rotor.
	 * @param position
	 * @return true if should double step
	 */
	public boolean doubleStepAtPosition(int position) {
		
		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.", position);
			return false;
		}
		
		// Return if double step
		return rotorsDoubleStep[index];
	}
	
	public void setDoubleStepAtPosition(int position, boolean value) {
		// Convert to index
		int index = positionToIndex(position);
		
		// If not valid
		if(index < 0){
			l.error("Position %d does not exist.", position);
			return;
		}
		
		// Set double step
		rotorsDoubleStep[index] = value;
	}

	/**
	 * Get all rotors
	 * @return all rotors as a map<name, object>
	 */
	public Map<String, Rotor> getAllRotors() {
		return allRotors;
	}

	/**
	 * Get all reflectors
	 * @return all reflectors as map<name, object>
	 */
	public Map<String, Reflector> getAllReflectors() {
		return allReflectors;
	}
	
	/**
	 * Get rotors installed
	 * @return array of installed rotors
	 */
	public Rotor[] getRotorsInstalled() {
		return rotorsInstalled;
	}

	/**
	 * Get reflector installed
	 * @return reflector installed
	 */
	public Reflector getReflectorInstalled() {
		return reflectorInstalled;
	}

	/**
	 * Get registered rotors at all positions in a map
	 * @return map of registered rotors at all positions
	 */
	public Map<Integer, List<String>> getRegisteredRotors() {
		return registeredRotorsAtPosition;
	}
	
	/**
	 * Checks if rotors and reflectors are installed
	 * @return true if tools are ready, otherwise false
	 */
	public boolean isReady() {
		for(int i=0; i < rotorsInstalled.length; i++)
			if(rotorsInstalled[i] == null)
				return false;
		return reflectorInstalled != null;
	}
}
