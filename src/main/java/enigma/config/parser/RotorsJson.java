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
public class RotorsJson {
	
	@JsonProperty("rotors")
    public List<RotorJson> rotors;
	
	public static class RotorJson {
		
		@JsonProperty("name")
	    public String name;
		
		@JsonProperty("date")
	    public String date;
		
		@JsonProperty("map")
	    public String map;
		
		@JsonProperty("rotate")
	    public boolean rotate;
		
		@JsonProperty("notch")
	    public List<Character> notch;
	}
}
