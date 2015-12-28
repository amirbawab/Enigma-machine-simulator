/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import enigma.components.Reflector;
import enigma.components.Rotor;
import enigma.config.parser.MachinesJson;
import enigma.config.parser.MachinesJson.MachineJson;
import enigma.config.parser.MachinesJson.MachineRotorJson;
import enigma.config.parser.ReflectorsJson;
import enigma.config.parser.ReflectorsJson.ReflectorJson;
import enigma.config.parser.RotorsJson;
import enigma.config.parser.RotorsJson.RotorJson;
import enigma.utils.ObjectUtils;

public class Loader {
	
	// Files
	private final String MACHINES_JSON = "/machines.json";
	private final String ROTORS_JSON = "/rotors.json";
	private final String REFLECTORS_JSON = "/reflectors.json";
	
	// Logger
	Logger l = LogManager.getLogger();
	
	/**
	 * Load tools for a specific machine
	 * @param name
	 * @return tools for a machine
	 */
	public Tools loadTools(String name) {
		
		// Parse file
		ObjectMapper mapper;
		InputStream file;
		MachinesJson machinesContainer = null;
		RotorsJson rotorsContainer = null;
		ReflectorsJson reflectorsContainer = null;
		
		// Load all enigma machines
		try {
			mapper = new ObjectMapper();
			file = getClass().getResourceAsStream(MACHINES_JSON);
	    	machinesContainer = mapper.readValue(file, MachinesJson.class);
		} catch (JsonParseException e) {
			l.error(String.format("Failed to parse json file '%s': %s", MACHINES_JSON, e.getMessage()));
		} catch (JsonMappingException e) {
			l.error(String.format("Failed to map json file '%s': %s", MACHINES_JSON, e.getMessage()));
		} catch (IOException e) {
			l.error(String.format("Failed to load json file '%s': %s", MACHINES_JSON, e.getMessage()));
		}

		// Load all rotors
    	try{
			mapper = new ObjectMapper();
			file = getClass().getResourceAsStream(ROTORS_JSON);
	    	rotorsContainer = mapper.readValue(file, RotorsJson.class);
    	} catch (JsonParseException e) {
			l.error(String.format("Failed to parse json file '%s': %s", ROTORS_JSON, e.getMessage()));
		} catch (JsonMappingException e) {
			l.error(String.format("Failed to map json file '%s': %s", ROTORS_JSON, e.getMessage()));
		} catch (IOException e) {
			l.error(String.format("Failed to load json file '%s': %s", ROTORS_JSON, e.getMessage()));
		}

    	// Load all reflectors
    	try{
			mapper = new ObjectMapper();
			file = getClass().getResourceAsStream(REFLECTORS_JSON);
	    	reflectorsContainer = mapper.readValue(file, ReflectorsJson.class);
    	} catch (JsonParseException e) {
			l.error(String.format("Failed to parse json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		} catch (JsonMappingException e) {
			l.error(String.format("Failed to map json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		} catch (IOException e) {
			l.error(String.format("Failed to load json file '%s': %s", REFLECTORS_JSON, e.getMessage()));
		}
    	
		// Prepare Tool
		Tools tools = null;
		
		// Add rotors to the enigma tools
		for(MachineJson machine : machinesContainer.machines) {
			
			// If machine found
			if(machine.name.equals(name)) {
				
				// Create tools
				tools = new Tools(machine.rotors.size());
				
				// Register rotors
				for(MachineRotorJson registerRotor : machine.rotors) {
					
					// Set double step
					tools.setDoubleStepAtPosition(registerRotor.position, registerRotor.double_step);
					
					// Loop on rotors
					for(String definedRotorName : registerRotor.name) {
					
						// Loop on rotors
						for(RotorJson definedRotor : rotorsContainer.rotors) {
						
							// If rotor found
							if(definedRotor.name.equals(definedRotorName)) {
								
								// Register rotor
								tools.registerRotorAtPosition(new Rotor(definedRotor.name, definedRotor.map, definedRotor.notch, definedRotor.rotate), registerRotor.position);
							}
						}
					}
				}
				
				// Register reflectors
				for(String registerReflector : machine.reflectors) {
					
					// Loop on reflectors
					for(ReflectorJson definedReflector : reflectorsContainer.reflectors) {
						
						// If reflector found
						if(definedReflector.name.equals(registerReflector)) {
							
							// Register reflector
							tools.registerReflector(new Reflector(definedReflector.name, definedReflector.map));
						}
					}
				}
			}
		}
		
		return tools;
	}
}
