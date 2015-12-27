/**
 * Amir El Bawab
 * Date: 25 December 2015
 */

package enigma.config.parser;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReflectorsJson {
	
	@JsonProperty("reflectors")
    public List<ReflectorJson> rotors;
	
	public class ReflectorJson {
		
		@JsonProperty("name")
	    public String name;
		
		@JsonProperty("date")
	    public String date;
		
		@JsonProperty("model")
	    public String model;
		
		@JsonProperty("map")
	    public String map;
	}
}
