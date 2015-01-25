/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui.listener;

public interface KeyboardListener {
	public int pressAction(char c);
	public void releaseAction(int c);
}
