/**
 * Amir El Bawab
 * Date: 25 December 2015
 */

package enigma.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import enigma.components.Reflector;
import enigma.components.Rotor;
import enigma.config.parser.ReflectorsJson;
import enigma.config.parser.ReflectorsJson.ReflectorJson;
import enigma.config.parser.RotorsJson;

public class Config {
	
	// Files
	private final String ROTORS_JSON = "/rotors.json";
	private final String REFLECTORS_JSON = "/reflectors.json";
	
	// Logger
	Logger l = LogManager.getLogger();
	
	/**
	 * Load rotors from json file
	 * @return Hashmap of the rotors
	 */
	public HashMap<String, Rotor> loadRotors() {
		
		// Prepare hash map
    	HashMap<String, Rotor> rotorMapper = new HashMap<>();
		
		try {
			
			// Parse file
			ObjectMapper mapper = new ObjectMapper();
			InputStream file = getClass().getResourceAsStream(ROTORS_JSON);
        	RotorsJson rotorsContainer = mapper.readValue(file, RotorsJson.class);
        	
        	// Get rotors
        	for(RotorsJson.RotorJson rotor : rotorsContainer.rotors){
        		Rotor tmpRotor = new Rotor(rotor.map, rotor.notch);
        		rotorMapper.put(rotor.name, tmpRotor);
        		l.info(String.format("Rotor %s loaded successfully!", rotor.name));
        	}
        	
		} catch (JsonParseException e) {
			l.error(String.format("Failed to parse json file '%s': %s", ROTORS_JSON, e.getMessage()));
		} catch (JsonMappingException e) {
			l.error(String.format("Failed to map json file '%s': %s", ROTORS_JSON, e.getMessage()));
		} catch (IOException e) {
			l.error(String.format("Failed to load json file '%s': %s", ROTORS_JSON, e.getMessage()));
		}
		
		return rotorMapper;
	}
	
	/**
	 * Load reflectors from json file
	 * @return Hashmap of the rotors
	 */
	public HashMap<String, Reflector> loadReflectors() {
		
		// Prepare hash map
    	HashMap<String, Reflector> reflectorMapper = new HashMap<>();
		
		try {
			
			// Parse file
			ObjectMapper mapper = new ObjectMapper();
			InputStream file = getClass().getResourceAsStream(REFLECTORS_JSON);
        	ReflectorsJson reflectorsContainer = mapper.readValue(file, ReflectorsJson.class);
        	
        	// Get reflectors
        	for(ReflectorsJson.ReflectorJson reflector : reflectorsContainer.reflectors){
        		Reflector tmpReflector = new Reflector(reflector.map);
        		reflectorMapper.put(reflector.name, tmpReflector);
        		l.info(String.format("Reflector %s loaded successfully!", reflector.name));
        	}
        	
		} catch (JsonParseException e) {
			l.error(String.format("Failed to parse json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		} catch (JsonMappingException e) {
			l.error(String.format("Failed to map json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		} catch (IOException e) {
			l.error(String.format("Failed to load json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		}
		
		return reflectorMapper;
	}
}
