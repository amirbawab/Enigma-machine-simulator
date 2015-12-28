/**
* Enigma Machine
* Coded by Amir El Bawab
* Date: 27 December 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package enigma.config;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import enigma.components.Reflector;
import enigma.components.Rotor;

public class ToolsTest {
	
	@Test
	public void testRegisterRotorAtPosition_EnigmaI_success() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertTrue(tools.registerRotorAtPosition(rotor, 1));
		
		// Expected
		List<String> list = new ArrayList<>();
		list.add(rotor.getName());
		
		assertEquals(tools.getRegisteredRotorsAtPosition(1).toString(), list.toString());
	}
	
	@Test
	public void testRegisterRotorAtPosition_EnigmaI_duplicate() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertTrue(tools.registerRotorAtPosition(rotor, 1));
		assertFalse(tools.registerRotorAtPosition(rotor, 1));
		
		// Expected
		List<String> list = new ArrayList<>();
		list.add(rotor.getName());
		
		assertEquals(tools.getRegisteredRotorsAtPosition(1).toString(), list.toString());
	}
	
	@Test
	public void testRegisterRotorAtPosition_EnigmaI_invalid_position() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertFalse(tools.registerRotorAtPosition(rotor, 4));
		
		// Expected
		List<String> list = new ArrayList<>();

		assertEquals(tools.getRegisteredRotorsAtPosition(1), list);
		
		// Register rotor
		tools.registerRotorAtPosition(rotor, 0);
		
		assertEquals(tools.getRegisteredRotorsAtPosition(1).toString(), list.toString());
	}
	
	@Test
	public void testRegisterRotorAtPosition_EnigmaI_null() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Register rotor
		assertFalse(tools.registerRotorAtPosition(null, 1));
		
		// Expected
		List<String> list = new ArrayList<>();

		assertEquals(tools.getRegisteredRotorsAtPosition(1).toString(), list.toString());
	}
	
	@Test
	public void testRegisterReflector_EnigmaI_success() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Create reflector
		Reflector reflector = new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
		
		// Register reflector
		assertTrue(tools.registerReflector(reflector));
		
		// Expected
		List<Reflector> list = new ArrayList<>();
		list.add(reflector);

		assertEquals(tools.getAllReflectors().values().toString(), list.toString());
	}
	
	@Test
	public void testRegisterReflector_EnigmaI_duplicate() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Create reflector
		Reflector reflector = new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
		
		// Register reflector
		assertTrue(tools.registerReflector(reflector));
		assertFalse(tools.registerReflector(reflector));
		
		// Expected
		List<Reflector> list = new ArrayList<>();
		list.add(reflector);

		assertEquals(tools.getAllReflectors().values().toString(), list.toString());
	}
	
	@Test
	public void testRegisterReflector_EnigmaI_null() {
		
		// Create tools
		Tools tools = new Tools(3);
		
		// Register reflector
		assertFalse(tools.registerReflector(null));
		
		// Expected
		List<Reflector> list = new ArrayList<>();

		assertEquals(tools.getAllReflectors().values().toString(), list.toString());
	}
	
	@Test
	public void testIsRotorAvailable_is_available() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertTrue(tools.registerRotorAtPosition(rotor, 1));
		assertTrue(tools.isRotorAvailable("I"));
	}
	
	@Test
	public void testIsRotorAvailable_is_not_available() {
		// Create tools
		Tools tools = new Tools(3);
		
		assertFalse(tools.isRotorAvailable("I"));
	}
	
	@Test
	public void testIsReflectorAvailable_is_available() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create reflector
		Reflector reflector = new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
		
		// Register reflector
		assertTrue(tools.registerReflector(reflector));
		assertTrue(tools.isReflectorAvailable("A"));
	}
	
	@Test
	public void testIsReflectorAvailable_is_not_available() {
		// Create tools
		Tools tools = new Tools(3);
		
		assertFalse(tools.isReflectorAvailable("A"));
	}
	
	@Test
	public void testGetAvailableRotors_before_use_success() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertTrue(tools.registerRotorAtPosition(rotor, 1));
		
		// Expected
		List<String> list = new ArrayList<>();
		list.add(rotor.getName());
		
		assertEquals(tools.getAvailableRotors().toString(), list.toString());
	}
	
	@Test
	public void testGetAvailableRotors_after_use_success() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create rotor
		Rotor rotor = new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", Arrays.asList(new Character[]{'Q'}), true);
		
		// Register rotor
		assertTrue(tools.registerRotorAtPosition(rotor, 1));
		
		// Install rotor
		assertTrue(tools.installRotorAtPosition("I", 1));
		
		// Expected
		List<String> list = new ArrayList<>();
		
		assertEquals(tools.getAvailableRotors().toString(), list.toString());
	}
	
	@Test
	public void testGetAvailableReflectors_before_use_success() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create reflector
		Reflector reflector = new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
		
		// Register reflector
		assertTrue(tools.registerReflector(reflector));
		
		// Expected
		List<String> list = new ArrayList<>();
		list.add(reflector.getName());

		assertEquals(tools.getAvailableReflectors().toString(), list.toString());
	}
	
	@Test
	public void testGetAvailableReflectors_after_use_success() {
		// Create tools
		Tools tools = new Tools(3);
		
		// Create reflector
		Reflector reflector = new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
		
		// Register reflector
		assertTrue(tools.registerReflector(reflector));

		// Install reflector
		tools.installReflector("A");
		
		// Expected
		List<Reflector> list = new ArrayList<>();

		assertEquals(tools.getAvailableReflectors().toString(), list.toString());
	}
	
	@Test
	public void testPositionToIndex() {
		
		// Create tools
		Tools tools = new Tools(3);
				
		Method method;
		try {
			method = Tools.class.getDeclaredMethod("positionToIndex", int.class);
			method.setAccessible(true);
			
			Object index = null;
			index = method.invoke(tools, 1);
			assertTrue(Integer.parseInt(index.toString()) == 2);
			
			index = method.invoke(tools, 2);
			assertTrue(Integer.parseInt(index.toString()) == 1);
			
			index = method.invoke(tools, 3);
			assertTrue(Integer.parseInt(index.toString()) == 0);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
