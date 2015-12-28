/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtils {
	
	/**
	 * Convert java object to json
	 * @param object
	 * @return json string
	 */
	public static String objectToJSON(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			//Convert object to JSON string
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
