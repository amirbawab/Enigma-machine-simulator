/**
 * Amir El Bawab
 * Date: 25 December 2015
 */

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
		
		@JsonProperty("model")
	    public String model;
		
		@JsonProperty("map")
	    public String map;
		
		@JsonProperty("notch")
	    public char notch;
	}
}
