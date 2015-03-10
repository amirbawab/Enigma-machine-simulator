/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui.panels;

import enigma.Enigma;
import gui.listener.KeyboardListener;
import gui.listener.TypeListener;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TypewriterPanel extends JPanel{

	private KeyboardPanel keyboardPanel;
	private BulbPanel bulbPanel;
	private JSplitPane splitter;
	private Enigma enigma;
	private TypeListener typeListener;
	
	public TypewriterPanel(){
		
		// Set Layout
		setLayout(new BorderLayout());
		
		// Create panels
		keyboardPanel = new KeyboardPanel();
		bulbPanel = new BulbPanel();
		
		// Splitter
		splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,bulbPanel,keyboardPanel);
		splitter.setResizeWeight(0.50);
		splitter.setDividerSize(0);
		splitter.setContinuousLayout(true);
		splitter.setEnabled(false);
		splitter.setBorder(null);
		
		// Add keyboard listener
		keyboardPanel.setKeyboardListener(new KeyboardListener() {
			public void releaseAction(int c) {
				bulbPanel.bulbOff(c);
			}
			
			public int pressAction(char original) {
				int encoded = enigma.type("" + original).charAt(0) - 'A';
				bulbPanel.bulbOn(encoded);
				bulbPanel.getOutputField().setText(bulbPanel.getOutputField().getText() + (char)(encoded+'A'));
				typeListener.typeAction(); // refresh the states
				return encoded;
			}
		});
		
		// Add to panel
		add(splitter, BorderLayout.CENTER);
	}

	/**
	 * Set the enigma machine
	 * @param enigma
	 */
	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}
	
	/**
	 * Set type listener
	 * @param typeListener
	 */
	public void setTypeListener(TypeListener typeListener){
		this.typeListener = typeListener;
	}
	
	/**
	 * Clear input field
	 */
	public void clear(){
		enigma.resetRotation();
		typeListener.typeAction(); // refresh the states
		keyboardPanel.getInputField().setText("");
		bulbPanel.getOutputField().setText("");
	}
	
	/**
	 * Get output
	 * @return output
	 */
	public String getOutput(){
		return bulbPanel.getOutputField().getText();
	}
}