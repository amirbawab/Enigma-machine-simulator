/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui.listener;

public interface EnigmaMenuListener {
	public void importFile(String text);
	public String exportFile();
	public void keyboardDisplay();
	public void textBoxDisplay();
}
