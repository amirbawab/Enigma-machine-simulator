/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.config.parser;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachinesJson {
	
	@JsonProperty("machines")
    public List<MachineJson> machines;
	
	public static class MachineJson {
		
		@JsonProperty("name")
	    public String name;
		
		@JsonProperty("date")
	    public String date;
		
		@JsonProperty("type")
	    public String type;
		
		@JsonProperty("rotors")
	    public List<MachineRotorJson> rotors;
		
		@JsonProperty("reflectors")
	    public List<String> reflectors;
	}
	
	public static class MachineRotorJson {
		
		@JsonProperty("position")
	    public int position;
		
		@JsonProperty("double_step")
	    public boolean double_step;
		
		@JsonProperty("name")
	    public List<String> name;
	}
}
