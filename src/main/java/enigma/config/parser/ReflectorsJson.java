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
public class ReflectorsJson {
	
	@JsonProperty("reflectors")
    public List<ReflectorJson> reflectors;
	
	public static class ReflectorJson {
		
		@JsonProperty("name")
	    public String name;
		
		@JsonProperty("date")
	    public String date;
		
		@JsonProperty("map")
	    public String map;
	}
}
